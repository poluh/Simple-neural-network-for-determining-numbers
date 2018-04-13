package logic.network.education;

import logic.image.ImagePreprocessor;

public class Education {

    private double[][] imagesSignals = new double[10][15];
    private ImagePreprocessor[] imagePreprocessors = new ImagePreprocessor[10];
    private String commonPath = "src/com/main/java/logic/network/education/imageForEducation/";

    Education() {
        for (int i = 0; i < 10; i++) {
            imagePreprocessors[i] = new ImagePreprocessor(commonPath + i + ".jpg", 15);
            imagesSignals[i] = imagePreprocessors[i].getImageSignals();
        }
    }

}
