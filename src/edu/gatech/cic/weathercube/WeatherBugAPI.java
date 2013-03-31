package edu.gatech.cic.weathercube;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class WeatherBugAPI {
		
	public static final String API_KEY = "dhyxw9g665ytk4u4v8harajb";
	Forecast forecast;
	
	
	public WeatherBugAPI() {
		forecast = new Forecast();
	}
	
	
	
	public void getForecastByZipCode(String zipCode) {
			String url = "http://i.wxbug.net/REST/Direct/GetForecast.ashx?zip="+zipCode+"&nf=1&ih=1&ht=t&ht=i&ht=d&api_key=fkpjnxbndy3ztxb2r3b2b8ch";
			new RequestTask().execute(url);
	}
	
	class RequestTask extends AsyncTask<String, String, String>{

	    @Override
	    protected String doInBackground(String... uri) {
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpResponse response;
	        String responseString = null;
	        try {
	            response = httpclient.execute(new HttpGet(uri[0]));
	            StatusLine statusLine = response.getStatusLine();
	            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	                ByteArrayOutputStream out = new ByteArrayOutputStream();
	                response.getEntity().writeTo(out);
	                out.close();
	                responseString = out.toString();
	                JSONObject json = new JSONObject(responseString );
	                JSONObject main = json.getJSONArray("forecastList").getJSONObject(0);
	                forecast.setDayDesc(main.getString("dayDesc"));
	                long time = Long.parseLong(main.getString("dateTime"));
	                forecast.setHigh(main.getString("high"));
	                forecast.setLow(main.getString("low"));
	                forecast.setDayTitle(main.getString("dayTitle"));
	                JSONObject hourly = main.getJSONArray("hourly").getJSONObject(0);
	                forecast.setTemperature(hourly.getString("temperature"));
	                
	                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");
	                forecast.setDate(sdf.format(new Date(time)));
	                String displayTime = (new Date().getTime() / (1000 * 60 * 60)) % 24 + ":" + (new Date().getTime() / (1000 * 60)) % 60;
	                forecast.setTime(displayTime);
	                
	            } else{
	                //Closes the connection.
	                response.getEntity().getContent().close();
	                throw new IOException(statusLine.getReasonPhrase());
	            }
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	        	e.printStackTrace();
	        } catch (JSONException e) {
				e.printStackTrace();
			}
	        return responseString;
	    }
	}
	
	
}