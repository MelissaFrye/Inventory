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
 * This class modifies an existing part.
 */
public class ModifyPart implements Initializable {

    private static Part toMod = null;
    private static int toModIndex = 0;

    /**
     * Indicates Part of subclass InHouse.
     */
    public RadioButton inHouse;

    /**
     * Indicates Part subclass Outsourced.
     */
    public RadioButton outsourced;

    /**
     * Saves modifications when clicked.
     */
    public Button savePart;

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
     * User enters machineId or Company Name depending on radio selection.
     */
    public TextField machineIdTxt;

    /**
     * User enters Part min quantity.
     */
    public TextField minTxt;

    /**
     * Alternates text based on radio button selection.
     */
    public Label machineIdLabel;

    /**
     * Gets the selected Part from Main Menu scene.
     * @param mod Part to modify.
     */
    public static void passMod(Part mod) {
        toMod = mod;
    }

    /**
     * Gets the index of selected Part.
     * @param index List index of Part to modify.
     */
    public static void passModIndex(int index) {
        toModIndex = index;
    }

    /**
     * Initialize Modify Part scene with selected part's data displayed for modification.
     * @param url Instance of the URL class; uniform resource locator.
     * @param resourceBundle Instance of the ResourceBundle class; resources needed.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIdTxt.setText(String.valueOf(toMod.getId()));
        nameTxt.setText(toMod.getName());
        stockTxt.setText(String.valueOf(toMod.getStock()));
        priceTxt.setText(String.valueOf(toMod.getPrice()));
        maxTxt.setText(String.valueOf(toMod.getMax()));
        minTxt.setText(String.valueOf(toMod.getMin()));


        if (toMod instanceof InHouse) {
            inHouse.setSelected(true);
            machineIdTxt.setText(String.valueOf(((InHouse) toMod).getMachineId()));
        } else {
            outsourced.setSelected(true);
            machineIdTxt.setText(((Outsourced) toMod).getCompanyName());
            machineIdLabel.setText("Company Name");
        }

    }

    /**
     * Cancel button click loads the Main menu scene.
     * @param actionEvent Cancel button click.
     * @throws IOException In case specified fxml file is not found.
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
     * When this radio button is selected the alternate label will display and the text field is cleared.
     * @param actionEvent InHouse radio selection.
     */
    public void onInHouse(ActionEvent actionEvent) {
        machineIdTxt.setText("");
        machineIdLabel.setText("Machine ID");
    }

    /**
     * When this radio button is selected the alternate label will display and the text field is cleared.
     * @param actionEvent Outsourced radio selection.
     */
    public void onOutsourced(ActionEvent actionEvent) {
        machineIdTxt.setText("");
        machineIdLabel.setText("Company Name");
    }

    /**
     * On button click, entered data is validated and part modifications are saved.
     * @param actionEvent Save button click.
     * @throws IOException In case specified fxml file is not found.
     */
    public void onSavePart(ActionEvent actionEvent) throws IOException {
        int id = 0;
        try {
            id = Integer.parseInt(partIdTxt.getText());
        } catch (NumberFormatException e) {
            MainController.invalidEntry();
            return;
        }
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
                Inventory.updatePart(toModIndex, new InHouse(id, name, price, stock, min, max, machineId));

            }

            if (outsourced.isSelected()) {
                Inventory.updatePart(toModIndex, new Outsourced(id, name, price, stock, min, max, companyName));
            }

            //load the main menu after adding new part
            Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1400, 675);
            stage.setTitle("FryeCo. Software         Inventory Management System         Main Menu");
            stage.setScene(scene);
            stage.show();

        } else {
          MainController.checkInventory();
        }


    }
}