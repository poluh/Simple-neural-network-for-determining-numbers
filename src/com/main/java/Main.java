import logic.image.Geometry.Line;
import logic.image.Geometry.Point;
import logic.image.ImageIOManager;
import logic.network.Network;

public class Main {
    public static void main(String[] args) {
        System.out.println(new Line(new Point(2, 3), new Point(13, 3)).intersectionOfLines(
                new Line(new Point(1, 1), new Point(1, 8))
        ));
        //ImageIOManager imageIOManager = new ImageIOManager("/Users/sergey/Desktop/Untitled.png");
    }
}
