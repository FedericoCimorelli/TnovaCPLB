package eu.tnova.cplb;

import java.net.InetAddress;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import eu.tnova.cplb.data.Constants;
import eu.tnova.cplb.data.TempData;
import eu.tnova.cplb.task.LoadBalancerThread;
import eu.tnova.cplb.task.WorkerMonitoringThread;
import eu.tnova.cplb.utils.Utils;


public class CPLoadBalancer{

    /*TODO
     * Externalize configuration loading from file (use some library for that)
     */


    public static void main( String[] args ){
        inizializeLogger();
        TempData.LOGGER.info("Starting LoadBalancer...");
        Utils.LoadingInstancesConfiguration();
        setupAuthenticatedClient();
        configureAndStartMonitoringInstaceTasks();
        doLoadBalancingTask();
    }



    private static void doLoadBalancingTask() {
        TempData.LOGGER.info("Configure and start Load Balancing...");
        TempData.LOGGER.info("Scheduling LoadBalancer working Thread");
        ScheduledThreadPoolExecutor stpe =
                new ScheduledThreadPoolExecutor(Constants.scheduledThreadPoolExecutorCorePoolSize);
        /*
         * This will execute the WorkerThread continuously for every 'TempData.scheduledMonitoringThreadFixedTimeout'
         * seconds with an initial delay of 'TempData.scheduledMonitoringThreadInitialDelay'
         * seconds for the first WorkerThread to start execution cycle. In this case, whether the first
         * WorkerThread is completed or not, the second WorkerThread will start exactly after 5 seconds hence
         * called schedule at fixed rate. This continues till 'n' threads are executed.
         */
        stpe.scheduleAtFixedRate(
                new LoadBalancerThread(),
                Constants.scheduledLoadbalancerThreadInitialDelay,
                Constants.scheduledLoadbalancerThreadFixedTimeout,
                TimeUnit.SECONDS);
        }



    private static void inizializeLogger() {
        TempData.LOGGER = Logger.getLogger("TnovaCPLB");
        TempData.LOGGER.info("LoadBalancer logger initialized");
    }




    private static void configureAndStartMonitoringInstaceTasks() {
        TempData.LOGGER.info("Configure and start monitoring instace tasks...");
        for(InetAddress iIp : TempData.cpInstances.keySet()){
            String wmtName = "wmt@"+iIp;
            TempData.LOGGER.info("Started Worker Monitoring Thread "+wmtName+" execution loop...");
            ScheduledThreadPoolExecutor stpe =
                    new ScheduledThreadPoolExecutor(Constants.scheduledThreadPoolExecutorCorePoolSize);
            /*
             * This will execute the WorkerThread continuously for every 'TempData.scheduledMonitoringThreadFixedTimeout'
             * seconds with an initial delay of 'TempData.scheduledMonitoringThreadInitialDelay'
             * seconds for the first WorkerThread to start execution cycle. In this case, whether the first
             * WorkerThread is completed or not, the second WorkerThread will start exactly after 5 seconds hence
             * called schedule at fixed rate. This continues till 'n' threads are executed.
             */
            stpe.scheduleAtFixedRate(
                    new WorkerMonitoringThread(wmtName, iIp),
                        Constants.scheduledMonitoringThreadInitialDelay,
                        Constants.scheduledMonitoringThreadFixedTimeout,
                            TimeUnit.SECONDS);
        }
    }

    private static void setupAuthenticatedClient(){
        TempData.client = Client.create();
        TempData.client.addFilter(new HTTPBasicAuthFilter(Constants.ODL_username, Constants.ODL_password));
        for(String instanceIp : TempData.instanceAndresses){
            WebResource webResource = TempData.client.resource(Constants.getOdlNodesPath(instanceIp));
            ClientResponse response = webResource.get(ClientResponse.class);
            TempData.LOGGER.info("Getting cookies for instance at "+instanceIp);
            TempData.odlCookies = response.getCookies();
        }
        //TempData.client = Client.create();
    }

}