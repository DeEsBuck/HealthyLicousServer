package Connection;

import java.util.Scanner;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class Publisher {
	public static final String TOPIC = "Topic";
	private static final String USER = "admin";
	private static final String PASSWORD = "openfire";
	
	/**
	 * @param args
	 */
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
			handler.discoverNodes(TOPIC);
			handler.getAffiliation(TOPIC);
//			handler.delAllItems(TOPIC);
			handler.publishPayload(TOPIC, new DataHandler().setResult());
			
			
			while(true){
				Scanner scan = new Scanner(System.in);
				System.out.println("Titel eingeben: ");
				String input = scan.next();
				handler.publishPayload(TOPIC, new DataHandler().setKalories(input));
//				handler.deleteTopic(input);
			}
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		

	}

}
