/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
/**
 *
 * @author GOATS
 */
public class Project2 extends Application{
    public static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/view/LogIn2.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Expenses Manager");
        try {
            stage.getIcons().add(new Image("/image/logo.png"))  ;
        }catch (Exception e){
            System.out.println("Image could not be loaded");
        }
        stage.show();
    }
    //Hola
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static Stage getStage(){
        return stage;
    }
    
    public static void setRoot(Parent root){

        stage.getScene().setRoot(root);
        stage.show();

    }
    
}
