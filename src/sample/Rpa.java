package sample;

public class Rpa extends formule{
    static double calculer(double phi,double beta,double teta ){
        double gauche=carré(cos(phi-teta)/cos(teta));
        double racine=0;
        if(phi>beta+teta){
            racine=(sin(phi)*sin(phi-beta-teta))/(cos(teta)*cos(beta));
        }
        return gauche*(Math.pow(1+Math.sqrt(racine),-2));
    }
}
