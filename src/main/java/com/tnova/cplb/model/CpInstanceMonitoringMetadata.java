package com.tnova.cplb.model;

import java.sql.Timestamp;
import org.joda.time.DateTime;


public class CpInstanceMonitoringMetadata {

    public Timestamp timestamp;
    public double numberOfFlow = 0;
    public double freeRam = 0;
    public double freePercentualRam = 0;
    public double usedRam = 0;
    public double totalRam = 0;


    public CpInstanceMonitoringMetadata() {
        timestamp = new Timestamp(new DateTime().getMillis());
        numberOfFlow = 0;
        freeRam = 0;
        freePercentualRam = 0;
        usedRam = 0;
        totalRam = 0;
    }


}
