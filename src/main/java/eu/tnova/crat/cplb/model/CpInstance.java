package eu.tnova.crat.cplb.model;

import java.net.InetAddress;
import com.google.common.collect.EvictingQueue;

import eu.tnova.crat.cplb.data.Constants;

public class CpInstance {

    public InetAddress ip;
    public EvictingQueue<CpInstanceMachineMonitoringMetadata> monitoringMachineMetadata;
    public EvictingQueue<CpInstanceODLOpenFlowMonitoringMetadata> monitoringOdlOpenFlowMetadata;
    public boolean monitoringTaskActive = false;


    public CpInstance() {
        monitoringMachineMetadata = EvictingQueue.create(Constants.monitoringDataHistoryLenght);
        monitoringOdlOpenFlowMetadata = EvictingQueue.create(Constants.monitoringDataHistoryLenght);
    }

    public CpInstance(InetAddress ipInstance) {
        this.ip = ipInstance;
        monitoringMachineMetadata = EvictingQueue.create(Constants.monitoringDataHistoryLenght);
        monitoringTaskActive = false;
        monitoringOdlOpenFlowMetadata = EvictingQueue.create(Constants.monitoringDataHistoryLenght);
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ipInstance) {
        this.ip = ipInstance;
    }


}