

import javafx.application.Application;
import controller.*;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        new Cprincipale();
        primaryStage.setTitle("MRGA 1.0");
        primaryStage.setMaximized(true);
        Cprincipale.primaryStage=primaryStage;
        primaryStage.setScene(Chome.getInstance().scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
