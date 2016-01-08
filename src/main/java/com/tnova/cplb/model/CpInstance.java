package com.tnova.cplb.model;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.EvictingQueue;
import com.tnova.cplb.data.TempData;

public class CpInstance {

    public InetAddress ip;
    public EvictingQueue<CpInstanceMonitoringMetadata> monitoringMetadata;
    private List<OFSwitchMonitoringMetadata> OFSwitchesMonitoringMetadata;
    public boolean monitoringTaskActive = false;


    public CpInstance() {
        monitoringMetadata = EvictingQueue.create(TempData.monitoringDataHistoryLenght);
        OFSwitchesMonitoringMetadata = new ArrayList<OFSwitchMonitoringMetadata>();
    }

    public CpInstance(InetAddress ipInstance) {
        this.ip = ipInstance;
        monitoringMetadata = EvictingQueue.create(TempData.monitoringDataHistoryLenght);
        monitoringTaskActive = false;
        OFSwitchesMonitoringMetadata = new ArrayList<OFSwitchMonitoringMetadata>();
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ipInstance) {
        this.ip = ipInstance;
    }


}