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

import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
/**
 * FXML Controller class
 *
 * @author leoda
 */
public class AccountController implements Initializable {


    @FXML
    private Button home;
    @FXML
    private PieChart pieChart;
    @FXML
    private BarChart<?, ?> barChart;
    @FXML
    private ComboBox<?> yearSelect;
    @FXML
    private ComboBox<?> monthSelect;
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

}
