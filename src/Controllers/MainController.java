/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import project2.Project2;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.User;
/**
 * FXML Controller class
 *
 * @author leoda
 */
public class MainController implements Initializable {


    @FXML
    private Button categories;
    @FXML
    private Button image;
    @FXML
    private ImageView changePhoto;
    @FXML
    private Label labelWelcome;
    @FXML
    private Label labelNickName;
    @FXML
    private Hyperlink linkMyAccount;
    @FXML
    private Button expenses;
    @FXML
    private Button account;
    @FXML
    private Button logOut;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        User user = null;
        try {
            user = Acount.getInstance().getLoggedUser();
        } catch (AcountDAOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        changePhoto.setImage(user.getImage());
        labelNickName.setText(labelNickName.getText() + " " + user.getNickName());
    }    
    
    @FXML
    private void categories(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Categories.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        try {
            currentStage.getIcons().add(new Image("/image/logo.png"))  ;
        }catch (Exception e){
            System.out.println("Image could not be loaded");
        }
        currentStage.setScene(scene);
        currentStage.setTitle("Expenses Manager");
        currentStage.show();   
}


    @FXML
    private void imagenOnAction(ActionEvent event) throws AcountDAOException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(Project2.getStage());
        //Se establece la imagen tanto en el menu como en el miembro
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            changePhoto.setImage(image);
            User user = Acount.getInstance().getLoggedUser();
            user.setImage(image);
        }
    }

    @FXML
    private void cuentaOnAction(ActionEvent event) throws IOException{
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/User_data.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        scene.getRoot().requestFocus();
        try {
            currentStage.getIcons().add(new Image("/image/logo.png"))  ;
        }catch (Exception e){
            System.out.println("Image could not be loaded");
        }
        currentStage.setScene(scene);
        currentStage.setTitle("Expenses Manager");
        currentStage.show();  
    }

    @FXML
    private void expenses(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Expenses.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        try {
            currentStage.getIcons().add(new Image("/image/logo.png"))  ;
        }catch (Exception e){
            System.out.println("Image could not be loaded");
        }
        currentStage.setScene(scene);
        currentStage.setTitle("Expenses Manager");
        currentStage.show();   
    }

    @FXML
    private void account(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Account.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        try {
            currentStage.getIcons().add(new Image("/image/logo.png"))  ;
        }catch (Exception e){
            System.out.println("Image could not be loaded");
        }
        currentStage.setScene(scene);
        currentStage.setTitle("Expenses Manager");
        currentStage.show();  
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

    Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
    confirmationDialog.setTitle("Confirmation");
    confirmationDialog.setHeaderText("Log out?");
    confirmationDialog.setContentText("Are you sure you want to perform this action?");

    confirmationDialog.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

    confirmationDialog.showAndWait().ifPresent(response -> {
        if (response == ButtonType.YES) {
            try {
                Acount.getInstance().logOutUser();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LogIn.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                scene.getRoot().requestFocus();

                currentStage.setScene(scene);
                currentStage.setTitle("Expenses Manager");
                currentStage.show(); 
            } catch (IOException e) {
                e.printStackTrace();
            } catch (AcountDAOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    });
}
}