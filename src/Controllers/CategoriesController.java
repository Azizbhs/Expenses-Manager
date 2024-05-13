/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author leoda
 */
public class CategoriesController implements Initializable {

    @FXML
    private Button home;
    @FXML
    private TableView<?> categories_table;
    @FXML
    private Button add_category;
    @FXML
    private Button remove_category;
    @FXML
    private Button edit_category;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void home(ActionEvent event) {
    }

    @FXML
    private void add_category(ActionEvent event) {
    }

    @FXML
    private void remove_category(ActionEvent event) {
    }

    @FXML
    private void edit_category(ActionEvent event) {
    }
    
}
