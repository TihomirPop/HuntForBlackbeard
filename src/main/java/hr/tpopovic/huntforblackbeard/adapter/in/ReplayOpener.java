package hr.tpopovic.huntforblackbeard.adapter.in;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ReplayOpener {

    private ReplayOpener() {
    }

    public static void openReplayWindow() {
        FXMLLoader loader = new FXMLLoader(ReplayOpener.class.getResource("/hr/tpopovic/huntforblackbeard/adapter/in/replay-view.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            AlertManager.showInfo("Error", "Failed to load replay view: %s".formatted(e.getMessage()));
            return;
        }

        if (root == null) {
            AlertManager.showInfo("Error", "FXML root is null. Replay view cannot be loaded.");
            return;
        }

        Stage newWindow = new Stage();
        newWindow.setTitle("Replay");
        newWindow.setScene(new Scene(root, 1078, 694));
        newWindow.setResizable(false);
        newWindow.show();
    }

}
