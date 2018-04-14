package logic.network.education;

import logic.image.ImagePreprocessor;
import logic.network.Layer;
import logic.network.Network;
import logic.network.Neuron;

import java.util.Random;

public class Education {

    private double[][] imagesSignals = new double[10][15];
    private ImagePreprocessor[] imagePreprocessors = new ImagePreprocessor[10];
    private String commonPath = "src/com/main/java/logic/network/education/imageForEducation/";
    private Network network;

    Education() {
        for (int i = 0; i < 10; i++) {
            imagePreprocessors[i] = new ImagePreprocessor(commonPath + i + ".png", 15);
            imagesSignals[i] = imagePreprocessors[i].getImageSignals();
        }
    }

    private void training() {
        for (int i = 0; i < 10; i++) {
            Layer layer = new Layer(15);
            for (int j = 0; j < 300000000; j++) {

                int randomNumber = new Random().nextInt(10);
                double[] imageSignals = imagesSignals[randomNumber];
                layer.addAllSignals(imageSignals);

                if (randomNumber == i) {
                    if (layer.getResult() < 0.55) setW(false, layer);
                } else {
                    if (layer.getResult() > 0.45) setW(true, layer);
                }

            }
            System.out.println(i);
            System.out.println(layer.getAllWeight());
        }
    }

    private static void setW(boolean isDecrease, Layer layer) {
        layer.getNeurons().forEach((Neuron neuron) -> {
            if (neuron.getInput() != 0) {
                neuron.setWeight(neuron.getWeight() + (isDecrease ? -0.00001 : 0.00001));
            }
        });
    }

    public static void main(String[] args) {
        Education education = new Education();
        education.training();
    }

}
