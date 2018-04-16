package network;

import logic.image.ImagePreprocessor;
import logic.network.Network;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NetworkTest {

    private List<String> normalNumbers =
            Arrays.asList(
                    "111101101101111",
                    "001001001001001",
                    "111001111100111",
                    "111001111001111",
                    "101101111001001",
                    "111100111001111",
                    "111100111101111",
                    "111001001001001",
                    "111101111101111",
                    "111101111001111");

    private List<String> zero = Arrays.asList(
            "010101101101010",
            "111101000000111",
            "111101101101000",
            "111101000000111");
    private List<String> one = Arrays.asList(
            "001001000000001",
            "001011001001001",
            "001001001000001");
    private List<String> two = Arrays.asList(
            "111001010100111",
            "111001011100110",
            "101001111100101");
    private List<String> three = Arrays.asList(
            "011001111001011",
            "111010111001111");
    private List<String> four = Arrays.asList(
            "001101111001001",
            "101101111001000");
    private List<String> five = Arrays.asList(
            "111100111000111",
            "111100010001111",
            "110100111001111",
            "110100111001011");
    private List<String> six = Arrays.asList(
            "111000111101111",
            "111100110101111",
            "000100111101111");
    private List<String> seven = Arrays.asList(
            "111000000001001",
            "110001001001000");
    private List<String> eight = Arrays.asList(
            "111001111101111",
            "101101111101111",
            "111101110101111");
    private List<String> nine = Arrays.asList(
            "011101111001111",
            "111101111001111",
            "111101111001001");

    private List<List<String>> testSample = Arrays.asList(zero, one, two, three, four, five, six, seven, eight, nine);
    private String commonPath = "src/com/main/java/logic/network/education/imageForEducation/";

    @Test
    void checkNormalNumber() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < new Random().nextInt(10); j++) {
                ImagePreprocessor imagePreprocessor = new ImagePreprocessor(String.format("%s%d/%d%d.png", commonPath, i, i, j),
                        60);
                Network network = new Network(imagePreprocessor.getImageSignals());
                assertEquals(i, network.getResult());
                System.out.println(i + "" + j);
            }
        }
    }
}
