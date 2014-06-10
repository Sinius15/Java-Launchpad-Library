package com.sinius15.test;

import java.io.IOException;

import javax.sound.midi.MidiUnavailableException;

import com.sinius15.launchpad.LPRack;
import com.sinius15.launchpad.LaunchListener;
import com.sinius15.launchpad.Launchpad;
import com.sinius15.launchpad.LaunchpadException;
import com.sinius15.launchpad.LaunchpadPattern;

public class LightTest implements LaunchListener {
	
	Launchpad pad;
	
	public static final int[][] EFFECT_STAR = { { -1, 15, -1, -1, -1, -1, -1, -1, -1 },
			{ 15, 15, 15, -1, -1, -1, -1, -1, -1 }, { -1, 15, -1, -1, -1, -1, -1, -1, -1 },
			{ -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 },
			{ -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 },
			{ -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 } };
	public static final int[][] CUBE4 = { { -1, 63, 63, 63, 63, 63, 63, 63, 63 },
			{ -1, 63, -1, -1, -1, -1, -1, -1, 63 }, { -1, 63, -1, -1, -1, -1, -1, -1, 63 },
			{ -1, 63, -1, -1, -1, -1, -1, -1, 63 }, { -1, 63, -1, -1, -1, -1, -1, -1, 63 },
			{ -1, 63, -1, -1, -1, -1, -1, -1, 63 }, { -1, 63, -1, -1, -1, -1, -1, -1, 63 },
			{ -1, 63, 63, 63, 63, 63, 63, 63, 63 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 } };
	public static final int[][] CUBE3 = { { -1, -1, -1, -1, -1, -1, -1, -1, -1 },
			{ -1, -1, 63, 63, 63, 63, 63, 63, -1 }, { -1, -1, 63, -1, -1, -1, -1, 63, -1 },
			{ -1, -1, 63, -1, -1, -1, -1, 63, -1 }, { -1, -1, 63, -1, -1, -1, -1, 63, -1 },
			{ -1, -1, 63, -1, -1, -1, -1, 63, -1 }, { -1, -1, 63, 63, 63, 63, 63, 63, -1 },
			{ -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 } };
	public static final int[][] CUBE2 = { { -1, -1, -1, -1, -1, -1, -1, -1, -1 },
			{ -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, 63, 63, 63, 63, -1, -1 },
			{ -1, -1, -1, 63, -1, -1, 63, -1, -1 }, { -1, -1, -1, 63, -1, -1, 63, -1, -1 },
			{ -1, -1, -1, 63, 63, 63, 63, -1, -1 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 },
			{ -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 } };
	public static final int[][] CUBE1 = { { -1, -1, -1, -1, -1, -1, -1, -1, -1 },
			{ -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 },
			{ -1, -1, -1, -1, 63, 63, -1, -1, -1 }, { -1, -1, -1, -1, 63, 63, -1, -1, -1 },
			{ -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 },
			{ -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 } };
	
	public LightTest(String device) {
		try {
			pad = new Launchpad(device);
			pad.open();
		} catch (MidiUnavailableException | LaunchpadException e) {
			e.printStackTrace();
		}
		
		LPRack rack = new LPRack(pad);
		rack.setLaunchpadPattern(4, 3, new LaunchpadPattern(CUBE1).setColor(Launchpad.COLOUR_AMBER_FULL));
		rack.setLaunchpadPattern(3, 2, new LaunchpadPattern(CUBE2).setColor(Launchpad.COLOUR_AMBER_FULL));
		rack.setLaunchpadPattern(2, 1, new LaunchpadPattern(CUBE3).setColor(Launchpad.COLOUR_AMBER_FULL));
		rack.setLaunchpadPattern(1, 0, new LaunchpadPattern(CUBE4).setColor(Launchpad.COLOUR_AMBER_FULL));
		
		for(int col = 0; col<=8; col++){
			rack.setOnButtonDown(8, col, new ShotEffect(8, col));
		}
		rack.setOnButtonDown(0, 0, new Runnable() {
			@Override
			public void run() {
				pad.setFullLaunchpadColor(Launchpad.COLOUR_GREEN_FULL);
			}
		});
		rack.setOnButtonDown(0, 1, new Runnable() {
			@Override
			public void run() {
				pad.setFullLaunchpadColor(Launchpad.COLOUR_RED_FULL);
			}
		});
		rack.setOnButtonDown(0, 2, new Runnable() {
			@Override
			public void run() {
				pad.setFullLaunchpadColor(Launchpad.COLOUR_AMBER_FULL);
			}
		});
		rack.setOnButtonDown(0, 3, new Runnable() {
			@Override
			public void run() {
				pad.reset();
			}
		});
		
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
		if (row != 0 && row != 8 && colomn > 3)
			pad.showPattern(new LaunchpadPattern(EFFECT_STAR), row - 1, colomn - 1);
	}
	
	@Override
	public void onButtonUp(int row, int colomn) {
		if (row != 0 && row != 8 && colomn > 3)
			pad.showPattern(new LaunchpadPattern(EFFECT_STAR).setColor(Launchpad.COLOUR_OFF), row - 1, colomn - 1);
		
	}
	
	public static void main(String[] args) {
		new LightTest("Launchpad S");
	}
	
	class ShotEffect implements Runnable{

		final int row, col;
		
		public ShotEffect(int row, int col){
			this.row = row;
			this.col = col;
		}
		
		@Override
		public void run() {
			int curRow = row;
			do {
				pad.setLedOn(col, curRow, Launchpad.COLOUR_GREEN_FULL);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				curRow--;
				pad.setLedOff(col, curRow + 1);
			} while (curRow >= 0);
		}
		
	}
	
}
