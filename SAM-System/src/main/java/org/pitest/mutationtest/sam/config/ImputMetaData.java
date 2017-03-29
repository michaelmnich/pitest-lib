package org.pitest.mutationtest.sam.config;

import org.pitest.mutationtest.sam.config.ImputObjects.ImputObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gosc on 29.03.2017.
 */
public class ImputMetaData implements IProjectMetaData {
  private  ImputObject _imputObject;

    public ImputMetaData(String ClassesClassPatch, String TestClassPath, String DumpDir, String ClassesStringList){
        _imputObject= new ImputObject();
        _imputObject.set_projectClassesList(ClassesStringList);
        _imputObject.set_projectSorcePatch(TestClassPath);
        _imputObject.set_projectClassesPatch(ClassesClassPatch);
        _imputObject.set_DupmDir(DumpDir);
    }
/*
    * Created by Michał Mnich on 29.03.2017.

    Representation of:----------------------------------------
            --classPath
    D:\\Doktorat\\PitPlayground\\IOgr602-master\\target\\test-classes\\,D:\\Doktorat\\PitPlayground\\IOgr602-master\\target\\classes\\
            --reportDir
    D:\\trash\\
            --targetClasses
    matrixlibrary.*
            --targetTests
    matrixlibrary.*
            --sourceDirs
    D:\\Doktorat\\PitPlayground\\IOgr602-master\\
            ----------------------------------------------------------
  */
    @Override
    public String[] GetMetaData() {

        return (String[]) GetMetaDataList().toArray();
    }

    @Override
    public List<String> GetMetaDataList() {
        List<String> toretL = new ArrayList<>();

        if(!_imputObject.get_projectClassesPatch().equals("")){
            toretL.add("--classPath");
            toretL.add(_imputObject.get_projectClassesPatch());
        }
        if(!_imputObject.get_DupmDir().equals("")){
            toretL.add("--reportDir");
            toretL.add(_imputObject.get_DupmDir());
        }
        if(!_imputObject.get_projectClassesList().isEmpty()){
            toretL.add("--targetClasses");
            toretL.add( String.join(", ", _imputObject.get_projectClassesList()));
            //TODO
            toretL.add("--targetTests");
            toretL.add("matrixlibrary.*");
        }
        if(!_imputObject.get_projectClassesPatch().equals("")){
            toretL.add("--sourceDirs");
            toretL.add(_imputObject.get_projectSorcePatch());
        }
        return toretL;
    }
}
