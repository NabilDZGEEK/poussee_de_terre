package controller;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class Controller {

    Parent root;
    public Scene scene;

    Controller(String fichier,int numMenu) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/view/"+fichier));
        scene=new Scene(root,900,610);
        VBox v= (VBox) scene.lookup("#v");
        v.getChildren().set(0,Cprincipale.menus[numMenu]);
        System.out.println(fichier);


    }

}
