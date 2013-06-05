package com.healthylicous.connection.pubsub;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.pubsub.ItemPublishEvent;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;

import com.healthylicous.util.DataHandler;
import com.healthylicous.util.Compute;
import com.healthylicous.util.ResultCreator;


public class ItemEventCoordinator<T> implements ItemEventListener {
	private final static String FILE = "./././././Ressources/tempProfile.xml";
	
	@Override
	public void handlePublishedItems(ItemPublishEvent items) {
		double age,weight,height,newkal;
		int kal;
		String gender, user;
		Compute comp;
		System.out.println(items.getPublishedDate());
        
        if(items.getNodeId().contains("Profile")) {
        	if (items.getItems().isEmpty()) {
        		System.out.println("Keine Items");
        	}
        	else {
		        age = Double.parseDouble(new DataHandler().getProfileAlter(items));
		        gender = new DataHandler().getProfileGeschlecht(items);
		        weight = Double.parseDouble(new com.healthylicous.util.DataHandler().getProfileGewicht(items));
		        height = Double.parseDouble(new DataHandler().getProfileGroesse(items));
		        user = new DataHandler().getUser(items);
		        System.out.println("age: "+age+"; gender: "+gender+"; weight: "+weight+"; height: "+height);
		        System.out.println("User: "+user);
		        Writer wr = null;
		        try {
					wr = new FileWriter(FILE);
					wr.write(items.getItems().toString());
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						wr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		        comp = new Compute(age, weight, height, gender);
		        newkal = comp.defaultNeeds();
		        ResultCreator res = new ResultCreator(getItemId(items.getItems().toString()), user, newkal);
		        System.out.println(res.getid());
		        res.start();
		        System.out.println("base: "+Double.toString(comp.base(age, weight, height, gender)));
		        System.out.println("Needed def Kalories: "+Double.toString(comp.defaultNeeds()));
				
        	}
        }        
        else if(items.getNodeId().contains("Kalories")) {
        	if (items.getItems().isEmpty()) {
        		System.out.println("Keine Items");
        	}
        	else {
        		kal = Integer.decode(new DataHandler().getKalories(items));
        		Reader re = null;        		
        		try {
					re = new FileReader(FILE);
					BufferedReader br = new BufferedReader(re);
					String in;
					while ((in = br.readLine()) != null) {
						age = Double.parseDouble(getProfileAlter(in));
				        gender = getProfileGeschlecht(in);
				        weight = Double.parseDouble(getProfileGewicht(in));
				        height = Double.parseDouble(getProfileGroesse(in));
				        user = getUser(in);
				        System.out.println("tempProf User: "+user);
				        System.out.println("age: "+age+"; gender: "+gender+"; weight: "+weight+"; height: "+height);
				        comp = new Compute(age, weight, height, gender);
				        newkal = comp.newNeeds(kal);
		        		System.out.println("Needed Kalories: "+newkal);
				        ResultCreator res = new ResultCreator(getItemId(in), user, newkal);
				        res.start();
		        		System.out.println("Items User: "+new DataHandler().getUser(items));
					}
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						re.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
        		
        	}
        }
        else System.out.println("No Item for Node Kalories or Profile were sent");
        
	}
	

	private String getUser(String string) {
		Pattern regex = Pattern.compile("user='[a-z]*@[a-z0-9-]*/Smack");
        Matcher ma = regex.matcher(string);
        String user = null;
        
        if (ma.find()) {
            String[] result = ma.group().split("user='");
            for (String r : result) {
            	user = r;
            }

        }
        else
        	System.out.println("Ungültig");
		
		return user;
	}

	private String getProfileGroesse(String string) {
		Pattern regex = Pattern.compile("[\\d]{1,3}</groesse>");
        Matcher ma = regex.matcher(string);
        String groesse = null;
        
        if (ma.find()) {
            String[] result = ma.group().split("</groesse>");
            for (String r : result) {
            	groesse = r;
            }
        }
        else
        	System.out.println("Ungültig");
		
        return groesse;
	}

	private String getProfileGewicht(String string) {
		Pattern regex = Pattern.compile("[\\d]{1,3}</gewicht>");
        Matcher ma = regex.matcher(string);
        String gewicht = null;
        
        if (ma.find()) {
            String[] result = ma.group().split("</gewicht>");
            for (String r : result) {
            	gewicht = r;
            }
        }
        else
        	System.out.println("Ungültig");
		
        return gewicht;
	}

	private String getProfileGeschlecht(String string) {
		Pattern regex = Pattern.compile("female|male</geschlecht>");
        Matcher ma = regex.matcher(string);
        String geschlecht = null;
        
        if (ma.find()) {
            String[] result = ma.group().split("</geschlecht>");
            for (String r : result) {
            	geschlecht = r;
            }
        }
        else
        	System.out.println("Ungültig");
		
        return geschlecht;
	}

	private String getProfileAlter(String string) {
		Pattern regex = Pattern.compile("[\\d]{1,2}</alter>");
        Matcher ma = regex.matcher(string);
        String alter = null;
        
        if (ma.find()) {
            String[] result = ma.group().split("</alter>");
            for (String r : result) {
            	alter = r;
            }
        }
        else
        	System.out.println("Ungültig");
		
        return alter;
	}
	
	
	public String getItemId(String items) {
		Pattern regExp = Pattern.compile("<item id='[a-z0-9-]*");
		String give = items;
		String result = null;
		Matcher ma = regExp.matcher(give);
	    while (ma.find()) {
	    	result = ma.group();
	        String[] ja = ma.group().split("<item id='");
	        for (String r : ja) {
	         	result = r;
	        }
	    }
		return result;	
	}
}

