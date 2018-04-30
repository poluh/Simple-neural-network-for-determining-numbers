import logic.network.education.Education;

import java.io.IOException;

public class Main {

    // Just save for history
    /*static int magic(double x) {
        return (int) ((((double)
                72 * (x - 1) * (x - 2) * (x - 3) * (x - 4) * (x - 5) * (x - 6) * (x - 7) *
                (x - 8) * (x - 9) * (x - 10) * (x - 11)) / -39916800.000000) + (((x - 0) *
                101 * (x - 2) * (x - 3) * (x - 4) * (x - 5) * (x - 6) * (x - 7) * (x - 8) *
                (x - 9) * (x - 10) * (x - 11)) / 3628800.000000) + (((x - 0) * (x - 1) * 108 *
                (x - 3) * (x - 4) * (x - 5) * (x - 6) * (x - 7) * (x - 8) * (x - 9) * (x - 10) *
                (x - 11)) / -725760.000000) + (((x - 0) * (x - 1) * (x - 2) * 108 * (x - 4) *
                (x - 5) * (x - 6) * (x - 7) * (x - 8) * (x - 9) * (x - 10) * (x - 11)) / 241920.000000) +
                (((x - 0) * (x - 1) * (x - 2) * (x - 3) * 111 * (x - 5) * (x - 6) * (x - 7) * (x - 8) *
                        (x - 9) * (x - 10) * (x - 11)) / -120960.000000) + (((x - 0) * (x - 1) * (x - 2) *
                (x - 3) * (x - 4) * 44 * (x - 6) * (x - 7) * (x - 8) * (x - 9) * (x - 10) *
                (x - 11)) / 86400.000000) + (((x - 0) * (x - 1) * (x - 2) * (x - 3) * (x - 4) *
                (x - 5) * 32 * (x - 7) * (x - 8) * (x - 9) * (x - 10) * (x - 11)) / -86400.000000) +
                (((x - 0) * (x - 1) * (x - 2) * (x - 3) * (x - 4) * (x - 5) * (x - 6) * 75 * (x - 8) *
                        (x - 9) * (x - 10) * (x - 11)) / 105060.000000) + (((x - 0) * (x - 1) * (x - 2) *
                (x - 3) * (x - 4) * (x - 5) * (x - 6) * (x - 7) * 97 * (x - 9) * (x - 10) *
                (x - 11)) / -221920.000000) + (((x - 0) * (x - 1) * (x - 2) * (x - 3) * (x - 4) * (x - 5) *
                (x - 6) * (x - 7) * (x - 8) * 116 * (x - 10) * (x - 11)) / 785760.000000) + (((x - 0) *
                (x - 1) * (x - 2) * (x - 3) * (x - 4) * (x - 5) * (x - 6) * (x - 7) * (x - 8) * (x - 9) *
                101 * (x - 11)) / -1599000.000000) + (((x - 0) * (x - 1) * (x - 2) * (x - 3) * (x - 4) *
                (x - 5) * (x - 6) * (x - 7) * (x - 8) * (x - 9) * (x - 10) * 33) / 39916800.000000));
    }

    public static void main(String[] args) {
        for (int i = 0; i < 12; ++i) System.out.print((char) magic(i));
    }*/

    public static void main(String[] args) throws IOException {
        Education education = new Education();
        education.training();
    }
}
