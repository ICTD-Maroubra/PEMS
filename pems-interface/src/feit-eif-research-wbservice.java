package feit.eif.research.ws;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
public class TestWbService {

	private static String URL_BASE = 'http://eif-research.feit.uts.edu.au/api/json/?';

	private String rFromDate;
	private String rToDate;
	private String rFamily;
	private String rSensor;
	private String rSubsensor;

	public TestWbService() {}

	public static void main(String args[])
	{
		BufferedReader rd;
		OutputStreamWriter wr;

		try
		{

			URL url = new URL("http://eif-research.feit.uts.edu.au/api/json/?rFromDate=2017-10-10T01%3A59%3A38&rToDate=2017-10-10T12%3A22%3A38&rFamily=weather&rSensor=AT");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			wr = new OutputStreamWriter(conn.getOutputStream());
			wr.flush();

			// Get the response
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
			}
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public String getSensorData () {

		String url;
		url = URL_BASE + "rFromDate=" + rFromDate + "&" + "rToDate=" + rToDate + "&" ;

		if (this.rFamily.equals("weather")) {
			url += "rFamily=" + "weather" + "&" + "rSensor=" + rSensor;

		}

		else if (this.rFamily.equals("wasp")) {
			url += "rFamily=" + "wasp" + "&" + "rSensor=" + rSensor + "&" + "rSubsensor" + rSubsensor;
		}

		else {
			// invalid sensor type
		}

		BufferedReader rd;
		OutputStreamWriter wr;

		try
		{

			URL url = new URL(url);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			wr = new OutputStreamWriter(conn.getOutputStream());
			wr.flush();

			// Get the response
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			String result;
			while ((line = rd.readLine()) != null) {
				result += line;
			}
			return result;
		}
		catch (Exception e) {
			//
		}

	}

	public String getrFromDate() {
		return rFromDate;
	}

	public void setrFromDate(String rFromDate) {
		this.rFromDate = rFromDate;
	}

	public String getrToDate() {
		return rToDate;
	}

	public void setrToDate(String rToDate) {
		this.rToDate = rToDate;
	}

	public String getrFamily() {
		return rFamily;
	}

	public void setrFamily(String rFamily) {
		this.rFamily = rFamily;
	}

	public String getrSensor() {
		return rSensor;
	}

	public void setrSensor(String rSensor) {
		this.rSensor = rSensor;
	}

	public String getrSubsensor() {
		return rSubsensor;
	}

	public void setrSubsensor(String rSubsensor) {
		this.rSubsensor = rSubsensor;
	}
}