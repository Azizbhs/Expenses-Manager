/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import model.Acount;
import model.AcountDAOException;
import model.Charge;

import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Category;

public class AccountController implements Initializable {

    @FXML
    private ComboBox<Month> monthSelect;

    @FXML
    private ComboBox<Integer> yearSelect;

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;
    @FXML
    private Button home;
    @FXML
    private Button print;
    @FXML
    private Label printing;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        yearSelect.setItems(FXCollections.observableArrayList(
                2024, 2023, 2022
        ));

        monthSelect.setItems(FXCollections.observableArrayList(
                Month.values()
        ));

        yearSelect.setOnAction(event -> updateCharts());

        monthSelect.setOnAction(event -> updatePieChart());
        try {
            populateYearSelect();
        } catch (AcountDAOException ex) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int getEarliestYear(List<Charge> charges) {
        return charges.stream()
                .mapToInt(category -> category.getDate().getYear())
                .min()
                .orElse(Year.now().getValue());
    }
    
    private int getLatestYear(List<Charge> charges) {
        return charges.stream()
                .mapToInt(category -> category.getDate().getYear())
                .max()
                .orElse(Year.now().getValue());
    }
    
    private void populateYearSelect() throws AcountDAOException, IOException {
        List<Charge> charges = Acount.getInstance().getUserCharges();
        int earliestYear = getEarliestYear(charges);
        int latestYear = getLatestYear(charges);
        int currentYear = Year.now().getValue();
        
        List<Integer> years = IntStream.rangeClosed(earliestYear, latestYear)
                .boxed()
                .collect(Collectors.toList());

        yearSelect.setItems(FXCollections.observableArrayList(years));
    }
    
    private void updateCharts() {
        int selectedYear = yearSelect.getValue();
        updateBarChart(selectedYear);
        updatePieChart();
    }

    private void updateBarChart(int year) {
        barChart.getData().clear();

        try {
            List<Charge> charges = Acount.getInstance().getUserCharges().stream()
                    .filter(charge -> charge.getDate().getYear() == year)
                    .collect(Collectors.toList());

            Map<Month, Double> monthlyExpenses = charges.stream()
                    .collect(Collectors.groupingBy(charge -> charge.getDate().getMonth(),
                            Collectors.summingDouble(Charge::getCost)));

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Monthly Expenses");

            for (Month month : Month.values()) {
                Double total = monthlyExpenses.getOrDefault(month, 0.0);
                series.getData().add(new XYChart.Data<>(month.toString(), total));
            }

            barChart.getData().add(series);

            xAxis.setCategories(FXCollections.observableArrayList(
                    Arrays.asList(Month.values()).stream().map(Month::toString).collect(Collectors.toList())
            ));

        } catch (AcountDAOException | IOException e) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    private void updatePieChart() {
 
        pieChart.getData().clear();

        Month selectedMonth = monthSelect.getValue();
        int selectedYear = yearSelect.getValue();

        try {
            
            List<Charge> charges = Acount.getInstance().getUserCharges().stream()
                    .filter(charge -> charge.getDate().getMonth() == selectedMonth && charge.getDate().getYear() == selectedYear)
                    .collect(Collectors.toList());

            Map<String, Double> categoryExpenses = charges.stream()
    .collect(Collectors.toMap(charge -> charge.getCategory().getName(), Charge::getCost, Double::sum));


            
            for (Map.Entry<String, Double> entry : categoryExpenses.entrySet()) {
                pieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }

        } catch (AcountDAOException | IOException e) {
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    @FXML
    private void home(ActionEvent event) throws IOException{
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        currentStage.setScene(scene);
        currentStage.setTitle("Expenses Manager");
        currentStage.show(); 
    }
    
    private void setPrint(boolean set){
        printBol = set;
    }
    
    private boolean printBol = false;
    
    private void showPrint(boolean bol){
        if(bol){
            printing.setText("Check your printer!");
        }
    }
    
    @FXML
    private void print(ActionEvent event) {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Print account?");
        confirmationDialog.setContentText("Are you sure you want to perform this action?");
        confirmationDialog.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                setPrint(true);
            }
            else{
                setPrint(false);
            }
        });
        showPrint(printBol);
    }
}