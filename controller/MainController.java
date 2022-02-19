package controller;

import javafx.application.Platform;
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
 * This class is the GUI Main Menu Screen controller.
 */
public class MainController implements Initializable {

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
     * User enters search criteria into this text field.
     */
    public TextField partSearchTF;

    /**
     * User enters search criteria into this text field.
     */
    public TextField productsSearchTF;

    /**
     * Table display for the allProducts list.
     */
    public TableView productTable;

    /**
     * Displays product ID in table.
     */
    public TableColumn allProductsIDCol;

    /**
     * Displays product in name table.
     */
    public TableColumn allProductsNameCol;

    /**
     * Displays product inventory quantity in table.
     */
    public TableColumn allProductsInvCol;

    /**
     * Displays product price in table.
     */
    public TableColumn allProductsPriceCol;

    /**
     * Initialize method sets up the Parts and Products table views.
     *
     * @param url Instance of the URL class; uniform resource locator.
     * @param resourceBundle Instance of the ResourceBundle class; resources needed.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //setting up the Parts and Products tables at initialize
        allPartsTable.setItems(Inventory.getAllParts());

        allPartsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(Inventory.getAllProducts());

        allProductsIDCol.setCellValueFactory((new PropertyValueFactory<>("id")));
        allProductsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allProductsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allProductsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * On button click the Add Part scene is opened.
     * @param actionEvent Add Part button click.
     * @throws IOException In case specified fxml file cannot be found.
     */
    public void onAddPartAction(ActionEvent actionEvent) throws IOException {

            Parent root = FXMLLoader.load(getClass().getResource("/view/addPart.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1400, 675);
            stage.setTitle("FryeCo. Software         Inventory Management System         Add A Part");
            stage.setScene(scene);
            stage.show();
    }

    /**
     * On button is click the data from the selected part will be displayed on Modify Part scene.
     *
     * @param actionEvent Modify Part button click.
     */
    public void onModifyPartAction(ActionEvent actionEvent) {

        //gets the Part highlighted in the main screen's All Parts table
        Part mod = (Part) allPartsTable.getSelectionModel().getSelectedItem();

        //gets the AllParts list index of the selected Part
        int modIndex = Inventory.getAllParts().indexOf(mod);

        // in case nothing is selected, pop up box tell user to select item
        if (mod == null) {
         MainController.nothingSelected();
        return;
        }
        //sending the selected part to the mod part scene
        else {
            ModifyPart.passMod(mod);
            ModifyPart.passModIndex(modIndex);
        }

        //Modify Part scene is displayed
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/modifyPart.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1400, 675);
            stage.setTitle("FryeCo. Software         Inventory Management System         Modify An Existing Part");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            MainController.invalidEntry();
        }
    }

