package com.tnova.cplb;

import java.util.logging.Logger;
import com.tnova.cplb.data.TempData;
import com.tnova.cplb.utils.Utils;


public class CPLoadBalancer{

    public static void main( String[] args ){
        InizializeLogger();
        TempData.LOGGER.info("Starting LoadBalancer...");
        Utils.LoadingInstancesConfiguration();
    }


    private static void InizializeLogger() {
        TempData.LOGGER = Logger.getLogger("TnovaCPLB");
        TempData.LOGGER.info("LoadBalancer logger initialized");
    }

}