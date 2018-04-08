import logic.network.Network;

public class Main {
    public static void main(String[] args) {
        Network network = new Network("001001001001001");
        System.out.println(network.getResult());
    }
}
