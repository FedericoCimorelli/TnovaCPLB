package eu.tnova.crat.cplb.model;

import java.io.Serializable;

public class OFSwitch implements Serializable {

	public OFSwitch(String id) {
		// TODO Auto-generated constructor stub
		switchID = id;
	}
    @Override
    public String toString() {
        return "OFSwitch [switchID=" + switchID + "]";
    }

    public String switchID = "";
    public double delay = 0;
    public int role;
    
    public String getSwitchID() {
        return switchID;
    }

    public void setSwitchID(String switchID) {
        this.switchID = switchID;
    }

}
