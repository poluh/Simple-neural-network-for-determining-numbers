package logic.network;

import java.util.List;

public class Neural {

    private double weight = 0;
    private int input;

    Neural() {}

    Neural(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getInput() {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }

    public double getOutput() {
        return input * weight;
    }
}
