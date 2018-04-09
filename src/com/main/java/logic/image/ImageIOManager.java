package logic.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageIOManager {

    private int[] numberMatrix = new int[15];
    private List<BufferedImage> subImages = new ArrayList<>();

    public ImageIOManager(String path) {
        File imageFile = new File(path);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert bufferedImage != null;
        int subHeight = bufferedImage.getHeight() / 5;
        int subWidth = bufferedImage.getWidth() / 3;
        System.out.println(subHeight + " " + subWidth);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                subImages.add(bufferedImage.getSubimage(i * subWidth, j * subHeight, subWidth, subHeight));
            }
        }

        final int[] i = {0};
        subImages.forEach(image -> {
            try {
                ImageIO.write(image, "PNG", new File("image" + i[0]));
                i[0]++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void cropImage(BufferedImage bufferedImage) {
        int[][] allPixels = allImagePixels(bufferedImage);
    }

    private int[][] allImagePixels(BufferedImage bufferedImage) {
        int[][] allPixels = new int[bufferedImage.getWidth()][bufferedImage.getHeight()];

        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {
                allPixels[i][j] = bufferedImage.getRGB(i, j);
            }
        }
        return allPixels;
    }

    public int[] getNumberMatrix() {
        return numberMatrix;
    }
}
