package eu.tnova.crat.cplb.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.List;
import java.util.Properties;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import eu.tnova.crat.cplb.data.Constants;
import eu.tnova.crat.cplb.data.TempData;
import eu.tnova.crat.cplb.model.CpInstance;
import eu.tnova.crat.cplb.model.MachineMonitoringMetadata;
import eu.tnova.crat.cplb.model.ODLOpenFlowMonitoringMetadata;
import eu.tnova.crat.cplb.model.OFSwitch;
import eu.tnova.crat.cplb.services.ODLServices;

public class WorkerMonitoringThread implements Runnable {

	private String threadName = "";
	private String instanceIp;

	public WorkerMonitoringThread(String threadName, String instanceIp) {
		this.threadName = threadName;
		this.instanceIp = instanceIp;
	}

	public void run() {
		//getODLInstanceMonitoringData(instanceIp);
		getInstanceMonitoringData(instanceIp);
	}

	private int getODLInstanceMonitoringData(String ipInstance) {
		ODLServices.updateAllNodes(ipInstance);
		ODLServices.updateNodesRole(ipInstance, null);
		return 0;
	}
	
	private int getInstanceMonitoringData(String ip) {
		// TempData.LOGGER.info(ip.toString());
		JSch jsch = new JSch();
		Session session;
		MachineMonitoringMetadata cpimm = new MachineMonitoringMetadata();
		try {
			session = jsch.getSession(Constants.REMOTE_HOST_USERNAME, ip, 2222);
			session.setPassword(Constants.REMOTE_HOST_PASSWORD);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			ChannelExec channel = (ChannelExec) session.openChannel("exec");
			BufferedReader in = new BufferedReader(new InputStreamReader(channel.getInputStream()));
			channel.setCommand("" + "free | awk 'FNR == 3 {print $3 + $4}';" // total
																				// RAM
					+ "free | awk 'FNR == 3 {print $3}';" // used RAM
					+ "free | awk 'FNR == 3 {print $4}';" // free RAM
					+ "nproc;" // num CPUs
					+ "uptime | awk 'FNR == 1 {print $8}';" + "uptime | awk 'FNR == 1 {print $9}';"
					+ "uptime | awk 'FNR == 1 {print $10}';" + "");
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
			aux = aux.substring(0, aux.length() - 1);
			aux = aux.replace(',', '.');
			cpimm.setLoadAvgOneMinute(Float.parseFloat(aux));
			aux = in.readLine();
			aux = aux.substring(0, aux.length() - 1);
			aux = aux.replace(',', '.');
			cpimm.setLoadAvgFiveMinute(new Float(aux));
			aux = in.readLine();
			aux = aux.replace(',', '.');
			cpimm.setLoadAvgFifteenMinute(new Float(aux));
			TempData.LOGGER.info("Monitoring instance's machine metadata for instance " + ip + ": " + cpimm.toString());
			// while((msg=in.readLine())!=null){
			// TempData.LOGGER.info(msg);
			// }
			in.close();
			channel.disconnect();
			session.disconnect();
		} catch (Exception e) {
			TempData.LOGGER.severe(e.getMessage());
			return -1;
		}
		CpInstance cp = TempData.cpInstances.get(ip);
		if (cp != null)
				cp.addMachineMonitoringMetadata(cpimm);
		return 0;
	}

}
