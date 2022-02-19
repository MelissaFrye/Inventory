package controller;

/**
 * This class inherits from Part plus has a companyName attribute.
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * Constructor per UML, calls the Part superclass constructor.
     * @param id sets Id.
     * @param name sets name.
     * @param price sets price.
     * @param stock sets inventory.
     * @param min sets min.
     * @param max sets max.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
    }

    /**
     * Constructor with companyName attribute added.
     * @param id sets Id.
     * @param name sets name.
     * @param price sets price.
     * @param stock sets inventory.
     * @param min sets min.
     * @param max sets max.
     * @param companyName sets companyName.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Sets company name attribute.
     * @param companyName String passed into method.
     */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    /**
     * Public method gets private attribute companyName.
     * @return companyName string value.
     */
    public String getCompanyName(){
        return companyName;
    }
}
