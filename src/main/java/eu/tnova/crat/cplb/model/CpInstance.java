package eu.tnova.crat.cplb.model;

import java.net.InetAddress;
import java.util.HashMap;

import com.google.common.collect.EvictingQueue;

import eu.tnova.crat.cplb.data.Constants;

public class CpInstance {

    private String ip;
    private MachineMonitoringMetadata MachineMonitoringDataCurrent;
    private EvictingQueue<MachineMonitoringMetadata> MachineMonitoringDataHistory;
    
    private ODLOpenFlowMonitoringMetadata ODLOpenFlowMonitoringDataCurrent;
    private EvictingQueue<ODLOpenFlowMonitoringMetadata> ODLOpenFlowMonitoringDataHistory;
   // public boolean monitoringTaskActive = false;
    
    private HashMap<String, OFSwitch> OFSwitches;


    public CpInstance(String ipInstance) {
        this.ip = ipInstance;
        MachineMonitoringDataHistory = EvictingQueue.create(Constants.monitoringDataHistoryLenght);
    //    monitoringTaskActive = false;
        ODLOpenFlowMonitoringDataHistory = EvictingQueue.create(Constants.monitoringDataHistoryLenght);
        OFSwitches = new HashMap<String, OFSwitch>();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ipInstance) {
        this.ip = ipInstance;
    }
    
    public void addMachineMonitoringMetadata(MachineMonitoringMetadata mmm){
    	MachineMonitoringDataCurrent = mmm;
    	MachineMonitoringDataHistory.add(mmm);
    }
    
    public void addODLOpenFlowMonitoringMetadata(ODLOpenFlowMonitoringMetadata mmm){
    	ODLOpenFlowMonitoringDataCurrent = mmm;
    	ODLOpenFlowMonitoringDataHistory.add(mmm);
    }

	public ODLOpenFlowMonitoringMetadata getODLOpenFlowMonitoringDataCurrent() {
		// TODO Auto-generated method stub
		return ODLOpenFlowMonitoringDataCurrent;
	}
	
	public MachineMonitoringMetadata getMachineMonitoringDataCurrent() {
		// TODO Auto-generated method stub
		return MachineMonitoringDataCurrent;
	}
	
	public boolean addSwitch(String id, OFSwitch ofs){
		if (OFSwitches.get(id) == null){
			OFSwitches.put(id, ofs);
			return true;
		}
		return false;
	}
	
	public void setSwitchDelay(String id, double delay){
		OFSwitch ofs = OFSwitches.get(id);
		if (ofs == null){
			OFSwitches.put(id, new OFSwitch(id));
		}
		OFSwitches.get(id).delay = delay;
	
	}
	
	public void setSwitchRole(String id, int role){
		OFSwitch ofs = OFSwitches.get(id);
		if (ofs == null){
			OFSwitches.put(id, new OFSwitch(id));
		}
		OFSwitches.get(id).role = role;
	
	}

}