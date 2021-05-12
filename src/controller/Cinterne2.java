package controller;
import Formules.interne;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class Cinterne2 extends Controller{
    public static Cinterne2 instance = null;

    static {
        try {
            instance = new Cinterne2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Cinterne2 getInstance() {
        return instance;
    }
    Cinterne2() throws IOException {
        super("interne2.fxml",6);
        TextField rtd=(TextField) scene.lookup("#rtd");
        Button calculer=(Button) scene.lookup("#calculer");
        GridPane gp=(GridPane)scene.lookup("#gp");
        TextField[]t=new TextField[7];
        t[0]=(TextField)gp.getChildren().get(1);
        t[1]=(TextField)gp.getChildren().get(4);
        t[2]=(TextField)gp.getChildren().get(7);
        t[3]=(TextField)gp.getChildren().get(10);
        t[4]=(TextField)gp.getChildren().get(13);
        t[5]=(TextField)gp.getChildren().get(16);
        t[6]=(TextField)gp.getChildren().get(19);

        calculer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Double[] tt=new Double[7];
                for (int i=0;i<7;i++){
                    tt[i]=Double.parseDouble(t[i].getText());
                }
                Double res=interne.calcul2(tt[0],tt[1],tt[2],tt[3],tt[4],tt[5],tt[6]);
                rtd.setText(res.toString());
            }
        });
    }
}
