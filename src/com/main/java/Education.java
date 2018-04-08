import logic.network.Layer;
import logic.network.Network;
import logic.network.Neural;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Education {
    private List<Layer> layers;

    private Education(List<Layer> layers) {
        this.layers = layers;
    }

    private static List<int[]> numbers = Arrays.asList(
            new int[]{1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1},
            new int[]{0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
            new int[]{1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1},
            new int[]{1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1},
            new int[]{1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1},
            new int[]{1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1},
            new int[]{1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1},
            new int[]{1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
            new int[]{1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
            new int[]{1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1});

    private static List<int[]> zero = Arrays.asList(
            new int[]{0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0},
            new int[]{1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
            new int[]{1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0},
            new int[]{1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1});
    private static List<int[]> one = Arrays.asList(
            new int[]{0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            new int[]{0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
            new int[]{0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1});
    private static List<String> two = Arrays.asList(
            "111000111000111",
            "111001010100111",
            "101001111100101");
    private List<String> three = Arrays.asList(
            "111000111000111",
            "011000011000011",
            "111010111010111");
    private List<String> four = Arrays.asList(
            "001011101111001",
            "101101111001000");
    private static List<String> five = Arrays.asList(
            "111100111000111",
            "111100010001111",
            "111100011001111",
            "110100111001111",
            "110100111001011",
            "111100101001111");
    private List<String> six = Arrays.asList(
            "111100000101111",
            "110100111101111",
            "000100111101111");
    private List<String> seven = Arrays.asList(
            "111000000001001",
            "110001001001000",
            "011001001001001" +
                    "111001000000001");
    private List<String> eight = Arrays.asList(
            "111000111000111",
            "101101111101111",
            "111100110101111" +
                    "111101110101111");
    private List<String> nine = Arrays.asList(
            "111100111001111",
            "101101111001111",
            "111101111000111");

    //private List<List<String>> numbersForTest = Arrays.asList(zero, one, two, three, four, five, six, seven, eight, nine);



    private void education() {
        List<String> allWeight = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Layer layer = layers.get(i);
            for (int j = 0; j < 10000; j++) {
                int randomNum = new Random().nextInt(10);
                int[] number = numbers.get(randomNum);
                layer.addAllSignals(number);

                if (randomNum == i && layer.getResult() < 0.55) {
                    changeWeinght(layer, false);
                } else if (layer.getResult() > 0.45) {
                    changeWeinght(layer, true);
                }
            }
            allWeight.add(layer.getAllWeight());
        }
        System.out.println(allWeight);
    }


    private void changeWeinght(Layer layer, boolean reduce) {
        for (Neural neural : layer.getNeurals()) {
            if (neural.getInput() != 0) {
                double newWeight = neural.getWeight();
                if (reduce) {
                    newWeight -= 0.0001;
                } else {
                    newWeight += 0.0001;
                }
                neural.setWeight(newWeight);

            }
        }
    }

    public static void main(String[] args) {
        List<Layer> layers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            layers.add(new Layer(15));
        }
        Education education = new Education(layers);
        education.education();
        System.out.println(layers.get(1).getAllWeight());
        for (int[] ints : numbers) {
            Network network = new Network(ints, layers);
            System.out.println(network.getResult());
        }
    }
}
