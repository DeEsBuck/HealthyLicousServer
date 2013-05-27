package com.healthylicous.connection.pubsub;

import java.util.Scanner;

import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.healthylicous.connection.data.DataHandler;

public class Subscriber {
	public static final String TOPIC = "Vorschlag";
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
			DataHandler data = new DataHandler();

			while(true){
				Scanner scan = new Scanner(System.in);
				System.out.println("Wähle (s)ubscribe, (w)hois, get (v)orschlag, (l)isten to (Kalories, Vorschlag or Profile) oder (u)nsubscribe");
				String input = scan.next();
				if (input.matches("s")) {
					System.out.println("Node wählen:");
					String sub = scan.next();
					con.subscribe(con.getUser(), sub);
					System.out.println(con.getItem(sub));
				}
				else if (input.matches("w")) {
					System.out.println("Node wählen:");
					String sub = scan.next();
					con.getChildElXML(sub);
					System.out.println("getItem: "+con.getItem(sub));
					con.getCurrentItems(sub);
					System.out.println(con.getThisSubscriber(sub));
				}
				else if (input.matches("v")) {
					if (con.getItem(TOPIC).isEmpty()) {
						System.out.println("Nix da");
					} 
					else {
						String item = con.getItem(TOPIC);
						if (item.contains(con.getLastItemId(TOPIC))){
							System.out.println(con.getItemId(TOPIC));
							System.out.println(data.getResultID(item));
							System.out.println(data.getResultTag(item));
							System.out.println(data.getResultGewicht(item));
							System.out.println(data.getResultName(item));
							String[] wert = data.getResults(item);
		//					for (String s : wert) {
		//						System.out.println(s);
		//					}
							String vitamin = wert[4];
							System.out.println("Ein Vitamin: "+vitamin);
						}
						System.out.println(con.getLastItemId(TOPIC));
					}
				}
				else if (input.matches("l")) {
					System.out.println("Node wählen:");
					String sub = scan.next();
					if (con.getSubscriptions(sub) == null) {
						System.out.println("No Subs");
					}
					else
					con.listener(sub);
				}
				else if (input.matches("u")) {
					System.out.println("Node wählen:");
					String sub = scan.next();
					con.unSubscribe(con.getUser(), sub);
				}
				else System.exit(1);
			}

		} catch (XMPPException e) {
			e.printStackTrace();
		}

	}

}
