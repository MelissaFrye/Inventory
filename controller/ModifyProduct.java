package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class modifies an existing Product.
 */
public class ModifyProduct implements Initializable {


    private static Product toMod = null;
    private static int toModIndex = 0;


    /**
     * Table display for the allParts list.
     */
    public TableView allPartsTable;

    /**
     * Displays part ID in table.
     */
    public TableColumn allPartsIDCol;

    /**
     * Displays part name in table.
     */
    public TableColumn allPartsNameCol;

    /**
     * Displays part inventory quantity in table.
     */
    public TableColumn allPartsInvCol;

    /**
     * Displays part price in table.
     */
    public TableColumn allPartsPriceCol;

    /**
     * Table display for product's associated parts list.
     */
    public TableView fewPartsTable;

    /**
     * Displays associated part ID in table.
     */
    public TableColumn fewPartsIDCol;

    /**
     * Displays associated part name in table.
     */
    public TableColumn fewPartsNameCol;

    /**
     * Displays associated part inventory quantity in table.
     */
    public TableColumn fewPartsInvCol;

    /**
     * Displays associated part price in table.
     */
    public TableColumn fewPartsPriceCol;

    /**
     * Button click to remove part from associated parts list.
     */
    public Button removeP;

    /**
     * Button click adds part to associated parts list.
     */
    public Button addP;

    /**
     * User enters search criteria into this text field.
     */
    public TextField partSearchTF;

    /**
     * Product Id is displayed and is unalterable.
     */
    public TextField productIdTxt;

    /**
     * User enters product name.
     */
    public TextField nameTxt;

    /**
     * User enters product inventory quantity.
     */
    public TextField invTxt;
    /**
     * User enters product price.
     */
    public TextField priceTxt;

    /**
     * User enters max quantity.
     */
    public TextField maxTxt;

    /**
     * User enters min quantity.
     */
    public TextField minTxt;

    /**
     * Saves modified product.
     */
    public Button saveModButton;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private ObservableList<Part> allParts = FXCollections.observableArrayList();

    //to display in the lower table, any parts manipulated here will only be saved to product when Save Button clicked
    private ObservableList<Part> fewPartsDisplay = FXCollections.observableArrayList();

    /**
     * Gets the selected Product from Main menu scene.
     * @param mod Product to modify.
     */
    public static void passMod(Product mod){
        toMod = mod;
    }

    /**
     * Gets the index of selected Product.
     * @param index List index of Product to modify.
     */
    public static void passModIndex(int index) {
        toModIndex = index;
    }

    /**
     * Initializes the Modify Product scene and displays the selected Product's data to modify.
     * @param url Instance of the URL class; uniform resource locator.
     * @param resourceBundle Instance of the ResourceBundle class; resources needed.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fewPartsDisplay.addAll(toMod.getAllAssociatedParts());
        fewPartsTable.setItems(fewPartsDisplay);
        allPartsTable.setItems(Inventory.getAllParts());

        allPartsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        fewPartsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        fewPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        fewPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        fewPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIdTxt.setText(String.valueOf(toMod.getId()));
        nameTxt.setText(toMod.getName());
        invTxt.setText(String.valueOf(toMod.getStock()));
        priceTxt.setText(String.valueOf(toMod.getPrice()));
        maxTxt.setText(String.valueOf(toMod.getMax()));
        minTxt.setText(String.valueOf(toMod.getMin()));

    }

    /**
     * Cancel button click loads the Main menu scene.
     * @param actionEvent Cancel button click.
     * @throws IOException In case specified fxml file isn't found.
     */
    public void onCancelBAction (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1400, 675);
        stage.setTitle("FryeCo. Software         Inventory Management System         Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * When Add Part button is clicked, the selected Part will be added to the Associated Parts list and table.
     * @param actionEvent Add Part button click.
     */
    public void onAddP (ActionEvent actionEvent){

        Part SP = (Part)allPartsTable.getSelectionModel().getSelectedItem();
        if(SP == null)
       MainController.nothingSelected();

        else {
            allParts.remove(SP);
            fewPartsDisplay.add(SP);
        }
    }

    /**
     * When the Remove Part button is clicked, part is removed from Associated Parts list and table.
     * @param actionEvent Remove Part button click.
     */
    public void onRemoveP (ActionEvent actionEvent){

        Part SP = (Part)fewPartsTable.getSelectionModel().getSelectedItem();

        if(SP == null)
            MainController.checkInventory();

        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Removal");
            alert.setHeaderText("The Associated Part Will Be Removed");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                fewPartsDisplay.remove(SP);
                allParts.add(SP);
            }
        }
    }

    /**
     * Searches for a part using Id or (partial) Name.
     * @param actionEvent The enter key is pressed while caret is in the part search text field.
     */
    public void partSearchAction(ActionEvent actionEvent) {
        String p = partSearchTF.getText();

        ObservableList<Part> parts = Inventory.lookupPart(p);

        try {
            int idToFind = Integer.parseInt(p);
            Part partIdMatch = Inventory.lookupPart(idToFind);
            if (partIdMatch != null)
                parts.add(partIdMatch);

        } catch (NumberFormatException e) {
            //A character entered in the search bar would cause an exception to occur here
        }

        //if no results are found display the pop up and reload AllParts list when OK is clicked
        if (parts.size() == 0) {

            MainController.noResultsFound();
            //this return statement keeps the table filled with the entire list; empty table without it
            return;
        }

        allPartsTable.setItems(parts);
        partSearchTF.setText("");
    }

    /**
     * Input is validated and modified Product is saved.
     * @param actionEvent Save button click.
     * @throws IOException In case specified fxml file is not found.
     */
    public void onSaveModAction(ActionEvent actionEvent) throws IOException {

        // this validates the ID entry is an int and isn't empty
        int id = 0;
        try {
            id = Integer.parseInt(productIdTxt.getText());
        } catch (NumberFormatException e) {
            MainController.invalidEntry();
            return;
        }
        //must not be blank
        String name = nameTxt.getText();
        if (name.isEmpty()) {
            MainController.invalidEntry();
            return;
        }

        //must be a int and not be blank. min <= stock <= max
        int stock = 0;
        try {
            stock = Integer.parseInt(invTxt.getText());
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

        ObservableList<Part> assParts = fewPartsDisplay;


        //better way to write this? but it does work
        if ((min < max) && (min <= stock) && (max >= stock)) {
            Inventory.updateProduct(toModIndex, (new Product(id, name, stock, price, max, min, assParts)));

            Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1400, 675);
            stage.setTitle("FryeCo. Software         Inventory Management System         Main Menu");
            stage.setScene(scene);
            stage.show();
        }
        else
            MainController.checkInventory();
    }
}
