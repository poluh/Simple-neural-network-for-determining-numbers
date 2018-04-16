package logic.network.education;

import logic.image.ImagePreprocessor;
import logic.network.Layer;
import logic.network.Network;
import logic.network.Neuron;

import java.util.Random;

public class Education {

    private double[][][] imagesSignals = new double[10][10][60];
    private ImagePreprocessor[] imagePreprocessors = new ImagePreprocessor[10];
    private String commonPath = "src/com/main/java/logic/network/education/imageForEducation/";
    private Network network;

    private Education() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                imagePreprocessors[i] = new ImagePreprocessor(String.format("%s%d/%d%d.png", commonPath, i, i, j),
                        60);
                System.out.println(String.format("%s%d/%d%d.png", commonPath, i, i, j));
                imagesSignals[i][j] = imagePreprocessors[i].getImageSignals();
            }
        }
    }

    private void training() {
        for (int i = 0; i < 10; i++) {
            Layer layer = new Layer(60);
            for (int j = 0; j < 3000000; j++) {
                for (int l = 0; l < 10; l++) {
                    int randomNumber = new Random().nextInt(10);
                    double[][] imageSignals = imagesSignals[randomNumber];
                    layer.addAllSignals(imageSignals[l]);

                    if (randomNumber == i) {
                        if (layer.getResult() < 0.55) setW(false, layer);
                    } else {
                        if (layer.getResult() > 0.45) setW(true, layer);
                    }
                }
            }
            System.out.println(i);
            System.out.println(layer.getAllWeight());
        }
    }

    private static void setW(boolean isDecrease, Layer layer) {
        layer.getNeurons().forEach((Neuron neuron) -> {
            if (neuron.getInput() != 0) {
                neuron.setWeight(neuron.getWeight() + (isDecrease ? -0.000001 : 0.000001));
            }
        });
    }

    public static void main(String[] args) {
        Education education = new Education();
        education.training();
    }

}
