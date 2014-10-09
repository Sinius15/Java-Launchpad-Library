package com.sinius15.test;

import com.sinius15.launchpad.Launchpad;
import com.sinius15.launchpad.OwnedLaunchpad;

public class BufferedTest {

	public static void main(String[] args) throws Exception {
		OwnedLaunchpad pad = new OwnedLaunchpad("Launchpad S");
		pad.open();
		pad.setLedOn(0, 0, Launchpad.COLOR_RED_FULL, "player2");
		pad.setLedOn(0, 0, Launchpad.COLOR_AMBER_FULL, "player1");
		
		
		pad.setLedOff(0, 0, "player1");

		
		pad.close();
	}
	
}
