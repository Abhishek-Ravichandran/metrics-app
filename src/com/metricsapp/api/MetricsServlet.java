package com.metricsapp.api;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.metricsapp.storage.DBWrapper;

@SuppressWarnings("serial")
public class MetricsServlet extends HttpServlet {
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
    {	
    	//get bdbstore path
		ServletContext context = getServletContext();
		String BDBPath = context.getInitParameter("BDBstore");
		
		DBWrapper.init(BDBPath);
		DBWrapper.setupEntityStore();
		
		//initialize response writer
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
    	
    	//if create metric request
		if(request.getServletPath().equals("/createmetric")) {
		    String name = request.getParameter("metricname");
		    if(DBWrapper.insertMetric(name)) {
		        String message = "<html>Metric created succesfully</html>";
	            response.setContentType("text/html");
				response.setContentLength(message.length() + 1);
				response.setStatus(HttpServletResponse.SC_OK);
				out.println(message);
		    } else {
		        String message = "<html>Metric already exists!</html>";
	            response.setContentType("text/html");
				response.setContentLength(message.length() + 1);
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				out.println(message);
		    }
		} else if(request.getServletPath().equals("/addvalue")) {
		    String name = request.getParameter("metricname");
		    Double val = Double.parseDouble(request.getParameter("value"));
		    
		    if(DBWrapper.addValue(name, val)) {
		        String message = "<html>Value added succesfully</html>";
	            response.setContentType("text/html");
				response.setContentLength(message.length() + 1);
				response.setStatus(HttpServletResponse.SC_OK);
				out.println(message);
		    } else {
		        String message = "<html>Cannot add to metric/metric doesn't exist!</html>";
	            response.setContentType("text/html");
				response.setContentLength(message.length() + 1);
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				out.println(message);
		    }
		}

		DBWrapper.shutdownEntityStore();
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
    {	
    	//get bdbstore path
		ServletContext context = getServletContext();
		String BDBPath = context.getInitParameter("BDBstore");
		
		DBWrapper.init(BDBPath);
		DBWrapper.setupEntityStore();
		
		//initialize response writer
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		if(request.getServletPath().equals("/getmean")) {
		    
		    String name = request.getParameter("metricname");
            Double mean = DBWrapper.getMean(name);
        	if(mean != null) {
        	    String message = "<html>Mean: " + Double.toString(mean) + "</html>";
        	    response.setContentType("text/html");
        		response.setContentLength(message.length() + 1);
        	    response.setStatus(HttpServletResponse.SC_OK);
        		out.println(message);
        	} else {
        	    String message = "<html>No values for metric/metric doesn't exist!</html>";
        	    response.setContentType("text/html");
        	    response.setContentLength(message.length() + 1);
        		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        		out.println(message);
        	}
        } else if(request.getServletPath().equals("/getmedian")) {
		    String name = request.getParameter("metricname");

            Double median = DBWrapper.getMedian(name);
		    if(median != null) {
		        String message = "<html>Median: " + Double.toString(median) + "</html>";
	            response.setContentType("text/html");
				response.setContentLength(message.length() + 1);
				response.setStatus(HttpServletResponse.SC_OK);
				out.println(message);
		    } else {
		        String message = "<html>No values for metric/metric doesn't exist!</html>";
	            response.setContentType("text/html");
				response.setContentLength(message.length() + 1);
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				out.println(message);
		    }
		} else if(request.getServletPath().equals("/getmin")) {
		    String name = request.getParameter("metricname");

            Double min = DBWrapper.getMin(name);
		    if(min != null) {
		        String message = "<html>Min: " + Double.toString(min) + "</html>";
	            response.setContentType("text/html");
				response.setContentLength(message.length() + 1);
				response.setStatus(HttpServletResponse.SC_OK);
				out.println(message);
		    } else {
		        String message = "<html>No values for metric/metric doesn't exist!</html>";
	            response.setContentType("text/html");
				response.setContentLength(message.length() + 1);
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				out.println(message);
		    }
		} else if(request.getServletPath().equals("/getmax")) {
		    String name = request.getParameter("metricname");

            Double max = DBWrapper.getMax(name);
		    if(max != null) {
		        String message = "<html>Max: " + Double.toString(max) + "</html>";
	            response.setContentType("text/html");
				response.setContentLength(message.length() + 1);
				response.setStatus(HttpServletResponse.SC_OK);
				out.println(message);
		    } else {
		        String message = "<html>No values for metric/metric doesn't exist!</html>";
	            response.setContentType("text/html");
				response.setContentLength(message.length() + 1);
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				out.println(message);
		    }
		} else if(request.getServletPath().equals("/printmetric")) {
		    String name = request.getParameter("metricname");

            String vals = DBWrapper.printMetric(name);
		    if(vals != null) {
		        String message = "<html>Vals: " + vals + "</html>";
	            response.setContentType("text/html");
				response.setContentLength(message.length() + 1);
				response.setStatus(HttpServletResponse.SC_OK);
				out.println(message);
		    } else {
		        String message = "<html>No values for metric/metric doesn't exist!</html>";
	            response.setContentType("text/html");
				response.setContentLength(message.length() + 1);
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				out.println(message);
		    }
		} 
		
		DBWrapper.shutdownEntityStore();
    }
}

  