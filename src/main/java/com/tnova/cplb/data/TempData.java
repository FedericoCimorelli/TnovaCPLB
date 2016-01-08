package com.tnova.cplb.data;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.tnova.cplb.model.CpInstance;

public class TempData {

    public static int numberOfInstancies = 0;
    public static int monitoringDataHistoryLenght = 10;
    public static Logger LOGGER;
    public static String configurationFileName = "config";
    public static Map<String, CpInstance> cpInstances = new HashMap<String, CpInstance>();

}
