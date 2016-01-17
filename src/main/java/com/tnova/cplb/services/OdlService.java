package com.tnova.cplb.services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.tnova.cplb.data.Constants;
import com.tnova.cplb.data.TempData;
import com.tnova.cplb.model.OFSwitchMonitoringMetadata;
import com.tnova.cplb.utils.OdlServiceUtils;

public class OdlService {

    public static List<OFSwitchMonitoringMetadata> getAllNodes(String instanceIp){
        try {
            WebResource res = TempData.client.resource(Constants.getOdlNodesPath(instanceIp));
            TempData.LOGGER.info("Monitoring instance ODL OpenFlow metadata, getting "+Constants.getOdlNodesPath(instanceIp));
            Builder builder = res.type(MediaType.APPLICATION_JSON);
            for(NewCookie nc : TempData.odlCookies)
                builder.cookie(nc);
            String r = builder.get(String.class);
            return OdlServiceUtils.parseGetAllNodes(r);
        }
        catch (ClientHandlerException e) {
            TempData.LOGGER.severe(e.getMessage());
        }
        return new ArrayList<OFSwitchMonitoringMetadata>();
    }
}
