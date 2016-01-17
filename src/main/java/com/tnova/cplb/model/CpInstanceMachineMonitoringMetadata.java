package com.tnova.cplb.model;

import java.sql.Timestamp;
import org.joda.time.DateTime;


public class CpInstanceMachineMonitoringMetadata {

    public Timestamp timestamp;
    public double nSwitch = -1;
    private float freeRam = -1;
    private float usedRam = -1;
    private float totalRam = -1;
    private int nCPU = -1;
    private float loadAvgOneMinute = -1;
    private float loadAvgFiveMinute = -1;
    private float loadAvgFifteenMinute = -1;


    public CpInstanceMachineMonitoringMetadata() {
        timestamp = new Timestamp(new DateTime().getMillis());
    }


    public double getnSwitch() {
        return nSwitch;
    }


    public void setnSwitch(double nSwitch) {
        this.nSwitch = nSwitch;
    }


    public float getFreeRam() {
        return freeRam;
    }


    public void setFreeRam(float freeRam) {
        this.freeRam = freeRam;
    }


    public float getUsedRam() {
        return usedRam;
    }


    public void setUsedRam(float usedRam) {
        this.usedRam = usedRam;
    }


    public float getTotalRam() {
        return totalRam;
    }


    public void setTotalRam(float totalRam) {
        this.totalRam = totalRam;
    }


    public int getnCPU() {
        return nCPU;
    }


    public void setnCPU(int nCPU) {
        this.nCPU = nCPU;
    }


    public float getLoadAvgOneMinute() {
        return loadAvgOneMinute;
    }


    public void setLoadAvgOneMinute(float loadAvgOneMinute) {
        this.loadAvgOneMinute = loadAvgOneMinute;
    }


    public float getLoadAvgFiveMinute() {
        return loadAvgFiveMinute;
    }


    public void setLoadAvgFiveMinute(float loadAvgFiveMinute) {
        this.loadAvgFiveMinute = loadAvgFiveMinute;
    }


    public float getLoadAvgFifteenMinute() {
        return loadAvgFifteenMinute;
    }


    public void setLoadAvgFifteenMinute(float loadAvgFifteenMinute) {
        this.loadAvgFifteenMinute = loadAvgFifteenMinute;
    }


    @Override
    public String toString() {
        return "CpInstanceMonitoringMetadata [timestamp=" + timestamp
                + ", nSwitch=" + nSwitch + ", freeRam=" + freeRam
                + ", usedRam=" + usedRam + ", totalRam=" + totalRam + ", nCPU="
                + nCPU + ", loadAvgOneMinute=" + loadAvgOneMinute
                + ", loadAvgFiveMinute=" + loadAvgFiveMinute
                + ", loadAvgFifteenMinute=" + loadAvgFifteenMinute + "]";
    }


}
