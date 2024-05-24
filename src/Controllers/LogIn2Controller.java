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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;


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
    private Label erroeLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        erroeLabel.setVisible(false);
        
        BooleanBinding allFieldsFilled = Bindings.createBooleanBinding(() -> 
      !usernameField.getText().isEmpty() && 
    !passwordField.getText().isEmpty(), 
    usernameField.textProperty(), 
    passwordField.textProperty()
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
        stage.setScene(scene);
        stage.setMinWidth(400);
        stage.setMinHeight(600);
        stage.setResizable(false);
        stage.setTitle("Sign Up");
        try {
            stage.getIcons().add(new Image("/image/logo.png"))  ;
        }catch (Exception e){
            System.out.println("Image could not be loaded");
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void logIn(ActionEvent event) throws IOException, AcountDAOException {
        String userName = usernameField.getText();
        String password = passwordField.getText();
        
        
        boolean isOK = Acount.getInstance().logInUserByCredentials(userName, password);
       if(!isOK){
       erroeLabel.setVisible(true);
       usernameField.clear();
       passwordField.clear();
       }else{
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Home");
            stage.setMinHeight(530);
            stage.setMinWidth(715);
            stage.show();
            try {
            stage.getIcons().add(new Image("/image/logo.png"))  ;
        }catch (Exception e){
            System.out.println("Image could not be loaded");
        }
            // Close the current stage
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            currentStage.close();
        
       }
        
    }

    private void forgotPassword(ActionEvent event) throws IOException{
        Stage stage = new Stage();
    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Username.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getRoot().requestFocus();
        stage.initModality(Modality.APPLICATION_MODAL);
        try {
            stage.getIcons().add(new Image("/image/logo.png"))  ;
        }catch (Exception e){
            System.out.println("Image could not be loaded");
        }
        stage.setResizable(false);
        stage.setScene(scene);
        
        stage.setTitle("Reset password");
        stage.show(); 
    }
}