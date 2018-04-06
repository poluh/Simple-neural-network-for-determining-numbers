import logic.network.Network;

public class Main {
    public static void main(String[] args) {
        Network network = new Network("1111100110011111");
        System.out.println(network.getResult());
    }
}
