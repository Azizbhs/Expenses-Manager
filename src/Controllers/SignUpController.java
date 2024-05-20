/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import java.net.URL;
import java.io.IOException;
import javafx.util.Duration;
import javafx.application.Platform;
import javafx.scene.image.Image;
import java.time.LocalDate;
import javafx.stage.Stage;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import java.util.ResourceBundle;
import javax.swing.Timer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller class
 *  
 * 
 * @author MABENHAS
 */
public class SignUpController implements Initializable {
   
    
    private String firstName;
    private String surName;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField nickname;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField repeatPassword;
    @FXML
    private TextField email;
    @FXML
    private Button signUpButton;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BooleanBinding allFieldsFilled = Bindings.createBooleanBinding(() -> 
            !name.getText().isEmpty() && 
            !repeatPassword.getText().isEmpty() && 
            !repeatPassword.getText().isEmpty() && 
            !surname.getText().isEmpty() && 
            !email.getText().isEmpty() &&
            !nickname.getText().isEmpty(), 
            name.textProperty(), 
            password.textProperty(), 
            repeatPassword.textProperty(), 
            surname.textProperty(), 
            email.textProperty(), 
            nickname.textProperty()
        );

        signUpButton.disableProperty().bind(allFieldsFilled.not());
        
        errorLabel.setVisible(false);
        
        
        
    }

    public String getUserName() {
        return nickname.getText();
    }

    // Method to get text from PasswordField2
    public String getPassword() {
        return password.getText();
    }

    // Method to get text from RepeatPassword
    public String getRepeatPassword() {
        return repeatPassword.getText();
    }

    // Method to get text from RealName
    public String getName() {
        return name.getText();
    }

    // Method to get text from Email
    public String getEmail() {
        return email.getText();
    }    
    
    public String getSurname(){
        return surname.getText();
    }
    
    public boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }
    
    public boolean areEqualPasswords(String password, String repeatPassword){
     return password.equals(repeatPassword);
    }
    
   

    @FXML
    private void signUp(ActionEvent event) throws IOException, AcountDAOException {
        boolean isValid = true;
        boolean success = false;
        LocalDate currentDate = LocalDate.now();
        Image image = new Image("image/edit_Profile.png");

        if (!isValidEmail(getEmail())) {
            errorLabel.setText("Invalid email address");
            errorLabel.setVisible(true);
            isValid = false;
        } else if (!areEqualPasswords(getPassword(), getRepeatPassword())) {
            errorLabel.setText("Passwords do not match");
            errorLabel.setVisible(true);
            isValid = false;
        } else if (getUserName().equals(getName())) {
            errorLabel.setText("Nickname cannot be equal to your real name");
            errorLabel.setVisible(true);
            isValid = false;
        } else if (getUserName().contains(" ")) {
            errorLabel.setText("Nickname cannot contain blank spaces!");
            errorLabel.setVisible(true);
            isValid = false;
        } else if (getPassword().length() < 6) {
            errorLabel.setText("Password is too short!");
            errorLabel.setVisible(true);
            isValid = false;
        }

        if (isValid) {
            success = Acount.getInstance().registerUser(getName(), getSurname(), getEmail(), getUserName(), getPassword(), image, currentDate);
        }

        if (!isValid || !success) {
            name.clear();
            surname.clear();
            email.clear();
            password.clear();
            repeatPassword.clear();
            nickname.clear();
        } else {
            errorLabel.setStyle("-fx-text-fill: green;");
            errorLabel.setText("You have been registered!");
            errorLabel.setVisible(true);

            // Create a Timeline to delay closing the window
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
                ((Stage) nickname.getScene().getWindow()).close();
            }));
            timeline.play();
        }
    }

   }

