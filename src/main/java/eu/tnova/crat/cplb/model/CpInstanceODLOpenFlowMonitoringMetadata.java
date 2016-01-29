package eu.tnova.crat.cplb.model;

import java.util.ArrayList;
import java.util.List;

public class CpInstanceODLOpenFlowMonitoringMetadata {

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
