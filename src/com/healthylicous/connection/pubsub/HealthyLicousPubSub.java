package com.healthylicous.connection.pubsub;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.keepalive.KeepAliveManager;
import org.jivesoftware.smackx.ping.PingManager;

import com.healthylicous.util.DataHandler;
import com.healthylicous.util.Compute;

public class HealthyLicousPubSub extends Thread {
	private static final String VORSCHLAG = "Vorschlag";
	private static final String KALORIES = "Kalories";
	private static final String PROFILE = "Profile";
	private static final String USER = "admin";
	private static final String PASSWORD = "openfire";
	
	static PubSubHandler con;
	
	/**
	 * 1. Apply Connection (Test if only one Account for PubSub gets permission)
	 * 2. (Once) Subscribe to Kalories and Profile
	 *  2.1. Get ItemId of Item when published
	 * 3. (Only if Kalories or Profile is published) Compute event-driven
	 * 	3.1. Set ItemId of previous publisher to Vorschlag Item
	 *  3.2. Sent Value to util class
	 * 4. And Publish Result 
	 * @param args
	 */
	public void HealthylicousPubSub () {
		start();
	}
	
	@Override
	public void run() {
		try {
			connection();
			setVorschlag();
			subToNodes();
		} 
		catch (XMPPException e) {
			e.printStackTrace();
		}
		
	}
	
	private final static void connection() throws XMPPException {
		con = new PubSubHandler();
		con.connect();
		if (con.isConnected()) {
			System.out.println("Connection Success!!");
			con.login(USER, PASSWORD);
		}
		else System.out.println("Connection Error");

	}
		
	private final static void setVorschlag() throws XMPPException {
			// Controll if Node already exists, if not, create then
			if (!con.doesNodeExist(VORSCHLAG)) {
				System.out.println(VORSCHLAG+" does not exist. \nCreated now.");
				con.createTopic(VORSCHLAG);
			}
			else {
				con.deleteTopic(VORSCHLAG);
				con.createTopic(VORSCHLAG);
			}		
	}
		
		
	private final static void subToNodes() throws XMPPException {	
		// Server want to get some Kalories and Profile
		if (con.doesNodeExist(KALORIES) && con.doesNodeExist(PROFILE)) {
			con.subscribe(KALORIES, con.getUser());
			con.subscribe(PROFILE, con.getUser());
		}
		else {
			System.out.println("Can't listen to: "+KALORIES+" or "+PROFILE);
		}			
	}
	
}
