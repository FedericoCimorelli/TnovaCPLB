package eu.tnova.crat.cplb.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import eu.tnova.crat.cplb.data.TempData;

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
    
    public static JSONArray toJSONArray(HashMap<String, OFSwitch> oswitches){
    	JSONArray jsonarray = new JSONArray();
    	
    	Iterator<OFSwitch> it = oswitches.values().iterator();
		while (it.hasNext()) {
	        OFSwitch cp = (OFSwitch)it.next();
	        jsonarray.put(cp.toJSON());
		}
    	
    	return jsonarray;
    	
    }
	private JSONObject toJSON() {
		// TODO Auto-generated method stub
		JSONObject jo = new JSONObject();
		jo.put("id", switchID);
		jo.put("role", role);
		jo.put("delay", delay);
		return jo;
	}

}
