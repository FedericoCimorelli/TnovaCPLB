package com.tnova.cplb.data;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Logger;

import com.tnova.cplb.model.CpInstance;

public class TempData {

    public static int numberOfInstancies = 0;
    public static int monitoringDataHistoryLenght = 10;
    public static Logger LOGGER;
    public static String configurationFileName = "config";
    public static Map<InetAddress, CpInstance> cpInstances = new HashMap<InetAddress, CpInstance>();
    public static List<ScheduledThreadPoolExecutor> scheduledThreadPoolExcecutors = new ArrayList<ScheduledThreadPoolExecutor>();
    public static int scheduledThreadPoolExecutorCorePoolSize = 2;
    public static int scheduledMonitoringThreadFixedTimeout = 10; //secs
    public static int scheduledMonitoringThreadInitialDelay = 5; //secs
    public static String remoteHostUserName = "federico";
    public static String remoteHostpassword = "";
}
