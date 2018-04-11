package logic.image;

import logic.image.Geometry.Line;
import logic.image.Geometry.Point;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageIOManager {

    private int[] numberMatrix = new int[15];
    private int[][] allPixels;
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

        this.image = cropImage();

        try {
            ImageIO.write(this.image, "PNG", new File("image.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int subHeight = image.getHeight() / 5;
        int subWidth = image.getWidth() / 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                subImages.add(image.getSubimage(i * subWidth, j * subHeight, subWidth, subHeight));
            }
        }
    }

    private BufferedImage cropImage() {
        Point leftTopCropPoint = new Point(findAnchorPoint(Course.LEFT).x, findAnchorPoint(Course.TOP).y);
        Point leftBottomCropPoint = new Point(findAnchorPoint(Course.LEFT).x, findAnchorPoint(Course.BOTTOM).y);
        Point rightTopCropPoint = new Point(findAnchorPoint(Course.RIGHT).x, findAnchorPoint(Course.TOP).y);

        int cropWidth = leftTopCropPoint.distance(rightTopCropPoint);
        int cropHeight = leftTopCropPoint.distance(leftBottomCropPoint);
        return image.getSubimage(leftTopCropPoint.x, leftTopCropPoint.y, cropWidth, cropHeight);
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

    public int[] getNumberMatrix() {
        return numberMatrix;
    }
}
