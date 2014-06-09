package com.sinius15.test;

import javax.sound.midi.MidiUnavailableException;

import com.sinius15.launchpad.LaunchListener;
import com.sinius15.launchpad.Launchpad;
import com.sinius15.launchpad.LaunchpadException;

public class DontTouchTheWhite implements Runnable, LaunchListener{
	
	Launchpad pad;
	boolean isRunning;
	
	//9 rows, 8 cols
	boolean[][] grid = new boolean[9][8];
	int speed = 500;
	int score = 0;
	
	public DontTouchTheWhite(String launchpadName){
		
		try {
			pad = new Launchpad(launchpadName);
			pad.open();
		} catch (LaunchpadException | MidiUnavailableException e) {
			e.printStackTrace();
			return;
		}
		pad.addListener(this);
		
		Thread game = new Thread(this, "gameThread");
		isRunning = true;
		game.start();
		
	}
	
	public void updateLights(){
		pad.reset();
		for(int row = 0; row<9; row++){
			for(int col = 0; col<8; col++){
				if(grid[row][col])
					pad.setLedOn(col, row, Launchpad.COLOUR_RED_FULL);
			}
		}
	}

	@Override
	public void onButtonDown(int row, int colomn) {
		if(colomn == 8)
			return;
		if(row == 0)
			return;
		if(grid[row][colomn]){
			grid[row][colomn] = false;
			pad.setLedOff(colomn, row);
			score++;
		}else if(grid[row-1][colomn]){
			grid[row-1][colomn] = false;
			pad.setLedOff(colomn, row-1);
			score++;
		}else if(grid[row+1][colomn]){
			grid[row+1][colomn] = false;
			pad.setLedOff(colomn, row+1);
			score++;
		}else{
			isRunning = false;
			pad.setLedOn(colomn, row, Launchpad.COLOUR_AMBER_FULL);
		}
	}


	@Override
	public void onButtonUp(int row, int colomn) {}
	
	@Override
	public void run() {
		mainLoop :
		while(isRunning){
			boolean[][] newGrid = new boolean[9][8];
			newGrid[0][randomNumber(0, 7)] = true;
			for(int row = 1; row<9; row++)
				newGrid[row] = grid[row-1];
			for(int col = 0; col<8; col++)
				if(newGrid[8][col])
					break mainLoop;
			grid = newGrid;
			updateLights();
			speed -= 5;
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(score);
		pad.showText("YOU LOST", 300, Launchpad.COLOUR_RED_FULL);
		pad.reset();
		pad.close();
	}
	
	public static int randomNumber(int min, int max){
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	public static void main(String[] args) {
		new DontTouchTheWhite("Launchpad S");
	}
}
