package org.pitest.mutationtest.sam.web;

/**
 * Created by gosc on 30.10.2016.
 */
public interface ISerwer {
    void Start(Integer port);
    void Stop();
    void ConnectSlaveNode(String adress, Integer port);

}
