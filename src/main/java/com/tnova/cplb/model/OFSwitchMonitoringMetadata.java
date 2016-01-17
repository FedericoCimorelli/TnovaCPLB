package com.tnova.cplb.model;

public class OFSwitchMonitoringMetadata {

    @Override
    public String toString() {
        return "OFSwitchMonitoringMetadata [switchID=" + switchID + "]";
    }

    public String switchID = "";

    public String getSwitchID() {
        return switchID;
    }

    public void setSwitchID(String switchID) {
        this.switchID = switchID;
    }

}
