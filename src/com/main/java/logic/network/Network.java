package logic.network;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Network {

    private double result;
    private static List<Double> neuronsWeight = new ArrayList<>();
    static {
        List<String> neuronsWeightInFile;
        try {
            Pattern pattern = Pattern.compile("\\d");
            neuronsWeightInFile =
                    Files.readAllLines(Paths.get("src/com/main/java/logic/network/NeuronsWeight.txt"));

            neuronsWeightInFile.forEach(string -> {
                if (!pattern.matcher(string).matches()) {
                    neuronsWeight.add(Double.valueOf(string.replace("    ", "")));
                }
            });
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
    }

    public Network(String number) {
        List<Layer> layers = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            List<Neuron> neurons = new ArrayList<>();
            for (int j = 0; j < 15; ++j) {
                neurons.add(new Neuron(neuronsWeight.get(j + (i * 15))));
            }
            layers.add(new Layer(neurons));
        }
        int[] values = stringToIntArr(number);
        for (Layer layer : layers) {
            layer.addAllSignals(values);
            if (layer.getResult() > 0.5) {
                this.result = layers.indexOf(layer);
                break;
            }
        }
    }

    public Network(int[] values, List<Layer> layers) {
        for (Layer layer : layers) {
            layer.addAllSignals(values);
            if (layer.getResult() > 0.5) {
                this.result = layers.indexOf(layer);
                break;
            }
        }
    }

    private int[] stringToIntArr(String string) {
        int[] values = new int[15];
        char[] chars = string.toCharArray();
        Arrays.setAll(values, i -> Integer.parseInt(String.valueOf(chars[i])));
        return values;
    }

    public double getResult() {
        return result;
    }
}
