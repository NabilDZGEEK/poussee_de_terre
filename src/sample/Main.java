package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("glissement.fxml"));
        primaryStage.setTitle("Poussé des terres");
        //Scene s=new Scene(root, 900, 610);
        //new Controller(s);
        Cpoussee c=new Cpoussee();
        primaryStage.setScene(c.scene);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
