package Formules;

public class rankine extends formule{

    public static double calculer(double phi){
        double ka=carré(tan(45-phi/2));
        return ka;
    }
}
