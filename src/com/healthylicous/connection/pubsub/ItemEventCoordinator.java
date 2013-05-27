package com.healthylicous.connection.pubsub;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.pubsub.ItemPublishEvent;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;

import com.healthylicous.connection.data.DataHandler;


public class ItemEventCoordinator<T> implements ItemEventListener {

	@Override
	public void handlePublishedItems(ItemPublishEvent items) {
//		System.out.println("Item count: " + items.getItems().size());
//        System.out.println(items);
//        System.out.println(items.isDelayed());
        System.out.println(items.getItems().toString());
        System.out.println(items.getPublishedDate());
        
        if (items.getNodeId().contains("Vorschlag")) {
        	if (items.getItems().isEmpty()) {
        		System.out.println("Keine Items");
        	}
        	else {
		        System.out.println(new DataHandler().getResultTag(items.getItems().toString()));
		        System.out.println(new DataHandler().getResultGewicht(items.getItems().toString()));
		        System.out.println(new DataHandler().getResultName(items.getItems().toString()));
		        String[] wert = new DataHandler().getResults(items.getItems().toString());
				for (String s : wert) {
					System.out.println(s);
				}
				
//				String vitamin = wert[4];
//				System.out.println("Ein Vitamin: "+vitamin);
        	}
        }
        else if(items.getNodeId().contains("Profile")) {
        	if (items.getItems().isEmpty()) {
        		System.out.println("Keine Items");
        	}
        	else {
		        System.out.println(new DataHandler().getProfileAlter(items));
		        System.out.println(new DataHandler().getProfileGeschlecht(items));
		        System.out.println(new DataHandler().getProfileGewicht(items));
		        System.out.println(new DataHandler().getProfileGroesse(items));
        	}
        }
        else if(items.getNodeId().contains("Kalories")) {
        	if (items.getItems().isEmpty()) {
        		System.out.println("Keine Items");
        	}
        	else {
        		System.out.println(new DataHandler().getKalories(items));
        	}
        }
        else System.exit(1);
        
//        System.out.println(items.getNodeId());
//        System.out.println("Subscriptions: " + items.getSubscriptions());
        
	}

}

