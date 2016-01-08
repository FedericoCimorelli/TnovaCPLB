package com.tnova.cplb.model;

import java.net.InetAddress;
import com.google.common.collect.EvictingQueue;
import com.tnova.cplb.data.TempData;

public class CpInstance {

    public InetAddress ip;
    public EvictingQueue<CpInstanceMonitoringMetadata> monitoringMetadata;
    public boolean monitoringTaskActive = false;


    public CpInstance() {
    }

    public CpInstance(InetAddress ipInstance) {
        this.ip = ipInstance;
        monitoringMetadata = EvictingQueue.create(TempData.monitoringDataHistoryLenght);
        monitoringTaskActive = false;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ipInstance) {
        this.ip = ipInstance;
    }


}