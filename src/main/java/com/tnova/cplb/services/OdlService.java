package com.tnova.cplb.services;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.tnova.cplb.data.Constants;
import com.tnova.cplb.data.TempData;

public class OdlService {

    public static void getAllNodes(String instanceIp){
        try {
            WebResource res = TempData.client.resource(Constants.getOdlNodesPath(instanceIp));
            Builder builder = res.type(MediaType.APPLICATION_JSON);
            for(NewCookie nc : TempData.odlCookies)
                builder.cookie(nc);
        //    nodes = builder.get(Nodes.class);
        }
        catch (ClientHandlerException e) {
            TempData.LOGGER.severe(e.getMessage());
        }
        //return nodes;
    }
}
