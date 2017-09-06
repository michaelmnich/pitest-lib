package org.pitest.mutationtest.sam.config;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Michał Mnich on 25.10.2016.
 */
public interface IProjectMetaData extends Serializable {
    String[] GetMetaData();
    List<String> GetMetaDataList();
    List<String> GetClaspathAsAList();
    List<FromFileMetaData> GetMetaDataAsAList();
}
