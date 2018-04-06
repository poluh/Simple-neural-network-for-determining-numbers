package logic.network;

import java.util.ArrayList;
import java.util.List;

public class Layer {
    private List<Neural> neurals = new ArrayList<>();

    Layer(int amount, int[] values) {
        List<Neural> neurals = new ArrayList<>();
        for (int i = 0; i < amount; ++i) {
            Neural neural = new Neural();
            neural.setInput(values[i]);
        }
        this.neurals = neurals;
    }

    public double getResult() {
        return 1 / Math.exp(-0.5 * this.neurals.stream().mapToDouble(Neural::getOutput).sum());
    }
}
