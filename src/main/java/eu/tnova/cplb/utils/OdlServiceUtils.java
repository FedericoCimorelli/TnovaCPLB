package eu.tnova.cplb.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eu.tnova.cplb.data.TempData;
import eu.tnova.cplb.model.OFSwitchMonitoringMetadata;

public class OdlServiceUtils {

    public static List<OFSwitchMonitoringMetadata> parseGetAllNodes(String r) {
        JSONObject jo = new JSONObject(r);
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

}
