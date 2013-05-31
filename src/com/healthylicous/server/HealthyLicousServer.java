package com.healthylicous.server;

import java.util.Timer;
import java.util.TimerTask;

import com.healthylicous.connection.pubsub.HealthyLicousPubSub;

public class HealthyLicousServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final HealthyLicousPubSub thread = new HealthyLicousPubSub();
		Timer timer = new Timer(thread.getName());
		timer.scheduleAtFixedRate( new TimerTask() {
			@Override
			public void run() {
				if (thread.getState() == Thread.State.NEW) {
					thread.start();
				}
			}
			
		}, 100, 60000);
	}
	
}
