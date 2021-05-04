package sample;

public class Poncelet extends formule{
    static double calculer(double phi,double sigma,double lambda,double beta){
        double gauche=carré(sin(lambda-phi))/((carré(sin(lambda))*sin(lambda+sigma)));
        double racine=0;
        if(phi>beta){
            racine=(sin(phi+sigma)*sin(phi-beta))/(sin(lambda+sigma)*sin(lambda-beta));
        }
        return gauche*Math.pow(1+Math.sqrt(racine),-2);
    }

}
