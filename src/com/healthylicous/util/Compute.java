package com.healthylicous.util;

import com.healthylicous.connection.data.Vorschlag;


public class Compute {
	String[] results;
	String name, gender, foodweight, day;
	double kal, age, weight, height, aqua, foodkal; 
	double bmr;
	double met;
	
	DataHandler data = new DataHandler();
	
	/**
	  * Profile is recommended
	  * first use base(bmr), then activity(met), is needed to compute kalories needs (kal = bmr*met) 
	  * you can get the result with the [*]Needs() Methods
	  * @param age
	  * @param weight
	  * @param height
	  * @param gender
	  */
	public Compute(){}
	
	 /**
	  * Profile is recommended
	  * first use base(bmr), then activity(met), is needed to compute kalories needs (kal = bmr*met) 
	  * you can get the result with the [*]Needs() Methods
	  * @param age
	  * @param weight
	  * @param height
	  * @param gender
	  */
	public Compute(double age, double weight, double height, String gender){
		this.age = age;
		this.weight = weight;
		this.height = height;
		this.gender = gender;
		base();
		defaultActivity();
	}
	
	/**
	 * Compute the base Kalories of the person without activities
	 */
	public double base() {
		double basiswert = (gender == "female") ? 448 : 88;
		double m_weight = (gender == "female") ? 9.247 : 13.397;
		double m_height = (gender == "female") ? 3.098 : 4.799;
		double m_age = (gender == "female") ? 4.33 : 5.677;
		this.bmr = (basiswert+(m_weight*weight)+(m_height*height)-(m_age*age));
		return bmr;
	}
	
	/**
	 * Compute the base Kalories of the person without activities
	 * Use this Method when using the default Compute Constructor, for later inserts
	 * @param age
	 * @param weight
	 * @param height
	 * @param gender
	 */
	public double base(double age, double weight, double height, String gender) {
		double basiswert = (gender == "female") ? 448 : 88;
		double m_weight = (gender == "female") ? 9.247 : 13.397;
		double m_height = (gender == "female") ? 3.098 : 4.799;
		double m_age = (gender == "female") ? 4.33 : 5.677;
		this.bmr = (basiswert+(m_weight*weight)+(m_height*height)-(m_age*age));
		return bmr;
	}
	
	/**
	 * Computes the users activity by inserts
	 * @param sleep
	 * @param sitting
	 * @param easy
	 * @param middle
	 * @param hard
	 */
	public void activity(int sleep, int sitting, int easy, int middle, int hard) {
		double stunden = sleep + sitting + easy + middle + hard;
		if (stunden == 24) {
			this.met = ((((+sleep*0.9)+(+sitting*1.2)+(+easy*2.2)+(+middle*3.3)+(+hard*6.6))/stunden)*1000) / 1000;
		} else if (stunden < 24) {
			int rest = (int) (24 - stunden);
			stunden = stunden + rest;
			this.met = ((((+sleep*0.9)+(+sitting*1.2)+(+easy*2.2)+(+middle*3.3)+(+hard*6.6))/stunden)*1000) / 1000;
		} else if (stunden > 24) {
			int rest = (int) (stunden - 24);
			stunden = stunden + rest;
			this.met = ((((+sleep*0.9)+(+sitting*1.2)+(+easy*2.2)+(+middle*3.3)+(+hard*6.6))/stunden)*1000) / 1000;
		}
	}
	
	/**
	 * Gives only the a standard activity of normal day activity of a user 
	 */
	public void defaultActivity() {
		int sleep = 8; int sitting = 7; int easy = 6; int middle = 2; int hard = 1; // in hours, should be the sum of 24 hours
		int stunden = sleep + sitting + easy + middle + hard;
		this.met = ((((Math.abs(sleep)*0.9)+(Math.abs(sitting)*1.2)+(Math.abs(easy)*2.2)+(Math.abs(middle)*3.3)+(Math.abs(hard)*6.6))/stunden)*1000) / 1000;
	}
	
	/**
	 * 
	 * @param kal
	 */
	public void getKalories(int kal) {
		this.kal = kal;
	}
	
	/**
	 * The result of needed Kalories by default activities
	 * @return
	 */
	public double defaultNeeds() {
		double bedarf = Math.round(bmr*met);
		return bedarf;
	}
	
	/**
	 * Kalorie needs after Workout
	 * @return
	 */
	public double newNeeds() {
		double bedarf = Math.round((bmr*met)+kal);
		return bedarf;
	}
	
	/**
	 * Kalorie needs after Workout
	 * Kalorie needs with direct kalories input
	 * @param kal
	 * @return
	 */
	public double newNeeds(int kal) {
		double bedarf = Math.round((bmr*met)+kal);
		return bedarf;
	}
	
	
}
