package turner.inventorymanagement;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * //creates modify product controller class//
 */

public class ModifyProductController implements Initializable {

    Parent scene;
    Stage stage;

    private Product selectedProduct;
    int index;

    private ObservableList<Part> associatedParts = observableArrayList();
    private ObservableList<Part> prevAssocPart = observableArrayList();

    private ObservableList<Product> products = observableArrayList();

    @FXML
    private AnchorPane modifyproduct;

    @FXML
    private TextField modifyproductid;

    @FXML
    private TextField modifyproductname;

    @FXML
    private TextField modifyproductinv;

    @FXML
    private TextField modifyproductprice;

    @FXML
    private TextField modifyproductmax;

    @FXML
    private TextField modifyproductmin;

    @FXML
    private TextField modprodpartsearch;

    @FXML
    private TableView<Part> modprodparttable;

    @FXML
    private TableColumn<Part, Integer> partidparttable;


    @FXML
    private TableColumn<Part, String> partnameparttable;

    @FXML
    private TableColumn<Part, Integer> invlevelparttable;

    @FXML
    private TableColumn<Part, Double> priceparttable;

    @FXML
    private TableView<Part> assocpartstable;

    @FXML
    private TableColumn<Part, Integer> partidassoctable;

    @FXML
    private TableColumn<Part, String> partnameassoctable;

    @FXML
    private TableColumn<Part, Integer> invassoctable;

    @FXML
    private TableColumn<Part, Double> priceassoctable;

    @FXML
    private Button modprodaddbutton;

    @FXML
    private Button modprodremoveassociated;

    @FXML
    private Button modprodsave;


    @FXML
    private Button modprodcancel;
    @FXML
    private Button modprodpartsearchbtn;

    /**
     * @param event
     * @throws IOException
     * //Search button allowed to parse the part table//
     */

    @FXML
    public void onActionModProdSearch(ActionEvent event) throws IOException{
        ObservableList<Part> foundparts= searchForParts( modprodpartsearch.getText());
        modprodparttable.setItems(foundparts);
    }



    /**
     * @param event
     * @throws IOException
     * //Adds part to associated parts table//
     */
    @FXML
    public void onActionAddPartModProd(ActionEvent event) throws IOException {

        Part part = modprodparttable.getSelectionModel().getSelectedItem();
        associatedParts.add(part);
        assocpartstable.setItems(associatedParts);
    }

    /**
     * @param event
     * @throws IOException
     *  //Return to home screen//
     */

    @FXML
    public void onActionCancelModProd(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Cancel modifications?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            URL mainScreenLocation = HelloApplication.class.getResource("MainScreen.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * @param event
     * @throws IOException
     *   //Removes association between part and product//
     */

    @FXML
    public void onActionRemoveAssocPartModProd(ActionEvent event) throws IOException {
        Part selectedAssociatedPart = assocpartstable.getSelectionModel().getSelectedItem();

        if (selectedAssociatedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select an associated part.");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Remove the selected associated part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            associatedParts.remove(selectedAssociatedPart);
            assocpartstable.setItems(associatedParts);
        }
    }

    /**
     * @param event
     * @throws IOException
     * //RUNTIME ERROR occurred while saving product. Try/Catch fixed it, validates input and alerts user. Updates current part and removes part prior to modification//
     */

    @FXML
    public void onActionSaveModProd(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(modifyproductid.getText());
            String name = modifyproductname.getText();
            int stock = Integer.parseInt(modifyproductinv.getText());
            double price = Double.parseDouble(modifyproductprice.getText());
            int min = Integer.parseInt(modifyproductmin.getText());
            int max = Integer.parseInt(modifyproductmax.getText());

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Minimum should be less than the maximum.");
                alert.showAndWait();
                return;
            }
            if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Alert");
                alert.setContentText("Inventory should be between the minimum and maximum allowed.");
                alert.showAndWait();
                return;
            }
            Product modifyproduct = new Product(id, name, price, stock, min, max);
            for (Part part : associatedParts) {
                modifyproduct.addAssociatedPart(part);
            }
            Inventory.addProduct(modifyproduct);
            Inventory.deleteProduct(selectedProduct);


            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            URL mainScreenLocation = HelloApplication.class.getResource("MainScreen.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Enter a valid value.");
            alert.showAndWait();
            e.printStackTrace();
            return;
        }
    }


    /**
     * @param product
     * //Data transmitted between main controller and this one//
     */
    public void getSelectedProduct(Product product) {
        selectedProduct = product;

        modifyproductid.setText(Integer.toString(product.getId()));
        modifyproductname.setText(product.getName());
        modifyproductprice.setText((Double.toString(product.getPrice())));
        modifyproductinv.setText(Integer.toString(product.getStock()));
        modifyproductmin.setText(Integer.toString(product.getMin()));
        modifyproductmax.setText(Integer.toString(product.getMax()));
        associatedParts = product.getAllAssociatedParts();
        assocpartstable.setItems(associatedParts);
    }

    /**
     * @param url
     * @param resourceBundle
     * //Populate data and start the controllers//
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedProduct = new Product(0, "", 0.0, 0, 0, 0);
        associatedParts = selectedProduct.getAllAssociatedParts();

        partidparttable.setCellValueFactory(new PropertyValueFactory<>("id"));
        partnameparttable.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceparttable.setCellValueFactory(new PropertyValueFactory<>("price"));
        invlevelparttable.setCellValueFactory(new PropertyValueFactory<>("inventory"));
        modprodparttable.setItems(Inventory.getAllParts());

        partidassoctable.setCellValueFactory(new PropertyValueFactory<>("id"));
        partnameassoctable.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceassoctable.setCellValueFactory(new PropertyValueFactory<>("price"));
        invassoctable.setCellValueFactory(new PropertyValueFactory<>("inventory"));
        assocpartstable.setItems(selectedProduct.getAllAssociatedParts());
    }

    /**
     * @param event
     *     //Search parts table//
     */

    public void onActionSearchParts(ActionEvent event) {

        String s = modprodpartsearch.getText();
        ObservableList<Part> parts = searchForParts(s);

        modprodparttable.setItems(parts);

    }

    /**
     * @param searchString
     * @return
     * //Partial or full ID/Name search//
     */

    private ObservableList<Part>searchForParts(String searchString){
     ObservableList<Part> allParts = Inventory.getAllParts();
     ObservableList<Part> foundParts= FXCollections.observableArrayList();


    for(Part p:allParts) {

        if (String.valueOf(p.getId()).contains(searchString) || p.getName().contains(searchString)) {
            foundParts.add(p);
        }
    }
    if(foundParts.isEmpty())

    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("Part was not found");
        alert.showAndWait();
    }
    return foundParts;
}
 }
