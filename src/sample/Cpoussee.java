package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class Cpoussee {

    Scene scene;

    Button calculer,effacer,imprimer;
    MenuBar mb;
    Menu m1,m2,m3,m4,m5,m6,m7;
    char etat;//"statique ou dynamique
    int rang;//la methode séléctionnée
    TextArea info;
    CheckBox c1,c2;//Les checkbox
    RadioButton radio1,radio2;//static ou dynamique
    TextField[]t=new TextField[11];//les champs de données
    Label[] l=new Label[11];//leurs labels
    ComboBox combo;//selection methode
    TextField kap,kam,pap,pam;
    String[] static_methods={"RANKINE","PONCELET","COULOMB"};
    String[]dynamic_methods={"RPA","MONONOBE OKABE"};
    String[] static_infos={"RANKINE","PONCELET","COULOMB"};
    String[]dynamic_infos={"RPA","MONONOBE OKABE"};
    void désactiver(boolean[] vect){//activation des entrées t[1,2,3]
        for (int i=0;i< vect.length;i++){
            t[i+1].setDisable(vect[i]);//vect[0]=t[1]...
        }
    }
    Cpoussee()throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("poussee.fxml"));
        scene=new Scene(root,900,610);
        calculer= (Button) scene.lookup("#calculer");
        effacer=(Button) scene.lookup("#effacer");
        info=(TextArea)scene.lookup("#info");
        combo=(ComboBox)scene.lookup("#combo");
        c1=(CheckBox) scene.lookup("#c1");
        c2=(CheckBox) scene.lookup("#c2");
        radio1=(RadioButton)scene.lookup("#radio1");
        radio2=(RadioButton)scene.lookup("#radio2");
        ToggleGroup toggleGroup = new ToggleGroup();
        radio1.setToggleGroup(toggleGroup);
        radio2.setToggleGroup(toggleGroup);
        for(int i=0;i<11;i++){
            t[i]=(TextField) scene.lookup("#t"+i);
        }
        for(int i=0;i<11;i++){
            l[i]=(Label) scene.lookup("#l"+i);
        }

        t[8].disableProperty().bind(Bindings.not(c1.selectedProperty()));
        t[9].disableProperty().bind(Bindings.not(c1.selectedProperty()));
        t[10].disableProperty().bind(Bindings.not(c2.selectedProperty()));

        kap= (TextField) scene.lookup("#kap");//ka+
        kam= (TextField) scene.lookup("#kam");//ka-
        pap= (TextField) scene.lookup("#pap");//pa+
        pam= (TextField) scene.lookup("#pam");//pa-
        l[0].setTooltip(new Tooltip("angle de frotement"));
        l[1].setTooltip(new Tooltip("frotement sol/structure"));
        l[2].setTooltip(new Tooltip("fruit du mur"));
        l[3].setTooltip(new Tooltip("angle de surface"));
        l[4].setTooltip(new Tooltip("Cohesion du sol"));
        l[5].setTooltip(new Tooltip("coeff accélération horizontale"));
        l[6].setTooltip(new Tooltip("densité"));
        l[7].setTooltip(new Tooltip("hauteur"));
        l[8].setTooltip(new Tooltip("densité d'eau"));
        l[9].setTooltip(new Tooltip("niveau piézomètrique"));
        l[10].setTooltip(new Tooltip("surcharges"));

        //
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                t[0].setDisable(false);
                t[4].setDisable(false);
                combo.setDisable(false);
                t[6].setDisable(false);
                t[7].setDisable(false);
                RadioButton s=(RadioButton) toggleGroup.getSelectedToggle();
                switch (s.getId()){
                    case "radio1":etat='s';combo.getItems().clear();combo.getItems().addAll(static_methods);t[5].setDisable(true);break;//actif
                    case "radio2":etat='d';combo.getItems().clear();combo.getItems().addAll((dynamic_methods));t[5].setDisable(false);//dynamic
                }
            }
        });
        combo.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {

                try{
                    String res=combo.getSelectionModel().getSelectedItem().toString();
                    if(etat=='s'){

                        if(res.equals(static_methods[0])){
                            désactiver(new boolean[]{true, true, true});
                            info.setText(static_infos[0]);rang=0;

                        }
                        else if(res.equals(static_methods[1])){
                            désactiver(new boolean[]{false, false,false});
                            info.setText(static_infos[1]);rang=1;
                        }
                        else{
                            désactiver(new boolean[]{false, false,false});
                            info.setText(static_infos[2]);rang=2;
                        }
                    }
                    if(etat=='d'){
                        désactiver(new boolean[]{true,true,false});
                        if(res.equals(dynamic_methods[0])){info.setText(dynamic_infos[0]);rang=0;}
                        else{
                            info.setText(dynamic_infos[1]);rang=1;
                            désactiver(new boolean[]{false,false,false});
                        }
                    }
                }catch(NullPointerException e){
                    System.err.println(e);
                }


            }
        });
        effacer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                for(int i=0;i<11;i++){
                    t[i].setText("");
                }
            }
        });
        calculer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double tt0,tt1 = 0,tt2,tt3,tt4 = 0,tt5,tt6,tt7,tt8,tt9,tt10;
                double kapv=0,kamv = 0;//resultat ka
                double papv=0,pamv=0;//resultat pa
                tt0=Double.parseDouble(t[0].getText());
                tt6=Double.parseDouble(t[6].getText());
                tt7=Double.parseDouble(t[7].getText());
                if(etat=='s'){
                    switch (rang){
                        case 0:
                                kapv=rankine.calculer(tt0);break;
                        case 1:
                                tt1=Double.parseDouble(t[1].getText());
                                tt2=Double.parseDouble(t[2].getText());
                                tt3=Double.parseDouble(t[3].getText());
                                kapv=Poncelet.calculer(tt0,tt1,tt2,tt3);break;
                        case 2:
                                tt1=Double.parseDouble(t[1].getText());
                                tt2=Double.parseDouble(t[2].getText());
                                tt3=Double.parseDouble(t[3].getText());
                                kapv=Coulomb.calculer(tt0,tt1,tt2,tt3);
                    }
                    kamv=kapv;
                }else{
                    double teta_pos,teta_neg;
                    tt3=Double.parseDouble(t[3].getText());
                    tt5=Double.parseDouble(t[5].getText());
                    teta_pos=Math.atan(tt5/(1+1/3*tt5))*180/Math.PI;
                    teta_neg=Math.atan(tt5/(1-1/3*tt5))*180/Math.PI;
                    switch (rang){
                        case 0:kapv=Rpa.calculer(tt0,tt3,teta_pos);
                               kamv=Rpa.calculer(tt0,tt3,teta_neg);
                               break;
                        case 1: tt1=Double.parseDouble(t[1].getText());
                                tt2=Double.parseDouble(t[2].getText());
                                kapv=Mononobe_okabe.calculer(tt0,tt1,tt2,tt3,teta_pos);
                                kamv=Mononobe_okabe.calculer(tt0,tt1,tt2,tt3,teta_neg);
                    }
                }

                kap.setText(Double.toString(kapv));
                kam.setText(Double.toString(kamv));
                try{
                    double cp=(tt4/Math.tan(tt0*Math.PI/180))*(1-kapv*Math.cos(tt1*Math.PI/180));
                    double cm=(tt4/Math.tan(tt0*Math.PI/180))*(1-kamv*Math.cos(tt1*Math.PI/180));
                    papv=0.5*kapv*tt6*tt7*tt7-cp;
                    pamv=0.5*kamv*tt6*tt7*tt7-cm;
                    if(t[8].getText()!=""&t[9].getText()!="") {
                        try {
                            tt8 = Double.parseDouble(t[8].getText());
                            tt9 = Double.parseDouble(t[9].getText());
                            papv+=tt8*tt9;pamv+=tt8*tt9;
                        } catch (NumberFormatException e) {
                            System.err.println("erreur format");
                        }
                    }
                    if(t[10].getText()!=""){

                        try {
                            papv+=Double.parseDouble(t[10].getText())*kapv;
                            pamv+=Double.parseDouble(t[10].getText())*kamv;
                        } catch (NumberFormatException e) {
                            System.err.println("erreur format");
                        }
                    }

                    pap.setText(Double.toString(papv));
                    pam.setText(Double.toString(pamv));
                }catch(NumberFormatException x){
                    System.err.println("vous devez saisir tt4 et tt0");
                }

            }
        });
    }

}
