package org.pitest.mutationtest.sam.config;

import java.io.Serializable;

/**
 * Created by Michał Mnich on 25.10.2016.
 */
public interface IProjectMetaData extends Serializable {
    String[] GetMetaData();
}
