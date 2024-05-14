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
    }    
    
    @FXML
private void categories(ActionEvent event) throws IOException {
    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Categories.fxml"));
    Parent root = loader.load();
    
    Scene scene = new Scene(root);
    
    currentStage.setScene(scene);
    currentStage.setTitle("Expenses Manager");
    currentStage.show();   
}


    @FXML
    private void imagenOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(Project2.getStage());
        //Se establece la imagen tanto en el menu como en el miembro
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            changePhoto.setImage(image);
            //Member currentMember = GreenBallApp.getMember();
            //currentMember.setImage(image);
        }
    }

    @FXML
    private void cuentaOnAction(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/User_data.fxml"));
        Parent root = loader.load();

        Project2.setRoot(root);
    }

    @FXML
    private void expenses(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Expenses.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

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

        currentStage.setScene(scene);
        currentStage.setTitle("Expenses Manager");
        currentStage.show();  
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
    // Guardar el Stage actual en una variable
    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

    // Mostrar el diálogo de confirmación
    Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
    confirmationDialog.setTitle("Confirmation");
    confirmationDialog.setHeaderText("Confirm Action");
    confirmationDialog.setContentText("Are you sure you want to perform this action?");

    // Personalizar los botones del diálogo
    confirmationDialog.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

    // Manejar la respuesta del usuario
    confirmationDialog.showAndWait().ifPresent(response -> {
        if (response == ButtonType.YES) {
            try {
                // Cargar el nuevo FXML para el inicio de sesión
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LogIn.fxml"));
                Parent root = loader.load();

                // Crear una nueva escena con el FXML cargado
                Scene scene = new Scene(root);

                // Establecer la nueva escena en el Stage actual
                currentStage.setScene(scene);
                currentStage.setTitle("Expenses Manager");
                currentStage.show(); 
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
}


}
