package sample;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;

public class Controller {
    Button calculer,effacer,imprimer;

    char etat;
    TextArea info;
    CheckBox c1,c2;//Les checkbox
    RadioButton radio1,radio2;//static ou dynamique
    TextField t0,t1,t2,t3,t4,t5,t6,t7,t8,t9,t10;//les champs de données
    Label s0,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10;//leurs labels
    ComboBox combo;//selection methode
    String[] static_methods={"RANKINE","PONCELET","COULOMB"};
    String[]dynamic_methods={"RPA","MONONOBE OKABE"};
    String[] static_infos={"RANKINE","PONCELET","COULOMB"};
    String[]dynamic_infos={"RPA","MONONOBE OKABE"};
    Controller(Scene s){
        calculer= (Button) s.lookup("#calculer");
        info=(TextArea)s.lookup("#info");
        combo=(ComboBox)s.lookup("#combo");
        c1=(CheckBox) s.lookup("#c1");
        c2=(CheckBox) s.lookup("#c2");
        radio1=(RadioButton)s.lookup("#radio1");
        radio2=(RadioButton)s.lookup("#radio2");
        ToggleGroup toggleGroup = new ToggleGroup();
        radio1.setToggleGroup(toggleGroup);
        radio2.setToggleGroup(toggleGroup);
        t0=(TextField) s.lookup("#t0");
        t1=(TextField) s.lookup("#t1");
        t2=(TextField) s.lookup("#t2");
        t3=(TextField) s.lookup("#t3");
        t4=(TextField) s.lookup("#t4");
        t5=(TextField) s.lookup("#t5");
        t6=(TextField) s.lookup("#t6");
        t7=(TextField) s.lookup("#t7");
        t8=(TextField) s.lookup("#t8");
        t9=(TextField) s.lookup("#t9");
        t10=(TextField) s.lookup("#t10");
        s0=(Label)s.lookup("#s0");
        s1=(Label)s.lookup("#s1");
        s2=(Label)s.lookup("#s2");
        s3=(Label)s.lookup("#s3");
        s4=(Label)s.lookup("#s4");
        s5=(Label)s.lookup("#s5");
        s6=(Label)s.lookup("#s6");
        s7=(Label)s.lookup("#s7");
        s8=(Label)s.lookup("#s8");
        s9=(Label)s.lookup("#s9");
        s10=(Label)s.lookup("#s10");
        t8.disableProperty().bind(Bindings.not(c1.selectedProperty()));
        t9.disableProperty().bind(Bindings.not(c1.selectedProperty()));
        t10.disableProperty().bind(Bindings.not(c2.selectedProperty()));


        s0.setTooltip(new Tooltip("angle de frotement"));
        s1.setTooltip(new Tooltip("frotement sol/structure"));
        s2.setTooltip(new Tooltip("fruit du mur"));
        s3.setTooltip(new Tooltip("angle de surface"));
        s4.setTooltip(new Tooltip("Cohesion du sol"));
        s5.setTooltip(new Tooltip("coeff accélération horizontale"));
        s6.setTooltip(new Tooltip("densité"));
        s7.setTooltip(new Tooltip("hauteur"));
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                combo.setDisable(false);
                RadioButton s=(RadioButton) toggleGroup.getSelectedToggle();
                switch (s.getId()){
                    case "radio1":etat='s';combo.getItems().clear();combo.getItems().addAll(static_methods);break;//actif
                    case "radio2":etat='d';combo.getItems().clear();combo.getItems().addAll((dynamic_methods));//dynamic
                }
            }
        });
        combo.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                try{
                    String res=combo.getSelectionModel().getSelectedItem().toString();
                    if(etat=='s'){
                        if(res.equals(static_methods[0])){info.setText(static_infos[0]);}
                        else if(res.equals(static_methods[1])){info.setText(static_infos[1]);}
                        else{info.setText(static_infos[2]);}
                    }
                    if(etat=='d'){
                        if(res.equals(dynamic_methods[0])){info.setText(dynamic_infos[0]);}
                        else{info.setText(dynamic_infos[1]);}
                    }
                }catch(NullPointerException e){
                    System.err.println(e);
                }


            }
        });

    }
}
