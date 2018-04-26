package logic.network;

import logic.image.ImagePreprocessor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Network {

    private double result;
    private static List<Double> neuronsWeight = new ArrayList<>();
    public static int NUMBER_OF_NEURON = 225;
    public static int IMAGE_SIDE_FOR_SIGNAL = 15;

    static {
        List<String> neuronsWeightInFile;
        try {
            Pattern pattern = Pattern.compile("\\d");
            neuronsWeightInFile =
                    Files.readAllLines(Paths.get("src/com/main/java/logic/network/NeuronsWeight.txt"));

            neuronsWeightInFile.forEach(string -> {
                if (!pattern.matcher(string).matches() && !string.isEmpty()) {
                    neuronsWeight.add(Double.valueOf(string.replace("    ", "")));
                }
            });
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
    }

    private void detecting(double[] values) {
        List<Layer> layers = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            List<Neuron> neurons = new ArrayList<>();
            for (int j = 0; j < NUMBER_OF_NEURON; ++j) {
                neurons.add(new Neuron(neuronsWeight.get(j + (i * NUMBER_OF_NEURON))));
            }
            layers.add(new Layer(neurons));
        }
        for (Layer layer : layers) {
            layer.addAllSignals(values);
            if (layer.getResult() > 0.5) {
                this.result = layers.indexOf(layer);
                break;
            }
        }
    }

    public Network(BufferedImage image) {
        ImagePreprocessor imagePreprocessor = new ImagePreprocessor(image);
        imagePreprocessor.cropImage();
        imagePreprocessor.resize(imagePreprocessor.getImage(), 50, 50);
        /*try {
            imagePreprocessor.resize(imagePreprocessor.getImage(), 50, 50);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = "src/com/main/java/logic/network/education/imageForEducation/9/";
        String imageName = "0.png";
        while (new File(path + imageName).exists()) {
            int imageNum = Integer.parseInt(imageName.split("\\.")[0]) + 1;
            imageName = imageNum + ".png";
        }
        imagePreprocessor.saveImage(imagePreprocessor.getImage(), path + imageName);*/
        detecting(imagePreprocessor.getImageSignals());
    }

    public double getResult() {
        return result;
    }
}
