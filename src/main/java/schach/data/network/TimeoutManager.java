package schach.data.network;


import javax.swing.JOptionPane;

import schach.data.Variables;

public class TimeoutManager {
	
	private long last = 0;
	private final UDPClient udp;
	private static boolean stillActive = true;
	
	public TimeoutManager(UDPClient udp) {
		super();
		this.udp = udp;
	}
	
	public void startThreads() {
		last = System.currentTimeMillis();
		(new AntiTimeoutThread()).start();
		(new TimeoutThread()).start();
	}
	
	protected void packetRecieved() {
		last = System.currentTimeMillis();
	}
	
	
	private class AntiTimeoutThread extends Thread {
		
		@Override
		public void run(){
			while(true) {
				try {
					if(stillActive) {
						udp.sendNullPacket();
						sleep(Variables.ANTI_TIMEOUT_INTERVAL);
					} else {
						break;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private class TimeoutThread extends Thread {
		
		@Override
		public void run(){
			while(true) {
				long now = System.currentTimeMillis();
				if(now - last > Variables.TIMEOUT) {
					if(stillActive) {
						timeout();
					} else {
						break;
					}
				}
				try {
					sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	private void timeout() {
		udp.view.setEnabled(false);
		udp.listener.setMode(ListeningMode.OFF);
		shutdownAll();
		JOptionPane.showMessageDialog(null, "Error: Connection Timeout");
		System.exit(0);
	}
	
	public static void shutdownAll() {
		stillActive = false;
	}

}
