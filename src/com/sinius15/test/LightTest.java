package com.sinius15.test;

import java.io.IOException;

import javax.sound.midi.MidiUnavailableException;

import com.sinius15.launchpad.LPRack;
import com.sinius15.launchpad.Launchpad;
import com.sinius15.launchpad.LaunchpadException;
import com.sinius15.launchpad.LaunchpadPattern;

public class LightTest {
	
	Launchpad pad;
	LPRack curRack;
	
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
	public static final int[][] HIT_LEFT = { { 60, 63, 63, 63, 63, 63, 63, 63, 63 },
			{ 60, 63, 60, 60, 60, 60, 60, 60, 63 }, { 60, 63, 60, 60, 60, 60, 60, 60, 63 },
			{ 60, 63, 63, 63, 63, 63, 63, 63, 63 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 },
			{ -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 },
			{ -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 } };
	public static final int[][] HIT_RIGHT = { { -1, -1, -1, -1, -1, -1, -1, -1, -1 },
			{ -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 },
			{ -1, -1, -1, -1, -1, -1, -1, -1, -1 }, { -1, 63, 63, 63, 63, 63, 63, 63, 63 },
			{ -1, 63, 15, 15, 15, 15, 15, 15, 63 }, { -1, 63, 15, 15, 15, 15, 15, 15, 63 },
			{ -1, 63, 63, 63, 63, 63, 63, 63, 63 }, { -1, -1, -1, -1, -1, -1, -1, -1, -1 } };
	
	public LightTest(String device) {
		try {
			pad = new Launchpad(device);
			pad.open();
		} catch (MidiUnavailableException | LaunchpadException e) {
			e.printStackTrace();
		}
		setGridA();
		setControlls();
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pad.close();
	}
	
	public void setGridA() {
		curRack = new LPRack(pad);
		curRack.setLaunchpadPattern(4, 3,
				new LaunchpadPattern(CUBE1).setColor(Launchpad.COLOUR_AMBER_FULL));
		curRack.setLaunchpadPattern(3, 2,
				new LaunchpadPattern(CUBE2).setColor(Launchpad.COLOUR_AMBER_FULL));
		curRack.setLaunchpadPattern(2, 1,
				new LaunchpadPattern(CUBE3).setColor(Launchpad.COLOUR_AMBER_FULL));
		curRack.setLaunchpadPattern(1, 0,
				new LaunchpadPattern(CUBE4).setColor(Launchpad.COLOUR_AMBER_FULL));
		
		for (int col = 0; col <= 8; col++) {
			curRack.setOnButtonDown(8, col, new ShotEffect(8, col));
		}
		
		curRack.setOnButtonDown(0, 0, new Runnable() {
			@Override
			public void run() {
				pad.setFullLaunchpadColor(Launchpad.COLOUR_GREEN_FULL);
			}
		});
		curRack.setOnButtonDown(0, 1, new Runnable() {
			@Override
			public void run() {
				pad.setFullLaunchpadColor(Launchpad.COLOUR_RED_FULL);
			}
		});
		curRack.setOnButtonDown(0, 2, new Runnable() {
			@Override
			public void run() {
				pad.setFullLaunchpadColor(Launchpad.COLOUR_AMBER_FULL);
			}
		});
		curRack.setOnButtonDown(0, 3, new Runnable() {
			@Override
			public void run() {
				pad.reset();
			}
		});
		curRack.setOnButtonDown(0, 4, new Runnable() {
			@Override
			public void run() {
				pad.showColorPallette();
			}
		});
	}
	
	public void setGridB() {
		curRack = new LPRack(pad);
		for (int row = 0; row < 9; row++)
			for (int col = 0; col < 4; col++)
				curRack.setLaunchpadPattern(row, col, new LaunchpadPattern(HIT_LEFT));
		for (int row = 0; row < 9; row++)
			for (int col = 4; col < 9; col++)
				curRack.setLaunchpadPattern(row, col, new LaunchpadPattern(HIT_RIGHT));
		
	}
	
	public void setGridC() {
		curRack = new LPRack(pad);
	}
	
	public void setGridD() {
		curRack = new LPRack(pad);
	}
	
	public void setControlls() {
		curRack.setOnButtonDown(1, 8, new Runnable() {
			@Override
			public void run() {
				curRack.stop();
				setGridA();
				setControlls();
				System.out.println("a");
			}
		});
		curRack.setOnButtonDown(2, 8, new Runnable() {
			@Override
			public void run() {
				curRack.stop();
				setGridB();
				setControlls();
				System.out.println("b");
			}
		});
		curRack.setOnButtonDown(3, 8, new Runnable() {
			@Override
			public void run() {
				curRack.stop();
				setGridC();
				setControlls();
			}
		});
		curRack.setOnButtonDown(4, 8, new Runnable() {
			@Override
			public void run() {
				curRack.stop();
				setGridD();
				setControlls();
			}
		});
	}
	
	public static void main(String[] args) {
		new LightTest("Launchpad S");
	}
	
	class ShotEffect implements Runnable {
		
		final int row, col;
		
		public ShotEffect(int row, int col) {
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
