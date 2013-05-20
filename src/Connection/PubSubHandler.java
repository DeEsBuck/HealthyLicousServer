package Connection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smackx.packet.DiscoverItems;
import org.jivesoftware.smackx.pubsub.AccessModel;
import org.jivesoftware.smackx.pubsub.Affiliation;
import org.jivesoftware.smackx.pubsub.ConfigureForm;
import org.jivesoftware.smackx.pubsub.FormType;
import org.jivesoftware.smackx.pubsub.Item;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.Node;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.PublishModel;
import org.jivesoftware.smackx.pubsub.SimplePayload;
import org.jivesoftware.smackx.pubsub.Subscription;
import org.jivesoftware.smackx.pubsub.provider.ItemProvider;

public class PubSubHandler extends XMPPConnection{
	private static final String HOST = "doro-f5sr";
	private static final int PORT = 5222;
	
	static String host;
	static ConnectionConfiguration config = new ConnectionConfiguration(HOST,PORT);
	
	/**
	 * 
	 * @throws XMPPException
	 */
	public PubSubHandler() throws XMPPException {
		super(config);
	}

	/**
	 * 
	 * @param topicID
	 * @throws XMPPException
	 */
	public Node createTopic(String topicID) throws XMPPException {
		ConfigureForm form = new ConfigureForm(FormType.submit);
		form.setPersistentItems(true);
		form.setDeliverPayloads(true);
		form.setAccessModel(AccessModel.open);
		form.setPublishModel(PublishModel.open);
		form.setNotifyDelete(true);
		form.setSubscribe(true);
		
		Node n = createPubSubManager().createNode(topicID, form);
		return n;
	}
	
	/**
	 * 
	 * @return
	 */
	private PubSubManager createPubSubManager() {
		PubSubManager mgr = new PubSubManager(PubSubHandler.this, "pubsub." + HOST);
		return mgr;
	}
	
	/**
	 * 
	 * @param topicID
	 * @return
	 * @throws XMPPException
	 */
	private Node getNode(String topicID) throws XMPPException{
		PubSubManager mgr = new PubSubManager(PubSubHandler.this, "pubsub." + HOST);
		Node n = mgr.getNode(topicID);
		return n;
	}

	/**
	 * 
	 * @param topicID
	 * @param input
	 * @throws XMPPException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void publishPayload(String topicID, PayloadItem payload) throws XMPPException{
		((LeafNode)getNode(topicID)).publish(payload);
	}
	
	/**
	 * 
	 * @param jid
	 * @param topicID
	 * @throws XMPPException
	 */
	public void subscribe(String jid, String topicID) throws XMPPException{
		getNode(topicID).subscribe(jid);
	}
	
	/**
	 * 
	 * @param jid
	 * @param topicID
	 * @throws XMPPException
	 */
	public void unSubscribe(String jid, String topicID) throws XMPPException{
		getNode(topicID).unsubscribe(jid);
	}
	
	/**
	 * 
	 * @param topicID
	 * @throws XMPPException
	 */
	public void listener(String topicID) throws XMPPException {		
		getNode(topicID).addItemEventListener(new ItemEventCoordinator<Item>());
	}
	
	public void deListener(String topicID) throws XMPPException {		
		((LeafNode)getNode(topicID)).addItemDeleteListener(new ItemDeleteCoordinator<Item>());
	}
	
	/**
	 * 
	 * @param topicID
	 * @throws XMPPException
	 */
	public void getTopicID(String topicID) throws XMPPException{
		System.out.println(getNode(topicID).getId());
	}
	
	/**
	 * 
	 * @param topicID
	 * @throws XMPPException
	 */
	public void discoverNodes(String topicID) throws XMPPException {
		System.out.println(createPubSubManager().discoverNodes(topicID).toXML());
	}
	
	/**
	 * Only the Owner of the Node has permission for deletion
	 * @param topicID
	 * @throws XMPPException
	 */
	public void deleteTopic(String topicID) throws XMPPException {
		createPubSubManager().deleteNode(topicID);
	}
	
	/**
	 * 
	 * @param topicID
	 * @throws XMPPException
	 */
	public void discoItems(String topicID) throws XMPPException {
		DiscoverItems disco = ((LeafNode)getNode(topicID)).discoverItems();
		System.out.println("DiscoResult: " + disco.toXML());
		//RegExp??
	}
	
	/**
	 * Get List of Items inclusive current per Subscription 
	 * @param topicID
	 * @throws XMPPException
	 */
	public void getCurrentItems(String topicID) throws XMPPException {
		Iterable<Subscription> id = (Iterable<Subscription>) getNode(topicID).getSubscriptions();
		for (Subscription i : id) {
			System.out.println("SubscriptionID: " + i.getId());
			System.out.println("ItemResult: " + ((LeafNode)getNode(topicID)).getItems(i.getId()));
		}
	}
	
//	Nur direkt nach Topic Erstellung anwendbar, ansonsten tritt folgende Fehlerausgabe auf:	
//	kann nur ersten index zeigen
//	org.jivesoftware.smack.packet.DefaultPacketExtension cannot be cast to org.jivesoftware.smackx.pubsub.Item
	public void getItems(String topicID) throws XMPPException {
		 Iterable<Item> disco = ((LeafNode)getNode(topicID)).getItems();
		 for (Item i : disco) {
	        System.out.println("ItemResult: " + i.toString());
		 }
	}
	
	/**
	 * Get List of persisted Items per Subscription
	 * @param topicID
	 * @throws XMPPException
	 */
	public void getPersistedItems(String topicID) throws XMPPException {
		Iterable<Subscription> id = (Iterable<Subscription>) getNode(topicID).getSubscriptions();
		for (Subscription i : id) {
			System.out.println("SubscriptionID: " + i.getId());
			System.out.println("ItemResult: " + ((LeafNode)getNode(topicID)).getItems(4, i.getId()));
		}
	}
	
	/**
	 * 
	 * @param topicID
	 * @throws XMPPException
	 */
	public void getChildElXML(String topicID) throws XMPPException {
		DiscoverItems disco = ((LeafNode)getNode(topicID)).discoverItems();
		System.out.println("ResultChildElement: " + disco.getChildElementXML());
	}
	
	/**
	 * Only the Owner of the Node has permission for deletion
	 * @param topicID
	 * @throws XMPPException
	 */
	public void delAllItems(String topicID) throws XMPPException {
		((LeafNode)getNode(topicID)).deleteAllItems();
	}
	
	/**
	 * 
	 * @param topicID
	 * @throws XMPPException
	 */
	public void getAffiliation(String topicID) throws XMPPException {
		Iterable<Affiliation> affil = createPubSubManager().getAffiliations();
		for (Affiliation a : affil) {
			System.out.println("Affiliation: " + a.getType());
		}
	}
}
