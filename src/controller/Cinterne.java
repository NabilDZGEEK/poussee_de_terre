package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import Formules.interne;
import java.io.IOException;

public class Cinterne extends Controller{
    public static Cinterne instance = null;

    static {
        try {
            instance = new Cinterne();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Cinterne getInstance() {
        return instance;
    }
    Cinterne() throws IOException {
        super("interne.fxml",5);
        ToggleGroup g1 = new ToggleGroup();
        ToggleGroup g2 = new ToggleGroup();
        ToggleGroup g3 = new ToggleGroup();
        GridPane gp1= (GridPane) scene.lookup("#g1");
        GridPane gp2= (GridPane) scene.lookup("#g2");
        GridPane gp3= (GridPane) scene.lookup("#g3");

        RadioButton r11= (RadioButton) gp1.getChildren().get(6);
        RadioButton r12= (RadioButton) gp1.getChildren().get(7);
        RadioButton r13= (RadioButton) gp1.getChildren().get(8);
        RadioButton r14= (RadioButton) gp1.getChildren().get(9);

        RadioButton r21= (RadioButton) gp2.getChildren().get(5);
        RadioButton r22= (RadioButton) gp2.getChildren().get(6);
        RadioButton r23= (RadioButton) gp2.getChildren().get(7);

        RadioButton r311= (RadioButton) gp3.getChildren().get(11);
        RadioButton r312= (RadioButton) gp3.getChildren().get(12);
        RadioButton r313= (RadioButton) gp3.getChildren().get(13);
        RadioButton r314= (RadioButton) gp3.getChildren().get(14);
        RadioButton r321= (RadioButton) gp3.getChildren().get(15);
        RadioButton r322= (RadioButton) gp3.getChildren().get(16);
        RadioButton r323= (RadioButton) gp3.getChildren().get(17);
        RadioButton r324= (RadioButton) gp3.getChildren().get(18);
        RadioButton r331= (RadioButton) gp3.getChildren().get(19);
        RadioButton r332= (RadioButton) gp3.getChildren().get(20);

        r11.setToggleGroup(g1);
        r12.setToggleGroup(g1);
        r13.setToggleGroup(g1);
        r14.setToggleGroup(g1);
        r21.setToggleGroup(g2);
        r22.setToggleGroup(g2);
        r23.setToggleGroup(g2);
        r311.setToggleGroup(g3);
        r312.setToggleGroup(g3);
        r313.setToggleGroup(g3);
        r314.setToggleGroup(g3);
        r321.setToggleGroup(g3);
        r322.setToggleGroup(g3);
        r323.setToggleGroup(g3);
        r324.setToggleGroup(g3);
        r331.setToggleGroup(g3);
        r332.setToggleGroup(g3);
        Label rtk_label=(Label)scene.lookup("#rtk_label");
        Label rtd_label=(Label)scene.lookup("#rtd_label");
        rtk_label.setTooltip(new Tooltip("résistance  caracteristique"));
        rtd_label.setTooltip(new Tooltip("résistance  ultime a la traction"));
        TextField rtd= (TextField) scene.lookup("#rtd");
        TextField rtk=(TextField) scene.lookup("#rtk");
        Button calculer=(Button) scene.lookup("#calculer");
        calculer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                RadioButton b;
                b= (RadioButton) g1.getSelectedToggle();
                Double pend=Double.parseDouble(b.getText());
                b= (RadioButton) g2.getSelectedToggle();
                Double pflu=Double.parseDouble(b.getText());
                b= (RadioButton) g3.getSelectedToggle();
                Double pdeg=Double.parseDouble(b.getText());
                Double rtkv=Double.parseDouble(rtk.getText());
                Double res=interne.calcul(pend,pflu,pdeg,rtkv);
                rtd.setText(res.toString());
            }
        });
    }
}
