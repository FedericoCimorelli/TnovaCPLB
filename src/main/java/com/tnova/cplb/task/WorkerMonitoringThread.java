package com.tnova.cplb.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Properties;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.tnova.cplb.data.TempData;
import com.tnova.cplb.model.CpInstanceMonitoringMetadata;

public class WorkerMonitoringThread implements Runnable{

    private String threadName = "";
    private InetAddress instanceIp;


    public WorkerMonitoringThread(String threadName, InetAddress instanceIp){
        this.threadName = threadName;
        this.instanceIp = instanceIp;
    }


    public void run(){
        getInstanceMachineResourceMonitoringData(instanceIp);
    }


    private int getInstanceMachineResourceMonitoringData(InetAddress ip){
        TempData.LOGGER.info(ip.toString());
        JSch jsch=new JSch();
        Session session;
        CpInstanceMonitoringMetadata cpimm = new CpInstanceMonitoringMetadata();
        try {
            String i = ip.toString();
            if(i.startsWith("/"))
                i = i.substring(1);
            session = jsch.getSession(TempData.REMOTE_HOST_USERNAME, i, 22);
            session.setPassword(TempData.REMOTE_HOST_PASSWORD);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            ChannelExec channel=(ChannelExec) session.openChannel("exec");
            BufferedReader in=new BufferedReader(new InputStreamReader(channel.getInputStream()));
            channel.setCommand(""
                    + "free | awk 'FNR == 3 {print $3 + $4}';" //total RAM
                    + "free | awk 'FNR == 3 {print $3}';" //used RAM
                    + "free | awk 'FNR == 3 {print $4}';" //free RAM
                    + "nproc;" //num CPUs
                    + "uptime | awk 'FNR == 1 {print $8}';"
                    + "uptime | awk 'FNR == 1 {print $9}';"
                    + "uptime | awk 'FNR == 1 {print $10}';"
                            + "");
            channel.connect();
            String aux = in.readLine();
            cpimm.setTotalRam(Float.parseFloat(aux));
            aux = in.readLine();
            cpimm.setUsedRam(Float.parseFloat(aux));
            aux = in.readLine();
            cpimm.setFreeRam(Float.parseFloat(aux));
            aux = in.readLine();
            cpimm.setnCPU(new Integer(aux));
            aux = in.readLine();
            aux = aux.substring(0, aux.length()-1);
            aux = aux.replace(',', '.');
            cpimm.setLoadAvgOneMinute(Float.parseFloat(aux));
            aux = in.readLine();
            aux = aux.substring(0, aux.length()-1);
            aux = aux.replace(',', '.');
            cpimm.setLoadAvgFiveMinute(new Float(aux));
            aux = in.readLine();
            aux = aux.replace(',', '.');
            cpimm.setLoadAvgFifteenMinute(new Float(aux));
            TempData.LOGGER.info("Monitoring metadata for instance "+ip+": "+cpimm.toString());
            //while((msg=in.readLine())!=null){
            //  TempData.LOGGER.info(msg);
            //}
            in.close();
            channel.disconnect();
            session.disconnect();
        }
        catch (JSchException e) {
            TempData.LOGGER.severe(e.getMessage());
            return -1;
        } catch (IOException e) {
            TempData.LOGGER.severe(e.getMessage());
            return -1;
        }
        TempData.cpInstances.get(ip).monitoringMetadata.add(cpimm);
        TempData.LOGGER.info("444");
        return 0;
    }

}
