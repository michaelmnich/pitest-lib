package org.pitest.extensions;

import java.util.Random;

/**
 * Created by gosc on 25.08.2016.
 */
public class ProbabilityEvaluator {

    final private Integer _scale;
    private int _eventProbability;
    public void set_eventProbability(int eventProbability) {
        this._eventProbability = eventProbability;
    }



    public ProbabilityEvaluator(int scale, int eventProbability ){
     this._scale = scale;
     this._eventProbability = eventProbability;
    }

    public boolean draw(){
        Random randomno = new Random();
        int rn = randomno.nextInt(_scale);
        if(rn>=_eventProbability){
            //z specyfikacji wyglada ze zawsze z zerem liczy a nie losuje ostatniej SPEC _\|/_
            //The method call returns a pseudorandom, uniformly distributed int value     /|\
            // between 0 (inclusive) and n (exclusive).
            return true;
        }
        return false;
    }

    private void calculateProbablity(){

    }
}
