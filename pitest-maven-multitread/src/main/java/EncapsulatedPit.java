import org.pitest.maven.GoalStrategy;
import org.pitest.maven.RunPitStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gosc on 13.09.2016.
 */
public class EncapsulatedPit {

    public GoalStrategy Goal;
    public List<EncapsulatedPit> child; //moze przerpbimy na mape bedzie dostep po kluczu tylko jeszcze nie wiem jakim

    public EncapsulatedPit(){
        Goal =new RunPitStrategy();
        child= new ArrayList<EncapsulatedPit>(); //workery
    }



}
