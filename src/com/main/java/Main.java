import logic.network.Network;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(new Network(ImageIO.read(new File("/Users/sergey/Desktop/4.jpg"))).getResult());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
