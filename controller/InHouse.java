package controller;

/**
 * This class inherits from Part plus has a machine ID attribute.
 */
public class InHouse extends Part {

    private int machineId;

    /**
     * Constructor per UML, calls the Part superclass constructor.
     * @param id sets Id.
     * @param name sets name.
     * @param price sets price.
     * @param stock sets inventory.
     * @param min sets min.
     * @param max sets max.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
    }

    /**
     * Constructor with machineID parameter added.
      * @param id sets Id.
     * @param name sets name.
     * @param price sets price.
     * @param stock sets inventory.
     * @param min sets min.
     * @param max sets max.
     * @param machineId sets machineId.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Sets machineId field.
     * @param machineId An int is passed into the method.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Public method gets the private attribute machineId of an InHouse instance.
     * @return int indicating the InHouse part's machineId.
     */
    public int getMachineId() {
        return machineId;
    }
}
