import logic.image.Geometry.Line;
import logic.image.Geometry.Point;
import logic.image.ImageIOManager;
import logic.network.Network;

public class Main {
    public static void main(String[] args) {
        ImageIOManager imageIOManager = new ImageIOManager("/Users/sergey/Desktop/123.jpg");
        Network network = new Network("111001001001111");
        System.out.println(network.getResult());
    }
}
