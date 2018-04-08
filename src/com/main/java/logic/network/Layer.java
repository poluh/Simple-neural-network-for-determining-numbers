package logic.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Layer {
    private List<Neural> neurals;

    Layer(int amount, int[] values) {
        List<Neural> neurals = new ArrayList<>();
        for (int i = 0; i < amount; ++i) {
            Neural neural = new Neural();
            neural.setInput(values[i]);
        }
        this.neurals = neurals;
    }

    public Layer(int amount) {
        List<Neural> neurals = new ArrayList<>();
        for (int i = 0; i < amount; ++i) {
            Neural neural = new Neural();
            neurals.add(neural);
        }
        this.neurals = neurals;
    }

    public Layer(List<Neural> neurals) {
        this.neurals = neurals;
    }

    public void addAllSignals(int[] signals) {
        for (int i = 0; i < this.neurals.size(); i++) {
            Neural neural = this.neurals.get(i);
            neural.setInput(signals[i]);
        }
    }

    public String getAllWeight() {
        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < this.neurals.size(); i++) {
            Neural neural = this.neurals.get(i);
            answer.append(i).append("=").append(neural.getWeight()).append("\n");
        }
        return answer.toString();
    }

    public double getResult() {
        return 1 / (1 + Math.exp(-0.5 * neurals.stream().mapToDouble(Neural::getOutput).sum()));
    }

    public List<Neural> getNeurals() {
        return neurals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Layer layer = (Layer) o;
        return Objects.equals(neurals, layer.neurals);
    }

    @Override
    public int hashCode() {

        return Objects.hash(neurals);
    }
}
