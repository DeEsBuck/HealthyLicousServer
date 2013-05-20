package Connection;
import java.lang.reflect.Array;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smackx.pubsub.ItemPublishEvent;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;


public class ItemEventCoordinator<T> implements ItemEventListener {

	@Override
	public void handlePublishedItems(ItemPublishEvent items) {
//		System.out.println("Item count: " + items.getItems().size());
//        System.out.println(items);
//        System.out.println(items.isDelayed());
//        System.out.println(items.getItems().toString());
        System.out.println(items.getPublishedDate());
        
        System.out.println(new DataHandler().getResult(items));
        
        
        
//        System.out.println(items.getNodeId());
        System.out.println("Subscriptions: " + items.getSubscriptions());
        
	}

}

