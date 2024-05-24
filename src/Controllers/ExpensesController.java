/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;
import Controllers.ShowDetailsController;

/**
 * FXML Controller class
 *
 * @author leoda
 */
public class ExpensesController implements Initializable {

    @FXML
    private Button home;
    @FXML
    private TableView<Charge> expenses_table;
    @FXML
    private Button add_expense;
    @FXML
    private Button remove_expense;
    @FXML
    private Button edit_category;
    @FXML
    private TableColumn<Charge, Integer> ID;
    @FXML
    private TableColumn<Charge, String> name;
    @FXML
    private TableColumn<Charge, LocalDate> date;
    @FXML
    private TableColumn<Charge, Category> category;
    @FXML
    private TableColumn<Charge, Integer> units;
    @FXML
    private TableColumn<Charge, Double> cost;

    private ObservableList<Charge> expensesData = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Charge, Void> image;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        expenses_table.widthProperty().addListener((observable, oldValue, newValue) -> {
            double tableWidth = newValue.doubleValue();
            ID.setPrefWidth(tableWidth * 0.14);
            name.setPrefWidth(tableWidth * 0.12);
            date.setPrefWidth(tableWidth * 0.12);
            category.setPrefWidth(tableWidth * 0.24);
            units.setPrefWidth(tableWidth * 0.14);
            cost.setPrefWidth(tableWidth * 0.14);
            image.setPrefWidth(tableWidth * 0.1);
        });
        
        
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));

// Configurar la celda de la columna de ID para mostrar el ID del charge
        ID.setCellFactory(column -> {
            return new TableCell<Charge, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(String.valueOf(item));
                        setStyle("-fx-text-alignment: center; -fx-alignment: center;");

                    }
                }
            };
        });
        
        name.setCellFactory(column -> {
            return new TableCell<Charge, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item);
                        setStyle("-fx-text-alignment: center; -fx-alignment: center;");

                    }
                }
            };
        });
        
        date.setCellFactory(column -> {
            return new TableCell<Charge, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.toString());
                        setStyle("-fx-text-alignment: center; -fx-alignment: center;");

                    }
                }
            };
        });
        
        category.setCellFactory(column -> {
            return new TableCell<Charge, Category>() {
                @Override
                protected void updateItem(Category item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getName());
                        setStyle("-fx-text-alignment: center; -fx-alignment: center;");
                    }
                }
            };
        });
        
        units.setCellFactory(column -> {
            return new TableCell<Charge, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.toString());
                        setStyle("-fx-text-alignment: center; -fx-alignment: center;");

                    }
                }
            };
        });
        
        cost.setCellFactory(column -> {
            return new TableCell<Charge, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.toString());
                        setStyle("-fx-text-alignment: center; -fx-alignment: center;");

                    }
                }
            };
        });

        image.setCellFactory(new Callback<TableColumn<Charge, Void>, TableCell<Charge, Void>>() {
    @Override
    public TableCell<Charge, Void> call(final TableColumn<Charge, Void> param) {
        final TableCell<Charge, Void> cell = new TableCell<Charge, Void>() {

            private final Button btn = new Button("View");

            {
                btn.setOnAction((ActionEvent event) -> {
                    Charge charge = getTableView().getItems().get(getIndex());
                    String name = charge.getName();
                    String desc = charge.getDescription();
                    Image image = charge.getImageScan();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ShowDetails.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(ExpensesController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    // Get the controller instance from the loader
                    ShowDetailsController controller = loader.getController();
                    // Set the values on the controller
                    controller.setValues(name, desc, image);

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    scene.getRoot().requestFocus();
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.setTitle("More Details");

                    try {
                        stage.getIcons().add(new Image("/image/logo.png"));
                    } catch (Exception e) {
                        System.out.println("Image could not be loaded");
                    }

                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.showAndWait();
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(btn);
                    hbox.setStyle("-fx-alignment: center;"); // Center the HBox
                    setGraphic(hbox);
                }
            }
        };
        return cell;
    }
});

        
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));

