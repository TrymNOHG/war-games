package edu.ntnu.trym.simulation.model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

/**
 * This class contains the features essential for an alert dialog. Therefore, it provides different functions for the
 * various types of alert.
 *
 * @author Trym Hamer Gudvangen
 */
public class AlertDialog {


    /**
     * This method produces a confirmation dialog by taking in the information to be displayed and returning the status
     * of whether the alert was confirmed or not. In order to create the dialog, the method
     * {@link #createAlert(Alert.AlertType, String, String)} was used.
     * @param message   The message the user will read, represented as a String
     * @param header    The header, or brief information on the alert, represented as a String
     * @return          The status of the dialog where cancel is {@code false} and confirm/ok is {@code true}.
     */
    public static boolean showConfirmation(String message, String header){
        Optional<ButtonType> buttonPressed = createAlert(Alert.AlertType.CONFIRMATION, message, header);

        return buttonPressed.get() != ButtonType.CANCEL;
    }

    /**
     * This method produces an error dialog with the input message displayed. In order to create the dialog, the method
     * {@link #createAlert(Alert.AlertType, String, String)} was used.
     * @param message   The error message the user will read, represented as a String
     */
    public static void showError(String message){
        createAlert(Alert.AlertType.ERROR, message, "An error has occurred!");
    }

    /**
     * This method produces a warning dialog with the input message displayed. In order to create the dialog, the method
     * {@link #createAlert(Alert.AlertType, String, String)} was used.
     * @param message   The warning message the user will read, represented as a String
     */
    public static void showWarning(String message){
        createAlert(Alert.AlertType.WARNING, message, "Warning!");
    }

    /**
     * This method produces an information dialog with the input message displayed. In order to create the dialog, the
     * method {@link #createAlert(Alert.AlertType, String, String)} was used.
     * @param message   The information the user will read, represented as a String
     * @param header    The header, or brief information on the dialog, represented as a String.
     */
    public static void showInformation(String message, String header){
        createAlert(Alert.AlertType.INFORMATION, message, header);
    }

    /**
     * This method produces a text input dialog, where the user has the ability to enter a String input. In order to
     * create the dialog, the method {@link #setDialogInformation(Dialog, String, String, String)} was used.
     * @param title     The title of the dialog, represented as a String.
     * @param header    The header, or brief information on the dialog, represented as a String.
     * @param content   The prompt or message to the user, represented as a String
     * @return          The input from the user, represented as a String. If nothing was entered, then null is returned.
     */
    public static String createTextInputDialog(String title, String header, String content){
        TextInputDialog inputDialog = new TextInputDialog();
        setDialogInformation(inputDialog, title, header, content);
        Optional<String> inputText = inputDialog.showAndWait();
        return inputText.orElse(null);
    }

    /**
     * This method acts as a general outline for the creation of alert boxes. It takes in what type of alert is desired,
     * as well as the information to appear on the alert, and then creates it. The information is attached to the
     * dialog using the {@link #setDialogInformation(Dialog, String, String, String)} method.
     * @param alertType     The type of alert, represented using the enum Alert.AlertType.
     * @param message       The warning message the user will read, represented as a String
     * @param headerText    The header, or brief information on the dialog, represented as a String.
     * @return              A potential result from a button, represented using a Optional{@code <ButtonType>} object.
     */
    private static Optional<ButtonType> createAlert(Alert.AlertType alertType, String message, String headerText){
        Alert alert = new Alert(alertType);
        setDialogInformation(alert, alertType.name(), headerText, message);

        return alert.showAndWait();
    }

    /**
     * This method attaches the information to a given dialog.
     * @param dialog    The dialog the information will be attached to, represented using a Dialog object.
     * @param title     The title of the dialog, represented as a String.
     * @param header    The header, or brief information on the dialog, represented as a String.
     * @param content   The prompt or message to the user, represented as a String.
     */
    private static void setDialogInformation(Dialog<?> dialog, String title, String header, String content){
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);
    }

}