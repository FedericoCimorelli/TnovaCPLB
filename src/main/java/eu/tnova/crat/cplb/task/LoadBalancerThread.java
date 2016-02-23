package eu.tnova.crat.cplb.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import eu.tnova.crat.cplb.data.Constants;
import eu.tnova.crat.cplb.data.TempData;
import eu.tnova.crat.cplb.model.CpInstance;
import eu.tnova.crat.cplb.model.OFSwitch;
import eu.tnova.crat.cplb.services.ODLServices;
import eu.tnova.crat.cplb.utils.Utils;

public class LoadBalancerThread implements Runnable{

    public void run() {
        // TODO Auto-generated method stub
        TempData.LOGGER.info("Doing Load Balancer...");
        balanceNumberMasters();
        
    }
    
    
    public static void balanceNumberMasters() {
    	List<Migration> migrations = new LinkedList<Migration>();
    	TempData.LOGGER.info("Balance based on the number of nodes currently being cared of by each controller");
        
    	int mean = TempData.ofSwitches.size()
                / TempData.cpInstances.size();
        int remainder = TempData.ofSwitches.size()
                % TempData.cpInstances.size();
        
        ArrayList<ArrayList<OFSwitch>> nodesMap = new ArrayList<ArrayList<OFSwitch>>();
        ArrayList<Integer> counter = new ArrayList<Integer>();
        String[] instances = new String[TempData.cpInstances.size()];
        
        
        TempData.LOGGER.info("mean="+mean+" remainder=" + remainder);
       
        int i = 0;
        for (String contr : TempData.cpInstances.keySet()) {
            CpInstance cp = TempData.cpInstances.get(contr);
            ArrayList<OFSwitch> switches = cp.getSwitchesIsMasterFor();
            instances[i] = contr;
            counter.add(switches.size());
            nodesMap.add(switches);  
            i++;
        }
        
        
        for (int idx = 0; idx < instances.length; idx++) {
        	ArrayList<OFSwitch> switches = nodesMap.get(idx);
        	while (nodesMap.get(idx).size() > mean + 1){
        		int candidate_idx = counter.indexOf(Collections.min(counter));
        		if (candidate_idx != idx){
        			OFSwitch ofswitch = switches.get(0); 
        			migrations.add(new Migration(instances[idx], instances[candidate_idx], ofswitch.getSwitchID()));
        			OFSwitch ofs = nodesMap.get(idx).remove(0);
        			nodesMap.get(candidate_idx).add(ofs);
        			counter.set(idx, nodesMap.get(idx).size());
         			counter.set(candidate_idx, nodesMap.get(candidate_idx).size());
        		} else {
        			break;
        		}
        	}
        }
        
        try {
			migrate(migrations);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			TempData.LOGGER.info("Error in migrating switches:" + e.getMessage());
	    }
        
        
        
        
    }
        
    private static void migrate(List<Migration> migrations) throws Exception{
    	for (Migration m : migrations){
    		TempData.LOGGER.info(m.toString());
                		
    		ODLServices.setNodesRole(m.to, new String[] { m.switch_id }, Constants.OFPCRROLEEQUAL);
    		Thread.sleep(100);
    		ODLServices.setNodesRole(m.from, new String[] { m.switch_id }, Constants.OFPCRROLESLAVE);
    		ODLServices.setNodesRole(m.to, new String[] { m.switch_id }, Constants.OFPCRROLEMASTER);
        
    	}
    }

    static class Migration{
    	public String from;
    	public String to;
    	public String switch_id;
    	
    	public Migration(String f, String t, String s){
    		from = f;
    		to = t;
    		switch_id = s;
    	}
    	
    	public String toString(){
    		return "From: " + from + " To:" + to + " Switch_id: " + switch_id;
    	}
    }

}
