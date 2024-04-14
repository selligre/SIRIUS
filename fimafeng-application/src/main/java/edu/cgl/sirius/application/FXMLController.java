package edu.cgl.sirius.application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class FXMLController implements Initializable {

    @FXML
    private TextField searchBar;

    @FXML
    private void handleLogo(ActionEvent event) {
        System.out.println("handleLogo");
    }

    @FXML
    private void handleCreate(ActionEvent event) {
        System.out.println("handleCreate");
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        System.out.println("handleSearch: " + searchBar.getText() + ".");
    }

    @FXML
    private void handleAccount(ActionEvent event) {
        System.out.println("handleAccount");
    }

    @FXML
    private void handleLogOut(ActionEvent event) {
        System.out.println("handleLogOut");
    }

    @FXML
    private void handleActivities(ActionEvent event) {
        System.out.println("handleActivities");
    }

    @FXML
    private void handleMaterials(ActionEvent event) {
        System.out.println("handleMaterials");
    }

    @FXML
    private void handleServices(ActionEvent event) {
        System.out.println("handleServices");
    }

    @FXML
    private void handleAroundMe(ActionEvent event) {
        System.out.println("handleAroundMe");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Idk what this is supposed to do lmao
    }
}
