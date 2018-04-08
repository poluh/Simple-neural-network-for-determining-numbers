import logic.network.Network;

public class Main {
    public static void main(String[] args) {
        Network network = new Network("111000111000111");
        System.out.println(network.getResult());
    }
}
