package com.healthylicous.util;

import org.jivesoftware.smack.XMPPException;
import com.healthylicous.connection.pubsub.PubSubHandler;

public class ResultCoordinator extends Thread {
	public static final String VORSCHLAG = "Vorschlag";
	public static final String KALORIES = "Kalories";
	public static final String PROFILE = "Profile";
	private static final String USER = "healthy";
	private static final String PASSWORD = "openfire";
		
	double kal;
	DataHandler data;
	String itemId, user;
	PubSubHandler con;
	
	public ResultCoordinator(String itemId, String user, double kal) {
		this.kal = kal;
		this.itemId = itemId;
		this.user = user;		
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
	
	public void sendVorschlag() {
		try {
			con = new PubSubHandler();
			con.connect();
			if (con.isConnected()) {
				con.login(USER, PASSWORD);
				con.setTopicID("Vorschlag");
				con.publishPayload(new DataHandler().setResult(itemId, user));
				con.disconnect();
			}
			else System.out.println("Connection Error");
		} 
		catch (XMPPException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		sendVorschlag();
	}
	
}
