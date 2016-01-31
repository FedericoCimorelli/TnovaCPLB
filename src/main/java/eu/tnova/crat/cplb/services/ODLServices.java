package eu.tnova.crat.cplb.services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import eu.tnova.crat.cplb.data.Constants;
import eu.tnova.crat.cplb.data.TempData;
import eu.tnova.crat.cplb.model.OFSwitch;
import eu.tnova.crat.cplb.utils.Utils;

public class ODLServices {

	public static void updateAllNodes(String instanceIp) {
		try {
			String path = Constants.getODLNodesPath(instanceIp);
			/*
			 * WebResource res = TempData.client.resource(path); Builder builder
			 * = res.type(MediaType.APPLICATION_JSON); for(NewCookie nc :
			 * TempData.odlCookies) builder.cookie(nc); String r =
			 * builder.get(String.class);
			 */
			TempData.LOGGER.info("Monitoring instance ODL OpenFlow metadata, getting " + path);
			JSONObject response = ODLRESTClient.get(path);

			JSONArray ja = response.getJSONObject("nodes").getJSONArray("node");
			for (int i = 0; i < ja.length(); i++) {
				String switchId = ja.getJSONObject(i).getString("id").split(":")[1];
				OFSwitch ofs = new OFSwitch(switchId);
				TempData.cpInstances.get(instanceIp).addSwitch(switchId, ofs);
			}

		} catch (Exception e) {
			TempData.LOGGER.severe(e.getMessage());
		}
	}

	public static List<OFSwitch> updateNodesRole(String instanceIp, String[] switch_ids) {
		try {
			String path = Constants.getODLGetRolesPath(instanceIp);
			if (switch_ids == null) {
				switch_ids = new String[] {};
			}

			JSONArray jarray = new JSONArray(switch_ids);
			JSONObject body = new JSONObject("{'input':{'switch-ids':" + jarray.toString() + "}}");
			JSONObject response = ODLRESTClient.post(path, body);

			JSONObject output = response.optJSONObject("output");

			if (output != null) {

				int responsecode = output.getInt("response-code");
				JSONArray responsemessage = output.getJSONArray("response-message");

				for (int i = 0; i < responsemessage.length(); i++) {
					String message = responsemessage.getString(i);
					String[] info = message.split(":");
					String switch_id = info[0];
					String role_code = info[1];
					String role_name = info[2];
					TempData.cpInstances.get(instanceIp).setSwitchRole(switch_id, Integer.parseInt(role_code));
				}

			}

			
		} catch (Exception e) {
			TempData.LOGGER.severe(e.getMessage());
		}
		return new ArrayList<OFSwitch>();
	}

	public static List<OFSwitch> setNodesRole(String instanceIp, String[] switch_ids, int role) {
		try {
			String path = Constants.getODLSetRolesPath(instanceIp);
			if (switch_ids == null) {
				switch_ids = new String[] {};
			}

			JSONArray jarray = new JSONArray(switch_ids);
			JSONObject body = new JSONObject(
					"{'input':{'ofp-role':" + Utils.getRoleCode(role) + ",'switch-ids':" + jarray.toString() + "}}");
			JSONObject response = ODLRESTClient.post(path, body);

			return null;
		} catch (Exception e) {
			TempData.LOGGER.severe(e.getMessage());
		}
		return new ArrayList<OFSwitch>();
	}
}
