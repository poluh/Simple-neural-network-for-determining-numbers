package network;

import logic.network.Network;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

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

    @Test
    void checkNormalNumber() {
        for (int i = 0; i < 10; i++) {
            Network network = new Network(normalNumbers.get(i));
            assertEquals(i, network.getResult());
        }
    }

    @Test
    void check() {
        assertEquals(9, new Network("111101111001111").getResult());
    }

}
