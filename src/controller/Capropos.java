package controller;

import java.io.IOException;

public class Capropos extends Controller{
    public static Capropos instance = null;

    static {
        try {
            instance = new Capropos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Capropos getInstance() {
        return instance;
    }
    Capropos() throws IOException {
        super("apropos.fxml",7);
    }
}
