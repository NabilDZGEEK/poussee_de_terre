package controller;

import controller.poiconnement.Ccisaillement;
import controller.poiconnement.Cpenetrometre;
import controller.poiconnement.Cpressiometre;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Cpoiconnement extends Controller{
    public static Cpoiconnement instance = null;

    static {
        try {
            instance = new Cpoiconnement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Cpoiconnement getInstance() {
        return instance;
    }

    public TextField qnet,rvd;
    Cpoiconnement() throws IOException {
        super("poiconnement.fxml",3);
        VBox essai=(VBox)scene.lookup("#essai");
        VBox parent= (VBox) essai.getParent();

        VBox cissaiment=FXMLLoader.load(getClass().getResource("/view/poiconnement/cissaiment.fxml"));
        HBox penetrometre=FXMLLoader.load(getClass().getResource("/view/poiconnement/penetromètre.fxml"));
        HBox pressiometre=FXMLLoader.load(getClass().getResource("/view/poiconnement/pressiomètre.fxml"));





        ComboBox combo= (ComboBox) scene.lookup("#combo");
        combo.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                switch ((String) t1){
                    case "pressiomètre":parent.getChildren().set(1,pressiometre);
                                        Cpressiometre.getInstance().config_calculer();break;
                    case "penetromètre":parent.getChildren().set(1,penetrometre);
                                Cpenetrometre.getInstance().config_calculer();break;
                    case "cisaillemment":parent.getChildren().set(1,cissaiment);
                                        Ccisaillement.getInstance().config_calculer();
                }
               Label res= (Label)scene.lookup("#res");res.setText("");
               TextField vk=(TextField) scene.lookup("#vk");vk.setText("");
                qnet.setText("");rvd.setText("");
            }
        });
        qnet= (TextField) scene.lookup("#qnet");
        rvd= (TextField) scene.lookup("#rvd");


    }
}
