package turner.inventorymanagement;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Inventory class setters and getters//

public class Inventory{
    // Parts Inv.//
    private static final ObservableList<Part> allParts= FXCollections.observableArrayList();
    //Products Inv//
    private static final ObservableList<Product> allProducts= FXCollections.observableArrayList();

    /**
     * @param newPart
     * Add parts
     */

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * @param newProduct
     *   Add products
     */

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    // Search parts by name//
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> partNameFound=FXCollections.observableArrayList();
        for (Part part: allParts){
            if(part.getName().equals(partName)){
                partNameFound.add(part);
            }
            return partNameFound;
        }
        return partNameFound;
    }
    //Search parts by ID//
    public static Part lookupPart(int partID) {
        Part  partIdFound=null;

        for(Part part: allParts){
            if(part.getId()==partID){
                partIdFound=part;
            }
        }
        return partIdFound;
    }
    //Searches products by name//
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> productNameFound = FXCollections.observableArrayList();

        for(Product product: allProducts){
            if(product.getName().equals(productName)){
                productNameFound.add(product);
            }
        }
        return productNameFound;
    }

    /**
     * @param productID
     * @return
     * searches list of products by ID and returns if found
     */
    public static Product lookupProduct(int productID){
        Product foundProductId=null;

        for(Product product : allProducts) {
            if (product.getId() == productID) {
                foundProductId = product;
            }
        }
        return foundProductId;
            }


    //updates Part//
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }
    //updates the product//
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    /**
     * @param selectedPart
     * @return
     *   //Deletes part selected//
     */

    public static boolean deletePart(Part selectedPart) {
        if(allParts.contains(selectedPart)){
            allParts.remove(selectedPart);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * @param selectedProduct
     * @return
     * // Deletes Product selected//
     */

    public static boolean deleteProduct(Product selectedProduct){
        if(allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * @return
     *  // All Parts returned//
     */

    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * @return
     *  // All Products Returned//
     */

    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}