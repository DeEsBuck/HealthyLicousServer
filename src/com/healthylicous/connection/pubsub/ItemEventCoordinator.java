package com.healthylicous.connection.pubsub;
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
        
        
        System.out.println(new DataHandler().getResultTag(items.getItems().toString()));
        new DataHandler().getResults(items.getItems().toString());
        
        System.out.println(new DataHandler().getKalories(items));
        
        
//        System.out.println(items.getNodeId());
        System.out.println("Subscriptions: " + items.getSubscriptions());
        
	}

}

