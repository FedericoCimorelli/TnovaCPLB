package eu.tnova.crat.cplb;

import java.net.InetAddress;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import eu.tnova.crat.cplb.data.Constants;
import eu.tnova.crat.cplb.data.TempData;
import eu.tnova.crat.cplb.http.DashBoardHandler;
import eu.tnova.crat.cplb.task.LoadBalancerThread;
import eu.tnova.crat.cplb.task.WorkerMonitoringThread;
import eu.tnova.crat.cplb.utils.Utils;


public class CPLoadBalancer{

    /*TODO
     * Externalize configuration loading from file (use some library for that)
     */


    public static void main( String[] args ){
        inizializeLogger();
        TempData.LOGGER.info("Starting LoadBalancer...");
        Utils.LoadingInstancesConfiguration();
        //setupAuthenticatedClient();
        setupMonitoringInstanceTasks();
        //doLoadBalancingTask();
        setupHTTPServer();
    }



    private static void setupHTTPServer() {
		// TODO Auto-generated method stub
    		final HttpServer server = HttpServer.createSimpleServer(null, Constants.HTTP_SERVER_PORT);
    		server.getServerConfiguration().addHttpHandler(new DashBoardHandler(), "/lb_dash");

    		try {
    			server.start();
    			TempData.LOGGER.info("HTTPServer running..");
    			Thread.currentThread().join();
    		} catch (Exception e) {
    			TempData.LOGGER.info("There was an error while starting Grizzly HTTP server: " + e.getMessage());
    		}
    		server.shutdown();
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




    private static void setupMonitoringInstanceTasks() {
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
        for(String instanceIp : TempData.instanceAddresses){
            WebResource webResource = TempData.client.resource(Constants.getODLNodesPath(instanceIp));
            ClientResponse response = webResource.get(ClientResponse.class);
            TempData.LOGGER.info("Getting cookies for instance at "+instanceIp);
            TempData.odlCookies = response.getCookies();
        }
        //TempData.client = Client.create();
    }

}