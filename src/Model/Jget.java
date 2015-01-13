package Model;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Jget {

	private String url;
	private URL u;
	private InputStream is = null;
	private DataInputStream dis;
	private String s;

	public Jget() {

	}

	public Jget(String url) {

		this.url = url;
		DowloadonScreen();
	}

	@SuppressWarnings("deprecation")
	public List<String> Dowload(String url) {

		this.url = url;
		List<String> lines = new ArrayList<String>();

		try {
			u = new URL(url);
			is = u.openStream();
			dis = new DataInputStream(new BufferedInputStream(is));
			while ((s = dis.readLine()) != null) {
				lines.add(s);
			}
		} catch (MalformedURLException mue) {
			System.err.println("Ouch - a MalformedURLException happened.");
			mue.printStackTrace();
			//System.exit(2);
		} catch (IOException ioe) {
			System.err.println("Oops- an IOException happened.");
			ioe.printStackTrace();
			//System.exit(3);
		} finally {
			try {
				is.close();
			} catch (IOException ioe) {
			}
		}

		return (lines);
	}

	@SuppressWarnings("deprecation")
	public void DowloadonScreen() {

		try {
			u = new URL(url);
			is = u.openStream();
			dis = new DataInputStream(new BufferedInputStream(is));
			while ((s = dis.readLine()) != null) {
				System.out.println(s);
			}
		} catch (MalformedURLException mue) {
			System.err.println("Ouch - a MalformedURLException happened.");
			mue.printStackTrace();
			System.exit(2);
		} catch (IOException ioe) {
			System.err.println("Oops- an IOException happened.");
			ioe.printStackTrace();
			System.exit(3);
		} finally {
			try {
				is.close();
			} catch (IOException ioe) {
			}
		}
	}

}
