package task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.tnova.cplb.data.TempData;

public class WorkerMonitoringThread implements Runnable{

    private String threadName = "";
    private String instanceIp = "";


    public WorkerMonitoringThread(String threadName, String instanceIp){
        this.threadName = threadName;
        this.instanceIp = instanceIp;
    }


    public void run(){
        TempData.LOGGER.info("WorkerMonitoringThread "+threadName+" execution...");
        getInstanceMachineResourceMonitoringData(instanceIp);
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        TempData.LOGGER.info("WorkerMonitoringThread "+threadName+" result: XYZ");
    }


    private int getInstanceMachineResourceMonitoringData(String ip){
        if(ip.startsWith("/"))
            ip = ip.substring(1);
        TempData.LOGGER.info(ip);
        JSch jsch=new JSch();
        Session session;
        try {
            session = jsch.getSession(TempData.remoteHostUserName, ip, 22);
            session.setPassword(TempData.remoteHostpassword);
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
            float freeRam = -1, usedRam = -1, totalRam = -1;
            int nCPU = -1;
            String aux = in.readLine();
            totalRam = new Float(aux);
            aux = in.readLine();
            usedRam = new Float(aux);
            aux = in.readLine();
            freeRam = new Float(aux);
            aux = in.readLine();
            nCPU = new Integer(aux);
            aux = in.readLine();
            aux = aux.substring(0, aux.length()-1);
            TempData.LOGGER.info(aux);
            aux = in.readLine();
            aux = aux.substring(0, aux.length()-1);
            TempData.LOGGER.info(aux);
            aux = in.readLine();
            TempData.LOGGER.info(aux);
            TempData.LOGGER.info("Monitoring metadata for instance "+ip+": "
                    +"FreeRAM:"+freeRam+" UsedRAM:"+usedRam+" TotalRAM:"+totalRam+" nCPU:"+nCPU);
            //while((msg=in.readLine())!=null){
            //  TempData.LOGGER.info(msg);
            //}
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
        return 0;
    }

}
