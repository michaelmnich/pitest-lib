package org.pitest.mutationtest.sam.web;

import org.pitest.mutationtest.config.ReportOptions;

import java.io.Serializable;

/**
 * Created by gosc on 10.11.2016.
 */
public interface IWebCommunicationProtocolData extends Serializable {

    String GetInfo();
    void SetInfo(String info);

    ReportOptions GetRepportOpitions();
    void SetReportOptions(ReportOptions data);

}


