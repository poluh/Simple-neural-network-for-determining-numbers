package logic.network;

import java.util.List;

public class Neuron {

    private double weight = 0;
    private double input;

    Neuron() {}

    Neuron(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getInput() {
        return input;
    }

    public void setInput(double input) {
        this.input = input;
    }

    public double getOutput() {
        return input * weight;
    }
}
