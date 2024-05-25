/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import static Controllers.AddCategoryController.abort;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.AcountDAOException;
import model.Category;

/**
 * FXML Controller class
 *
 * @author leoda
 */
public class EditCategoryController implements Initializable {

    @FXML
    private Text title;
    @FXML
    private TextField name;
    @FXML
    private TextField description;
    @FXML
    private Label errorLabel;
    @FXML
    private Button cancel;
    @FXML
    private Button confirm;
    
    private Category category;

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

        confirm.disableProperty().bind(allFieldsFilled.not());
    }    

    @FXML
    private void cancel(ActionEvent event) {
        ((Stage) name.getScene().getWindow()).close();
        abort = true;
    }

    @FXML
    private void confirm(ActionEvent event) throws IOException, AcountDAOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/view/Categories.fxml"));
        Parent root = miCargador.load();
        CategoriesController controller = miCargador.getController();
        errorLabel.setStyle("-fx-text-fill: green;");
            errorLabel.setText("Category edited!");
            errorLabel.setVisible(true);
        category.setName(getCategoryName());
        category.setDescription(getCategoryDescription());
        controller.loadUserCategories();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
                ((Stage) name.getScene().getWindow()).close();
            }));
            timeline.play();
        
        
    }
    
    public void setCategory(Category category) {
        this.category = category;
        name.setText(category.getName());
        description.setText(category.getDescription());
    }

    public String getCategoryName() {
        return name.getText();
    }

    public String getCategoryDescription() {
        return description.getText();
    }
}
