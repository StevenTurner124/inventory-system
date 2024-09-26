package turner.inventorymanagement;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Initializes the controller for Add Part Screen
 */
//This is the controller for the add part screen//
public class AddPartFormController implements Initializable {

@FXML
private AnchorPane addpartscreen;

@FXML
private TextField addpartidtext;

@FXML
private TextField addpartnametext;

@FXML
private TextField addpartinvtext;

@FXML
private TextField addpartpricetext;

@FXML
private TextField addpartmaxtext;

@FXML
private TextField addpartmintext;

@FXML
private TextField addpartswitchtext;

@FXML
private ToggleGroup outsourcedorinhouse;

@FXML
private RadioButton addpartinhouseradio;

@FXML
private RadioButton addpartoutsourcedradio;

@FXML
private Label addpartswitchlabel;

    /**
     * @param event
     * @throws IOException
     * Uses Cancel Button to return to main menu
     */
//Button returns user to the main screen//
//RUNTIME ERROR application kept deleting even when canceling. Added in Optional to only allow when hitting ok.//
@FXML
public void onActionCancelAddPart(ActionEvent event) throws IOException {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Alert");
    alert.setContentText("Cancel adding this part?");
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
     *Changes field to Machine ID when In-House is selected
     */

    @FXML
    public void onActionInHouseSelect(ActionEvent event) {
    addpartswitchlabel.setText("Machine ID");
    }

    /**
     * @param event
     * Changes the field when Outsourced is selected
     */

    @FXML
    public void onActionOutsourcedSelect(ActionEvent event){
    addpartswitchlabel.setText("Company Name");
    }

    /**
     * @param event
     * @throws IOException
     * RUNTIME ERROR occured during saving a part, and it was solved using try/catch method to validate input. This adds part to inventory
     */

    public void onActionSavePart(ActionEvent event) throws IOException{
    int id =0;
    for (Part part: Inventory.getAllParts()){
        if(part.getId()>id){
            id=part.getId();
        }
    }
    try{
        String name= addpartnametext.getText();
        int inventorylevel= Integer.parseInt(addpartinvtext.getText());
        double price= Double.parseDouble(addpartpricetext.getText());
        int min= Integer.parseInt(addpartmintext.getText());
        int max= Integer.parseInt(addpartmaxtext.getText());

        if( max<min){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Minimum must be less than the maximum.");
            alert.showAndWait();
            return;
        }
        if(inventorylevel>max || inventorylevel<min){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Stock must be between the min and max allowed.");
            alert.showAndWait();
            return;
        }

        Part partnew;
        int newid = 0;
        for (Part tempPart : Inventory.getAllParts()) {
            if (tempPart.getId() > newid)
                newid = tempPart.getId()+1;

        }

        if(outsourcedorinhouse.getSelectedToggle().equals(addpartinhouseradio)) {



             partnew = new InHouse(
                    newid+1,
                    addpartnametext.getText(),
                    Double.parseDouble(addpartpricetext.getText()),
                    Integer.parseInt(addpartinvtext.getText()),
                    Integer.parseInt(addpartmintext.getText()),
                    Integer.parseInt(addpartmaxtext.getText()),
                    Integer.parseInt(addpartswitchtext.getText()));

            Inventory.addPart(partnew);

        }
        else{
            partnew = new Outsourced(
                    newid+1,
                    addpartnametext.getText(),
                    Double.parseDouble(addpartpricetext.getText()),
                    Integer.parseInt(addpartinvtext.getText()),
                    Integer.parseInt(addpartmintext.getText()),
                    Integer.parseInt(addpartmaxtext.getText()),
                    addpartswitchtext.getText());

            Inventory.addPart(partnew);


        }
        Stage stage=(Stage)((Button)event.getSource()).getScene().getWindow();
        URL mainScreenLocation= HelloApplication.class.getResource("MainScreen.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
        Parent root=fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    catch (NumberFormatException e){
        e.printStackTrace();
        Alert alert= new Alert((Alert.AlertType.ERROR));
        alert.setTitle("Alert");
        alert.setContentText("Enter correct Values");
        alert.showAndWait();
    }
    }
@Override
    public void initialize (URL url, ResourceBundle resourceBundle){
}
}
