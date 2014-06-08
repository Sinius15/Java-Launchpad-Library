package com.sinius15.launchpad;

import java.util.Arrays;

/**
 * This class is used to create or load a (new) LaunchpadPattern. See the
 * github-documentation for fearther explination and examples.
 * 
 * @author Sinius15
 * @see www.sinius15.com
 */
public class LaunchpadPatternFactory extends LaunchpadPattern implements LaunchListener {
	
	/**
	 * needs serail because it extends LaunchpadPattern withch is serializable.
	 * 
	 * @author Sinius15
	 */
	private static final long serialVersionUID = 2148080826341765869L;
	Launchpad l;
	// this is the actual number stored in the data array. -1=off,
	// velocity=colour codes
	int selectedColour = 1;
	
	/**
	 * Creates a new launchpadPatternFactory.
	 * 
	 * @param launchpad
	 *            the launchpad.
	 * @author Sinius15
	 */
	public LaunchpadPatternFactory(Launchpad launchpad) {
		this.l = launchpad;
		Arrays.fill(data, new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1 });
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
		l.addListener(this);
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
		l.removeListener(this);
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
		if (selectedColour != -1) {
			l.setLedOn(colomn, row, selectedColour);
			data[colomn][row] = selectedColour;
		} else {
			l.setLedOff(colomn, row);
			data[colomn][row] = -1;
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
				return Launchpad.COLOUR_RED_LOW;
			case 2:
				return Launchpad.COLOUR_RED_FULL;
			case 3:
				return Launchpad.COLOUR_AMBER_LOW;
			case 4:
				return Launchpad.COLOUR_AMBER_FULL;
			case 5:
				return Launchpad.COLOUR_YELLOW_FULL;
			case 6:
				return Launchpad.COLOUR_GREEN_LOW;
			case 7:
				return Launchpad.COLOUR_GREEN_FULL;
			default:
				return 0;
		}
	}
	
}
