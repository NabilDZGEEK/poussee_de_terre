package controller;

import java.io.IOException;
import Formules.Renversement;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import static java.lang.Math.sin;

public class Crenversement extends Controller{
    public static Crenversement instance = null;

    static {
        try {
            instance = new Crenversement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Crenversement getInstance() {
        return instance;
    }

    TextField fst;
    Label res;
    void calcul(){

        Cpoussee cp=Cpoussee.getInstance();
        Cglissement cg=Cglissement.getInstance();
        TextField vkt=(TextField) scene.lookup("#vk");
        TextField pat=(TextField) scene.lookup("#pa");
        double vk=cg.b* cp.h* Cglissement.getInstance().gama+cp.pa_non_elu*sin(cp.sigma*Math.PI/180);
        double fs=0;
        fs=Renversement.calculer(cp.pa,cg.b,vk);
        vkt.setText(formatter.format(vk));
        pat.setText(formatter.format(cp.pa));
        if(fs==0){
            fst.setText("");
        }else{
            fst.setText(Double.toString(fs));
            if(fs>1.5){
                res.setText("STABLE");
                res.setTextFill(Color.web("#19a112"));
            }else{
                res.setText("INSTABLE");
                res.setTextFill(Color.web("#A1170E"));
            }
        }

    }
    Crenversement() throws IOException {
        super("renversement.fxml",4);
        fst=(TextField)scene.lookup("#fs");
        res=(Label) scene.lookup("#res");

    }
}
