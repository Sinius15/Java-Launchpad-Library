package com.sinius15.launchpad;

public class LPRack implements LaunchListener{

	
	//[row][colomn]
	private Runnable[][] runnersUp = new Runnable[9][9];
	private Runnable[][] runnersDown = new Runnable[9][9];
	private LaunchpadPattern[][] patterns = new LaunchpadPattern[9][9];
	
	private Launchpad pad;
	
	/**
	 * Creates a new LPRack.<br>
	 * A lpRack can do two things:<br>
	 * 1. when a button is pressed, show a pattern, and when the button is released the 
	 * pattern is automaticly un-shown.<br>
	 * 2. when a button is pressed start a new thread with a runnable you added.<br>
	 * <br>
	 * @param pad
	 */
	public LPRack(Launchpad pad){
		this.pad = pad;
		if(pad != null)
			pad.addListener(this);
	}
	
	/**
	 * This method stops this class from receiving any events from the launchpad.
	 */
	public void stop(){
		pad.removeListener(this);
	}
	
	/**
	 * This sets the pattern that is shown when a button is pressed.
	 * This pattern is automaticly un-shown when the button is released.
	 * 
	 * @param row the row of the button where the pattern should be shown
	 * @param colomn the colomn of the button where the pattern should be shown
	 * @param pattern the pattern to show
	 */
	public void setLaunchpadPattern(int row, int colomn, LaunchpadPattern pattern){
		patterns[row][colomn] = pattern;
	}
	
	/**
	 * Removes a pattern that is previousely set by "setLaunchpadPattern"
	 * 
	 * @param row the row of the button that the pattern needs to be remove
	 * @param colomn the colomn of the button that the pattern needs to be remove
	 */
	public void removeLaunchpadPattern(int row, int colomn){
		patterns[row][colomn] = null;
	}
	
	/**
	 * This sets the runnable that is called when a button is pressed.
	 * 
	 * @param row the row of the button where the runnable sould be called
	 * @param colomn the colomn of the button where the runnable should be called
	 * @param r the runnable that is going to be called when the button is pressed.
	 * <b>remember that the same runnable is called mulatble times!</b>
	 */
	public void setOnButtonDown(int row, int colomn, Runnable r){
		runnersDown[row][colomn] = r;
	}
	
	/**
	 * Removes a runnable that is previousely set by "setOnButtonDown"
	 * 
	 * @param row the row of the button
	 * @param colomn the colomn of the button
	 */
	public void removeOnButtonDown(int row, int colomn){
		runnersDown[row][colomn] = null;
	}
	
	/**
	 * This sets the runnable that is called when a button is released.
	 * 
	 * @param row the row of the button where the runnable sould be called
	 * @param colomn the colomn of the button where the runnable should be called
	 * @param r the runnable that is going to be called when the button is released.
	 * <b>remember that the same runnable is called mulatble times!</b>
	 */
	public void setOnButtonUp(int row, int colomn, Runnable r){
		runnersUp[row][colomn] = r;
	}
	
	/**
	 * Removes a runnable that is previousely set by "setOnButtonUp"
	 * 
	 * @param row the row of the button
	 * @param colomn the colomn of the button
	 */
	public void removeOnButtonUp(int row, int colomn){
		runnersUp[row][colomn] = null;
	}
	
	/**
	 * DO NOT CALL! THIS IS AN IMPLEMENTATION FOR THIS CLASS ITSSELF.
	 * 
	 * @author Sinius15
	 */
	@Override
	public void onButtonDown(int row, int colomn) {
		if(runnersDown[row][colomn] != null)
			new Thread(runnersDown[row][colomn], "Created_By_LPRack.class_onButtonDown"+row+","+colomn).start();
		if(patterns[row][colomn] != null)
			pad.showPattern(patterns[row][colomn]);
	}

	/**
	 * DO NOT CALL! THIS IS AN IMPLEMENTATION FOR THIS CLASS ITSSELF.
	 * 
	 * @author Sinius15
	 */
	@Override
	public void onButtonUp(int row, int colomn) {
		if(runnersUp[row][colomn] != null)
			new Thread(runnersUp[row][colomn], "Created_By_LPRack.class_onButtonUp"+row+","+colomn).start();
		if(patterns[row][colomn] != null)
			pad.showPattern(patterns[row][colomn].setColor(Launchpad.COLOUR_OFF));
	}
	
	
}
