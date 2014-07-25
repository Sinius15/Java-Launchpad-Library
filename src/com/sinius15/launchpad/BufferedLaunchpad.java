package com.sinius15.launchpad;

import java.util.Arrays;

/**
 * The big difference between a BufferdLaunchpad and a standard Launchpad is
 * than when a led is turned on, the standard Launchpad will always send a on
 * message to the Launchpad where as the BufferedLaunchpad will first check if
 * the led you are going to set not already has been turned on. To do this, the
 * BufferedLaunchpad keeps an int[][] of the led state on the launchpad. This
 * means you will also be able to ask what the current led state is with
 * funciton {@link #getLedState()}.
 * 
 * @author Sinius15
 * @see www.sinius15.com
 */
public class BufferedLaunchpad extends Launchpad {
	
	// [row][col]
	int[][] ledState = new int[9][9];
	
	/**
	 * see {@link BufferedLaunchpad} for the difference between a Standared
	 * Launchpad and a Buffered Launchpad.
	 * 
	 * @author Sinius15
	 * @param midiDeviceName
	 *            The name of the device. If the name is NULL, than a epty
	 *            BufferedLaunchpad will be constructed: All variables are null.
	 *            To see all the avalable devices, use
	 *            <code>MidiCommon.listDevices(true, true);</code>
	 * @throws LaunchpadException
	 *             when stuff goes wrong. See github-documentation for possible
	 *             explinations.
	 */
	public BufferedLaunchpad(String midiDeviceName) throws LaunchpadException {
		super(midiDeviceName);
		for (int[] a : ledState) 
			Arrays.fill(a, COLOR_OFF);
	}
	
	/**
	 * Turns on a led on the launchpad. If the button on the launchpad is
	 * already turned on on this color, than it will not send a message to the
	 * lanchpad.
	 * 
	 * @param colomn
	 *            the colomn on the launchpad where the left colomn is 0 and the
	 *            right colomn with the round buttons is 8
	 * @param row
	 *            the row on the launchpad where the top rowh with the round
	 *            buttons is 0 and the bottom row is 8
	 * @param color
	 *            the colour of the led. Values are found in the static feelds
	 *            in the Launchpad class
	 * @author Sinius15
	 */
	@Override
	public void setLedOn(int colomn, int row, int color) {
		if(ledState[row][colomn] == color)
			return;
		super.setLedOn(colomn, row, color);
		ledState[row][colomn] = color;
	}
	
	/**
	 * Turns off a led on the launchpad. If the butotn on the launchpad is
	 * already turned off, than this function will not send a message to the
	 * launchpad.
	 * 
	 * @param colomn
	 *            the colomn on the launchpad where the left colomn is 0 and the
	 *            right colomn with the round buttons is 8
	 * @param row
	 *            the row on the launchpad where the top rowh with the round
	 *            buttons is 0 and the bottom row is 8
	 * @author Sinius15
	 */
	@Override
	public void setLedOff(int colomn, int row) {
		if(ledState[row][colomn] == COLOR_OFF)
			return;
		super.setLedOff(colomn, row);
		ledState[row][colomn] = COLOR_OFF;
	}
	
	@Override
	public void reset() {
		super.reset();
		for (int[] a : ledState) 
			Arrays.fill(a, COLOR_OFF);
	}
	
	/**
	 * Get the color of a button on the launchpad.
	 * 
	 * @param colomn
	 *            the colomn of the button you want to get te led color of.
	 * @param row
	 *            the row of the button you want the led color of.
	 * @return a integer repesenting the color of the led on the launchpad. See
	 *         {@link #calculateColour(int, int)} for the meaning of the color.
	 */
	public int getLedState(int colomn, int row) {
		return ledState[row][colomn];
	}
	
	/**
	 * Get the array where all the color codes of the colors on the launchpad
	 * are stored.
	 * 
	 * @return a 2d integer array where int[row][colomn].
	 */
	public int[][] getLedStates() {
		return ledState;
	}
}
