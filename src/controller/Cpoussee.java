package controller;
import Formules.*;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;


public class Cpoussee extends Controller{


    public static Cpoussee instance = null;

    static {
        try {
            instance = new Cpoussee();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Cpoussee getInstance() {
        return instance;
    }
    public double phi=0,sigma=0,c=0,h=0,q=0,pa=0;
    private Button calculer,effacer,imprimer;

    private char etat;//"statique ou dynamique
    private int rang;//la methode séléctionnée
    private TextArea info;
    private CheckBox c1,c2;//Les checkbox
    private RadioButton radio1,radio2;//static ou dynamique
    private TextField[]t=new TextField[11];//les champs de données
    private Label[] l=new Label[11];//leurs labels
    private ComboBox combo;//selection methode
    private TextField kap,kam,pap,pam;
    private String[] static_methods={"RANKINE","PONCELET","COULOMB"};
    private String[]dynamic_methods={"RPA","MONONOBE OKABE"};
    private String[] static_infos={"RANKINE","PONCELET","COULOMB"};
    private  String[]dynamic_infos={"RPA","MONONOBE OKABE"};
    private void désactiver(boolean[] vect){//activation des entrées t[1,2,3]
        for (int i=0;i< vect.length;i++){
            t[i+1].setDisable(vect[i]);//vect[0]=t[1]...
        }
    }
    Cpoussee()throws Exception{
        super("poussee.fxml",1);

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
        l[0].setTooltip(new Tooltip("angle de frotement du sol"));
        l[1].setTooltip(new Tooltip("frotement sol/structure"));
        l[2].setTooltip(new Tooltip("fruit du mur"));
        l[3].setTooltip(new Tooltip("angle de surface"));
        l[4].setTooltip(new Tooltip("Cohesion du sol"));
        l[5].setTooltip(new Tooltip("coeff accélération horizontale"));
        l[6].setTooltip(new Tooltip("densité du sol"));
        l[7].setTooltip(new Tooltip("hauteur"));
        l[8].setTooltip(new Tooltip("densité d'eau"));
        l[9].setTooltip(new Tooltip("niveau piézomètrique"));
        l[10].setTooltip(new Tooltip("surcharges"));

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
                double[] tt= new double[11];//valeurs de données
                double kapv=0,kamv = 0;//resultat ka
                double papv=0,pamv=0;//resultat pa
                double paeluv=0;//resultat pa(elu)
                tt[0]=Double.parseDouble(t[0].getText());phi=tt[0];
                tt[6]=Double.parseDouble(t[6].getText());
                tt[7]=Double.parseDouble(t[7].getText());h=tt[7];
                if(etat=='s'){//static
                    switch (rang){
                        case 0:
                                kapv=rankine.calculer(tt[0]);break;
                        case 1:
                                tt[1]=Double.parseDouble(t[1].getText());
                                tt[2]=Double.parseDouble(t[2].getText());sigma=tt[2];
                                tt[3]=Double.parseDouble(t[3].getText());
                                kapv=Poncelet.calculer(tt[0],tt[1],tt[2],tt[3]);break;
                        case 2:
                                tt[1]=Double.parseDouble(t[1].getText());
                                tt[2]=Double.parseDouble(t[2].getText());sigma=tt[2];
                                tt[3]=Double.parseDouble(t[3].getText());
                                kapv=Coulomb.calculer(tt[0],tt[1],tt[2],tt[3]);
                    }
                    kamv=kapv;
                }else{
                    double teta_pos,teta_neg;
                    tt[3]=Double.parseDouble(t[3].getText());
                    tt[5]=Double.parseDouble(t[5].getText());
                    teta_pos=Math.atan(tt[5]/(1+1/3*tt[5]))*180/Math.PI;
                    teta_neg=Math.atan(tt[5]/(1-1/3*tt[5]))*180/Math.PI;
                    switch (rang){
                        case 0:kapv=Rpa.calculer(tt[0],tt[3],teta_pos);
                               kamv=Rpa.calculer(tt[0],tt[3],teta_neg);
                               break;
                        case 1: tt[1]=Double.parseDouble(t[1].getText());
                                tt[2]=Double.parseDouble(t[2].getText());sigma=tt[2];
                                kapv=Mononobe_okabe.calculer(tt[0],tt[1],tt[2],tt[3],teta_pos);
                                kamv=Mononobe_okabe.calculer(tt[0],tt[1],tt[2],tt[3],teta_neg);
                    }
                }

                kap.setText(Double.toString(kapv));
                kam.setText(Double.toString(kamv));
                double paeluvp=0,paeluvm=0;//pa(elu)+,pa(elu)-
                try{
                    double cp=(tt[4]/Math.tan(tt[0]*Math.PI/180))*(1-kapv*Math.cos(tt[1]*Math.PI/180));
                    double cm=(tt[4]/Math.tan(tt[0]*Math.PI/180))*(1-kamv*Math.cos(tt[1]*Math.PI/180));
                    papv=0.5*kapv*tt[6]*tt[7]*tt[7]-cp;
                    pamv=0.5*kamv*tt[6]*tt[7]*tt[7]-cm;
                    if(t[8].getText()!=""&t[9].getText()!="") {
                        try {
                            tt[8] = Double.parseDouble(t[8].getText());
                            tt[9] = Double.parseDouble(t[9].getText());
                            papv+=tt[8]*tt[9];pamv+=tt[8]*tt[9];

                        } catch (NumberFormatException e) {
                            System.err.println("erreur format");
                        }
                    }
                    paeluvp=papv*1.35;
                    paeluvm=pamv*1.35;
                    if(t[10].getText()!=""){
                        q=Double.parseDouble(t[10].getText());
                        try {
                            papv+=q*kapv;
                            pamv+=q*kamv;
                            paeluvp+=kapv*q*1.5;
                            paeluvm+=kamv*q*1.5;

                        } catch (NumberFormatException e) {
                            System.err.println("erreur format");
                        }
                    }

                    pap.setText(Double.toString(papv));
                    pam.setText(Double.toString(pamv));
                    paeluv=Math.max(paeluvp,paeluvm);//max(pa(elu)+,pa(elu)-)
                    TextField paelu=(TextField)scene.lookup("#paelu");
                    paelu.setText(String.valueOf(paeluv));
                    pa=paeluv;
                }catch(NumberFormatException x){
                    System.err.println("vous devez saisir tt4 et tt0");
                }

            }
        });
    }

}
