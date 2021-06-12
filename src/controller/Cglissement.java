package controller;
import Formules.glissement;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


public class Cglissement extends Controller{
    public static Cglissement instance = null;

    static {
        try {
            instance = new Cglissement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Cglissement getInstance() {
        return instance;
    }

    void désactiver(boolean[] vect){//activation des entrées t[1,2,3]
        for (int i=0;i< vect.length;i++){
             t[i].setDisable(vect[i]);//vect[0]=t[1]...
        }
    }
    public double vk=0;
    public double b=0;
    double cu=0;
    Double rd,fs;
    public double sigma=0,gama=0,pa=0;
    Double pah;
    Button calculer,effacer,imprimer;
    RadioButton radio1,radio2;
    boolean dr;
    TextField[]t=new TextField[6];

    Cglissement() throws IOException {
        super("glissement.fxml",2);
        GridPane donnees=(GridPane)scene.lookup("#données");
        Label d;
        d= (Label) donnees.getChildren().get(0);d.setTooltip(new Tooltip("Largeur du mur"));
        d= (Label) donnees.getChildren().get(1);d.setTooltip(new Tooltip("Angle de frotement du mur"));
        d= (Label) donnees.getChildren().get(2);d.setTooltip(new Tooltip("Cohesion du mur"));
        d= (Label) donnees.getChildren().get(3);d.setTooltip(new Tooltip("Cohesion non drainée du sol"));
        d= (Label) donnees.getChildren().get(4);d.setTooltip(new Tooltip("Densité du mur"));
        d= (Label) donnees.getChildren().get(5);d.setTooltip(new Tooltip("Coefficient partiel"));
        d=(Label) scene.lookup("#rd_label");d.setTooltip(new Tooltip("Résistance au glissement"));
        d=(Label) scene.lookup("#vk_label");d.setTooltip(new Tooltip("Charge vertical"));
        d=(Label) scene.lookup("#fs_label");d.setTooltip(new Tooltip("Coefficient de sécurité "));
        calculer=(Button) scene.lookup("#calculer");
        effacer=(Button) scene.lookup("#effacer");
        for(int i=0;i<6;i++){
            t[i]=(TextField) scene.lookup("#t"+i);
        }
        radio1=(RadioButton)scene.lookup("#radio1");
        radio2=(RadioButton)scene.lookup("#radio2");
        ToggleGroup toggleGroup = new ToggleGroup();
        radio1.setToggleGroup(toggleGroup);
        radio2.setToggleGroup(toggleGroup);
        Label vk_label= (Label) scene.lookup("#vk_label");
        TextField vkt= (TextField) scene.lookup("#vk");
        TextField rdt=(TextField)scene.lookup("#rd");
        TextField fst=(TextField)scene.lookup("#fs");
        Label res=(Label)scene.lookup("#res");
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                RadioButton s=(RadioButton) toggleGroup.getSelectedToggle();
                ImageView formule=(ImageView) s.lookup("#formdr_img");

                switch (s.getId()){
                    case "radio1":désactiver(new boolean[]{false,false,false,true,false,false});
                        //formule.setImage(draine);
                        vk_label.setVisible(true);vkt.setVisible(true);
                        dr=true;break;
                    case "radio2":désactiver(new boolean[]{false,true,true,false,true,false});
                       // formule.setImage(non_draine);
                        dr=false;
                        vk_label.setVisible(false);vkt.setVisible(false);
                }
            }
        });

        calculer.setOnAction(new EventHandler<ActionEvent>() {


            @Override
            public void handle(ActionEvent actionEvent) {
                Cpoussee cp=Cpoussee.getInstance();
                sigma=cp.sigma;
                pa=cp.pa;
                pah=pa*Math.cos(sigma*Math.PI/180);
                double[]tt=new double[6];

                Double[] r;
                tt[0]=Double.parseDouble(t[0].getText());b=tt[0];
                tt[5]=Double.parseDouble(t[5].getText());
                if(dr){

                    tt[1]=Double.parseDouble(t[1].getText());
                    tt[2]=Double.parseDouble(t[2].getText());
                    tt[4]=Double.parseDouble(t[4].getText());gama=tt[4];
                    r=glissement.drainee(tt[0],tt[1],tt[2],tt[4],tt[5]);
                    rd=r[0];vk=r[1];
                    vkt.setText(formatter.format(vk));
                }else{
                    tt[3]=Double.parseDouble(t[3].getText());cu=tt[3];
                    rd=glissement.non_drainee(tt[0],tt[3],tt[5]);

                }
                rdt.setText(formatter.format(rd));
                fs=rd/pah;
                fst.setText(formatter.format(fs));
                if(fs>1.1){

                    res.setText("STABLE");
                    res.setTextFill(Color.web("#19A112"));
                }else{
                    res.setText("INSTABLE");
                    res.setTextFill(Color.web("#A1170E"));
                }
            }

        });
        effacer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for(int i=6;i<12;i++){
                    TextField t= (TextField) donnees.getChildren().get(i);
                    t.setText("");
                }
            }
        });
    }
}
