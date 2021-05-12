package controller;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class menu extends HBox {
    Button[] b=new Button[9];



    menu() {
        b[0]=new Button("Accueil");
        b[1]=new Button("Poussée");
        b[2]=new Button("Glissement");
        b[3]=new Button("Renversement");
        b[4]=new Button("Poiçonnement");
        b[5]=new Button("Interne");
        b[6]=new Button("à propos");
        b[7]=new Button("Aide");
        b[8]=new Button("interne2");
        this.getChildren().addAll(b);
    }
}
