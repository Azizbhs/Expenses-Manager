package Controllers;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Category;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableCell;
import model.Charge;

/**
 * FXML Controller class
 *
 * @author leoda
 */
public class CategoriesController implements Initializable {

    @FXML
    private TableView<Category> categories_table;
    @FXML
    private TableColumn<Category, String> nameCol;
    @FXML
    private TableColumn<Category, String> descriptionCol;
    @FXML
    private Button home;
    @FXML
    private Button add_category;
    @FXML
    private Button remove_category;
    @FXML
    private Button edit_category;

    private ObservableList<Category> categoryData = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categories_table.widthProperty().addListener((observable, oldValue, newValue) -> {
            double tableWidth = newValue.doubleValue();
            nameCol.setPrefWidth(tableWidth * 0.20);
            descriptionCol.setPrefWidth(tableWidth * 0.80);
        });
        
        nameCol.setCellFactory(column -> {
            return new TableCell<Category, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
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
        
        descriptionCol.setCellFactory(column -> {
            return new TableCell<Category, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
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
        
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        categories_table.setItems(categoryData);
        try {
            loadUserCategories();
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(CategoriesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadUserCategories() throws AcountDAOException, IOException {
        List<Category> list = Acount.getInstance().getUserCategories();
        if (list != null) {
            categoryData.setAll(list);
        }
    }

    @FXML
    private void home(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Main.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        currentStage.setScene(scene);
        currentStage.setTitle("Expenses Manager");
        currentStage.show();
    }

    @FXML
    private void add_category(ActionEvent event) throws IOException, AcountDAOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/view/AddCategory.fxml"));
        Stage stage = new Stage();
        Parent root = miCargador.load();
        Scene scene = new Scene(root);
        scene.getRoot().requestFocus();
        stage.setScene(scene);
        stage.setMinWidth(400);
        stage.setMinHeight(420);
        stage.setTitle("Add Category");
        AddCategoryController controller = miCargador.getController();
        controller.changeName("Add");
        controller.changeTitle("Add Category");
        try {
            stage.getIcons().add(new Image("/image/logo.png"));
        } catch (Exception e) {
            System.out.println("Image could not be loaded");
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        // Recargar la lista de categorías después de añadir una nueva
        loadUserCategories();
    }

    @FXML
    private void remove_category(ActionEvent event) throws IOException {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Remove category?");
        confirmationDialog.setContentText("Are you sure you want to perform this action?");
        confirmationDialog.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                Category selectedCategory = categories_table.getSelectionModel().getSelectedItem();
                if (selectedCategory != null) {
                    try {
                        if (Acount.getInstance().removeCategory(selectedCategory)) {
                            categoryData.remove(selectedCategory);
                        } else {
                            System.out.println("Failed to remove category from database.");
                        }
                    } catch (AcountDAOException | IOException e) {
                        Logger.getLogger(CategoriesController.class.getName()).log(Level.SEVERE, null, e);
                    }
                } else {
                    Alert errorDialog = new Alert(Alert.AlertType.WARNING);
                    errorDialog.setTitle("Warning!");
                    errorDialog.setHeaderText("No category selected!");
                    errorDialog.setContentText("Please select a category to delete!");
                    errorDialog.showAndWait();
                }
            }
        });
    }

    @FXML
    private void edit_category(ActionEvent event) throws IOException, AcountDAOException {
        Category cat = categories_table.getSelectionModel().getSelectedItem();
        if (cat == null) {
            System.out.println("No category selected for editing.");
            return;
        }
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/view/AddCategory.fxml"));
        Stage stage = new Stage();
        Parent root = miCargador.load();
        Scene scene = new Scene(root);
        scene.getRoot().requestFocus();
        stage.setScene(scene);
        stage.setMinWidth(400);
        stage.setMinHeight(420);
        stage.setTitle("Edit Category");
        AddCategoryController controller = miCargador.getController();
        controller.changeName("Confirm");
        controller.changeTitle("Edit Category");
        try {
            stage.getIcons().add(new Image("/image/logo.png"));
        } catch (Exception e) {
            System.out.println("Image could not be loaded");
        }
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        if(!AddCategoryController.abort){
            Category selectedCategory = categories_table.getSelectionModel().getSelectedItem();
            Acount.getInstance().removeCategory(selectedCategory);
        }
        
        // Recargar la lista de categorías después de editar una
        loadUserCategories();
    }
}