package com.healthylicous.connection.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jivesoftware.smackx.pubsub.ItemPublishEvent;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.SimplePayload;


public class DataHandler {

	/**
	 * 
	 * @param input
	 * @return
	 */
	public PayloadItem setKalories(String input) {
		SimplePayload payload = new SimplePayload("kcal","http://www.example.org/kalories", "<kalories xmlns='http://www.example.org/kalories'><base metric='kcal' >"+input+"</base></kalories>");
		PayloadItem payloaditem = new PayloadItem(null, payload);
		return payloaditem;
	}
	
	/**
	 * 
	 * @param items
	 * @return
	 */
	public String getKalories(ItemPublishEvent items) {
		Pattern regex = Pattern.compile("[\\d]{1,4}</base>");
        Matcher ma = regex.matcher(items.getItems().toString());
        String kalories = null;
        
        if (ma.find()) {
            String[] result = ma.group().split("</base>");
            for (String r : result) {
            	kalories = r;
            }
            // Control output
            System.out.println(items.getItems().toString());
        }
        else
        	System.out.println("Ung�ltig");
		
        return kalories;
	}
	
	/**
	 * 
	 * @param alter
	 * @param gewicht
	 * @param groesse
	 * @param geschlecht
	 * @return
	 */
	public PayloadItem setProfile(String alter, String gewicht, String groesse, String geschlecht) {
		SimplePayload payload = new SimplePayload("profil","http://www.example.org/profil", "<profil xmlns='http://www.example.org/profil'><alter>"+alter+"</alter><gewicht metric='kg'>"+gewicht+"</gewicht><groesse metric='cm'>"+groesse+"</groesse><geschlecht>"+geschlecht+"</geschlecht></profil>");
		PayloadItem payloaditem = new PayloadItem(null, payload);
		return payloaditem;
	}
	
	/**
	 * 
	 * @param items
	 * @return
	 */
	public String getProfileAlter(ItemPublishEvent items) {
		Pattern regex = Pattern.compile("[\\d]{1,2}</alter>");
        Matcher ma = regex.matcher(items.getItems().toString());
        String alter = null;
        
        if (ma.find()) {
            String[] result = ma.group().split("</alter>");
            for (String r : result) {
            	alter = r;
            }
            // Control output
            System.out.println(items.getItems().toString());
        }
        else
        	System.out.println("Nööööööööööööööö, passt nischt");
		
        return alter;
	}
	
	public String getProfileGewicht(ItemPublishEvent items) {
		Pattern regex = Pattern.compile("[\\d]{1,3},[\\d]{2}</gewicht>");
        Matcher ma = regex.matcher(items.getItems().toString());
        String gewicht = null;
        
        if (ma.find()) {
            String[] result = ma.group().split("</gewicht>");
            for (String r : result) {
            	gewicht = r;
            }
            // Control output
            System.out.println(items.getItems().toString());
        }
        else
        	System.out.println("Nööööööööööööööö, passt nischt");
		
        return gewicht;
	}
	
	public String getProfileGroesse(ItemPublishEvent items) {
		Pattern regex = Pattern.compile("[\\d]{1,3},[\\d]{2}</groesse>");
        Matcher ma = regex.matcher(items.getItems().toString());
        String groesse = null;
        
        if (ma.find()) {
            String[] result = ma.group().split("</groesse>");
            for (String r : result) {
            	groesse = r;
            }
            // Control output
            System.out.println(items.getItems().toString());
        }
        else
        	System.out.println("Nööööööööööööööö, passt nischt");
		
        return groesse;
	}
	
	public String getProfileGeschlecht(ItemPublishEvent items) {
		Pattern regex = Pattern.compile("female|male</geschlecht>");
        Matcher ma = regex.matcher(items.getItems().toString());
        String geschlecht = null;
        
        if (ma.find()) {
            String[] result = ma.group().split("</geschlecht>");
            for (String r : result) {
            	geschlecht = r;
            }
            // Control output
            System.out.println(items.getItems().toString());
        }
        else
        	System.out.println("Nööööööööööööööö, passt nischt");
		
        return geschlecht;
	}
	
	public String getResult(ItemPublishEvent items) {
		return items.getItems().toString();
	}
	
	public PayloadItem setResult() {
		SimplePayload payload = new SimplePayload("result","http://www.example.org/result", "<result xmlns:healthyns='http://www.example.org/result'>"+setVorschlag("1")+"</result>");
		PayloadItem payloaditem = new PayloadItem(null, payload);
		return payloaditem;
		
	}
	
	public String setVorschlag(String index) {
		String s = "<vorschlag  id='"+index+"' tageszeit='"+Vorschlag.head(Vorschlag.Head.TAGESZEIT)+"'><name metric='g' gewicht='"+Vorschlag.head(Vorschlag.Head.GEWICHT)+"'>"+Vorschlag.head(Vorschlag.Head.NAME)+"</name><kalorien metric='kcal'>"+Vorschlag.head(Vorschlag.Head.KALORIEN)+"</kalorien><fluessigkeit metric='l'>"+Vorschlag.head(Vorschlag.Head.FLUESSIGKEIT)+"</fluessigkeit>"+naehrstoffe()+mselemente()+vitamine()+"</vorschlag>";
		return s;
	}
	
