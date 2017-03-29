package org.pitest.mutationtest.sam.web;

import org.pitest.mutationtest.config.ReportOptions;
import org.pitest.mutationtest.sam.config.IProjectMetaData;

/**
 * Created by Michał Mnich on 10.11.2016.
 */
public class WebCommunicationProtocol implements IWebCommunicationProtocolData {

    private String _stringInfo="";
    private ReportOptions _reportOptions;
    private IProjectMetaData _IProjectMetaData;

    @Override
    public String GetInfo() {
        return _stringInfo;
    }

    @Override
    public void SetInfo(String info) {
        _stringInfo = info;
    }

    @Override
    public ReportOptions GetRepportOpitions() {
        return _reportOptions;
    }

    @Override
    public void SetReportOptions(ReportOptions data) {
        _reportOptions = data;

    }

    @Override
    public IProjectMetaData GetPitRunMetaData() {
        return _IProjectMetaData;
    }

    @Override
    public void SetPitRunMetadata(IProjectMetaData data) {
        _IProjectMetaData = data;
    }
}
