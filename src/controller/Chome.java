package controller;

import java.io.IOException;

public class Chome extends Controller{
    public static Chome instance = null;
    static {
        try {
            instance = new Chome();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Chome getInstance() {
        return instance;
    }

    Chome() throws IOException {
        super("home.fxml",0);
    }
}
