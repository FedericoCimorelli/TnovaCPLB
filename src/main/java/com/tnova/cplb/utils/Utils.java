package com.tnova.cplb.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import com.tnova.cplb.data.TempData;
import com.tnova.cplb.model.CpInstance;


public class Utils {

    public static void LoadingInstancesConfiguration(){
        TempData.LOGGER.info("Loading instaces configurations...");
        FileReader fr = null;
        try {
            fr = new FileReader(TempData.configurationFileName);
        } catch (FileNotFoundException e) {
            TempData.LOGGER.severe(e.getMessage());
        }
        BufferedReader textReader = new BufferedReader(fr);
        String numberOfInstances = "0";
        try {
            numberOfInstances = textReader.readLine();

        } catch (IOException e) {
            TempData.LOGGER.severe(e.getMessage());
        }
        String l = "Found "+numberOfInstances+" instace(s) configurations...";
        TempData.LOGGER.info(l);
        int numOfInstances = Integer.parseInt(numberOfInstances);
        //TempData.cpInstances = new ArrayList<CpInstance>();
        TempData.numberOfInstancies = numOfInstances;
        InetAddress ipInstance = null;
        for(int i=0; i<numOfInstances; i++){
            l = "Configuring instace #"+(i+1)+", IP:";
            String ip = "0.0.0.0";
            try {
                ip = textReader.readLine();
            } catch (IOException e) {
                TempData.LOGGER.severe(e.getMessage());
            }
            try {
                ipInstance = InetAddress.getByName(ip);
            } catch (UnknownHostException e) {
                TempData.LOGGER.severe(e.getMessage());
            }
            l+=ip;
            TempData.LOGGER.info(l);
            CpInstance cpi = new CpInstance(ipInstance);
            TempData.cpInstances.put(cpi.getIp().toString(), cpi);
        }
    }



}