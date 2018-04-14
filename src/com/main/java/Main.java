import logic.image.ImagePreprocessor;
import logic.network.Network;

public class Main {
    public static void main(String[] args) {
        ImagePreprocessor imagePreprocessor = new ImagePreprocessor("/Users/sergey/Desktop/123123.png",
                15);

        Network network = new Network(imagePreprocessor.getImageSignals());
        System.out.println(network.getResult());
    }
}
