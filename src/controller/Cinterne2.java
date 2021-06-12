package controller;
import Formules.interne;
import javafx.event.ActionEvent;
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
        Button effacer=(Button) scene.lookup("#effacer");
        GridPane gp=(GridPane)scene.lookup("#données");
        TextField[]t=new TextField[7];
        t[0]=(TextField)gp.getChildren().get(1);
        t[1]=(TextField)gp.getChildren().get(4);
        t[2]=(TextField)gp.getChildren().get(7);
        t[3]=(TextField)gp.getChildren().get(10);
        t[4]=(TextField)gp.getChildren().get(13);
        t[5]=(TextField)gp.getChildren().get(16);
        t[6]=(TextField)gp.getChildren().get(19);

        calculer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Double[] tt=new Double[7];
                for (int i=0;i<7;i++){
                    tt[i]=Double.parseDouble(t[i].getText());
                }
                Double res=interne.calcul2(tt[0],tt[1],tt[2],tt[3],tt[4],tt[5],tt[6]);
                rtd.setText(formatter.format(res));
            }
        });
        GridPane table= (GridPane) scene.lookup("#table");
        Button calculer_table= (Button) scene.lookup("#calculer_table");
        calculer_table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Double[] tt=new Double[7];
                tt[0]=Double.parseDouble(t[0].getText());
                tt[2]=Double.parseDouble(t[2].getText());
                tt[3]=Double.parseDouble(t[3].getText());
                tt[5]=Double.parseDouble(t[5].getText());
                tt[6]=Double.parseDouble(t[6].getText());
                TextField zt,lst,rdt;
                double z,ls,rd;

                for (int i=1;i<17;i++){
                    try{
                        zt=(TextField) table.getChildren().get(i);
                        lst=(TextField) table.getChildren().get(17+i);
                        if(zt.getText()!=""&lst.getText()!=""){
                            z=Double.parseDouble(zt.getText());
                            ls=Double.parseDouble(lst.getText());
                            rdt=(TextField) table.getChildren().get(34+i);
                            rd=interne.calcul2(tt[0],z,tt[2],tt[3],ls,tt[5],tt[6]);
                            rdt.setText(formatter.format(rd));
                        }


                    }catch (NumberFormatException e){
                        System.err.println("erreur format");
                    }

                }
            }
        });
        effacer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for(int i=0;i<7;i++){
                    t[i].setText("");
                }
            }
        });
    }
}
