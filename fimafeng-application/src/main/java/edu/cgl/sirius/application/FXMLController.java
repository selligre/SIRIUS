package edu.cgl.sirius.application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FXMLController implements Initializable {

    @FXML
    private TextField searchBar;
    private Pane contentPane;

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
    private void handleActivities(ActionEvent event) throws IOException {
        System.out.println("handleActivities");
        displayActivities();
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

    @FXML
    public void displayActivities() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("InsertView.fxml"));
        Stage stage = (Stage) contentPane.getScene().getWindow();
        stage.setScene(new Scene(root, 1280, 720));
    }
}
