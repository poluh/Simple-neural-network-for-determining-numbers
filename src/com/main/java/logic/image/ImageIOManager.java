package logic.image;

import logic.image.Geometry.Point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class ImageIOManager {

    private int[] numberMatrix = new int[15];
    private int[][] allPixels;
    private List<Integer> allPixelsArr;
    private List<BufferedImage> subImages = new ArrayList<>();
    private BufferedImage image;

    public ImageIOManager(String path) {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new IllegalArgumentException("Error open image");
        }

        assert image != null;
        this.image = image;
        this.allPixels = allImagePixels();
        toGrayImage();
        this.image = binarizaid();
        this.image = cropImage();
        try {
            ImageIO.write(this.image, "PNG", new File("bin.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        createSubImages();
    }



    private void createSubImages() {
        int subHeight = image.getHeight() / 5;
        int subWidth = image.getWidth() / 3;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                subImages.add(image.getSubimage(j * subWidth, i * subHeight, subWidth, subHeight));
            }
        }
        AtomicInteger i = new AtomicInteger();
        subImages.forEach(image -> {
            i.getAndIncrement();
            try {
                ImageIO.write(image, "PNG", new File("image" + i + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private BufferedImage cropImage() {
        Point topPoint = findAnchorPoint(Course.TOP);
        Point leftPoint = findAnchorPoint(Course.LEFT);
        Point leftTopCropPoint = new Point(leftPoint.x, topPoint.y);
        Point leftBottomCropPoint = new Point(leftPoint.x, findAnchorPoint(Course.BOTTOM).y);
        Point rightTopCropPoint = new Point(findAnchorPoint(Course.RIGHT).x, topPoint.y);

        int cropWidth = leftTopCropPoint.distance(rightTopCropPoint);
        int cropHeight = leftTopCropPoint.distance(leftBottomCropPoint);
        return this.image.getSubimage(leftTopCropPoint.x, leftTopCropPoint.y, cropWidth, cropHeight);
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

        Point resultPoint = new Point(-1, -1);

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
        return resultPoint;
    }

    private Point isCorrectedPoint(int i, int j, Course course) {
        boolean courseLeftOrRight = course == Course.LEFT || course == Course.RIGHT;
        int xSupportPixels = courseLeftOrRight ? i : j;
        int ySupportPixels = courseLeftOrRight ? j : i;
        int topLeftPixel = allPixels[xSupportPixels - 1][ySupportPixels - 1];
        int bottomRightPixel = allPixels[xSupportPixels + 1][ySupportPixels + 1];
        if (topLeftPixel - bottomRightPixel != 0) {
            return new Point(xSupportPixels, ySupportPixels);
        }
        return new Point(-1, -1);
    }

    private int[][] allImagePixels() {
        int[][] allPixels = new int[this.image.getWidth()][this.image.getHeight()];

        for (int i = 0; i < this.image.getWidth(); i++) {
            for (int j = 0; j < this.image.getHeight(); j++) {
                allPixels[i][j] = this.image.getRGB(i, j);
            }
        }
        return allPixels;
    }

    private void toGrayImage() {
        for (int i = 0; i < this.image.getWidth(); i++) {
            for (int j = 0; j < this.image.getHeight(); j++) {
                Color pixelColor = new Color(this.image.getRGB(i, j));
                int rgb = (pixelColor.getRed() + pixelColor.getGreen() + pixelColor.getBlue()) / 3;
                Color newPixelColor = new Color(rgb, rgb, rgb);
                this.image.setRGB(i, j, newPixelColor.getRGB());
            }
        }
    }



    private BufferedImage binarizaid() {
        int treshold = searchTresholdBinarizaid();
        for (int i = 0; i < this.image.getWidth(); i++) {
            for (int j = 0; j < this.image.getHeight(); j++) {
                if (this.image.getRGB(i, j) > treshold) {
                    this.image.setRGB(i, j, new Color(255, 255, 255).getRGB());
                } else {
                    this.image.setRGB(i, j, new Color(0, 0, 0).getRGB());
                }
            }
        }
        return this.image;
    }

    private int searchTresholdBinarizaid() {
        int maxBrightness = 0;
        int minBrightness = 255;
        for (int i = 0; i < this.image.getWidth(); i++) {
            for (int j = 0; j < this.image.getHeight(); j++) {
                int currentBrightness = allPixels[i][j];
                maxBrightness = currentBrightness > maxBrightness ? currentBrightness : maxBrightness;
                minBrightness = currentBrightness < minBrightness ? currentBrightness : minBrightness;
            }
        }
        int threshold = thresholdCounting(maxBrightness, minBrightness);
        threshold = maxBrightness - threshold;

        return threshold;
    }

    private int[] createBarChart(int maxBrightness, int minBrightness) {
        int barChartSize = maxBrightness - minBrightness + 1;
        int[] barChart = new int[barChartSize];
        for (int i = 0; i < this.image.getHeight() * this.image.getWidth(); i++) {
            int x = i < this.image.getWidth() ? i : i % this.image.getWidth();
            int y = x == i ? 0 : i / this.image.getWidth();
            barChart[allPixels[x][y] - minBrightness]++;
        }
        return barChart;
    }

    private int thresholdCounting(int maxBrightness, int minBrightness) {
        int[] barChart = createBarChart(maxBrightness, minBrightness);
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

    public int[] getNumberMatrix() {
        return numberMatrix;
    }
}
