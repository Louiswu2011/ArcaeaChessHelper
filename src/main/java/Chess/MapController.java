package Chess;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MapController {
    final static int gridSize = 500;

    @FXML
    Button shrinkButton, exitButton;
    @FXML
    AnchorPane rootPane;

    public MapController() {

    }

    @FXML
    private void initialize() {
        GridPane mapGrid = new GridPane();
        mapGrid.setPrefSize(500, 500);
        int containerSize = gridSize / 4;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                Pane titleContainer2 = new AnchorPane();
                Label testLabel = new Label();
                testLabel.setText("Song Title Here");
                testLabel.setStyle("-fx-font-weight: bold;-fx-font: 20 arial;");
                testLabel.setTextAlignment(TextAlignment.CENTER);
                testLabel.setPrefSize(containerSize, containerSize);
                testLabel.setWrapText(true);
                titleContainer2.setStyle("-fx-border-color: black");
                titleContainer2.setPrefSize(containerSize, containerSize);
                titleContainer2.getChildren().add(testLabel);
                mapGrid.addColumn(j, titleContainer2);
            }
            Pane titleContainer = new AnchorPane();
            Label testLabel = new Label();
            testLabel.setText("Song Title Here");
            testLabel.setStyle("-fx-font-weight: bold;-fx-font: 20 arial;");
            testLabel.setTextAlignment(TextAlignment.CENTER);
            titleContainer.setStyle("-fx-border-color: black");
            titleContainer.setPrefSize(containerSize, containerSize);
            testLabel.setWrapText(true);
            testLabel.setPrefSize(containerSize, containerSize);
            titleContainer.getChildren().add(testLabel);
            mapGrid.addRow(i, titleContainer);
        }

        rootPane.getChildren().add(mapGrid);
        System.out.println("Map view initialized.");
    }

    public void exitAction(ActionEvent actionEvent) {
        Stage nowStage = (Stage) rootPane.getScene().getWindow();
        nowStage.close();
    }

    public void shrinkAction(ActionEvent actionEvent) {
        System.out.println("Shrinking...");
    }
}
