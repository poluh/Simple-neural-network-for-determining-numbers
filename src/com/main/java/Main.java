import logic.image.ImagePreprocessor;
import logic.network.Network;

public class Main {
    public static void main(String[] args) {

        /*String path = "/Users/sergey/Desktop/test/";

        ImagePreprocessor imagePreprocessor = new ImagePreprocessor("/Users/sergey/Desktop/INFINITY.jpg", 15);
        Network network = new Network(imagePreprocessor.getImageSignals());
        System.out.println(network.getResult());
        */

        ImagePreprocessor imagePreprocessor = new ImagePreprocessor("/Users/sergey/Desktop/Simple-neural-network-for-determining-numbers/src/com/main/java/logic/network/education/imageForEducation/8/85.png", 60);
        imagePreprocessor.saveImage(imagePreprocessor.getImage());
    }
}
