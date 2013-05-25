package com.healthylicous.connection.pubsub;

import java.util.Scanner;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.healthylicous.connection.data.DataHandler;

public class Publisher {
	public static final String TOPIC = "Vorschlag";
	private static final String USER = "admin";
	private static final String PASSWORD = "openfire";
	
	static String newTopic = null;
	
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
			
//			handler.discoverNodes(TOPIC);
//			handler.delAllItems(TOPIC);
			
//			handler.getItem(TOPIC);
			
			while(true){
				Scanner scan = new Scanner(System.in);
				System.out.println("Wähle (r)esult, (d)elete, (c)reate oder set (k)alorie");
				String input = scan.next();
				if (input.matches("r")) {
					handler.publishPayload(TOPIC, new DataHandler().setResult());
				}
				else if(input.matches("k")){
					System.out.println("Titel eingeben: ");
					handler.publishPayload(TOPIC, new DataHandler().setKalories(input));
				}
				else if(input.matches("d")){
					System.out.println("Node eingeben: ");
					String del = scan.next();
					handler.getAffiliation(del);
					handler.discoverNodes(del);
					handler.deleteTopic(del);
				}
				else if(input.matches("c")){
					System.out.println("Node eingeben: ");
					newTopic = scan.next();
					handler.createTopic(newTopic);
					handler.discoverNodes(newTopic);
				}
				else
					System.exit(0);
				
			}
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		

	}

}
