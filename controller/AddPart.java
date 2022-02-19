package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is the controller for the Add Part Scene.
 */
public class AddPart implements Initializable {

    /**
     * Alternates text based on radio button selection.
     */
    public Label machineID;

    /**
     * Indicates Part subclass InHouse.
     */
    public RadioButton inHouse;

    /**
     * Indicates Part subclass Outsourced.
     */
    public RadioButton outsourced;

    /**
     * Where the unique Id is displayed, uneditable.
     */
    public TextField partIdTxt;

    /**
     * User enters Part name.
     */
    public TextField nameTxt;

    /**
     * User enters Part inventory quantity.
     */
    public TextField stockTxt;

    /**
     * User enters Part price.
     */
    public TextField priceTxt;

    /**
     * User enters Part max quantity.
     */
    public TextField maxTxt;

    /**
     * User enters a Machine Id if InHouse or Company Name if Outsourced.
     */
    public TextField machineIdTxt;

    /**
     * User enters Part min quantity.
     */
    public TextField minTxt;

    private static int idCounter = 4;

    /**
     * Initializes the Add Part Scene and sets up unique ID to display.
     * @param url Instance of the URL class; uniform resource locator.
     * @param resourceBundle Instance of the ResourceBundle class; resources needed.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partIdTxt.setText(String.valueOf(idCounter));
    }

    /**
     * Cancel button click returns the user to the main menu screen making no changes.
     *
     * @param actionEvent The Cancel button click.
     * @throws IOException When the main menu fxml cannot be found.
     */
    public void OnCancelBAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1400, 675);
        stage.setTitle("FryeCo. Software         Inventory Management System         Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * When InHouse radio is selected, the label for the machineId text field will display "MachineID".
     *
     * @param actionEvent In House radio button is selected.
     */
    public void onInHouse(ActionEvent actionEvent) {
        machineID.setText("Machine ID");
    }

    /**
     * When Outsourced radio is selected, the label for the machineId text field will display "Company Name".
     *
     * @param actionEvent The Outsourced radio button is selected.
     */
    public void onOutsourced(ActionEvent actionEvent) {
        machineID.setText("Company Name");
    }

    /**
     * Saves valid data entered by user into a new Part object, then adds it to the allParts list.
     * @param actionEvent Save Part button click.
     */
    public void onSavePart(ActionEvent actionEvent) {

        //must not be blank
        String name = nameTxt.getText();
        if (name.isEmpty()) {
            MainController.invalidEntry();
            return;
        }
        //must be a int and not be blank. min <= stock <= max
        int stock = 0;
        try {
            stock = Integer.parseInt(stockTxt.getText());
        } catch (NumberFormatException e) {
            MainController.invalidEntry();
            return;
        }
        //must be a double and not blank. if an int is entered it is converted to a double. How to not?
        double price = 0.0;
        try {
            price = Double.parseDouble(priceTxt.getText());
        } catch (NumberFormatException e) {
            MainController.invalidEntry();
            return;
        }
        //must be a int and not be blank. must be more than min
        int max = 0;
        try {
            max = Integer.parseInt(maxTxt.getText());
        } catch (NumberFormatException e) {
            MainController.invalidEntry();
            return;
        }
        //must be a int and not be blank. must be less than max
        int min = 0;
        try {
            min = Integer.parseInt(minTxt.getText());
        } catch (NumberFormatException e) {
            MainController.invalidEntry();
            return;
        }
        //better way to write this? but it does work
        if ((min < max) && (min <= stock) && (max >= stock)) {

            //must not be empty
            String companyName = machineIdTxt.getText();
            if (companyName.isEmpty()) {
                MainController.invalidEntry();
                return;
            }
            if (inHouse.isSelected()) {

                //must be an int and not be empty
                int machineId = 0;
                try {
                    machineId = Integer.parseInt(machineIdTxt.getText());
                } catch (NumberFormatException e) {
                    MainController.invalidEntry();
                    return;
                }
                Inventory.addPart(new InHouse(idCounter++, name, price, stock, min, max, machineId));
            }

            if (outsourced.isSelected()) {
                Inventory.addPart(new Outsourced(idCounter++, name, price, stock, min, max, companyName));
            }

            //load the main menu after adding new part
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1400, 675);
                stage.setTitle("FryeCo. Software         Inventory Management System         Main Menu");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                MainController.invalidEntry();
            }

        } else {
            MainController.checkInventory();
        }
    }

}