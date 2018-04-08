package logic.network;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Network {

    private double result;
    private List<Layer> layers = new ArrayList<>();
    private static List<String> neuronsWeightInFile;
    private static List<Double> neuronsWeight = new ArrayList<>();
    static {
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
            neuronsWeightInFile = Collections.emptyList();
        }
    }

    public Network(String number) {
        List<Layer> layers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<Neural> neurals = new ArrayList<>();
            for (int j = 0; j < 15; j++) {
                neurals.add(new Neural(neuronsWeight.get(j + (i * 10))));
            }
            layers.add(new Layer(neurals));
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