    /**
     * On button click, data from the selected product will be displayed on Modify Product scene.
     *
     * @param actionEvent Modify Product button click.
     */
    public void onModifyProductAction(ActionEvent actionEvent) {

        // to get the selected product from the table
        Product mod = (Product) productTable.getSelectionModel().getSelectedItem();

        int modIndex = Inventory.getAllProducts().indexOf(mod);

        //in case nothing is selected, pop up box tell user to select item
        if (mod == null) {

            MainController.nothingSelected();
            return;
        }

        //sending the selected product from the table to the modify product menu
        else {
        ModifyProduct.passMod(mod);
        ModifyProduct.passModIndex(modIndex);}

        //load modify product screen with selected product's info
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/modifyProduct.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1400, 675);
            stage.setTitle("FryeCo. Software         Inventory Management System         Modify An Existing Product");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            MainController.invalidEntry();
        }

    }

    /**
     * On button click the Add Product scene is opened.
     * @param actionEvent Add Product button click.
     */
    public void onAddProductAction(ActionEvent actionEvent) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/addProduct.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1400, 675);
            stage.setTitle("FryeCo. Software         Inventory Management System         Add A New Product");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            MainController.invalidEntry();
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
     * Searches for a product using Id or (partial) Name.
     * @param actionEvent The enter key is pressed while caret is in the product search text field.
     */
    public void onProductSearch(ActionEvent actionEvent) {
        String p = productsSearchTF.getText();

        ObservableList<Product> products = Inventory.lookupProduct(p);

        try {
            int idToFind = Integer.parseInt(p);
            Product productIdMatch = Inventory.lookupProduct(idToFind);
            if (productIdMatch != null)
                products.add(productIdMatch);

        } catch (NumberFormatException e) {
            //A character entered in the search bar would cause an exception to occur here
        }

        //if no results are found display the pop up and reload list
        if (products.size() == 0) {

            MainController.noResultsFound();
            return;
        }

        productTable.setItems(products);
        productsSearchTF.setText("");
    }

    /**
     * This method will exit the application.
     *
     * @param actionEvent The Exit button click.
     */
    public void onExit(ActionEvent actionEvent) {
        //pop up to confirm exit
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Exit Button Clicked");
        alert.setContentText("Are you sure you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            Platform.exit();
        }

    }

    /**
     * This method will delete the selected product.
     * @param actionEvent Delete button click.
     */
    public void productDeleteAction(ActionEvent actionEvent) {
        Product mod = (Product) productTable.getSelectionModel().getSelectedItem();

        // in case nothing is selected
        if (mod == null)
           MainController.nothingSelected();

        //confirm delete. with option to cancel.
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Item Will Be Permanently Deleted");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // ... user chose OK
                //if ass parts is empty
                if ((mod.getAllAssociatedParts().size() == 0)) {

                    //if delete Product returns true then pop up successful operation
                    if (Inventory.deleteProduct(mod)) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Operation Successful");
                        alert.setHeaderText(null);
                        alert.setContentText("Product was successfully deleted");
                    }
                    // in case part is not deleted for some other reason
                    else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Notification");
                        alert.setHeaderText("Part Deletion Failed");
                        alert.setContentText("Please check your selection and try again!");

                    }

                    //the delete failed because associated parts are still in the list
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Notification");
                    alert.setHeaderText("Product Deletion Failed");
                    alert.setContentText("Please remove any associated parts and try again!");

                }
                alert.showAndWait();

            }

        }
    }

    /**
     * This method will delete the selected part.
     * @param actionEvent Delete button click.
     */
    public void partDeleteAction(ActionEvent actionEvent) {

        //gets the Part highlighted in the main screen's All Parts table
        Part mod = (Part) allPartsTable.getSelectionModel().getSelectedItem();

        // in case nothing is selected
        Alert alert;
        if (mod == null) {

            MainController.nothingSelected();

        } else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Item Will Be Permanently Deleted");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                //delete part after user clicks OK
                if (Inventory.deletePart(mod)) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Operation Successful");
                    alert.setHeaderText(null);
                    alert.setContentText("Part was successfully deleted");
                }
                // in case part is not deleted
                else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Notification");
                    alert.setHeaderText("Part Deletion Failed");
                    alert.setContentText("Please check your selection and try again!");

                }
                alert.showAndWait();
            }
        }
    }

    /**
     * Displays a pop-up dialog when no search results are found
     */
    public static void noResultsFound() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Search Results");
        alert.setHeaderText(null);
        alert.setContentText("Your Search Returned No Results");

        alert.showAndWait();
    }

    /**
     * Displays a pop-up dialog when an invalid entry is made.
     */
    public static void invalidEntry() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText("Invalid Entry");
        alert.setContentText("Invalid Entry, please check for errors and try again");

        alert.showAndWait();
    }

    /**
     * Displays a pop-up dialog when no item is selected to modify or delete.
     */
    public static void nothingSelected() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("No Selection Made");
        alert.setHeaderText(null);
        alert.setContentText("Select an item");

        alert.showAndWait();
    }

    /**
     * Displays a pop-up dialog to indicate a value entered in inventory, max, or min isn't allowed.
     */
    public static void checkInventory() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText("Invalid Entry");
        alert.setContentText("Please check inventory levels and try again");

        alert.showAndWait();
    }

}