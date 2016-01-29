package eu.tnova.crat.cplb.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eu.tnova.crat.cplb.data.TempData;
import eu.tnova.crat.cplb.model.OFSwitchMonitoringMetadata;

public class ODLServiceUtils {

    public static List<OFSwitchMonitoringMetadata> parseGetAllNodes(JSONObject jo) {
        List<OFSwitchMonitoringMetadata> lofsmm = new ArrayList<OFSwitchMonitoringMetadata>();
        try {
            OFSwitchMonitoringMetadata ofsmm = new OFSwitchMonitoringMetadata();
            JSONArray ja = jo.getJSONObject("nodes").getJSONArray("node");
            for(int i=0;i<ja.length();i++){
                ofsmm = new OFSwitchMonitoringMetadata();
                ofsmm.setSwitchID(ja.getJSONObject(i).getString("id").split(":")[1]);
                lofsmm.add(ofsmm);
            }
        }
        catch (JSONException e) {
            TempData.LOGGER.severe(e.getMessage());
        }
        return lofsmm;
    }
    
    
 
//  { "output": {  "response-code": 0,  "response-message": [ "1:2:OFPCRROLEMASTER"]    }

public static List<OFSwitchMonitoringMetadata> parseGetAllNodesRole(JSONObject jo) {
	int responsecode = jo.getJSONObject("output").getInt("response-code");
	JSONArray responsemessage = jo.getJSONObject("output").getJSONArray("response-message");
	
    for(int i=0;i<responsemessage.length();i++){
    	String message = responsemessage.getString(i);
        String[] info = message.split(":");
        String switch_id = info[0];
        String role_code = info[1];
        String role_name = info[2];
    }
    
    return null;

}

}
