package com.healthylicous.connection.pubsub;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.keepalive.KeepAliveManager;
import org.jivesoftware.smackx.ping.PingManager;

import com.healthylicous.connection.data.DataHandler;

public class HealthyLicousPubSub extends Thread {
	public static final String VORSCHLAG = "Vorschlag";
	public static final String KALORIES = "Kalories";
	public static final String PROFILE = "Profile";
	private static final String USER = "admin";
	private static final String PASSWORD = "openfire";
	
	DataHandler data = new DataHandler();
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
	
	public static void connection() throws XMPPException {
		con = new PubSubHandler();
		con.connect();
		if (con.isConnected()) {
			keepConnection();
			System.out.println("Connection Success!!");
			con.login(USER, PASSWORD);
		}
		else System.out.println("Connection Error");

	}
		
	public static void setVorschlag() throws XMPPException {
		// Controll if Node already exists, if not, create then
		if (con.doesNodeExist(VORSCHLAG)) {
			con.discoverNodes(VORSCHLAG);
			// TODO Resultate publishen und ItemID zuweisen
//			con.publishPayload(new DataHandler().setResult(con.getItemId()));
		}
		else {
			System.out.println(VORSCHLAG+" does not exist. \nCreated now.");
			con.createTopic(VORSCHLAG);
		}
	}
		
		
	public static void subToNodes() throws XMPPException {	
		// Server want to get some Kalories and Profile
		if (con.doesNodeExist(KALORIES) && con.doesNodeExist(PROFILE)) {
			con.subscribe(KALORIES, con.getUser());
			con.subscribe(PROFILE, con.getUser());
				
//			con.unSubscribe(KALORIES, con.getUser());				
//			con.unSubscribe(KALORIES, con.getUser());
		}
		else {
			System.out.println("Can't listen to: "+KALORIES+" or "+PROFILE);
		}			
	}
	
	
	public static void keepConnection() {
		PingManager.getInstanceFor(con).pingMyServer();
		KeepAliveManager.getInstanceFor(con);
		KeepAliveManager.getInstanceFor(con).setPingInterval(10000);
		System.out.println("lastping:" + KeepAliveManager.getInstanceFor(con).getTimeSinceLastContact());
		System.out.println("PingInterval: "+KeepAliveManager.getInstanceFor(con).getPingInterval());
		KeepAliveManager.getInstanceFor(con).addPingFailedListener(new PingFailedCoordinator());
	}
}
