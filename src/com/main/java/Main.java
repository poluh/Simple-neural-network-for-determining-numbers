import logic.image.Geometry.Line;
import logic.image.Geometry.Point;
import logic.image.ImagePreprocessor;
import logic.network.Network;

import java.awt.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ImagePreprocessor imagePreprocessor = new ImagePreprocessor("/Users/sergey/Desktop/1111111111.jpg",
                15);
        System.out.println(Arrays.toString(imagePreprocessor.getImageSignals()));
        Network network = new Network("011001011001111");
        System.out.println(network.getResult());
        /*System.out.println(Arrays.toString(new int[12]));
        System.out.println(new Color(255, 255, 255).getRGB());
        System.out.println(new Color(0, 0, 0).getRGB());*/
    }
}
