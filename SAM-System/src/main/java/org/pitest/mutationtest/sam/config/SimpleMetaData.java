package org.pitest.mutationtest.sam.config;

import java.util.List;

/**
 * Created by gosc on 29.03.2017.
 */
public class SimpleMetaData implements IProjectMetaData {
    public SimpleMetaData(List<String> arguments) {
        this.arguments = arguments;
    }

    private List<String> arguments;

    @Override
    public String[] GetMetaData() {
            return  arguments.toArray(new String[arguments.size()]);
        }

    @Override
    public List<String> GetMetaDataList() {
        return arguments;
    }
}

