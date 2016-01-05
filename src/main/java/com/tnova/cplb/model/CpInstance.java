package com.tnova.cplb.model;

import java.net.InetAddress;
import com.google.common.collect.EvictingQueue;
import com.tnova.cplb.data.TempData;

public class CpInstance {

    public InetAddress ipInstance;
    public EvictingQueue<CpInstanceMonitoringMetadata> monitoringMetadata;
    public boolean monitoringTaskActive = false;


    public CpInstance() {
    }

    public CpInstance(InetAddress ipInstance) {
        this.ipInstance = ipInstance;
        monitoringMetadata = EvictingQueue.create(TempData.monitoringDataHistoryLenght);
        monitoringTaskActive = false;
    }

}