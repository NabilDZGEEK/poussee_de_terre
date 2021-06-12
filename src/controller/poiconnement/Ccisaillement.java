package controller.poiconnement;

import Formules.poiconnement.cisaillement;
import controller.Cglissement;
import controller.Cpoiconnement;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Ccisaillement{
    public static Ccisaillement instance = null;

    static {
        try {
            instance = new Ccisaillement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Ccisaillement getInstance() {
        return instance;
    }

    TextField[] t=new TextField[11];
    ToggleGroup gtype=new ToggleGroup();
    ComboBox fondation;
    GridPane donnees,coefs,sous_resultat;
    boolean condition;
    Button calculer,effacer;
    public static String etat;
    NumberFormat formatter = new DecimalFormat("#0.00");
    Ccisaillement(){

        Scene scene=Cpoiconnement.getInstance().scene;

        donnees= (GridPane) scene.lookup("#données");
        coefs= (GridPane) scene.lookup("#coefs");
        fondation=(ComboBox) scene.lookup("#fondation");
        sous_resultat=(GridPane) scene.lookup("#sous_resultat");
        t[0]=(TextField) donnees.getChildren().get(11);
        t[1]=(TextField) donnees.getChildren().get(12);
        t[2]=(TextField) donnees.getChildren().get(13);
        t[3]=(TextField) donnees.getChildren().get(14);
        t[4]=(TextField) donnees.getChildren().get(15);
        t[5]=(TextField) donnees.getChildren().get(16);
        t[6]=(TextField) donnees.getChildren().get(17);
        t[7]=(TextField) donnees.getChildren().get(18);
        t[8]=(TextField) donnees.getChildren().get(19);
        t[9]=(TextField) donnees.getChildren().get(20);
        t[10]=(TextField) donnees.getChildren().get(21);


        RadioButton radio1,radio2;
        radio1=(RadioButton)scene.lookup("#radio1");
        radio2=(RadioButton)scene.lookup("#radio2");
        ToggleGroup toggleGroup = new ToggleGroup();
        radio1.setToggleGroup(toggleGroup);
        radio2.setToggleGroup(toggleGroup);
        VBox type_sol=(VBox) scene.lookup("#type");
        HBox types= (HBox) type_sol.getChildren().get(1);
        RadioButton b=(RadioButton)types.getChildren().get(0);b.setToggleGroup(gtype);
        b=(RadioButton)types.getChildren().get(1);b.setToggleGroup(gtype);
        b=(RadioButton)types.getChildren().get(2);b.setToggleGroup(gtype);

        Label label;

        label=(Label) donnees.getChildren().get(0);label.setTooltip(new Tooltip("inclinaison de la base de fondation par rapport à l'horizontal"));
        label=(Label) donnees.getChildren().get(1);label.setTooltip(new Tooltip("Largeur du mur"));
        label=(Label) donnees.getChildren().get(2);label.setTooltip(new Tooltip("Longueur du mur"));
        label=(Label) donnees.getChildren().get(3);label.setTooltip(new Tooltip("Densité déjaugée du sol"));
        label=(Label) donnees.getChildren().get(4);label.setTooltip(new Tooltip("Profendeur d'encastrement"));
        label=(Label) donnees.getChildren().get(5);label.setTooltip(new Tooltip("Cohésion non drainnée"));
        label=(Label) donnees.getChildren().get(6);label.setTooltip(new Tooltip("Angle de frottement"));
        label=(Label) donnees.getChildren().get(7);label.setTooltip(new Tooltip("Cohesion du sol"));
        label=(Label) donnees.getChildren().get(8);label.setTooltip(new Tooltip("surcharges"));
        label=(Label) donnees.getChildren().get(9);label.setTooltip(new Tooltip("densité du mur"));
        label=(Label) donnees.getChildren().get(10);label.setTooltip(new Tooltip("Hauteur du mur"));

        fondation.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                etat=t1.toString();Label lab;
                switch (etat){
                    case "carré":t[2].setDisable(true);lab=(Label) donnees.getChildren().get(1);lab.setText("B");break;
                    case "circulaire": lab=(Label) donnees.getChildren().get(1);lab.setText("R");t[2].setDisable(true);break;
                    default:t[2].setDisable(false);lab=(Label) donnees.getChildren().get(1);lab.setText("B");
                }
            }
        });
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                //Button radio sélectionné
                RadioButton b=(RadioButton)t1;
                if(b.getId().equals("radio1")){//condition drainnee
                    condition=true;
                    type_sol.setVisible(false);
                    t[5].setDisable(true);
                }else{//condition non drainnee
                    condition=false;
                    type_sol.setVisible(true);
                    t[5].setDisable(false);
                }
            }
        });
        calculer =(Button) scene.lookup("#calculer");
        effacer =(Button) scene.lookup("#effacer");
        effacer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for(int i=11;i<22;i++){
                    TextField t= (TextField) donnees.getChildren().get(i);
                    t.setText("");
                }
            }
        });

    }
    public void config_calculer(){
        calculer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                double[] tt=new double[11];

                tt[0]=Double.parseDouble(t[0].getText());
                tt[1]=Double.parseDouble(t[1].getText());
                if(etat.equals("carré")){
                    tt[2]=tt[1];
                }else{
                    tt[2]=Double.parseDouble(t[2].getText());
                }

                tt[3]=Double.parseDouble(t[3].getText());
                tt[6]=Double.parseDouble(t[6].getText());
                tt[7]=Double.parseDouble(t[7].getText());
                tt[8]=Double.parseDouble(t[8].getText());
                tt[9]=Double.parseDouble(t[9].getText());
                tt[10]=Double.parseDouble(t[10].getText());


                double qnetv=0,rvdv = 0;
                double b_prime,l_prime,a_prime;

                cisaillement.calcul_e();

                b_prime=tt[1]-2*cisaillement.e;
                l_prime=tt[2]-2*cisaillement.e;

                TextField f;
                f=(TextField) sous_resultat.getChildren().get(4);f.setText(String.valueOf(b_prime));
                f=(TextField) sous_resultat.getChildren().get(5);f.setText(String.valueOf(l_prime));
                if(etat.equals("filante")){
                    a_prime=tt[1]*tt[2]*(1-2*cisaillement.e/tt[1]);
                }else{
                    a_prime=l_prime*b_prime;
                }
                if(etat.equals("circulaire")){
                    a_prime=cisaillement.calculer_a_circulaire(tt[1]);
                }
                f=(TextField) sous_resultat.getChildren().get(6);f.setText(String.valueOf(a_prime));
                f=(TextField) sous_resultat.getChildren().get(7);f.setText(String.valueOf(cisaillement.e));
                if(condition){
                    tt[4]=Double.parseDouble(t[4].getText());

                    qnetv= cisaillement.calcul_drainnee(tt[6],tt[7],tt[10],tt[8],tt[9],tt[0],tt[1],tt[2],tt[3],tt[4]);
                    rvdv=cisaillement.calcul_rvd(qnetv,a_prime,2.8);
                }else {
                    RadioButton rb=(RadioButton) gtype.getSelectedToggle();
                    switch (rb.getText()){
                        case "coherent":tt[5]=Double.parseDouble(t[5].getText());
                            qnetv=cisaillement.calcul_non_drainnee_coherant(tt[5],tt[8],tt[0],tt[1],tt[3]);break;
                        case "non coherent":qnetv=cisaillement.calcul_non_drainnee_non_coherant(tt[6],tt[1],tt[3]);break;
                        case "heterogène" :tt[5]=Double.parseDouble(t[5].getText());
                            qnetv=cisaillement.calcul_non_drainnee_heterogene(tt[5],tt[8],tt[6],tt[0],tt[1],tt[2],tt[3]);
                    }
                    rvdv=cisaillement.calcul_rvd(qnetv,a_prime,1.68);
                }

                for(int i=0;i<12;i++){
                    Label lab=(Label) coefs.getChildren().get(12+i);
                    lab.setText(formatter.format(cisaillement.cofs[i]));
                }
                TextField vkt=(TextField) Cpoiconnement.instance.scene.lookup("#vk");
                vkt.setText(formatter.format(Cglissement.getInstance().vk));
                Label res=(Label) Cpoiconnement.instance.scene.lookup("#res");
                Cpoiconnement.getInstance().qnet.setText(formatter.format(qnetv));
                Cpoiconnement.getInstance().rvd.setText(formatter.format(rvdv));
                if(Cglissement.getInstance().vk>tt[1]*tt[2]*Double.parseDouble(t[4].getText())*tt[4]+rvdv){
                    res.setText("INSTABLE");
                    res.setTextFill(Color.web("#A1170E"));
                }else {
                    res.setText("STABLE");
                    res.setTextFill(Color.web("#19A112"));
                }
            }
        });
    }

}
