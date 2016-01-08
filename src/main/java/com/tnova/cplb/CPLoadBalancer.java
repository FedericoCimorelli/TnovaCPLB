package com.tnova.cplb;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import task.WorkerMonitoringThread;

import com.tnova.cplb.data.TempData;
import com.tnova.cplb.utils.Utils;


public class CPLoadBalancer{

    public static void main( String[] args ){
        InizializeLogger();
        TempData.LOGGER.info("Starting LoadBalancer...");
        Utils.LoadingInstancesConfiguration();
        ConfigureAndStartMonitoringInstaceTasks();
    }


    private static void InizializeLogger() {
        TempData.LOGGER = Logger.getLogger("TnovaCPLB");
        TempData.LOGGER.info("LoadBalancer logger initialized");
    }



    private static void ConfigureAndStartMonitoringInstaceTasks() {
        TempData.LOGGER.info("Configure and start monitoring instace tasks...");
        for(String iIp : TempData.cpInstances.keySet()){
            String wmtName = "wmt@"+iIp;
            TempData.LOGGER.info("Started Worker Monitoring Thread "+wmtName+" execution loop...");
            ScheduledThreadPoolExecutor stpe =
                    new ScheduledThreadPoolExecutor(TempData.scheduledThreadPoolExecutorCorePoolSize);
            /*
             * This will execute the WorkerThread continuously for every 'TempData.scheduledMonitoringThreadFixedTimeout'
             * seconds with an initial delay of 'TempData.scheduledMonitoringThreadInitialDelay'
             * seconds for the first WorkerThread to start execution cycle. In this case, whether the first
             * WorkerThread is completed or not, the second WorkerThread will start exactly after 5 seconds hence
             * called schedule at fixed rate. This continues till 'n' threads are executed.
             */
            stpe.scheduleAtFixedRate(
                    new WorkerMonitoringThread(
                            wmtName),
                            TempData.scheduledMonitoringThreadInitialDelay,
                            TempData.scheduledMonitoringThreadFixedTimeout,
                            TimeUnit.SECONDS);
        }
    }

}