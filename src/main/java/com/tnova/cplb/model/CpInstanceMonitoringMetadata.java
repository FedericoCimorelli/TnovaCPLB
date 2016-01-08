package com.tnova.cplb.model;

import java.sql.Timestamp;
import org.joda.time.DateTime;


public class CpInstanceMonitoringMetadata {

    public Timestamp timestamp;
    public double numberOfFlow = -1;
    public float freeRam = -1;
    public float usedRam = -1;
    public float totalRam = -1;


    public CpInstanceMonitoringMetadata() {
        timestamp = new Timestamp(new DateTime().getMillis());
        numberOfFlow = -1;
        freeRam = -1;
        usedRam = -1;
        totalRam = -1;
    }


}
