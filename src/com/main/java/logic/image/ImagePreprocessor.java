package logic.image;


import logic.image.Geometry.Point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


public class ImagePreprocessor {

    private BufferedImage image;
    private List<BufferedImage> subImages;
    private int[][] allImagePixels;
    private double[] imageSignals;
    private int numberOfWhitePixels = 0;
    private int numberOfBlackPixels = 0;
    private static double OBJECT_RATIO = 0.25;
    private static int WHITE_RGB = new Color(255, 255, 255).getRGB();
    private static int BLACK_RGB = new Color(0, 0, 0).getRGB();

    private void create(BufferedImage image, int numberOfNeurons) {
        this.image = image;
        this.imageSignals = new double[numberOfNeurons];
        allImagePixels();
        toGrayImage(this.image);
        allImagePixels();
        cropImage(this.image);
        allImagePixels();
        binarizaid(this.image);
        allImagePixels();
        cropImage(this.image);
        createSubImages();
    }

    ImagePreprocessor(BufferedImage image, int numberOfNeurons) {
        create(image, numberOfNeurons);
    }

    public ImagePreprocessor(String path, int numberOfNeurons) {
        try {
            create(ImageIO.read(new File(path)), numberOfNeurons);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error open image");
        }
    }

    public void createSubImages() {
        int subHeight = image.getHeight() / 5;
        int subWidth = image.getWidth() / 3;
        subImages = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                subImages.add(image.getSubimage(j * subWidth, i * subHeight, subWidth, subHeight));
            }
        }
    }

    private void toGrayImage(BufferedImage image) {
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Color pixelColor = new Color(allImagePixels[i][j]);
                int rgb = (pixelColor.getRed() + pixelColor.getGreen() + pixelColor.getBlue()) / 3;
                Color newPixelColor = new Color(rgb, rgb, rgb);
                image.setRGB(i, j, newPixelColor.getRGB());
            }
        }
        this.image = image;
    }

    public void cropImage(BufferedImage image) {
        Point topPoint = findAnchorPoint(Course.TOP);
        Point leftPoint = findAnchorPoint(Course.LEFT);
        Point leftTopCropPoint = new Point(leftPoint.x, topPoint.y);
        Point leftBottomCropPoint = new Point(leftPoint.x, findAnchorPoint(Course.BOTTOM).y);
        Point rightTopCropPoint = new Point(findAnchorPoint(Course.RIGHT).x, topPoint.y);

        int cropWidth = leftTopCropPoint.distance(rightTopCropPoint);
        int cropHeight = leftTopCropPoint.distance(leftBottomCropPoint);
        this.image = image.getSubimage(leftTopCropPoint.x, leftTopCropPoint.y, cropWidth, cropHeight);
    }

    private enum Course {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT;
    }


    private Point findAnchorPoint(Course course) {
        int imageHeight = image.getHeight() - 2;
        int imageWidth = image.getWidth() - 2;

        int firstLimiter = course == Course.RIGHT || course == Course.LEFT ? imageWidth : imageHeight;
        int secondLimiter = course == Course.TOP || course == Course.BOTTOM ? imageWidth : imageHeight;

        if (course == Course.LEFT || course == Course.TOP) {
            for (int i = 1; i < firstLimiter; i++) {
                for (int j = 1; j < secondLimiter; j++) {
                    Point answerPoint = isCorrectedPoint(i, j, course);
                    if (!answerPoint.equals(new Point(-1, -1))) {
                        return answerPoint;
                    }
                }
            }
        } else {
            for (int i = firstLimiter; i >= 1; i--) {
                for (int j = secondLimiter; j >= 1; j--) {
                    Point answerPoint = isCorrectedPoint(i, j, course);
                    if (!answerPoint.equals(new Point(-1, -1))) {
                        return answerPoint;
                    }
                }
            }
        }
        throw new IllegalArgumentException("Point not found");
    }

    private Point isCorrectedPoint(int i, int j, Course course) {
        boolean courseLeftOrRight = course == Course.LEFT || course == Course.RIGHT;
        int xSupportPixels = courseLeftOrRight ? i : j;
        int ySupportPixels = courseLeftOrRight ? j : i;
        int topLeftPixel = allImagePixels[xSupportPixels - 1][ySupportPixels - 1];
        int bottomRightPixel = allImagePixels[xSupportPixels + 1][ySupportPixels + 1];
        if (topLeftPixel - bottomRightPixel != 0) {
            return new Point(xSupportPixels, ySupportPixels);
        }
        return new Point(-1, -1);
    }

    public void binarizaid(BufferedImage image) {
        int treshold = searchTresholdBinarizaid(image);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                if (allImagePixels[i][j] > treshold) {
                    image.setRGB(i, j, WHITE_RGB);
                } else {
                    image.setRGB(i, j, BLACK_RGB);
                }
            }
        }
    }

    private int searchTresholdBinarizaid(BufferedImage image) {
        int maxBrightness = 0;
        int minBrightness = 255;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int currentBrightness = allImagePixels[i][j];
                maxBrightness = Math.max(currentBrightness, maxBrightness);
                minBrightness = Math.min(currentBrightness, minBrightness);
            }
        }
        return maxBrightness - thresholdCounting(maxBrightness, minBrightness, image);
    }

    private int[] createBarChart(int maxBrightness, int minBrightness, BufferedImage image) {
        int barChartSize = maxBrightness - minBrightness + 1;
        int[] barChart = new int[barChartSize];
        for (int i = 0; i < image.getHeight() * image.getWidth(); i++) {
            int x = (i < image.getWidth()) ? i : (i % image.getWidth());
            int y = (x == i) ? 0 : (i / image.getWidth());
            barChart[allImagePixels[x][y] - minBrightness]++;
        }
        return barChart;
    }

    private int thresholdCounting(int maxBrightness, int minBrightness, BufferedImage image) {
        int[] barChart = createBarChart(maxBrightness, minBrightness, image);
        int sumAllColumnHeights = 0;
        int sumAllCHAndMiddle = 0;
        for (int i = 0; i <= maxBrightness - minBrightness; i++) {
            sumAllColumnHeights += barChart[i];
            sumAllCHAndMiddle += i * barChart[i];
        }

        double maxSigma = -1;
        int threshold = 0;
        int alpha = 0;
        int beta = 0;
        for (int i = 0; i < maxBrightness - minBrightness; i++) {
            alpha += i * barChart[i];
            beta += barChart[i];
            double probability = (double) beta / sumAllColumnHeights;
            double intermediateMiddleSum = (double) alpha / beta -
                    (double) (sumAllCHAndMiddle - alpha) /
                            (sumAllColumnHeights - beta);
            double sigma = probability * (1 - probability) * intermediateMiddleSum * intermediateMiddleSum;

            if (sigma > maxSigma) {
                maxSigma = sigma;
                threshold = i;
            }
        }
        return threshold;
    }


    private void allImagePixels() {
        int[][] allPixels = new int[image.getWidth()][image.getHeight()];

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                allPixels[i][j] = image.getRGB(i, j);
            }
        }
        this.allImagePixels = allPixels;
    }

    public void saveImage(BufferedImage bufferedImage) {
        saveImage(bufferedImage, "image.jpg");
    }

    public void saveImage(BufferedImage image, String fileName) {
        try {
            ImageIO.write(image, "JPG", new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public List<BufferedImage> getSubImages() {
        return subImages;
    }

    public double[] getImageSignals() {
        imageSignals = new double[subImages.size()];
        IntStream.range(0, subImages.size()).forEach(i -> {
            imageSignals[i] = imageCoefficien(subImages.get(i));
        });

        return imageSignals;
    }

    private double imageCoefficien(BufferedImage image) {
        double backgroundColor = mainColor(image);
        double objectColor = (numberOfWhitePixels == backgroundColor) ? numberOfBlackPixels : numberOfWhitePixels;
        return objectColor / backgroundColor;
    }

    private int mainColor(BufferedImage image) {
        int whitePixels = 0;
        int blackPixels = 0;

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                if (image.getRGB(i, j) == WHITE_RGB) {
                    whitePixels++;
                } else {
                    blackPixels++;
                }
            }
        }
        numberOfWhitePixels = whitePixels;
        numberOfBlackPixels = blackPixels;
        return whitePixels > blackPixels ? whitePixels : blackPixels;
    }
}
