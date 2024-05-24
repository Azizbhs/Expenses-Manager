package Controllers;

import static Controllers.AddCategoryController.abort;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;
import model.User;
import project2.Project2;

/**
 * FXML Controller class
 *
 * @author leoda
 */
public class AddExpense implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField cost;
    @FXML
    private TextField units;
    @FXML
    private TextField description;
    @FXML
    private DatePicker date;
    private Button add;
    
    User user = null;
    
    private Image scanImage = new Image("image/expenseDefaultPhoto.png");
    
    private Category expenseCategory = null;
    @FXML
    private ComboBox<Category> category;
    @FXML
    private Button image;
    @FXML
    private Button cancel;
    @FXML
    private Button add1;
    
    public static boolean abort2 = true;
    @FXML
    private Text title;
    
    /**
     * Initializes the controller class.
     */
    
    private Charge charge;
    
    private int mode;
    @FXML
    private Button addCategory;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        abort2 = true;
        
        reloadCat();

        // Crear un enlace booleano para verificar si los campos están llenos o si se ha seleccionado una categoría
        BooleanBinding allFieldsFilled = new BooleanBinding() {
            {
                super.bind(
                    name.textProperty(), 
                    description.textProperty(), 
                    cost.textProperty(), 
                    units.textProperty(),
                    date.valueProperty(),
                    category.valueProperty()
                );
            }

            @Override
            protected boolean computeValue() {
                return !name.getText().isEmpty() && 
                       !description.getText().isEmpty() && 
                       !cost.getText().isEmpty() && 
                       !units.getText().isEmpty() && 
                       date.getValue() != null && 
                       category.getValue() != null;
            }
        };

    // Deshabilitar el botón ADD si alguno de los campos está vacío o si no se ha seleccionado una categoría
        add1.disableProperty().bind(allFieldsFilled.not());
    
    // Resto del código...
        cost.setTextFormatter(new TextFormatter<>(new UnaryOperator<TextFormatter.Change>() {
                private final Pattern doublePattern = Pattern.compile("\\d*|\\d+\\.\\d*");

                @Override
                public TextFormatter.Change apply(TextFormatter.Change change) {
                    if (doublePattern.matcher(change.getControlNewText()).matches()) {
                        return change;
                    } else {
                        return null;
                    }
                }
            }));

            // Establecer TextFormatter para el campo 'units' que solo acepta valores int
            units.setTextFormatter(new TextFormatter<>(new UnaryOperator<TextFormatter.Change>() {
                private final Pattern intPattern = Pattern.compile("\\d*");

                @Override
                public TextFormatter.Change apply(TextFormatter.Change change) {
                    if (intPattern.matcher(change.getControlNewText()).matches()) {
                        return change;
                    } else {
                        return null;
                    }
                }
            }));


        add1.disableProperty().bind(allFieldsFilled.not());
        errorLabel.setVisible(false);
        try {
            user = Acount.getInstance().getLoggedUser();
        } catch (AcountDAOException ex) {
            Logger.getLogger(AddExpense.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AddExpense.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        date.setDayCellFactory((DatePicker picker) -> {
            return new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();
                    setDisable(empty || date.compareTo(today) < 0);
                }
            };
        });
        if(mode == 1){
            initializeFields();
        }
    }

    private void reloadCat(){
        try {
            List<Category> someList = Acount.getInstance().getUserCategories(); // Obtener la lista de categorías
            ObservableList<Category> observableList = FXCollections.observableArrayList(someList); // Convertir a ObservableList
            category.setItems(observableList); // Asignar la lista observable al ComboBox

            category.setConverter(new StringConverter<Category>() {
                @Override
                public String toString(Category category) {
                    return category != null ? category.getName() : "";
                }

                @Override
                public Category fromString(String string) {
                    return category.getItems().stream()
                        .filter(cat -> cat.getName().equals(string))
                        .findFirst().orElse(null);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void add(ActionEvent event) throws AcountDAOException, IOException {
        Category selectedCategory = category.getSelectionModel().getSelectedItem();
        
        boolean good = true;
        try{
            Acount.getInstance().registerCharge(name.getText(), description.getText(), Double.parseDouble(cost.getText()),
                Integer.parseInt(units.getText()), scanImage, date.getValue(), selectedCategory);
        }
        catch(Exception e){
            good = false;
        }
        
        if (!good){
            name.clear();
            description.clear();
            units.clear();
            cost.clear();
            date.cancelEdit();
            category.cancelEdit();
            errorLabel.setStyle("-fx-text-fill: red;");
            errorLabel.setText("Check your expense data!");
            errorLabel.setVisible(true);
        } else {
            errorLabel.setStyle("-fx-text-fill: green;");
            errorLabel.setVisible(true);
        abort2 = false;
            // Create a Timeline to delay closing the window
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
                ((Stage) name.getScene().getWindow()).close();
            }));
            timeline.play();
        setMode(0);
        }
    }

    @FXML
    private void addImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(Project2.getStage());
        // Se establece la imagen tanto en el menu como en el miembro
        if (selectedFile != null) {
            scanImage = new Image(selectedFile.toURI().toString());
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        ((Stage) name.getScene().getWindow()).close();
        abort2 = true;
    }
    
    public void changeName(String text){
        add1.setText(text);
    }
    
    public void changeTitle(String text){
        title.setText(text);
    }
    
    public void changeLabel(String text){
        errorLabel.setText(text);
    }
    
    public void setMode(int mode) {
        this.mode = mode;
        if (mode == 1) {
            initializeFields();
        }
    }
    
    private void initializeFields() {
        if (charge != null) {
            name.setText(charge.getName());
            description.setText(charge.getDescription());
            units.setText(Integer.toString(charge.getUnits()));
            cost.setText(Double.toString(charge.getCost()));
            date.setValue(charge.getDate());
            category.setValue(charge.getCategory());
        }
    }
    
    public void setCharge(Charge charge) {
        this.charge = charge;
        if (mode == 1) {
            initializeFields();
        }
    }

    @FXML
    private void addCategory(ActionEvent event) throws IOException {
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
        reloadCat();
    }
}