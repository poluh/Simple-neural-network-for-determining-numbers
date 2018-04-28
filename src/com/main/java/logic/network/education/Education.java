package logic.network.education;

import logic.image.ImagePreprocessor;
import logic.network.Layer;
import logic.network.Network;
import logic.network.Neuron;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Education {

    private int[][][] imagesSignals = new int[10][50][Network.NUMBER_OF_NEURON];
    private ImagePreprocessor[] imagePreprocessors = new ImagePreprocessor[10];
    private String commonPath = "src/com/main/java/logic/network/education/imageForEducation/";
    private String[] allWeights;

    private Education() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 50; j++) {
                imagePreprocessors[i] = new ImagePreprocessor(String.format("%s%d/%d.png", commonPath, i, j));
                System.out.println(String.format("%s%d/%d.png", commonPath, i, j));
                imagesSignals[i][j] = imagePreprocessors[i].getImageSignals();
            }
        }
    }

    private void training() throws IOException {
        Thread[] threads = new Thread[10];
        IntStream.range(0, 10).forEach(i -> {
            threads[i] = new Thread(() -> checkNumber(i));
            threads[i].start();
            System.out.println("Thread " + i + " is started");
        });

        waitThreads(threads);

        Files.write(new File("weights.txt").toPath(), Arrays.asList(allWeights));
        System.out.println("Education is completed");
    }

    private void waitThreads(Thread... threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkNumber(int numberForCheck) {
        Layer layer = new Layer(Network.NUMBER_OF_NEURON);
        for (int j = 0; j < 30000000; j++) {
            int randomNumber = new Random().nextInt(10);
            int randomVariant = new Random().nextInt(50);
            int[][] imageSignals = imagesSignals[randomNumber];
            layer.addAllSignals(imageSignals[randomVariant]);

            if (randomNumber == numberForCheck) {
                if (layer.getResult() < 0.55) setW(false, layer);
            } else {
                if (layer.getResult() > 0.45) setW(true, layer);
            }
        }

        System.out.println("Layer " + numberForCheck + " is completed");
        allWeights[numberForCheck] = String.valueOf(numberForCheck) + "\n" + layer.getAllWeight();
    }

    private static void setW(boolean isDecrease, Layer layer) {
        layer.getNeurons().forEach((Neuron neuron) -> {
            if (neuron.getInput() != 0) {
                neuron.setWeight(neuron.getWeight() + (isDecrease ? -0.00000000001 : 0.00000000001));
            }
        });
    }

    public static void main(String[] args) {
        Education education = new Education();
        try {
            education.training();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
