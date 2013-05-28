package com.healthylicous.connection.pubsub;
import org.jivesoftware.smackx.pubsub.ItemPublishEvent;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;

import com.healthylicous.connection.data.DataHandler;


public class ItemEventCoordinator<T> implements ItemEventListener {

	@Override
	public void handlePublishedItems(ItemPublishEvent items) {
        System.out.println(items.getPublishedDate());
        
        if(items.getNodeId().contains("Profile")) {
        	if (items.getItems().isEmpty()) {
        		System.out.println("Keine Items");
        	}
        	else {
		        System.out.println(new DataHandler().getProfileAlter(items));
		        System.out.println(new DataHandler().getProfileGeschlecht(items));
		        System.out.println(new DataHandler().getProfileGewicht(items));
		        System.out.println(new DataHandler().getProfileGroesse(items));
		        // TODO Daten in Methode für anfragen und berechnungen übergeben
        	}
        }
        else if(items.getNodeId().contains("Kalories")) {
        	if (items.getItems().isEmpty()) {
        		System.out.println("Keine Items");
        		// TODO Daten in Methode für anfragen und berechnungen übergeben
        	}
        	else {
        		System.out.println(new DataHandler().getKalories(items));
        	}
        }
        else System.out.println("Something went wrong, couldn't understand Node");
        
	}

}

