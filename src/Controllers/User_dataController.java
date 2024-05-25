/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.User;
import project2.Project2;

/**
 * FXML Controller class
 *
 * @author leoda
 */
public class User_dataController implements Initializable {

    @FXML
    private ImageView profileImage;
    @FXML
    private Button save;
    @FXML
    private Button cancel;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField email;
    @FXML
    private Button image;
    @FXML
    private Hyperlink forgotPassword;

    private String tempName;
    private String tempSurname;
    private String tempEmail;
    private Image tempImage;
    private String formerEmail;
    @FXML
    private Text label;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        formerEmail = email.getText();
        User user = null;
        try {
            user = Acount.getInstance().getLoggedUser();
        } catch (AcountDAOException ex) {
            Logger.getLogger(User_dataController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(User_dataController.class.getName()).log(Level.SEVERE, null, ex);
        }
        label.setText("Registered date: " + user.getRegisterDate().toString() + "\n" + label.getText());
        tempName = user.getName();
        tempSurname = user.getSurname();
        tempEmail = user.getEmail();
        tempImage = user.getImage();

        name.setText(tempName);
        surname.setText(tempSurname);
        email.setText(tempEmail);
        profileImage.setImage(tempImage);
    }

    @FXML
    private void add_image(ActionEvent event) throws AcountDAOException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(Project2.getStage());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            profileImage.setImage(image);
            tempImage = image;  // Update temporary image
        }
    }
    
    public boolean isValidEmail(String email) {
        String emailRegex = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
        return email.matches(emailRegex);
    }
    
    
    
    @FXML
    private void save(ActionEvent event){
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        
        
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Save changes?");
        confirmationDialog.setContentText("Are you sure you want to perform this action?");

        confirmationDialog.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    boolean valid = isValidEmail(email.getText());
                    
                    if(!valid){
                        
                        Alert warning = new Alert(Alert.AlertType.ERROR);
                        warning.setTitle("Invalid email format");
                        warning.setHeaderText("Email introduced not valid!");
                        warning.setContentText("Introduce right email format");
                        warning.showAndWait();
                        email.setText(tempEmail);
                        email.requestFocus();
                    }
                    else{
                        User user = Acount.getInstance().getLoggedUser();
                        user.setName(name.getText());
                        user.setSurname(surname.getText());
                        user.setEmail(email.getText());
                        user.setImage(tempImage);

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);

                        currentStage.setScene(scene);
                        currentStage.setTitle("Expenses Manager");
                        currentStage.show();
                    }
                    
                    
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (AcountDAOException a){
                    a.printStackTrace();
                }
            }
        });
    }

    @FXML
    private void cancel(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("No changes will be saved.");
        confirmationDialog.setContentText("Do you wish to proceed?");

        confirmationDialog.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                name.setText(tempName);
                surname.setText(tempSurname);
                email.setText(tempEmail);
                profileImage.setImage(tempImage);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException ex) {
                    Logger.getLogger(User_dataController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Scene scene = new Scene(root);

                currentStage.setScene(scene);
                currentStage.setTitle("Expenses Manager");
                currentStage.show(); 
            }
        });
    }

    @FXML
    private void forgotPassword(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Change_password.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        try {
            stage.getIcons().add(new Image("/image/logo.png"))  ;
        }catch (Exception e){
            System.out.println("Image could not be loaded");
        }
        scene.getRoot().requestFocus();
        stage.setScene(scene);
        stage.setTitle("Change password");
        stage.setResizable(false);
        stage.show();  
    }   
}