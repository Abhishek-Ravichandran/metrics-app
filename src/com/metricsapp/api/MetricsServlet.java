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
		        String message = "{ \"status\" : \"Metric created succesfully\" }";
	            response.setContentType("application/json");
				response.setContentLength(message.length() + 1);
				response.setStatus(HttpServletResponse.SC_OK);
				out.println(message);
		    } else {
		        String message = "{ \"status\" : \"Metric already exists!\" }";
	            response.setContentType("application/json");
				response.setContentLength(message.length() + 1);
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				out.println(message);
		    }
		} else if(request.getServletPath().equals("/addvalue")) {
		    String name = request.getParameter("metricname");
		    Double val = Double.parseDouble(request.getParameter("value"));
		    
		    if(DBWrapper.addValue(name, val)) {
	            String message = "{ \"status\" : \"Value added succesfully!\" }";
	            response.setContentType("application/json");
				response.setContentLength(message.length() + 1);
				response.setStatus(HttpServletResponse.SC_OK);
				out.println(message);
		    } else {
	            String message = "{ \"status\" : \"Cannot add to metric/metric doesn't exist!\" }";
	            response.setContentType("application/json");
				response.setContentLength(message.length() + 1);
				response.setStatus(HttpServletResponse.SC_OK);
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
        	    String message = "{ \"Mean\" : \"" + Double.toString(mean) + "\" }";
	            response.setContentType("application/json");
				response.setContentLength(message.length() + 1);
				response.setStatus(HttpServletResponse.SC_OK);
				out.println(message);
        	} else {
        	    String message = "{ \"status\" : \"No values for metric/metric doesn't exist!\" }";
        	    response.setContentType("application/json");
        	    response.setContentLength(message.length() + 1);
        		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        		out.println(message);
        	}
        } else if(request.getServletPath().equals("/getmedian")) {
		    String name = request.getParameter("metricname");

            Double median = DBWrapper.getMedian(name);
		    if(median != null) {
        	    String message = "{ \"Median\" : \"" + Double.toString(median) + "\" }";
	            response.setContentType("application/json");
				response.setContentLength(message.length() + 1);
				response.setStatus(HttpServletResponse.SC_OK);
				out.println(message);
        	} else {
        	    String message = "{ \"status\" : \"No values for metric/metric doesn't exist!\" }";
        	    response.setContentType("application/json");
        	    response.setContentLength(message.length() + 1);
        		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        		out.println(message);
        	}
        	
		} else if(request.getServletPath().equals("/getmin")) {
		    String name = request.getParameter("metricname");

            Double min = DBWrapper.getMin(name);
		    if(min != null) {
		        String message = "{ \"Min\" : \"" + Double.toString(min) + "\" }";
	            response.setContentType("application/json");
				response.setContentLength(message.length() + 1);
				response.setStatus(HttpServletResponse.SC_OK);
				out.println(message);
		    } else {
		        String message = "{ \"status\" : \"No values for metric/metric doesn't exist!\" }";
        	    response.setContentType("application/json");
				response.setContentLength(message.length() + 1);
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				out.println(message);
		    }
		} else if(request.getServletPath().equals("/getmax")) {
		    String name = request.getParameter("metricname");

            Double max = DBWrapper.getMax(name);
		    if(max != null) {
		        String message = "{ \"Max\" : \"" + Double.toString(max) + "\" }";
	            response.setContentType("application/json");
				response.setContentLength(message.length() + 1);
				response.setStatus(HttpServletResponse.SC_OK);
				out.println(message);
		    } else {
		        String message = "{ \"status\" : \"No values for metric/metric doesn't exist!\" }";
        	    response.setContentType("application/json");
				response.setContentLength(message.length() + 1);
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				out.println(message);
		    }
		} else if(request.getServletPath().equals("/printmetric")) {
		    String name = request.getParameter("metricname");
		    String message = "<html>" + DBWrapper.printMetric(name) + "</html>";
	        response.setContentType("text/html");
			response.setContentLength(message.length() + 1);
			response.setStatus(HttpServletResponse.SC_OK);
			out.println(message);
		}
		
		DBWrapper.shutdownEntityStore();
    }
}

  