package Formules;

import controller.Cglissement;
import controller.Cpoussee;

public class Renversement extends formule{
    public static double calculer(double pa,double b,double vk){
        Cpoussee cp=Cpoussee.getInstance();
        if(pa!=0 && cp.h!=0){
            return (vk*b)/(pa*cp.h);
        }
        return 0;
    }
}
