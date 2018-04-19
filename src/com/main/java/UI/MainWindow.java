package UI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.stage.Stage;
import logic.image.Geometry.Point;
import logic.network.Network;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainWindow extends Application {
    private Label label;
    private Path path;
    private int[][] points;
    private int height = 0;
    private int width = 0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("AAAAAAAAAAAAAAAA");
        Group root = new Group();
        Scene scene = new Scene(root, 500, 500);

        height = (int) scene.getHeight();
        width = (int) scene.getWidth();
        points = new int[width][height];

        label = new Label("Wait mouse");

        path = new Path();
        path.setStrokeWidth(1);
        path.setStroke(Color.BLACK);

        scene.setOnMouseClicked(mouseHandler);
        scene.setOnMouseDragged(mouseHandler);
        scene.setOnMouseMoved(mouseHandler);
        scene.setOnMousePressed(mouseHandler);

        root.getChildren().add(label);
        root.getChildren().add(path);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent mouseEvent) {
            label.setText(mouseEvent.getEventType() + "\n"
                    + "X : Y - " + mouseEvent.getX() + " : " + mouseEvent.getY() + "\n"
                    + "SceneX : SceneY - " + mouseEvent.getSceneX() + " : " + mouseEvent.getSceneY() + "\n"
                    + "ScreenX : ScreenY - " + mouseEvent.getScreenX() + " : " + mouseEvent.getScreenY());

            if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                path.getElements().clear();
                path.getElements().add(new MoveTo(mouseEvent.getX(), mouseEvent.getY()));
                points[(int) mouseEvent.getSceneX()][(int) mouseEvent.getSceneY()] = 1;
            } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                path.getElements().add(new LineTo(mouseEvent.getX(), mouseEvent.getY()));
                points[(int) mouseEvent.getX()][(int) mouseEvent.getY()] = 1;
            } else if (mouseEvent.getEventType() != MouseEvent.MOUSE_PRESSED &&
                    mouseEvent.getEventType() != MouseEvent.MOUSE_MOVED &&
                    !Arrays.deepEquals(points, new int[width][height])) {
                System.out.println(new Network(createPicture()).getResult());
                points = new int[width][height];
            }
        }

    };

    private BufferedImage createPicture() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (points[i][j] == 1) {
                    image.setRGB(i, j, java.awt.Color.BLACK.getRGB());
                } else {
                    image.setRGB(i, j, java.awt.Color.WHITE.getRGB());
                }
            }
        }

        return image;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
