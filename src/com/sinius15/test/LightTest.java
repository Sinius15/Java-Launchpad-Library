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
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pad.close();
		
	}

	
	@Override
	public void onButtonDown(int row, int colomn) {
		pad.showPattern(new LaunchpadPattern(EFFECT_STAR), row-1, colomn-1);
	}

	@Override
	public void onButtonUp(int row, int colomn) {
		pad.showPattern(new LaunchpadPattern(EFFECT_STAR).setColor(Launchpad.COLOUR_OFF), row-1, colomn-1);
		
	}
	
	public static void main(String[] args) {
		new LightTest("Launchpad S");
	}


}
