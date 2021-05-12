package controller;
import Formules.glissement;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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
    Double rd,fs;
    double sigma=5,pa=5;
    Double pah=pa*Math.cos(sigma*Math.PI/180);
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
        calculer=(Button) scene.lookup("#calculer");
        for(int i=0;i<6;i++){
            t[i]=(TextField) scene.lookup("#t"+i);
        }
        radio1=(RadioButton)scene.lookup("#radio1");
        radio2=(RadioButton)scene.lookup("#radio2");
        ToggleGroup toggleGroup = new ToggleGroup();
        radio1.setToggleGroup(toggleGroup);
        radio2.setToggleGroup(toggleGroup);
        //Image draine=new Image("/images/drainnée.png");
        //Image non_draine=new Image("/images/nondr.png");
        Label vk_label= (Label) scene.lookup("#vk_label");
        TextField vk= (TextField) scene.lookup("#vk");
        TextField rdt=(TextField)scene.lookup("#rd");
        TextField fst=(TextField)scene.lookup("#fs");
        Label res=(Label)scene.lookup("#res");
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                RadioButton s=(RadioButton) toggleGroup.getSelectedToggle();
                ImageView formule=(ImageView) s.lookup("#formdr_img");
                if(formule==null){
                    System.out.println("nuull");
                }
                switch (s.getId()){
                    case "radio1":désactiver(new boolean[]{false,false,false,true,false,false});
                        //formule.setImage(draine);
                        vk_label.setVisible(true);vk.setVisible(true);
                        dr=true;break;
                    case "radio2":désactiver(new boolean[]{false,true,true,false,true,false});
                       // formule.setImage(non_draine);
                        dr=false;
                        vk_label.setVisible(false);vk.setVisible(false);
                }
            }
        });

        calculer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double[]tt=new double[6];
                double vkv;
                Double[] r;
                if(dr){
                    tt[0]=Double.parseDouble(t[0].getText());
                    tt[1]=Double.parseDouble(t[1].getText());
                    tt[2]=Double.parseDouble(t[2].getText());
                    tt[4]=Double.parseDouble(t[4].getText());
                    tt[5]=Double.parseDouble(t[5].getText());
                    r=glissement.drainee(tt[0],tt[1],tt[2],tt[4],tt[5]);
                    rd=r[0];vkv=r[1];
                    vk.setText(Double.toString(vkv));
                }else{
                    tt[0]=Double.parseDouble(t[0].getText());
                    tt[3]=Double.parseDouble(t[3].getText());
                    tt[5]=Double.parseDouble(t[5].getText());
                    rd=glissement.non_drainee(tt[0],tt[3],tt[5]);

                }
                rdt.setText(rd.toString());
                fs=rd/pah;
                fst.setText(fs.toString());
                if(fs>1.2){
                    res.setText("STABLE");
                    res.setTextFill(Color.web("#19a112"));
                }else{
                    res.setText("INSTABLE");
                    res.setTextFill(Color.web("#A1170E"));
                }
            }

        });
    }
}
