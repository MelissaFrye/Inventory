package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class creates Product objects which each contains a list of associated parts.
 */
public class Product {

    private int id;
    private String name;
    private int stock;
    private double price;
    private int min;
    private int max;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Constructor to create a Product per UML.
     * @param id sets id.
     * @param name sets name.
     * @param stock sets inventory.
     * @param price sets price.
     */
    public Product(int id, String name, int stock, double price){
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        }

    /**
     * Constructor with max, min, and associated parts attributes added.
     * @param id sets id.
     * @param name sets name.
     * @param stock sets inventory.
     * @param price sets price.
     * @param max sets max.
     * @param min sets min.
     * @param associatedParts sets associated parts list.
     */
    public Product(int id, String name, int stock, double price, int max, int min, ObservableList<Part> associatedParts)
    {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.max = max;
        this.min = min;
        this.associatedParts = associatedParts;
    }

    /**
     * Gets Product id.
     * @return Product id.
     */
    public int getId () { return id; }

    /**
     * Sets Product id.
     * @param id int passed into method.
     */
    public void setId (int id){ this.id = id; }

    /**
     * Gets Product name.
     * @return Product name.
     */
    public String getName () {
        return name;
    }

    /**
     * Sets Product name.
     * @param name String passed into method.
     */
    public void setName (String name){
        this.name = name;
    }

    /**
     * Gets Product stock.
     * @return Product stock quantity.
     */
    public int getStock () {
        return stock;
    }

    /**
     * Sets Product stock.
     * @param stock int passed into method.
     */
    public void setStock ( int stock){
        this.stock = stock;
    }

    /**
     * Gets Product price.
     * @return Product price
     */
    public double getPrice () {
        return price; }

    /**
     * Sets Product price.
     * @param price double passed into method.
     */
    public void setPrice ( double price){
        this.price = price;
    }

    /**
     * Gets min quantity.
     * @return Product min.
     */
    public int getMin () {
        return min;
    }

    /**
     * Sets min quantity.
     * @param min int passed into method.
     */
    public void setMin ( int min){
        this.min = min;
    }

    /**
     * Gets max quantity.
     * @return Product max.
     */
    public int getMax () {
        return max;
    }

    /**
     * Sets max quantity.
     * @param max int passed into method.
     */
    public void setMax ( int max){
        this.max = max;
    }

    /**
     * This method adds a part to the associated parts list.
     * @param part Selected part from table.
     */
    public void addAssociatedPart(Part part){

            associatedParts.add(part);
    }

    /**
     * This method removes an associated part from the list.
     * @param selectedAssociatedPart Selected from the parts table.
     * @return True if part was removed from list, False if not removed.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        associatedParts.remove(selectedAssociatedPart);

        return !associatedParts.contains(selectedAssociatedPart);
    }

    /**
     * Public method to get the private part list.
     * @return associatedParts list.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }


    }
