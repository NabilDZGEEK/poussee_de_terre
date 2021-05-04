package sample;

public class Coulomb extends formule{
    static double calculer(double phi,double sigma,double lambda,double beta){
        double s1=sin(lambda-phi)/sin(lambda);
        System.out.println(s1);
        double h=0;
        if(phi>=beta){
            h=(sin(sigma+phi)*sin(phi-beta))/sin(lambda-beta);
        }
        System.out.println(h);
        double s2=Math.sqrt(sin(lambda+sigma))+Math.sqrt(h);
        System.out.println(s2);
        return carré(s1/s2);
    }
}
//73 308