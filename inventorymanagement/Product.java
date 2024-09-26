package turner.inventorymanagement;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Product{

    private ObservableList<Part> associatedParts= FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     *  // constructor for Product//
     */

    public Product(int id, String name, double price, int stock, int min, int max){
        this.id=id;
        this.name=name;
        this.price=price;
        this.stock=stock;
        this.min=min;
        this.max=max;
    }

    /**
     * @return
     * //id getter//
     */

    public int getId() {
        return id;
    }

    /**
     * @param id
     * //id setter//
     */

    public void setId(int id) {
        this.id=id;
    }

    /**
     * @return
     * /Name getter//
     */

    public String getName(){
        return name;
    }

    /**
     * @param name
     * //Name Setter//
     */

    public void setName(String name) {
        this.name=name;
    }

    /**
     * @return
     * / Price Getter//
     */

    public double getPrice() {
        return price;
    }

    /**
     * @param price
     * //Price Setter//
     */

    public void setPrice(double price) {
        this.price=price;
    }

    /**
     * @return
     * //Stock Getter//
     */

    public int getStock() {
        return stock;
    }

    /**
     * @param stock
     * //Stock setter//
     */

    public void setStock(int stock) {
        this.stock=stock;
    }

    /**
     * @return
     *    //Min getter//
     */

    public int getMin() {
        return min;
    }

    /**
     * @param min
     * //Min setter//
     */

    public void setMin(int min) {
        this.min=min;
    }

    /**
     * @return
     * //Max getter//
     */

    public int getMax() {
        return max;
    }

    /**
     * @param max
     *  //Max setter//
     */

    public void setMax(int max) {
        this.max=max;
    }

    /**
     * @param part
     * //Associated to Product part setter//
     */

    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }
    //Associated to Product part delete//
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if(associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * @return
     * // Associated to Product part getter/
     */

    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
    public void remove(Part part){
    }
}
