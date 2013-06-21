package com.healthylicous.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.SimplePayload;

import com.healthylicous.connection.pubsub.PubSubHandler;

public class ResultCreator extends Thread {
	public static final String VORSCHLAG = "Vorschlag";
	private static final String USER = "healthy";
	private static final String PASSWORD = "openfire";
	private static final String TEST = "./././././Ressources/resFett2.xml";
	private static final String FILEFETT = "./././././Ressources/resFett.xml";
	private static final String FILEOKAY= "./././././Ressources/resOkay.xml";
	private static final String FILELESS = "./././././Ressources/resLess.xml";
	double kal;
	DataHandler data;
	PubSubHandler con;
	String itemId, user;
	
	public ResultCreator(String itemId, String user, double kal) {
		this.kal = kal;
		this.itemId = itemId;
		this.user = user;		
	}
	
	@Override
	public void run() {
		try {
			if (this.kal >= 3000) { // Kohlehydrat reich
				fatKal();
			}
			else if (this.kal < 3000 && this.kal >= 2500) { // normal
				midKal();
			}
			else { // Diät
				thinKal();
			}
		} 
		
		catch (XMPPException e) {
			e.printStackTrace();
		}
	}
	
	private void fatKal() throws XMPPException {
		Reader re = null;        		
		try {
			re = new FileReader(FILEFETT);
			BufferedReader br = new BufferedReader(re);
			String in;
			con = new PubSubHandler();
			while ((in = br.readLine()) != null) {
				con.connect();
				if (con.isConnected()) {
					con.login(USER, PASSWORD);
					con.setTopicID(VORSCHLAG);
					con.publishPayload(publisher(in));
				}
				else System.out.println("Connection Error");
				con.disconnect();
			}	
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				re.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void midKal() throws XMPPException {
		Reader re = null;        		
		try {
			re = new FileReader(FILEOKAY);
			BufferedReader br = new BufferedReader(re);
			String in;
			con = new PubSubHandler();
			while ((in = br.readLine()) != null) {
				con.connect();
				if (con.isConnected()) {
					con.login(USER, PASSWORD);
					con.setTopicID(VORSCHLAG);
					con.publishPayload(publisher(in));
				}
				else System.out.println("Connection Error");
				con.disconnect();
			}	
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				re.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void thinKal() throws XMPPException {
		Reader re = null;        		
		try {
			re = new FileReader(FILELESS);
			BufferedReader br = new BufferedReader(re);
			String in;
			con = new PubSubHandler();
			while ((in = br.readLine()) != null) {
				con.connect();
				if (con.isConnected()) {
					con.login(USER, PASSWORD);
					con.setTopicID(VORSCHLAG);
					con.publishPayload(publisher(in));
				}
				else System.out.println("Connection Error");
				con.disconnect();
			}	
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				re.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Double getKal() {
		return kal;
	}
	
	public String getid() {
		return itemId;
	}
	
	public String getUser() {
		return user;
	}

	private PayloadItem<SimplePayload> publisher(String in) {
		SimplePayload payload = new SimplePayload("result","http://www.example.org/result", in);
		PayloadItem<SimplePayload> item = new PayloadItem<SimplePayload>(null, payload);
		System.out.println("published");
		return item;
	}

	
}
