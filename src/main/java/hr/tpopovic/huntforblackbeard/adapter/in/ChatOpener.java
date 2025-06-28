package hr.tpopovic.huntforblackbeard.adapter.in;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatOpener {

    private ChatOpener() {
    }

    public static void openChatWindow() {
        FXMLLoader loader = new FXMLLoader(ChatOpener.class.getResource("/hr/tpopovic/huntforblackbeard/adapter/in/chat-view.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            AlertManager.showInfo("Error", "Failed to load chat view: %s".formatted(e.getMessage()));
        }

        Stage newWindow = new Stage();
        newWindow.setTitle("Chat");
        newWindow.setScene(new Scene(root, 600, 400));
        newWindow.show();
    }

}
