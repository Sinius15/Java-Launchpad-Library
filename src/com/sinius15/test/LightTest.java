package com.sinius15.test;

import java.io.IOException;

import javax.sound.midi.MidiUnavailableException;

import com.sinius15.launchpad.LaunchListener;
import com.sinius15.launchpad.Launchpad;
import com.sinius15.launchpad.LaunchpadException;
import com.sinius15.launchpad.LaunchpadPattern;

public class LightTest implements LaunchListener{
	
	Launchpad pad;
	
	public static final int[][] EFFECT_STAR = { 
		{ -1, 15, -1, -1, -1, -1, -1, -1, -1 },
		{ 15, 15, 15, -1, -1, -1, -1, -1, -1 }, 
		{ -1, 15, -1, -1, -1, -1, -1, -1, -1 },
		{ -1, -1, -1, -1, -1, -1, -1, -1, -1 }, 
		{ -1, -1, -1, -1, -1, -1, -1, -1, -1 },
		{ -1, -1, -1, -1, -1, -1, -1, -1, -1 }, 
		{ -1, -1, -1, -1, -1, -1, -1, -1, -1 },
		{ -1, -1, -1, -1, -1, -1, -1, -1, -1 }, 
		{ -1, -1, -1, -1, -1, -1, -1, -1, -1 } };
	
	public LightTest(String device) {
		try {
			pad = new Launchpad(device);
			pad.open();
		} catch (MidiUnavailableException | LaunchpadException e) {
			e.printStackTrace();
		}
		pad.addListener(this);
		pad.setFullLaunchpadColor(Launchpad.COLOUR_AMBER_FULL);
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pad.close();
		
	}

	
	@Override
	public void onButtonDown(int row, final int colomn) {
		if(row == 8){
			new Thread(new Runnable() {
				int curRow = 8;
				int col = colomn;
				@Override
				public void run() {
					do{
						pad.setLedOn(col, curRow, Launchpad.COLOUR_GREEN_FULL);
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						curRow--;
						pad.setLedOff(col, curRow+1);
					}while(curRow >= 0);
					
				}
			}).start();
		}else if(row == 0){
			if(colomn == 0)
				pad.setFullLaunchpadColor(Launchpad.COLOUR_GREEN_FULL);
			if(colomn == 1)
				pad.showColorPallette();
			
		}else{
			pad.showPattern(new LaunchpadPattern(EFFECT_STAR), row-1, colomn-1);
		}
		
	}

	@Override
	public void onButtonUp(int row, int colomn) {
		if(row != 0 && row != 8)
			pad.showPattern(new LaunchpadPattern(EFFECT_STAR).setColor(Launchpad.COLOUR_OFF), row-1, colomn-1);
		
	}
	
	public static void main(String[] args) {
		new LightTest("Launchpad S");
	}


}
