/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author leoda
 */
public class LogIn2Controller implements Initializable {

    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private Button logIn;
    @FXML
    private Button signUp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void logIn(ActionEvent event) throws IOException{
        //make this to enter only if a user exists in the cloud
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        currentStage.setScene(scene);
        currentStage.setTitle("Expenses Manager");
        currentStage.show();  
    }

    @FXML
    private void signUp(ActionEvent event) throws IOException{
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/view/SignUp.fxml"));
        Stage stage = new Stage();
        Parent root = miCargador.load();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sign Up");
        stage.show();
    }
    
}
