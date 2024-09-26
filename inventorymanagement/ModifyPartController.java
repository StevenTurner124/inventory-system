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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * /creates controller for modify parts screen//
 */

public class ModifyPartController implements Initializable {
    List<Part> parts= new ArrayList<Part>();
    private ObservableList<Part> oparts = FXCollections.observableList(parts);

    Parent scene;
    Stage stage;
    private int index;
    private Part selectedPart;

    @FXML
    private AnchorPane modifypartsscreen;

    @FXML
    private Label partswitchlabel;

    @FXML
    private RadioButton partinhouseradio;

    @FXML
    private RadioButton partoutsourcedradio;

    @FXML
    private TextField modifypartidtext;

    @FXML
    private TextField modifypartnametext;

    @FXML
    private TextField modifypartinvtext;

    @FXML
    private TextField modifypartpricetext;

    @FXML
    private TextField modifypartmaxtext;

    @FXML
    private TextField modifypartmintext;

    @FXML
    private TextField modifypartswitchtext;

    @FXML
    private ToggleGroup inhouseoroutsourced;
    @FXML
    private Button modpartsave;

    @FXML
    private Button modpartcancel;

    /**
     * @param event
     * //Sets label to company name if outsourced is selected//
     */

    @FXML
    public void onActionOutsourcedSelect(ActionEvent event){
        partswitchlabel.setText("Company Name");
    }

    /**
     * @param event
     * //Sets Machine ID if In House is selected//
     */

    @FXML
    public void onActionInHouseSelect(ActionEvent event){
        partswitchlabel.setText("Machine ID");
    }

    /**
     * @param event
     * @throws IOException
     * //Return user to Main Screen//
     */

    @FXML
    public void onActionCancelModPart(ActionEvent event)throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Return to Main Screen?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            Stage stage=(Stage)((Button)event.getSource()).getScene().getWindow();
            URL mainScreenLocation= HelloApplication.class.getResource("MainScreen.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
            Parent root=fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * @param event
     * @throws IOException
     * //Saves new part,deletes old variaton//
     *     //RUNTIME ERROR during save, try/catch fixed issue//
     */

@FXML
public void onActionSaveModPart(ActionEvent event) throws IOException {
    try {
        String name = modifypartnametext.getText();
        int stock = Integer.parseInt(modifypartinvtext.getText());
        double price = Double.parseDouble(modifypartpricetext.getText());
        int min = Integer.parseInt(modifypartmintext.getText());
        int max = Integer.parseInt(modifypartmaxtext.getText());

        if (max < min) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Minimum should be less than the maximum.");
            alert.showAndWait();
            return;
        }
        if (stock > max || stock < min) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Inventory value should be between the minimum and maximum values.");
            alert.showAndWait();
            return;
        }
        if (inhouseoroutsourced.getSelectedToggle().equals(partinhouseradio)) {
            Part modifiedParts = new InHouse(
                    Integer.parseInt(modifypartidtext.getText()),
                    modifypartnametext.getText(),
                    Double.parseDouble(modifypartpricetext.getText()),
                    Integer.parseInt(modifypartinvtext.getText()),
                    Integer.parseInt(modifypartmintext.getText()),
                    Integer.parseInt(modifypartmaxtext.getText()),
                    Integer.parseInt(modifypartswitchtext.getText()));

            Inventory.addPart(modifiedParts);
            Inventory.deletePart(selectedPart);
        }
        if (inhouseoroutsourced.getSelectedToggle().equals(partoutsourcedradio)) {
            Part modifiedParts = new Outsourced(Integer.parseInt(modifypartidtext.getText()),
                    modifypartnametext.getText(),
                    Double.parseDouble(modifypartpricetext.getText()),
                    Integer.parseInt(modifypartinvtext.getText()),
                    Integer.parseInt(modifypartmintext.getText()),
                    Integer.parseInt(modifypartmaxtext.getText()),
                    modifypartswitchtext.getText());

            Inventory.addPart(modifiedParts);
            Inventory.deletePart(selectedPart);

        }
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
     * @param modifiedpart
     *  //In house or outsourced part//
     */

    public void getPartSelected(Part modifiedpart){
        selectedPart=modifiedpart;

        modifypartidtext.setText(String.valueOf(selectedPart.getId()));
        modifypartnametext.setText(String.valueOf(selectedPart.getName()));
        modifypartinvtext.setText(String.valueOf(selectedPart.getStock()));
        modifypartpricetext.setText(String.valueOf(selectedPart.getPrice()));
        modifypartmintext.setText(String.valueOf(selectedPart.getMin()));
        modifypartmaxtext.setText(String.valueOf(selectedPart.getMax()));

        if(selectedPart instanceof InHouse){
            partinhouseradio.setSelected(true);
            partswitchlabel.setText("Machine ID");
            modifypartswitchtext.setText(String.valueOf(((InHouse)selectedPart).getMachineId()));
        }
        if(selectedPart instanceof Outsourced){
            partoutsourcedradio.setSelected(true);
            partswitchlabel.setText("Company Name");
            modifypartswitchtext.setText(((Outsourced)selectedPart).getCompanyName());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
}

}