package logic.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Network {

    private double result;

    public Network(String number) {
        this.result = 0;
        int[] values = stringToIntArr(number);
        while (result < 0.5) {
            result = new Layer(15, values).getResult();
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
