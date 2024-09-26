package turner.inventorymanagement;

public class Outsourced extends Part{

    // company name setter//
    private String companyName;

    /**
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     * //Constructor for Outsourced//
     */

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id,name,price,stock,min,max);
        this.companyName=companyName;
    }

    /**
     * @param companyName
     * //Outsourced Company name Setter//
     */

    public void setCompanyName(String companyName) {
        this.companyName=companyName;
    }

    /**
     * @return
     *  //Outsourced Company Name Getter//
     */

    public String getCompanyName() {
        return companyName;
    }
}