package controller;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Controller {

    Parent root;
    public Scene scene;
    public NumberFormat formatter;
    Controller(String fichier,int numMenu) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/view/"+fichier));
        scene=new Scene(root,1370,730);
        scene.getStylesheets().add("/stylesheet.css");
        formatter = new DecimalFormat("#0.00");
        AnchorPane v= (AnchorPane) scene.lookup("#v");
        v.getChildren().set(0,Cprincipale.menus[numMenu]);
    }

}
