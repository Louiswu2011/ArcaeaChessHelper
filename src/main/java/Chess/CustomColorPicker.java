package Chess;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.ColorPicker;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomColorPicker {

    public void open(Cell cell) {
        try {
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/colorFrame.fxml"));
            Scene scene = new Scene(root, 200, 70);
            primaryStage.setTitle("Color picker");
            primaryStage.setScene(scene);

            Button cancel = (Button) scene.lookup("#cancel");
            Button confirm = (Button) scene.lookup("#confirm");
            ColorPicker cp = (ColorPicker) scene.lookup("#picker");

            cancel.setOnAction(event -> {
                primaryStage.close();
            });

            confirm.setOnAction(event -> {
                cell.setTextFill(cp.getValue());
                primaryStage.close();
            });

            primaryStage.show();
        } catch (IOException e) {

        }
    }
}