// Configurar la celda de la columna de categoría para mostrar el nombre de la categoría
        

        units.setCellValueFactory(new PropertyValueFactory<>("units"));
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        
        expenses_table.setItems(expensesData);
        try {
            loadUserExpenses();
        } catch (AcountDAOException ex) {
            Logger.getLogger(ExpensesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExpensesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private void loadUserExpenses() throws AcountDAOException, IOException {
        List<Charge> list = Acount.getInstance().getUserCharges();
        if (list != null) {
            expensesData.setAll(list);
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

    @FXML
    private void add_expense(ActionEvent event) throws IOException, AcountDAOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/view/AddExpense.fxml"));
        Stage stage = new Stage();
        Parent root = miCargador.load();
        Scene scene = new Scene(root);
        scene.getRoot().requestFocus();
        stage.setScene(scene);
        stage.setMinWidth(400);
        stage.setMinHeight(600);  
        stage.setTitle("Sign Up");
        AddExpense controller = miCargador.getController();
        controller.changeName("Add");
        controller.changeTitle("Add Expense");
        controller.changeLabel("Expense added!");
        try {
            stage.getIcons().add(new Image("/image/logo.png"))  ;
        }catch (Exception e){
            System.out.println("Image could not be loaded");
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        
        loadUserExpenses();
    }

    @FXML
    private void remove_expense(ActionEvent event) {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Remove expense?");
        confirmationDialog.setContentText("Are you sure you want to perform this action?");
        confirmationDialog.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                Charge selectedCharge = expenses_table.getSelectionModel().getSelectedItem();
                if (selectedCharge != null) {
                    try {
                        if (Acount.getInstance().removeCharge(selectedCharge)) {
                            expensesData.remove(selectedCharge);
                        } else {
                            System.out.println("Failed to remove expense from database.");
                        }
                    } catch (AcountDAOException | IOException e) {
                        Logger.getLogger(CategoriesController.class.getName()).log(Level.SEVERE, null, e);
                    }
                } else {
                    Alert errorDialog = new Alert(Alert.AlertType.WARNING);
                    errorDialog.setTitle("Warning!");
                    errorDialog.setHeaderText("No expense selected!");
                    errorDialog.setContentText("Please select a expense to delete!");
                    errorDialog.showAndWait();
                    
                }
            }
        });
    }

    @FXML
private void edit_expense(ActionEvent event) throws AcountDAOException, IOException {
        Charge charge = expenses_table.getSelectionModel().getSelectedItem();
        if (charge == null) {
            System.out.println("No expense selected for editing.");
            return;
        }
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/view/AddExpense.fxml"));
        Stage stage = new Stage();
        Parent root = miCargador.load();
        Scene scene = new Scene(root);
        scene.getRoot().requestFocus();
        stage.setScene(scene);
        stage.setMinWidth(400);
        stage.setMinHeight(420);
        stage.setTitle("Edit Expense");
        AddExpense controller = miCargador.getController();
        controller.changeName("Confirm");
        controller.changeTitle("Edit Expense");
        controller.setCharge(charge);      
        controller.setMode(1);
        controller.changeLabel("Expense modified!");
        try {
            stage.getIcons().add(new Image("/image/logo.png"));
        } catch (Exception e) {
            System.out.println("Image could not be loaded");
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        if(!AddExpense.abort2){
            Charge selectedCharge = expenses_table.getSelectionModel().getSelectedItem();
            Acount.getInstance().removeCharge(selectedCharge);
        }

        // Recargar la lista de categorías después de editar una
        loadUserExpenses();
}

    
    
}

    class CenteredTableCell<S, T> extends TableCell<S, T> {
        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setText(null);
                setGraphic(null);
            } else {
                Text text = new Text(item.toString());
                text.setStyle("-fx-text-alignment: center; -fx-alignment: center;");
                setGraphic(text);
            }
        }
    }

    // Custom TableCellFactory that uses the CenteredTableCell
    class CenteredTableCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
        @Override
        public TableCell<S, T> call(TableColumn<S, T> param) {
            return new CenteredTableCell<>();
        }
    }