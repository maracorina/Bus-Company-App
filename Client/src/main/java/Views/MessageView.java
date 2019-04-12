package Views;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

import java.util.Optional;

public class MessageView {
    public static Optional<ButtonType> showMessage(Alert.AlertType type, String header, String text){
        Alert message=new Alert(type);
        DialogPane dialogPane = message.getDialogPane();
        message.setHeaderText(header);
        message.setContentText(text);
        return message.showAndWait();

    }


    public static Optional<ButtonType> showErrorMessage(String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        DialogPane dialogPane = message.getDialogPane();
        message.setHeaderText("Mesaj eroare");
        message.setContentText(text);
        return message.showAndWait();
    }
}
