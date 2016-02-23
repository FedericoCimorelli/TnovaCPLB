package eu.tnova.crat.cplb.task;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.jcraft.jsch.JSchException;

import eu.tnova.crat.cplb.data.TempData;
import eu.tnova.crat.cplb.services.MachineServices;
import eu.tnova.crat.cplb.services.ODLServices;

public class WorkerMonitoringThread implements Runnable {

	private String threadName = "";
	private String instanceIp;

	public WorkerMonitoringThread(String threadName, String instanceIp) {
		this.threadName = threadName;
		this.instanceIp = instanceIp;
	}

	public void run() {
		getODLInstanceMonitoringData(instanceIp);
		getInstanceMonitoringData(instanceIp);
	}

	private int getODLInstanceMonitoringData(String ipInstance) {
		try {
			ODLServices.updateAllNodes(ipInstance);
			ODLServices.updateNodesRole(ipInstance, null);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			TempData.LOGGER.severe(e.getMessage());
			return -1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			TempData.LOGGER.severe(e.getMessage());
			return -1;
		}
		return 0;
	}
	
	private int getInstanceMonitoringData(String ipInstance) {
		// TempData.LOGGER.info(ip.toString());
		try {
			MachineServices.updateMachineMetrics(ipInstance);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			TempData.LOGGER.severe(e.getMessage());
			return -1;
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			TempData.LOGGER.severe(e.getMessage());
			return -1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			TempData.LOGGER.severe(e.getMessage());
			return -1;
		}		
		return 0;
		}

}
