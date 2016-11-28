package org.pitest.mutationtest.sam.ui;

import org.pitest.mutationtest.sam.config.IProjectMetaData;

/**
 * Created by gosc on 19.11.2016.
 */
public interface Iui {



    public void startSerwer(int port);
    public void connectTo(String adress, int port);
    public void runnPit(IProjectMetaData data);

}
