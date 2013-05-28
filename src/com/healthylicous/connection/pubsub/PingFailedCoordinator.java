package com.healthylicous.connection.pubsub;

import org.jivesoftware.smack.ping.PingFailedListener;

public class PingFailedCoordinator implements PingFailedListener {

	public void pingFailed() {
		System.out.println("Ping Failed");
	}
}
