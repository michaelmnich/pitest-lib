package org.pitest.mutationtest.sam.config;

import java.util.List;

/**
 * Created by gosc on 29.03.2017.
 */
public class SimpleMetaDataToPropagate implements IProjectMetaData {
    public SimpleMetaDataToPropagate(List<String> arguments,List<String> ClaspathAsAListArguments )
    {
        this.arguments = arguments;
        this.claspathAsAListArguments = ClaspathAsAListArguments;
    }

    private List<String> arguments;
    private List<String> claspathAsAListArguments;

    @Override
    public String[] GetMetaData() {
            return  arguments.toArray(new String[arguments.size()]);
        }

    @Override
    public List<String> GetMetaDataList() {
        return arguments;
    }

    @Override
    public List<String> GetClaspathAsAList() {
        return claspathAsAListArguments;
    }

    @Override
    public List<FromFileMetaData> GetMetaDataAsAList() {
        return null;
    }
}

