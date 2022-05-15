package edu.ntnu.trym.simulation.model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class AlertDialog {


    public static boolean showConfirmation(String message, String header){
        Alert alert = createAlert(Alert.AlertType.CONFIRMATION, message, header);
        Optional<ButtonType> buttonPressed = alert.showAndWait();

        return buttonPressed.get() != ButtonType.CANCEL;
    }

    public static void showError(String message){
        createAlert(Alert.AlertType.ERROR, message, "An error has occurred!");
    }

    public static void showWarning(String message){
        createAlert(Alert.AlertType.WARNING, message, "Warning!");
    }

    public static String createTextInputDialog(String title, String header, String content){
        TextInputDialog inputDialog = new TextInputDialog();
        setDialogInformation(inputDialog, title, header, content);
        Optional<String> inputText = inputDialog.showAndWait();
        return inputText.orElse(null);
    }


    private static Alert createAlert(Alert.AlertType alertType, String message, String headerText){
        Alert alert = new Alert(alertType);
        setDialogInformation(alert, alertType.name(), headerText, message);
        alert.showAndWait();

        return alert;
    }

    private static void setDialogInformation(Dialog<?> dialog, String title, String header, String content){
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);
    }

}
