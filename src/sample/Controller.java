package sample;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Controller {

    void désactiver2(boolean[] vect){//activation des entrées t[1,2,3]
        for (int i=0;i< vect.length;i++){
           // t[i].setDisable(vect[i]);//vect[0]=t[1]...
        }
    }
    Controller(Scene s){
        /*for(int i=0;i<6;i++){
            t[i]=(TextField) s.lookup("#t"+i);
        }
        radio1=(RadioButton)s.lookup("#radio1");
        radio2=(RadioButton)s.lookup("#radio2");
        ToggleGroup toggleGroup = new ToggleGroup();
        radio1.setToggleGroup(toggleGroup);
        radio2.setToggleGroup(toggleGroup);
        Image draine=new Image("/sample/images/drainnée.png");
        Image non_draine=new Image("/sample/images/nondr.png") ;
        Label vk_label= (Label) s.lookup("#vk_label");
        TextField vk= (TextField) s.lookup("#vk");
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                RadioButton s=(RadioButton) toggleGroup.getSelectedToggle();
                ImageView formule=(ImageView) s.lookup("#form_img");
                if(formule==null){
                    System.out.println("nuull");
                }
                switch (s.getId()){
                    case "radio1":désactiver2(new boolean[]{false,false,false,true,false,false});
                        //formule.setImage(draine);
                        vk_label.setVisible(true);vk.setVisible(true);break;
                    case "radio2":désactiver2(new boolean[]{false,true,true,false,true,false});
                       // formule.setImage(non_draine);
                        vk_label.setVisible(false);vk.setVisible(false);
                }
            }
        });
*/


    }
}
