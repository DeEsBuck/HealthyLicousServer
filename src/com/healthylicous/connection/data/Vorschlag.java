package com.healthylicous.connection.data;

public class Vorschlag {

	public enum Head {
		TAGESZEIT, NAME, GEWICHT, KALORIEN, FLUESSIGKEIT
	};
	
	public enum Naehrstoffe {
		EIWEISS, FETTE, KOHLEHYDRATE
	}
	
	public enum Mselemente {
		MAGNESIUM, CALCIUM, EISEN, JOD, FLUORID, ZINK, SELEN
	}
	
	public enum Vitamine {
		VITAMIN_A, VITAMIN_B, VITAMIN_D, VITAMIN_E, VITAMIN_B1, VITAMIN_B2, VITAMIN_B6, VITAMIN_B12, VITAMIN_C, NIACIN, FOLSAEURE
	}
	
	
	public static String head(Head h){
		String result = null;
		
		switch (h) {
	    case TAGESZEIT:
	    	result = "Morgens";
	        break;
	    case GEWICHT:
	    	result = "1000";
	    	break;
	    case NAME:
	    	result = "Drachenei";
	    	break;
	    case KALORIEN:
	    	result = "1790";
	    	break;	
	    case FLUESSIGKEIT:
	    	result = "2,0";
	    	break;
		}
		return result;
	}
	
	public static String neahrstoffe(Naehrstoffe h){
		String result = null;
		
		switch (h) {
	    case EIWEISS:
	    	result = "138";
	        break;
	    case FETTE:
	    	result = "132";
	    	break;
	    case KOHLEHYDRATE:
	    	result = "13";
	    	break;
		}
		return result;
	}
	
	public static String mselemente(Mselemente h){
		String result = null;
		switch (h) {
	    case MAGNESIUM:
	    	result = "140";
	        break;
	    case CALCIUM:
	    	result = "990";
	    	break;
	    case EISEN:
	    	result = "41";
	    	break;
	    case JOD:
			result = "100";
			break;
	    case FLUORID:
			result = "1000";
			break;	
	    case ZINK:
			result = "14";
			break;
	    case SELEN:
			result = "300";
			break;
		}
		return result;
	}
	
	public static String vitamine(Vitamine h){
		String result = null;
		switch (h) {
	    case VITAMIN_A:
	    	result = "6930";
	        break;
	    case VITAMIN_B:
	    	result = "13";
	    	break;
	    case VITAMIN_D:
	    	result = "40";
	    	break;
	    case VITAMIN_E:
			result = "3000";
			break;
	    case VITAMIN_B1:
			result = "1100";
			break;	
	    case VITAMIN_B2:
			result = "4700";
			break;
	    case VITAMIN_B6:
			result = "1200";
			break;
	    case VITAMIN_B12:
			result = "90";
			break;
	    case VITAMIN_C:
			result = "0";
			break;
	    case NIACIN:
			result = "300";
			break;
	    case FOLSAEURE:
			result = "600";
			break;
		}
		return result;
	}
}
