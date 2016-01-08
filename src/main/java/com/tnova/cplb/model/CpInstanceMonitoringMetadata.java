package com.tnova.cplb.model;

import java.sql.Timestamp;
import org.joda.time.DateTime;


public class CpInstanceMonitoringMetadata {

    public Timestamp timestamp;
    public double numberOfFlow = -1;
    private float freeRam = -1;
    private float usedRam = -1;
    private float totalRam = -1;
    private int nCPU = -1;


    public CpInstanceMonitoringMetadata() {
        timestamp = new Timestamp(new DateTime().getMillis());
        numberOfFlow = -1;
        freeRam = -1;
        usedRam = -1;
        totalRam = -1;
        nCPU = -1;
    }


}
