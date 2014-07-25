package com.sinius15.test;

import com.sinius15.launchpad.Launchpad;
import com.sinius15.launchpad.events.ButtonListener;
import com.sinius15.launchpad.events.PadListener;

public class ListenerTester implements ButtonListener, PadListener {
	
	public static void main(String[] args) {
		new ListenerTester();
	}
	
	public ListenerTester() {
		Launchpad s;
		try {
			s = new Launchpad("Launchpad S");
			s.addButtonListener(this);
			s.addPadListener(this);
			s.open();
			Thread.sleep(5000);
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	@Override
	public void padOpen() {
		System.out.println("Open");
	}
	
	@Override
	public void padClose() {
		System.out.println("Close");
	}
	
	@Override
	public void padDisconnected() {
		System.out.println("Disconnect");
	}
	
	@Override
	public void onButtonDown(int row, int colomn) {
		System.out.println("Down");
	}
	
	@Override
	public void onButtonUp(int row, int colomn) {
		System.out.println("Up");
	}
	
}
