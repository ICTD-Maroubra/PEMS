package feit.eif.research.ws;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
public class TestWbService {

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
}