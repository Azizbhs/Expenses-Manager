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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author leoda
 */
public class User_dataController implements Initializable {

    @FXML
    private Button home;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField email;
    @FXML
    private Button image;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField repeatPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void home(ActionEvent event) {
    }

    @FXML
    private void add_image(ActionEvent event) {
    }
    
}
