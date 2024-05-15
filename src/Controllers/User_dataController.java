/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

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
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import project2.Project2;

/**
 * FXML Controller class
 *
 * @author leoda
 */
public class User_dataController implements Initializable {

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
    @FXML
    private SplitPane splitPane;
    @FXML
    private ImageView profileImage;
    @FXML
    private Button save;
    @FXML
    private Button cancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }    

    @FXML
    private void add_image(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(Project2.getStage());
        //Se establece la imagen tanto en el menu como en el miembro
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            profileImage.setImage(image);
            //Member currentMember = GreenBallApp.getMember();
            //currentMember.setImage(image);
        }
    }

    @FXML
    private void save(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Save changes?");
        confirmationDialog.setContentText("Are you sure you want to perform this action?");

        confirmationDialog.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
                    Parent root = loader.load();

                    Scene scene = new Scene(root);

                    currentStage.setScene(scene);
                    currentStage.setTitle("Expenses Manager");
                    currentStage.show(); 
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else{
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
                    Parent root = loader.load();

                    Scene scene = new Scene(root);

                    currentStage.setScene(scene);
                    currentStage.setTitle("Expenses Manager");
                    currentStage.show(); 
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    private void cancel(ActionEvent event) throws IOException{
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("No changes will be saved.");
        confirmationDialog.setContentText(" Do you wish to proceed?");

        confirmationDialog.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
                    Parent root = loader.load();

                    Scene scene = new Scene(root);

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
