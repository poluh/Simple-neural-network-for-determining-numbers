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
import javafx.stage.Stage;
import logic.image.Geometry.Point;
import logic.image.ImagePreprocessor;
import logic.network.Network;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends Application {
    private Label label;
    private Path path;
    private List<Point> points = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("AAAAAAAAAAAAAAAA");
        Group root = new Group();
        Scene scene = new Scene(root, 500, 500);

        label = new Label("Wait mouse");

        path = new Path();
        path.setStrokeWidth(1);
        path.setStroke(Color.BLACK);


        scene.setOnMouseClicked(mouseHandler);
        scene.setOnMouseDragged(mouseHandler);
        scene.setOnMouseEntered(mouseHandler);
        scene.setOnMouseExited(mouseHandler);
        scene.setOnMouseMoved(mouseHandler);
        scene.setOnMousePressed(mouseHandler);
        scene.setOnMouseReleased(mouseHandler);

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
                points.clear();
            } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                path.getElements().add(new LineTo(mouseEvent.getX(), mouseEvent.getY()));
                points.add(new Point((int) mouseEvent.getX(), (int) mouseEvent.getY()));
            }

        }

    };

    private BufferedImage createPicture() {
        BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < 400; i++) {
            for (int j = 0; j < 400; j++) {
                Point currentPoint = new Point(i, j);
                if (points.contains(currentPoint)) {
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
