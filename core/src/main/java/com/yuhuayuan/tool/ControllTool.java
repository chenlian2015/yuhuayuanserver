package com.yuhuayuan.tool;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ControllTool {
	
	private static final Logger logger = Logger.getLogger(ControllTool.class);
	
    public static String getRequestBody(HttpServletRequest req)  {
        StringBuilder body = new StringBuilder();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(req.getInputStream()));

            String line = null;
            while ((line = br.readLine()) != null) {
                body.append(line);
            }
        } catch (IOException e) {
            
        } finally {
            try {
                br.close();
            } catch (IOException e) {
              
            }
        }

        return body.toString();
    }
    
    public static void LogRequest(HttpServletRequest request, Logger logger)
    {
		Map<String, String> mapPar = request.getParameterMap();
		Set<String> keyPar = mapPar.keySet();
		Iterator<String> it = keyPar.iterator();
		while(it.hasNext())
		{
			String key = it.next();
			
			String value = request.getParameter(key);
			logger.error(key+"="+value);
		}
		
    }
    
    public static void LogString(String val)
    {
    	logger.error(val);
    }
}
