package controller.poiconnement;


import Formules.poiconnement.cisaillement;
import Formules.poiconnement.pressiomètre;
import controller.Cglissement;
import controller.Cpoiconnement;
import controller.Cpoussee;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Cpressiometre  {
    public static Cpressiometre instance = null;

    static {
        try {
            instance = new Cpressiometre();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Cpressiometre getInstance() {
        return instance;
    }
    NumberFormat formatter = new DecimalFormat("#0.00");
    double kp=0,ple=0,de=0,isigma=1,ibeta=1;
    GridPane table,données;
    Button calculer,effacer;
    ToggleGroup toggleGroup = new ToggleGroup();
    ComboBox sol,fondation,inclin_charge;
    Boolean condition;
    double[][] values;
    TextField[] t;
    Cpressiometre(){
        Scene scene=Cpoiconnement.getInstance().scene;
        table=(GridPane) scene.lookup("#table");
        données=(GridPane) scene.lookup("#données");
        calculer =(Button) scene.lookup("#calculer");
        effacer =(Button) scene.lookup("#effacer");

        RadioButton radio1,radio2,radio3;
        radio1=(RadioButton)scene.lookup("#radio1");
        radio2=(RadioButton)scene.lookup("#radio2");
        radio3=(RadioButton)scene.lookup("#radio3");

        radio1.setToggleGroup(toggleGroup);
        radio2.setToggleGroup(toggleGroup);
        radio3.setToggleGroup(toggleGroup);

        Label[] lab=new Label[5];
        lab[0]=(Label) données.getChildren().get(0);
        lab[1]=(Label) données.getChildren().get(1);
        lab[2]=(Label) données.getChildren().get(2);
        lab[3]=(Label) données.getChildren().get(3);
        lab[4]=(Label) données.getChildren().get(4);

        lab[0].setTooltip(new Tooltip("Largeur du mur"));
        lab[1].setTooltip(new Tooltip("Longeur du mur"));
        lab[2].setTooltip(new Tooltip("Angle du talus"));
        lab[3].setTooltip(new Tooltip("Distance entre talus et fondation"));
        lab[4].setTooltip(new Tooltip("Densité déjaugée du sol"));

        t=new TextField[5];
        t[0]=(TextField) données.getChildren().get(5);
        t[1]=(TextField) données.getChildren().get(6);
        t[2]=(TextField) données.getChildren().get(7);
        t[3]=(TextField) données.getChildren().get(8);
        t[4]=(TextField) données.getChildren().get(9);

        sol=(ComboBox) scene.lookup("#sol");
        fondation=(ComboBox) scene.lookup("#fondation");
        inclin_charge=(ComboBox) scene.lookup("#inclin_charge");
        values=new double[][]{{0.2,0.02,1.3,0.8},{0.3,0.02,1.5,0.8},{0.3,0.5,2,1},{0.22,0.18,5,1},{0.28,0.22,2.8,0.8},{0.35,0.31,3,0.8},{0.2,0.2,3,0.8},{0.2,0.3,3,0.8}};

    }
    public void config_calculer(){
        calculer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                double somme=0,produit=1,cpt=0;//compteur de lignes saisies
                for(int i=1;i<14;i++){
                    TextField plit=(TextField) table.getChildren().get(i);
                    TextField hit=(TextField) table.getChildren().get(14+i);
                    TextField plit_hit=(TextField) table.getChildren().get(28+i);
                    if(plit.getText()!=""&hit.getText()!=""){
                        double pli=Double.parseDouble(plit.getText());
                        double hi=Double.parseDouble(hit.getText());
                        double pli_hi=pli*hi;

                        plit_hit.setText(formatter.format(pli_hi));
                        somme+=pli_hi;produit*=pli_hi;
                        cpt++;
                    }
                }
                ple=Math.pow(produit,1/cpt);
                TextField plet=(TextField) Cpoiconnement.instance.scene.lookup("#ple");
                plet.setText(formatter.format(ple));
                de=somme/ple;
                String fonds=fondation.getSelectionModel().getSelectedItem().toString();
                int sols=sol.getSelectionModel().getSelectedIndex();
                int ligne;
                pressiomètre.calcul_e();
                double b_prime,l_prime,a_prime=0;
                double b=Double.parseDouble(t[0].getText());
                double l=Double.parseDouble(t[1].getText());
                b_prime=b-2*pressiomètre.e;
                l_prime=l-2*pressiomètre.e;

                double db=de/b_prime;

                switch (fonds){
                    case "carré":ligne=sols*2+1;
                        kp=pressiomètre.kp(values[ligne][0],values[ligne][1],values[ligne][2],values[ligne][3],db);
                        a_prime=l_prime*b_prime;
                        break;
                    case "filante":ligne=sols*2;
                        a_prime=b*l*(1-2* cisaillement.e/b);
                        kp=pressiomètre.kp(values[ligne][0],values[ligne][1],values[ligne][2],values[ligne][3],db);
                        break;
                    case "rectangulaire":
                        ligne=sols*2+1;
                        double kpc=pressiomètre.kp(values[ligne][0],values[ligne][1],values[ligne][2],values[ligne][3],db);
                        ligne=sols*2;
                        double kpf= pressiomètre.kp(values[ligne][0],values[ligne][1],values[ligne][2],values[ligne][3],db);
                        double bl=b_prime/l_prime;
                        kp=kpf*(1-bl)+kpc*bl;
                        a_prime=l_prime*b_prime;
                }

                double sigmad=Math.atan(Cpoussee.getInstance().pa*Math.cos(Cpoussee.instance.sigma*Math.PI/180)/ Cglissement.getInstance().vk*Math.PI/180);

                double beta=Double.parseDouble(t[2].getText());
                double d=Double.parseDouble(t[3].getText());
                RadioButton selected= (RadioButton) toggleGroup.getSelectedToggle();
                double qnetv=0;

                if(d>8*beta){//fondation à proximité
                    switch (selected.getId()){
                        case "radio1":ibeta=pressiomètre.ibeta_drainnee(beta,d);condition=true;break;
                        case "radio2":ibeta=pressiomètre.ibeta_non_drainnee(beta,d,b_prime,de);condition=false;break;
                        case "radio3":ibeta=pressiomètre.ibeta_inter(beta,d,b_prime,de,Cpoussee.getInstance().c,Cpoussee.getInstance().gama,Cpoussee.getInstance().phi);condition=true;
                    }
                    if(!inclin_charge.getSelectionModel().isSelected(0)) {//!=0
                        switch (selected.getId()){
                            case "radio1": isigma=pressiomètre.isigma_drainnee(sigmad,db);break;
                            case "radio2":isigma=pressiomètre.isigma_non_drainnee(sigmad);break;
                            case "radio3":isigma=pressiomètre.isigma_inter(sigmad,db,Cpoussee.getInstance().c, Cpoussee.getInstance().gama,b,Cpoussee.getInstance().phi);
                        }
                    }
                }else {
                    switch (selected.getId()){
                        case "radio1":condition=true;break;
                        case "radio2":condition=false;break;
                        case "radio3":condition=true;
                    }
                }


                if(inclin_charge.getSelectionModel().isSelected(2)&d>8*beta) {
                    qnetv=kp*ple*Math.min(ibeta/isigma,isigma);
                }else{
                    qnetv=kp*ple*isigma*ibeta;
                }
                TextField vkt=(TextField) Cpoiconnement.instance.scene.lookup("#vk");

                vkt.setText(formatter.format(Cglissement.getInstance().vk));
                double rvdv;
                if(condition){
                    rvdv=pressiomètre.calcul_rvd(qnetv,a_prime,2.8);
                }else{
                    rvdv= pressiomètre.calcul_rvd(qnetv,a_prime,1.68);
                }

                Cpoiconnement.getInstance().qnet.setText(formatter.format(qnetv));
                Cpoiconnement.getInstance().rvd.setText(formatter.format(rvdv));
                Label res=(Label) Cpoiconnement.instance.scene.lookup("#res");
                if(Cglissement.getInstance().vk>b*l*Double.parseDouble(t[4].getText())*d+rvdv){
                    res.setText("INSTABLE");
                    res.setTextFill(Color.web("#A1170E"));
                }else {
                    res.setText("STABLE");
                    res.setTextFill(Color.web("#19A112"));
                }
            }
        });
        effacer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for(int i=5;i<10;i++){
                    TextField t= (TextField) données.getChildren().get(i);
                    t.setText("");
                }
            }
        });
    }

}
