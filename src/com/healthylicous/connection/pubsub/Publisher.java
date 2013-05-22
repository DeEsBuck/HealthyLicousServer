package com.healthylicous.connection.pubsub;

import java.util.Scanner;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.healthylicous.connection.data.DataHandler;

public class Publisher {
	public static final String TOPIC = "Topic";
	private static final String USER = "admin";
	private static final String PASSWORD = "openfire";
	
	/**
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SASLAuthentication.supportSASLMechanism("PLAIN");
		XMPPConnection.DEBUG_ENABLED = true;
		try {
			PubSubHandler handler = new PubSubHandler();
			handler.connect();
			handler.login(USER, PASSWORD);
			if(handler.isConnected()){
				System.out.println("Connection Success!!");
			}
			
//			handler.createTopic(TOPIC);
//			handler.discoverNodes(TOPIC);
//			handler.getAffiliation(TOPIC);
//			handler.delAllItems(TOPIC);
			
//			handler.getItem(TOPIC);
			
			while(true){
				Scanner scan = new Scanner(System.in);
				System.out.println("Wähle r oder k");
				String input = scan.next();
				if (input.matches("r")) {
					handler.publishPayload(TOPIC, new DataHandler().setResult());
				break;
				}
				else if(input.matches("k")){
					System.out.println("Titel eingeben: ");
					handler.publishPayload(TOPIC, new DataHandler().setKalories(input));
				}
				else
					System.exit(0);
//				handler.deleteTopic(input);
			}
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		

	}

}
