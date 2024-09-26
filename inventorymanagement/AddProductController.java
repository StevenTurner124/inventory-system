package turner.inventorymanagement;

import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Class Controller for product add screen
 */

public class AddProductController  implements Initializable{
    @FXML
    private AnchorPane addproductscreen;

    @FXML
    private TextField addproductid;

    @FXML
    private TextField addproductname;

    @FXML
    private TextField addproductinv;

    @FXML
    private TextField addproductprice;

    @FXML
    private TextField addproductmax;

    @FXML
    private TextField addproductmin;

    @FXML
    private TextField partsearch;

    @FXML
    private TableView<Part> addproductparttable;

    @FXML
    private TableColumn<Part, Integer> addpartid;

    @FXML
    private TableColumn<Part, String> addpartname;

    @FXML
    private TableColumn<Part, Integer> addpartinventory;

    @FXML
    private TableColumn<Part, Double> addpartprice;

    @FXML
    private TableView<Part> addproductassociatedpartdata;

    @FXML
    private TableColumn<Part, Integer> addassociatedpartid;

    @FXML
    private TableColumn<Part, String> addassociatedpartname;

    @FXML
    private TableColumn<Part, Integer> addassociatedpartinventory;

    @FXML
    private TableColumn<Part, Double> addassociatedpartprice;

    @FXML
    private Button addprodsearchbtn;

    /**
     * @param event
     * @throws IOException
     * Searches the part table on Add Product Screen
     */

    @FXML
    public void onActionProdAddPartSearch(ActionEvent event) throws IOException{
        ObservableList<Part> foundparts= searchParts( partsearch.getText());
        addproductparttable.setItems(foundparts);
    }

    Parent scene;
    Stage stage;
    public Part addParttoProduct;
    private ObservableList<Part> associatedParts= FXCollections.observableArrayList();



    /**
     * @param event
     * This adds part from part table to the associated parts table for the product
     */
    public  void onActionAddAssocPart(ActionEvent event){
        Part part= addproductparttable.getSelectionModel().getSelectedItem();
        associatedParts.add(part);
        addproductassociatedpartdata.setItems(associatedParts);
    }

    /**
     * @param event
     * @throws IOException
     * Button takes user back to main menu
     */

    public void onActionCancelAddProd(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Cancel and return to main menu");
        alert.showAndWait();

        Stage stage=(Stage)((Button)event.getSource()).getScene().getWindow();
        URL mainScreenLocation= HelloApplication.class.getResource("MainScreen.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
        Parent root=fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param event
     * @throws IOException
     * Removes part from product and from associated table
     */

    @FXML
    public void onActionRemoveAssocPart(ActionEvent event) throws IOException {
        Part selectedAssociatedPart = addproductassociatedpartdata.getSelectionModel().getSelectedItem();
        if (selectedAssociatedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Choose an associated part first.");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Remove associated part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            associatedParts.remove(selectedAssociatedPart);
        }
    }

    /**
     * @param event
     * @throws IOException
     * RUNTIME ERROR occurred during saving of product, solved via try and catch, validates user input and alerts user//
     *     //Saves Product using button
     */

    @FXML
    public void onActionSaveProduct(ActionEvent event) throws IOException {
        int id = 0;
        for (Product product : Inventory.getAllProducts()) {
            if (product.getId() > id)
                id = product.getId()+1;
        }
        try {

            addpartid.setText(String.valueOf(id));
            String name = addproductname.getText();
            int stock = Integer.parseInt(addproductinv.getText());
            double price = Double.parseDouble(addproductprice.getText());
            int min = Integer.parseInt(addproductmin.getText());
            int max = Integer.parseInt(addproductmax.getText());

            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Min must be less than max");
                alert.showAndWait();
                return;
            }

            Product newProduct = new Product(id+1, "", 0.0, 0, 0, 0);
            newProduct.setName(addproductname.getText());
            newProduct.setPrice(Double.parseDouble(addproductprice.getText()));
            newProduct.setStock(Integer.parseInt(addproductinv.getText()));
            newProduct.setMin(Integer.parseInt(addproductmin.getText()));
            newProduct.setMax(Integer.parseInt((addproductmax.getText())));

            Inventory.addProduct(newProduct);
            for (Part part : associatedParts) {
                newProduct.addAssociatedPart(part);
            }
            Stage stage=(Stage)((Button)event.getSource()).getScene().getWindow();
            URL mainScreenLocation= HelloApplication.class.getResource("MainScreen.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
            Parent root=fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Alert alert = new Alert((Alert.AlertType.ERROR));
            alert.setTitle("Alert");
            alert.setContentText("Enter correct values");
            alert.showAndWait();
        }
    }


    /**
     * @param url
     * @param resourceBundle
     *  Will set values for cells and populate tables
     */

@Override
          public   void initialize(URL url, ResourceBundle resourceBundle){
        addproductparttable.setItems(Inventory.getAllParts());
        addproductassociatedpartdata.setItems(associatedParts);

        addpartid.setCellValueFactory(new PropertyValueFactory<>("id"));
        addpartname.setCellValueFactory(new PropertyValueFactory<>("name"));
        addpartinventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addpartprice.setCellValueFactory(new PropertyValueFactory<>("price/cost per unit"));

        addassociatedpartid.setCellValueFactory(new PropertyValueFactory<>("id"));
        addassociatedpartname.setCellValueFactory(new PropertyValueFactory<>("name"));
        addassociatedpartinventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addassociatedpartprice.setCellValueFactory(new PropertyValueFactory<>("price/cost per unit"));
    }

    /**
     * @param partialPart
     * @return
     *  Searches part in part table of product screen
     */

    private ObservableList<Part> searchParts(String partialPart){
        ObservableList<Part>allParts= Inventory.getAllParts();
        ObservableList<Part> nameParts=FXCollections.observableArrayList();
        String s =partsearch.getText();
        for(Part p: allParts){
            if(String.valueOf(p.getId()).contains(s)|| p.getName().contains(partialPart)){
                nameParts.add(p);
            }
        }
        if(nameParts.isEmpty()){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("Part was not found");
            alert.showAndWait();
        }
        return nameParts;
    }
}
