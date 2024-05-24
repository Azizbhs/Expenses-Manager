package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShowDetailsController implements Initializable {

    @FXML
    private ImageView receipt;
    @FXML
    private Label title;
    @FXML
    private Label desc;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization logic if needed
    }    
    
    public void setValues(String title, String desc, Image image) {
        this.title.setText(title);
        this.desc.setText(desc);
        this.receipt.setImage(image);
    }
}
