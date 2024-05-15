/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.io.IOException;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class LogIn2Controller implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button logInButton;
    @FXML
    private Button signupButton;
    @FXML
    private Hyperlink forgotPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BooleanBinding allFieldsFilled = Bindings.createBooleanBinding(() -> 
      !usernameField.getText().isEmpty() && !passwordField.getText().isEmpty(), usernameField.textProperty(), passwordField.textProperty()
);
        logInButton.disableProperty().bind(allFieldsFilled.not());
    }    
    
    @FXML
    void signUp(ActionEvent event) throws IOException{
      FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/view/SignUp.fxml"));
        Stage stage = new Stage();
        Parent root = miCargador.load();
        Scene scene = new Scene(root);
        scene.getRoot().requestFocus();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Sign Up");
        stage.show();
    }

    @FXML
    private void logIn(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        currentStage.setScene(scene);
        currentStage.setTitle("Expenses Manager");
        currentStage.show(); 
    }

    @FXML
    private void forgotPassword(ActionEvent event) throws IOException{
        Stage stage = new Stage();
    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Forgot_password.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getRoot().requestFocus();
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setScene(scene);
        stage.setTitle("Reset password");
        stage.show(); 
    }
}
