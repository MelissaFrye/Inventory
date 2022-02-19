package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class contains inventory methods.
 */
public class Inventory{
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds part to allParts list.
     * @param newPart New part data.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds product to allProducts list.
     * @param newProduct new product data.
     */
    public static void addProduct(Product newProduct){
       allProducts.add(newProduct);
    }

    /**
     * Searches for a part using Id.
     * @param partId An int representing part Id, passed into method.
     * @return Part object if found, null if not found.
     */
    public static Part lookupPart(int partId){

        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Searches for a product using Id.
     * @param productId An int representing product Id, passed into method.
     * @return Product object if found, null if not found.
     */
    public static Product lookupProduct(int productId){

        for (Product id : allProducts) {
            if (id.getId() == productId)
                return id;
        }
        return null;
    }

    /**
     * Searches for a part using (partial) name.
     * @param partName A string passed into the method.
     * @return A list of matching parts.
     */
    public static ObservableList<Part> lookupPart(String partName) {

        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        for (Part allP : allParts) {
            if (allP.getName().contains(partName)) {
                namedParts.add(allP);
            }
        }
        return namedParts;
    }

    /**
     * Searches for a product using (partial) name.
     * @param productName A string passed into the method.
     * @return A list of matching parts.
     */
    public static ObservableList<Product> lookupProduct(String productName){

        ObservableList<Product> namedProducts = FXCollections.observableArrayList();

        for (Product allP : allProducts) {
            if (allP.getName().contains(productName)) {
                namedProducts.add(allP);
            }
        }
        return namedProducts;
    }

    /**
     * Modify an existing part.
     * @param index Index of part to modify.
     * @param selectedPart Contains the modified data.
     */
    public static void updatePart(int index, Part selectedPart)
    {
        allParts.set(index, selectedPart);
    }

    /**
     * Modify an existing product.
     * @param index Index of product to modify.
     * @param newProduct Contains the modified data.
     */
    public static void updateProduct(int index, Product newProduct)
    {
        allProducts.set(index, newProduct);
    }

    /**
     * Deletes a part from the allParts list.
     * @param selectedPart Part selected from table view.
     * @return A return of true verifies the part was removed; false means still on the list.
     */
    public static boolean deletePart(Part selectedPart){
        allParts.remove(selectedPart);

        return !allParts.contains(selectedPart);
    }

    /**
     * Deletes a product from allProducts list.
     * @param selectedProduct Product selected from table view.
     * @return A return of true verifies the product was removed; false means still on the list.
     */
    public static boolean deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);

        return !allProducts.contains(selectedProduct);
    }

    /**
     * Public method to return the private allParts list.
     * @return allParts list.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Public method to return the private allProducts list.
     * @return allParts list.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    //to add test data
    static {
        addTestData();
    }

    /**
     * Adds test data.
     */
    public static void addTestData() {
        InHouse part1 = new InHouse(1,"bolt",0.25, 4, 1, 144, 66);
        InHouse part2 = new InHouse(2,"screw", 0.35, 8, 1, 144,  13);
        Outsourced part3 = new Outsourced(3, "fine gear", 1.05, 99, 10, 144, "Part Co.");

        Product pro1 = new Product(1, "machine", 2, 9.99);
        Product pro2 = new Product(2, "small boat", 5, 99.99);
        Product pro3 = new Product(3,"large  pool", 2, 799.99);

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);

        Inventory.addProduct(pro1);
        Inventory.addProduct(pro2);
        Inventory.addProduct(pro3);
    }

    }



