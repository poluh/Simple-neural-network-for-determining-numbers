import logic.network.Network;

public class Main {
    public static void main(String[] args) {
        Network network = new Network("011101111001111");
        System.out.println(network.getResult());
    }
}
