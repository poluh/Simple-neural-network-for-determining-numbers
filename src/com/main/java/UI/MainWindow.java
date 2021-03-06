package UI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import logic.image.Geometry.Point;
import logic.network.Network;


import java.awt.image.BufferedImage;
import java.util.Arrays;

public class MainWindow extends Application {
    private Label label;
    private Path path;
    private int[][] points;
    private int height = 0;
    private int width = 0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("AAAAAAAAAAAAAAAA");
        Group group = new Group();
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 500, 500);

        height = (int) scene.getHeight();
        width = (int) scene.getWidth();
        points = new int[width][height];

        label = new Label("Wait mouse");

        Button button = new Button("Recognize");
        button.setOnAction(buttonHandler);
        button.setMinWidth(width);

        path = new Path();
        path.setStrokeWidth(3);
        path.setStroke(Color.BLACK);

        scene.setOnMouseClicked(mouseHandler);
        scene.setOnMouseDragged(mouseHandler);
        scene.setOnMouseMoved(mouseHandler);
        scene.setOnMousePressed(mouseHandler);

        group.getChildren().add(label);
        group.getChildren().add(path);
        AnchorPane.setTopAnchor(group, 0.0);
        AnchorPane.setBottomAnchor(button, 0.0);
        root.getChildren().addAll(group, button);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            label.setText(mouseEvent.getEventType() + "\n"
                    + "X : Y - " + mouseEvent.getX() + " : " + mouseEvent.getY() + "\n");

            Point currentPoint = new Point((int) mouseEvent.getX(), (int) mouseEvent.getY());

            if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                path.getElements().add(new MoveTo(currentPoint.x, currentPoint.y));
            } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                path.getElements().add(new LineTo(currentPoint.x, currentPoint.y));

                // This knowledge was empirical way
                // to make the translation from points to images more clear
                for (int i = currentPoint.x - 4; i <= currentPoint.x; i++) {
                    for (int j = currentPoint.y - 4; j <= currentPoint.y; j++) {
                        points[i][j] = 1;
                    }
                }
            }
        }

    };

    private EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent buttonEvent) {
            if (!Arrays.deepEquals(points, new int[width][height])) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Ur number " + (int) new Network(createPicture()).getResult());
                alert.showAndWait();
                path.getElements().clear();
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
