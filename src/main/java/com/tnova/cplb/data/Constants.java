package com.tnova.cplb.data;

public class Constants {

    public static int numberOfInstancies = 0;
    public static int monitoringDataHistoryLenght = 10;
    public static int scheduledThreadPoolExecutorCorePoolSize = 2;
    public static int scheduledMonitoringThreadFixedTimeout = 10; //secs
    public static int scheduledMonitoringThreadInitialDelay = 5; //secs
    public static String REMOTE_HOST_USERNAME = "federico";

    public static String REMOTE_HOST_PASSWORD = "TOBECHANGED";

    public static String ODL_PORT = "8181";
    public static String ODL_username = "admin";
    public static String ODL_password = "admin";
    public static String ODL_NODES_PATH = "/restconf/operational/opendaylight-inventory:nodes/";



    public static String getOdlNodesPath(String instanceIp){
        if(TempData.instanceAndresses.contains(instanceIp)){
            return "http://"+instanceIp+":"+ODL_PORT+ODL_NODES_PATH;
        }
        return null;
    }
}
