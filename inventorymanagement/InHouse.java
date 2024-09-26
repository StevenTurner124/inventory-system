package turner.inventorymanagement;


public class InHouse extends Part {


    private int machineId;

    /**
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     *  Inhouse constructor.
     */

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @param machineId
     *   Inhouse machineId setter.
     */

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * @return
     * Inhouse machineId getter.
     */
    // Inhouse machineId getter.//
    public int getMachineId() {
        return machineId;
    }


}