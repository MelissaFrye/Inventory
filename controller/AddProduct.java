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
 * This class is the controller for the Add Product Scene, RUNTIME ERROR was encountered when creating the unique Id, at
 * first I tried to use the size of the list plus one when assigning the ids but that resulted in duplicate id numbers
 * when a part was deleted so I corrected the situation by creating a static variable that could be used as a counter
 * incrementing by one whenever a new Product was created, and assigning that unique number to id.
 */
public class AddProduct implements Initializable {

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
     * Button click saves new product.
     */
    public Button saveProductButton;

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

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static int idCounter = 4;

    /**
     * Initializes the Add Product scene and sets up the parts tables and unique ID.
     * @param url Instance of the URL class; uniform resource locator.
     * @param resourceBundle Instance of the ResourceBundle class; resources needed.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fewPartsTable.setItems(associatedParts);
        allPartsTable.setItems(Inventory.getAllParts());

        allPartsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        fewPartsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        fewPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        fewPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        fewPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIdTxt.setText(String.valueOf(idCounter));
    }
    /**
     * When Cancel Button is clicked, no changes are made and the Main Menu scene is displayed.
     * @param actionEvent Cancel button click.
     * @throws IOException In case specified fxml is not found.
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
        if(SP == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Selection Made");
            alert.setHeaderText(null);
            alert.setContentText("Select an item");

            alert.showAndWait();
        }
        else {
            allParts.remove(SP);
            associatedParts.add(SP);
        }
    }

    /**
     * When the Remove Part button is clicked, part is removed from Associated Parts list and table.
     * @param actionEvent Remove Part button click.
     */
    public void onRemoveP (ActionEvent actionEvent){

        Part SP = (Part)fewPartsTable.getSelectionModel().getSelectedItem();

        if(SP == null) {
           MainController.nothingSelected();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Removal");
            alert.setHeaderText("The Associated Part Will Be Removed");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                associatedParts.remove(SP);
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
     * This method saves valid data entered by user into a new Product object and adds it to the Products list.
     * @param actionEvent Save button click.
     * @throws IOException In case specified fxml file cannot be found.
     */
    public void onSaveProduct(ActionEvent actionEvent) throws IOException {

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

        ObservableList<Part> assParts = associatedParts;

        //better way to write this? but it does work
        if ((min < max) && (min <= stock) && (max >= stock)) {
            Inventory.addProduct(new Product(idCounter++, name, stock, price, max, min, assParts));


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
