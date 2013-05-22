package com.healthylicous.connection.pubsub;

import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.healthylicous.connection.data.DataHandler;

public class Subscriber {
	public static final String TOPIC = "Topic";
	private static final String USER = "sue";
	private static final String PASSWORD = "openfire";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SASLAuthentication.supportSASLMechanism("PLAIN");
		XMPPConnection.DEBUG_ENABLED = true;
		try {
			PubSubHandler con = new PubSubHandler();
			con.connect();
			con.login(USER, PASSWORD);
			if(con.isConnected()){
				System.out.println("Connection Success!!");
			}
			con.subscribe(con.getUser(), TOPIC);
			con.listener(TOPIC);
//			con.discoItems(TOPIC);
//			con.getChildElXML(TOPIC);
//			con.deListener(TOPIC);
			
			DataHandler data = new DataHandler();
//			System.out.println(data.getResultTag(con.getItem(TOPIC)));
//			System.out.println(data.getResultGewicht(con.getItem(TOPIC)));
//			System.out.println(data.getResultName(con.getItem(TOPIC)));
//			data.getResults(con.getItem(TOPIC));
			System.out.println(con.getItem(TOPIC));
//			con.getPersistedItems(TOPIC);
//			con.getCurrentItems(TOPIC);
//			con.getAffiliation(TOPIC);
			con.unSubscribe(con.getUser(), TOPIC);
			
	

		} catch (XMPPException e) {
			e.printStackTrace();
		}

	}

}
