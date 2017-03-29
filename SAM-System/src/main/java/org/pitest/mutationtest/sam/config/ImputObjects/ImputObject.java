package org.pitest.mutationtest.sam.config.ImputObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
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


public class ImputObject {

    private String _projectClassesPatch;
    private String _projectTestsPatch;
    private String _DupmDir;
    private List<String> _projectClassesList;

    public String get_DupmDir() {
        return _DupmDir;
    }

    public void set_DupmDir(String _DupmDir) {
        this._DupmDir = _DupmDir;
    }

    public String get_projectSorcePatch() {
        return _projectTestsPatch;
    }

    public void set_projectSorcePatch(String _projectTestsPatch) {
        this._projectTestsPatch = _projectTestsPatch;
    }

    public List<String> get_projectClassesList() {
        return _projectClassesList;
    }

    public void set_projectClassesList(String _projectClassesListString) {

       String[] a = _projectClassesListString.split(",");
        _projectClassesList=  Arrays.asList(a);
    }

    public void add_projectClassesList(String item) {
        if(_projectClassesList!=null){
            _projectClassesList.add(item);
        }else {
            _projectClassesList = new ArrayList<String>();
            _projectClassesList.add(item);
        }
    }

    public String get_projectClassesPatch() {
        return _projectClassesPatch;
    }

    public void set_projectClassesPatch(String _projectClassesPatch) {
        this._projectClassesPatch = _projectClassesPatch;
    }
}