	private String vitamine() {
		String v = "<vitamine><vitamina metric='mg'>"+Vorschlag.vitamine(Vorschlag.Vitamine.VITAMIN_A)+"</vitamina><vitamind metric='microg'>"+Vorschlag.vitamine(Vorschlag.Vitamine.VITAMIN_D)+"</vitamind><vitamine metric='mg'>"+Vorschlag.vitamine(Vorschlag.Vitamine.VITAMIN_E)+"</vitamine><vitaminb1 metric='mg'>"+Vorschlag.vitamine(Vorschlag.Vitamine.VITAMIN_B1)+"</vitaminb1><vitaminb2 metric='mg'>"+Vorschlag.vitamine(Vorschlag.Vitamine.VITAMIN_B2)+"</vitaminb2><vitaminb6 metric='mg'>"+Vorschlag.vitamine(Vorschlag.Vitamine.VITAMIN_B6)+"</vitaminb6><vitaminb12 metric='microg'>"+Vorschlag.vitamine(Vorschlag.Vitamine.VITAMIN_B12)+"</vitaminb12><vitaminc metric='mg'>"+Vorschlag.vitamine(Vorschlag.Vitamine.VITAMIN_C)+"</vitaminc><niacin metric='mg'>"+Vorschlag.vitamine(Vorschlag.Vitamine.NIACIN)+"</niacin><folsaeure metric='microg'>"+Vorschlag.vitamine(Vorschlag.Vitamine.FOLSAEURE)+"</folsaeure></vitamine>";
		return v;
	}

	private String mselemente() {
		String m = "<mselemente><magnesium metric='mg'>"+Vorschlag.mselemente(Vorschlag.Mselemente.MAGNESIUM)+"</magnesium><calcium metric='mg'>"+Vorschlag.mselemente(Vorschlag.Mselemente.CALCIUM)+"</calcium><eisen metric='mg'>"+Vorschlag.mselemente(Vorschlag.Mselemente.EISEN)+"</eisen><jod metric='microg'>"+Vorschlag.mselemente(Vorschlag.Mselemente.JOD)+"</jod><fluorid metric='mg'>"+Vorschlag.mselemente(Vorschlag.Mselemente.FLOURID)+"</fluorid><zink metric='mg'>"+Vorschlag.mselemente(Vorschlag.Mselemente.ZINK)+"</zink><selen metric='microg'>"+Vorschlag.mselemente(Vorschlag.Mselemente.SELEN)+"</selen></mselemente>";
		return m;
	}

	private String naehrstoffe() {
		String n = "<naehrstoffe><eiweiss metric='g'>"+Vorschlag.neahrstoffe(Vorschlag.Naehrstoffe.EIWEISS)+"</eiweiss><fette metric='g'>"+Vorschlag.neahrstoffe(Vorschlag.Naehrstoffe.FETTE)+"</fette><kohlenhydrate metric='g'>"+Vorschlag.neahrstoffe(Vorschlag.Naehrstoffe.KOHLEHYDRATE)+"</kohlenhydrate></naehrstoffe>";
		return n;
	}

	public String getResults(ItemPublishEvent items) {
		Pattern tageszeit = Pattern.compile("tageszeit=\"morgens\">");
		Pattern gewicht = Pattern.compile("gewicht=\"150\">");
		Pattern name = Pattern.compile("Banane</name>");
		Pattern kalorien = Pattern.compile("95,0</kalorien>");
		Pattern flussigkeit = Pattern.compile("2,0</fluessigkeit>");
		
		Pattern vitamina = Pattern.compile("0,8</vitamina>");
		Pattern vitamind = Pattern.compile("0,8</vitamind>");
		Pattern vitamine = Pattern.compile("0,8</vitamine>");
		Pattern vitaminb1 = Pattern.compile("0,8</vitaminb1>");
		Pattern vitaminb2 = Pattern.compile("0,8</vitaminb2>");
		Pattern vitaminb6 = Pattern.compile("0,8</vitaminb6>");
		Pattern vitaminb12 = Pattern.compile("0,8</vitaminb12>");
		Pattern vitaminc = Pattern.compile("0,8</vitaminc>");
		Pattern niacin = Pattern.compile("0,8</niacin>");
		Pattern folsaure = Pattern.compile("400,0</folsaeure>");
		
		Pattern magnesium = Pattern.compile("300,0</magnesium>");
		Pattern eisen = Pattern.compile("300,0</eisen>");
		Pattern calcium = Pattern.compile("300,0</calcium>");
		Pattern jod = Pattern.compile("300,0</jod>");
		Pattern flourid = Pattern.compile("300,0</flourid>");
		Pattern zink = Pattern.compile("300,0</zink>");
		Pattern selen = Pattern.compile("300,0</selen>");
		
		Pattern eiweiss = Pattern.compile("57,0</eiweiss>");
		Pattern fette = Pattern.compile("57,0</fette>");
		Pattern kohlehydrate = Pattern.compile("57,0</kohlenhydrate>");
		
		
        Matcher ma = tageszeit.matcher(items.getItems().toString());
        String geschlecht = null;
        
        if (ma.find()) {
            String[] result = ma.group().split("</geschlecht>");
            for (String r : result) {
            	geschlecht = r;
            }
            // Control output
            System.out.println(items.getItems().toString());
        }
        else
        	System.out.println("Nööööööööööööööö, passt nischt");
		
        return geschlecht;
	} 
	
}
