package com.healthylicous.connection.pubsub;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jivesoftware.smackx.pubsub.ItemPublishEvent;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;

import com.healthylicous.util.Compute;
import com.healthylicous.util.DataHandler;


public class ItemEventCoordinator<T> implements ItemEventListener {
	private final static String FILE = "./././././Ressources/tempProfile.xml";
	
	@Override
	public void handlePublishedItems(ItemPublishEvent items) {
		double age,weight,height;
		int kal;
		String gender;
		Compute comp;
		
		System.out.println(items.getPublishedDate());
        
        if(items.getNodeId().contains("Profile")) {
        	if (items.getItems().isEmpty()) {
        		System.out.println("Keine Items");
        	}
        	else {
		        age = Double.parseDouble(new DataHandler().getProfileAlter(items));
		        gender = new DataHandler().getProfileGeschlecht(items);
		        weight = Double.parseDouble(new DataHandler().getProfileGewicht(items));
		        height = Double.parseDouble(new DataHandler().getProfileGroesse(items));
		        System.out.println("User: "+new DataHandler().getUser(items));
		        System.out.println("age: "+age+"; gender: "+gender+"; weight: "+weight+"; height: "+height);
		        System.out.println("User: "+new DataHandler().getUser(items));
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
		        System.out.println("base: "+Double.toString(comp.base(age, weight, height, gender)));
		        System.out.println("Needed dev Kalories: "+Double.toString(comp.defaultNeeds()));
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
				        System.out.println("User: "+getUser(in));
				        System.out.println("age: "+age+"; gender: "+gender+"; weight: "+weight+"; height: "+height);
				        comp = new Compute(age, weight, height, gender);
				        System.out.println("Needed dev Kalories: "+Double.toString(comp.defaultNeeds()));
		        		System.out.println("Needed Kalories: "+Double.toString(comp.newNeeds(kal)));
		        		System.out.println("User: "+new DataHandler().getUser(items));
					}	
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
		Pattern regex = Pattern.compile("user=\"[a-z]*@[a-z0-9-]*/Smack");
        Matcher ma = regex.matcher(string);
        String user = null;
        
        if (ma.find()) {
            String[] result = ma.group().split("user=\"");
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
	
}

