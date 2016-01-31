package eu.tnova.crat.cplb.http;

import java.util.Iterator;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

import eu.tnova.crat.cplb.data.TempData;
import eu.tnova.crat.cplb.model.CpInstance;

public class DashBoardHandler extends HttpHandler {

	@Override
	public void service(Request request, Response response) throws Exception {
		// TODO Auto-generated method stub
				response.setContentType("text/html");
				
				String response_body = "";
				response_body+="<html>";
				response_body+="<head>";
				response_body+="<meta http-equiv='refresh' content='5'>";
				response_body+="<title>ODL LoadBalancer</title>";
				response_body+="</head>";
				
				response_body+="<body>";
					
				Iterator<CpInstance> it = TempData.cpInstances.values().iterator();
				while (it.hasNext()) {
			        CpInstance cp = (CpInstance)it.next();
			        response_body += cp.getIp() + "<br/>";
			        response_body += cp.getMachineMonitoringDataCurrent();
					// avoids a ConcurrentModificationException
			    }
			    			
				
				response_body+="</body></html>";
				
				response.getWriter().append(response_body);
								
			}
		
	}

