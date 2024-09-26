package turner.inventorymanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
//FUTURE ENHANCEMENT would add the company logo to main screen with company name//
// This class creates controller for the Main Screen//


//JAVADOC FOLDER LOCATED
public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;





    @FXML
    private AnchorPane mainscreen;


    @FXML
    private TableView<Part> partstable;

    @FXML
    private TableColumn<Part, Integer> partid;

    @FXML
    private TableColumn<Part, String> partsname;

    @FXML
    private TableColumn<Part, Integer> partsinv;

    @FXML
    private TableColumn<Part, Double> partscost;

    @FXML
    private TableView<Product> producttable;

    @FXML
    private TableColumn<Product, Integer> productsid;

    @FXML
    private TableColumn<Product, String> productsname;

    @FXML
    private TableColumn<Product, Integer> productsinv;

    @FXML
    private TableColumn<Product, Double> productscost;

    @FXML
    private TextField partsearchmain;

    @FXML
    private TextField productsearchmain;

    @FXML
    private Button partsaddbutton;

    @FXML
    private Button partsmodifybutton;

    @FXML
    private Button partsdeletebutton;

    @FXML
    private Button productsaddbutton;

    @FXML
    private Button productstsmodifybutton;

    @FXML
    private Button productsdeletebutton;

    @FXML
    private Button exitbutton;

    @FXML
    private Label systemlabel;

    @FXML
    private Button partsearchbtnmain;

    @FXML
    private Button prodsearchbtnmain;

    /**
     * @param event
     * @throws IOException
     * Searches Part Table on Main Screen+
     * JAVA DOC IS IN PACKAGE-SUMMARY
     */
@FXML
public void onActionPartSearchMain(ActionEvent event) throws IOException{
    ObservableList<Part> foundparts= searchParts( partsearchmain.getText());
    partstable.setItems(foundparts);
}

    /**
     * @param event
     * @throws IOException
     * Searches Products on Main Screen
     */
    @FXML
    public void onActionProdSearchMain(ActionEvent event) throws IOException {
        ObservableList<Product> foundprod= searchProduct( productsearchmain.getText());
        producttable.setItems(foundprod);
    }
    //Button will open Add Parts screen//

    /**
     * @param event
     * @throws IOException
     * Button Adds Parts
     */
    @FXML
    public void onActionAddPart(ActionEvent event) throws IOException {
        Stage stage=(Stage)((Button)event.getSource()).getScene().getWindow();
        URL mainScreenLocation= HelloApplication.class.getResource("AddPartScreen.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
        Parent root=fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param event
     * @throws IOException
     * Opens the Add Product Screen
     */
    //Button will open products add screen//
    @FXML
    public void onActionAddProd(ActionEvent event) throws IOException {
        Stage stage=(Stage)((Button)event.getSource()).getScene().getWindow();
        URL mainScreenLocation= HelloApplication.class.getResource("AddProductScreen.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
        Parent root=fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param event
     * Deletes the part from Part table
     */
    //Button deletes part//
    @FXML
    public void onActionDeletePart(ActionEvent event) {
        Part selectedPart = partstable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Choose a part first.");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Delete the part selected?");
        Optional<ButtonType>result=alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(selectedPart);
        }
    }

    /**
     * @param event
     * Deletes the Product from Product table
     */
    //Button deletes selected product//
    @FXML
    public void onActionDeleteProd(ActionEvent event) {
        Product selectedProduct = producttable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Choose a product first.");
            alert.showAndWait();
            return;
        }
        ObservableList<Part> associatedParts = selectedProduct.getAllAssociatedParts();
        if (associatedParts.size() >= 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Can not be deleted, product has associated parts.");
            alert.showAndWait();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Delete the selected product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(selectedProduct);
            }

        }
    }

    /**
     * @param event
     * Exits the App
     */
    //Button exits the Application//
    @FXML
    public void onActionExit(ActionEvent event) {
        System.exit(0);
    }
    //Button takes you to the modify Part screen//

    /**
     * @param event
     * @throws IOException
     * Opens the Modify Part Screen
     */
    @FXML
    public void onActionModPart(ActionEvent event) throws IOException {
        Part selectedPart = partstable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select a part first.");
            alert.showAndWait();
            return;
        }
        Stage stage=(Stage)((Button)event.getSource()).getScene().getWindow();
        URL mainScreenLocation= HelloApplication.class.getResource("ModifyPartScreen.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
        Parent root=fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        ModifyPartController controller= fxmlLoader.getController();
        controller.getPartSelected(selectedPart);
        stage.show();
    }
    //Button takes you to modify product//

    /**
     * @param event
     * @throws IOException
     * Opens the modify Product Screen
     */
    @FXML
    public void onActionModProd(ActionEvent event) throws IOException {
        Product selectedProduct = producttable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select a product first.");
            alert.showAndWait();

            return;
        }
        Stage stage=(Stage)((Button)event.getSource()).getScene().getWindow();
        URL mainScreenLocation= HelloApplication.class.getResource("ModifyProductScreen.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
        Parent root=fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        ModifyProductController controller= fxmlLoader.getController();
        controller.getSelectedProduct(selectedProduct);
        stage.show();
    }

    //Data for tables//
    private static boolean firstTime = true;

    /**
     * Adds Test data
     */
    public void addData() {
        if (!firstTime) {
            return;
        }
        firstTime = false;

        InHouse rim = new InHouse(1, "Rim", 20.00, 4, 3, 10, 4);
        Inventory.addPart(rim);

        Outsourced horn = new Outsourced(5, "Horn", 17.00, 2, 1, 6, "Abe's Horns");
        Inventory.addPart(horn);

        Product unicycle = new Product(15, "Unicycle", 135.00, 3, 1, 4);
        Inventory.addProduct(unicycle);

        Product bicycle = new Product(10, "Bicycle", 450.00, 2, 1, 7);
        bicycle.addAssociatedPart(rim);
        Inventory.addProduct(bicycle);
    }

    /**
     * @param url
     * @param resourceBundle
     * Populates and Initializes Controllers
     */
    //Populates tables and Initializes the controllers//
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addData();

        //parts//
        partstable.setItems(Inventory.getAllParts());
        partid.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsname.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsinv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partscost.setCellValueFactory(new PropertyValueFactory<>("price"));

        //products//
        producttable.setItems(Inventory.getAllProducts());
        productsid.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsname.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsinv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productscost.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
    //Search ability in parts table//

    /**
     * @param partialPart
     * @return
     * Searches through the Parts table
     */
    private ObservableList<Part> searchParts(String partialPart) {
        ObservableList<Part> nameParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();
        String s = partsearchmain.getText();

        for (Part p : allParts) {
            if (String.valueOf(p.getId()).contains(s) || p.getName().contains(partialPart)) {
                nameParts.add(p);
            }
        }
        if (nameParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alert");
            alert.setContentText("Part is not found");
            alert.showAndWait();
        }
        return nameParts;
    }

    /**
     * @param partialProduct
     * @return
     * Searches the Product Table
     */
    //Ability to search product in table//
    private ObservableList<Product> searchProduct(String partialProduct) {
        ObservableList<Product> nameProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        String s = productsearchmain.getText();

        for (Product p : allProducts) {
            if (String.valueOf(p.getId()).contains(s) || p.getName().contains(partialProduct)) {
                nameProducts.add(p);
            }

    }
        return nameProducts;
    }
}

