package eu.tnova.crat.cplb.data;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Logger;

import javax.ws.rs.core.NewCookie;

import com.sun.jersey.api.client.Client;

import eu.tnova.crat.cplb.model.CpInstance;

public class TempData {

    public static Logger LOGGER;
    public static String configurationFileName = "config";
    public static List<String> instanceAddresses = new ArrayList<String>();
    public static Map<String, CpInstance> cpInstances = new HashMap<String, CpInstance>();
    public static List<ScheduledThreadPoolExecutor> scheduledThreadPoolExcecutors = new ArrayList<ScheduledThreadPoolExecutor>();
    //public static Client client;
    //public static List<NewCookie> odlCookies = null;

}