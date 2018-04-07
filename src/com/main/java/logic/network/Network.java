package logic.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Network {

    private double result;
    private List<Layer> layers = new ArrayList<>();

    public Network(String number) {
        this.result = 0;
        int[] values = stringToIntArr(number);
        int i = 0;
    }

    public Network(String number, List<Layer> layers) {
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
