package eu.tnova.crat.cplb.services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

import eu.tnova.crat.cplb.data.Constants;
import eu.tnova.crat.cplb.data.TempData;
import eu.tnova.crat.cplb.model.OFSwitchMonitoringMetadata;
import eu.tnova.crat.cplb.utils.ODLServiceUtils;
import eu.tnova.crat.cplb.utils.ODLRESTClient;

public class ODLServices {

    public static List<OFSwitchMonitoringMetadata> getAllNodes(String instanceIp){
        try {
        	String path = Constants.getODLNodesPath(instanceIp);
            /*WebResource res = TempData.client.resource(path);
            Builder builder = res.type(MediaType.APPLICATION_JSON);
            for(NewCookie nc : TempData.odlCookies)
                builder.cookie(nc);
            String r = builder.get(String.class);*/
        	TempData.LOGGER.info("Monitoring instance ODL OpenFlow metadata, getting "+path);
            JSONObject nodes = ODLRESTClient.get(path);
        	
            return ODLServiceUtils.parseGetAllNodes(nodes);
        }
        catch (Exception e) {
            TempData.LOGGER.severe(e.getMessage());
        }
        return new ArrayList<OFSwitchMonitoringMetadata>();
    }
    
    
    public static List<OFSwitchMonitoringMetadata> getAllNodesRole(String instanceIp){
        try {
        	String path = Constants.getODLGetRolesPath(instanceIp);
        	TempData.LOGGER.info("Monitoring instance ODL OpenFlow metadata, getting "+path);
            
        	JSONArray switches_id = new JSONArray();
        	JSONObject body = new JSONObject("{'input':{'switch-ids':[]}}");
        	JSONObject nodes = ODLRESTClient.post(path,body);
        	
            return null;
        }
        catch (Exception e) {
            TempData.LOGGER.severe(e.getMessage());
        }
        return new ArrayList<OFSwitchMonitoringMetadata>();
    }
}
