package com.tnova.cplb.model;

import java.util.ArrayList;
import java.util.List;

public class CpInstanceOdlOpenFlowMonitoringMetadata {

    public List<OFSwitchMonitoringMetadata> switchesMonitoringMetadata = new ArrayList<OFSwitchMonitoringMetadata>();

    public List<OFSwitchMonitoringMetadata> getSwitchesMonitoringMetadata() {
        return switchesMonitoringMetadata;
    }

    public void setSwitchesMonitoringMetadata(
            List<OFSwitchMonitoringMetadata> switchesMonitoringMetadata) {
        this.switchesMonitoringMetadata = switchesMonitoringMetadata;
    }

    @Override
    public String toString() {
        return "CpInstanceOdlOpenFlowMonitoringMetadata [switchesMonitoringMetadata="
                + switchesMonitoringMetadata + "]";
    }

}
