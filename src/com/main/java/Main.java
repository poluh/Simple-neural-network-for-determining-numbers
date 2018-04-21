import logic.network.Network;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(new Network(ImageIO.read(new File("/Users/sergey/Desktop/4.png"))).getResult());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Pattern pattern = Pattern.compile("tail\\s+((-c\\s+\\d+)|(-n\\s+\\d+))?(-o\\s+\\w+)?(\\s+\\w+)*");
    }
}
