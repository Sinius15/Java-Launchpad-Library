package com.sinius15.launchpad.pattern;

import java.util.Arrays;

import com.sinius15.launchpad.Launchpad;
import com.sinius15.launchpad.events.ButtonListener;

/**
 * This class is used to create or load a (new) LaunchpadPattern. See the
 * github-documentation for further explination and examples.
 * 
 * @author Sinius15
 * @see www.sinius15.com
 */
@Deprecated
public class LaunchpadPatternFactory implements ButtonListener {
	
	private Launchpad l;
	// this is the actual number stored in the data array. -1=off,
	// velocity=colour codes
	private int selectedColour = -1;
	private int[][] data = new int[9][9];
	
	/**
	 * Creates a new launchpadPatternFactory.
	 * 
	 * @param launchpad
	 *            the launchpad.
	 * @author Sinius15
	 */
	public LaunchpadPatternFactory(Launchpad launchpad) {
		this.l = launchpad;
		for (int[] a : data) {
			Arrays.fill(a, -1);
		}
		// Arrays.fill(data, new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1 });
		// /\ This does not work!~!!! (i do not know why it does not work)  
		// /\   <now i do! it is because it references to the same array all the time>
	}
	
	/**
	 * This starts the recording of a new pattern. First it resets the
	 * launchpad, than it adds a new listener to the launchpad.<b> Make sure
	 * that other code is not changing the state of the lights!</b> If they do
	 * than the visual feedback will break.<br>
	 * <br>
	 * 
	 * The most right column is the colour selector. Press one of the colours to
	 * select the colour. Than press one of the buttons in the grid to set the
	 * button to the specific colour.
	 * 
	 * @author Sinius15
	 */
	public void startRecording() {
		l.reset();
		l.addButtonListener(this);
		for (int i = 1; i < 8; i++)
			l.setLedOn(8, i + 1, rowToColour(i));
	}
	
	/**
	 * This stops the recording.
	 * 
	 * @return the recorded pattern.
	 * @author Sinius15
	 */
	public LaunchpadPattern stopRecording() {
		l.removeButtonListener(this);
		return new LaunchpadPattern(data);
	}
	
	/**
	 * DO NOT CALL! THIS IS A IMPLEMENTATION!
	 * 
	 * @author Sinius15
	 */
	@Override
	public void onButtonDown(int row, int colomn) {
		if (colomn == 8) {
			selectedColour = rowToColour(row - 1);
			return;
		}
		if (selectedColour == -1) {
			l.setLedOff(colomn, row);
			data[colomn][row] = -1;
		} else {
			l.setLedOn(colomn, row, selectedColour);
			data[colomn][row] = selectedColour;
		}
		
	}
	
	/**
	 * Nothing to do here
	 * 
	 * @author Sinius15
	 */
	@Override
	public void onButtonUp(int row, int colomn) {}
	
	/**
	 * Converts a row number to a colour.
	 * 
	 * @author Sinius15
	 */
	private int rowToColour(int i) {
		switch (i) {
			case 0:
				return -1;
			case 1:
				return Launchpad.COLOR_RED_LOW;
			case 2:
				return Launchpad.COLOR_RED_FULL;
			case 3:
				return Launchpad.COLOR_AMBER_LOW;
			case 4:
				return Launchpad.COLOR_AMBER_FULL;
			case 5:
				return Launchpad.COLOR_YELLOW_FULL;
			case 6:
				return Launchpad.COLOR_GREEN_LOW;
			case 7:
				return Launchpad.COLOR_GREEN_FULL;
			default:
				return 0;
		}
	}
	
}
