package eu.tnova.crat.cplb.data;

public class Constants {

    public static final int OFPCRROLEEQUAL = 0;
    public static final int OFPCRROLESLAVE = 1;
    public static final int OFPCRROLEMASTER = 2;
    
    public static int numberOfInstancies = 0;
    public static int monitoringDataHistoryLenght = 10;
    public static int scheduledThreadPoolExecutorCorePoolSize = 2;
    public static int scheduledMonitoringThreadFixedTimeout = 10; //secs
    public static int scheduledMonitoringThreadInitialDelay = 2; //secs
    public static int scheduledLoadbalancerThreadFixedTimeout = 5; //secs
    public static int scheduledLoadbalancerThreadInitialDelay = 20; //secs
    
    public static String REMOTE_HOST_USERNAME = "kubla";
    public static String REMOTE_HOST_PASSWORD = "kubla";//"TOBECHANGED";

    public static String ODL_PORT = "8181";
    public static String ODL_username = "admin";
    public static String ODL_password = "admin";
    
    public static String ODL_NODES_PATH = "/restconf/operational/opendaylight-inventory:nodes/?depth=2";
    public static String ODL_RM_GETROLE_PATH =  "/restconf/operations/rolemanager:get-switch-role";
    public static String ODL_RM_SETROLE_PATH =  "/restconf/operations/rolemanager:set-switch-role";
    public static int HTTP_SERVER_PORT = 8888;
	
    
    public static String getODLNodesPath(String instanceIp){
        if(TempData.instanceAddresses.contains(instanceIp)){
            return "http://"+instanceIp+":"+ODL_PORT+ODL_NODES_PATH;
        }
        return null;
    }
    
    public static String getODLGetRolesPath(String instanceIp){
        if(TempData.instanceAddresses.contains(instanceIp)){
            return "http://"+instanceIp+":"+ODL_PORT+ODL_RM_GETROLE_PATH;
        }
        return null;
    }
    
    public static String getODLSetRolesPath(String instanceIp){
        if(TempData.instanceAddresses.contains(instanceIp)){
            return "http://"+instanceIp+":"+ODL_PORT+ODL_RM_SETROLE_PATH;
        }
        return null;
    }
    
}