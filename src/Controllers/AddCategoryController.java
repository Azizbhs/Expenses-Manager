/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller class
 *
 * @author leoda
 */
public class AddCategoryController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField description;
    @FXML
    private Label errorLabel;
    @FXML
    private Button cancel;
    @FXML
    private Button add1;

    public static boolean abort = true;
    @FXML
    private Text title;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        errorLabel.setVisible(false);
        abort = true;
        BooleanBinding allFieldsFilled = Bindings.createBooleanBinding(() -> 
            !name.getText().isEmpty() && 
            !description.getText().isEmpty(),
            name.textProperty(), 
            description.textProperty()
            
        );

        add1.disableProperty().bind(allFieldsFilled.not());
    }    

    @FXML
    private void add(ActionEvent event) throws AcountDAOException, IOException {
        boolean good = true;
        try{
            Acount.getInstance().registerCategory(name.getText(), description.getText());
        }
        catch(Exception e){
            good = false;
        }
        
        if (!good){
            name.clear();
            description.clear();
            errorLabel.setStyle("-fx-text-fill: red;");
            errorLabel.setText("Category already exists!");
            errorLabel.setVisible(true);
        } else {
            errorLabel.setStyle("-fx-text-fill: green;");
            errorLabel.setText("Category created!");
            errorLabel.setVisible(true);
            abort = false;
            // Create a Timeline to delay closing the window
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
                ((Stage) name.getScene().getWindow()).close();
            }));
            timeline.play();
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        ((Stage) name.getScene().getWindow()).close();
        abort = true;
    }
    
    public void changeName(String text){
        add1.setText(text);
    }
    
    public void changeTitle(String text){
        title.setText(text);
    }
    
}