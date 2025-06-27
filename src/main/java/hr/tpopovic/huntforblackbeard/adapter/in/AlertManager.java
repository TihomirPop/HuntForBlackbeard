package hr.tpopovic.huntforblackbeard.adapter.in;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class AlertManager {

    private AlertManager() {
    }

    public static void showInfo(String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }
}
