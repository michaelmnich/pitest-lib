package org.pitest.extensions;

/**
 * Created by gosc on 25.08.2016.
 */
public class MutationRandomizerSingleton {

    private static MutationRandomizerSingleton instance = null;

    private MutationRandomizerSingleton() {
        // Exists only to defeat instantiation.
    }

    public void hokusPokus(){

    }
    public static MutationRandomizerSingleton getInstance() {
        if(instance == null) {
            instance = new MutationRandomizerSingleton();
        }
        return instance;
    }
}
