package Model;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import rodrigo.sampedro.ems2ionex.emsdecoder;

public class Jget {

	// Parameters
	private String url;
	private String s;
	private int intentos = 3;
	private boolean show = false;

	// Necessary objets
	private URL u;
	private InputStream is = null;
	private DataInputStream dis;

	// Constructors
	public Jget() {

	}

	public Jget(String url) {

		this.url = url;
	}

	// Gets and Sets
	public void setIntentos(int intento) {
		this.intentos = intento;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	@SuppressWarnings("deprecation")
	public List<String> Dowload(String url) throws Exception {

		this.url = url;
		// init array of data to be download
		List<String> lines = new ArrayList<String>();
		// init dowload log
		ErrorLog log = ErrorLog.getInstance();
		boolean descargado = false;

		int i = 0;
		while (!descargado) {

			// try to download the file
			try {
				u = new URL(url);
				is = u.openStream();
				dis = new DataInputStream(new BufferedInputStream(is));
				// make a sleep to prevend to be denied on the server
				Thread.sleep(100);
				// get and save lines
				while ((s = dis.readLine()) != null) {
					lines.add(s);
					if (show) {
						emsdecoder.Countline();
					}
				}
				descargado = true;

			} catch (Exception e) {
				// reintent
				if (show)
					System.out.println("reintent " + (i + 1) + "º : " + url);
				//log.AddError("reintent " + (i + 1) + "º : " + url);
				log.AddError("reintent " + (i + 1) + "º : " + url);
				// wait 100 ms, 300 ms, 500 ms, 700 ms...
				Thread.sleep(200 + (i * 200));
				i++;
				if (i >= intentos) {
					throw e;
				}
			}

		}

		return (lines);
	}

	// @SuppressWarnings("deprecation")
	// public void DowloadonScreen() {
	//
	// try {
	// u = new URL(url);
	// is = u.openStream();
	// dis = new DataInputStream(new BufferedInputStream(is));
	// while ((s = dis.readLine()) != null) {
	// System.out.println(s);
	// }
	// } catch (MalformedURLException mue) {
	// System.err.println("Ouch - a MalformedURLException happened.");
	// mue.printStackTrace();
	// System.exit(2);
	// } catch (IOException ioe) {
	// System.err.println("Oops- an IOException happened.");
	// ioe.printStackTrace();
	// System.exit(3);
	// } finally {
	// try {
	// is.close();
	// } catch (IOException ioe) {
	// }
	// }
	// }

}
