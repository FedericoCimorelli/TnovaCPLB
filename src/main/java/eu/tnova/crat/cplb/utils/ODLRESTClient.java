package eu.tnova.crat.cplb.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import eu.tnova.crat.cplb.data.Constants;

public class ODLRESTClient {

	public static JSONObject post(String url) throws Exception {
		return post(url, null);
	}
    public static JSONObject post(String url, JSONObject body) throws Exception {
        	CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(url);
            
            
            String authStr = Constants.ODL_username + ":" + Constants.ODL_password;
            String encodedAuthStr = Base64.encodeBase64String(authStr.getBytes());
            
            request.addHeader("Authorization", "Basic " + encodedAuthStr);
            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json");

            if (body != null) {
                request.setEntity(new StringEntity(body.toString()));
            }

            HttpResponse result = httpClient.execute(request);

            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            /*JSONParser parser = new JSONParser();
            Object resultObject = parser.parse(json);

             if (resultObject instanceof JSONObject) {
                    JSONObject obj =(JSONObject)resultObject;
                    return obj;
             }*/

        return new JSONObject(json);
    }
    
    
    public static JSONObject get(String url) throws Exception {
    	CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        
        
        String authStr = Constants.ODL_username + ":" + Constants.ODL_password;
        String encodedAuthStr = Base64.encodeBase64String(authStr.getBytes());
        
        request.addHeader("Authorization", "Basic " + encodedAuthStr);
        request.addHeader("Accept", "application/json");
        request.addHeader("Content-Type", "application/json");

        HttpResponse result = httpClient.execute(request);

        String json = EntityUtils.toString(result.getEntity(), "UTF-8");
        /* JSONParser parser = new JSONParser();
        Object resultObject = parser.parse(json);

         if (resultObject instanceof JSONObject) {
                JSONObject obj =(JSONObject)resultObject;
                return obj;
         } */

    return new JSONObject(json);
}
}