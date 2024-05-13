/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author leoda
 */
public class MainController implements Initializable {

    @FXML
    private Button logOut;
    @FXML
    private Button expenses;
    @FXML
    private Button showAccount;
    @FXML
    private Button image;
    @FXML
    private ImageView changePhoto;
    @FXML
    private Label labelWelcome;
    @FXML
    private Label labelNickName;
    @FXML
    private Hyperlink myAccount;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void logOut(ActionEvent event) {
    }

    @FXML
    private void expenses(ActionEvent event) {
    }

    @FXML
    private void showAccount(ActionEvent event) {
    }

    @FXML
    private void imagen(ActionEvent event) {
    }

    @FXML
    private void myAccount(ActionEvent event) {
    }
    
}
