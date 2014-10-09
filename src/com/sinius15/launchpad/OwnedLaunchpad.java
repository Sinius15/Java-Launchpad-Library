package com.sinius15.launchpad;

import java.util.ArrayList;

/**
 * This class is smart {@link Launchpad}. It keeps track of who sets the color
 * of the Launchpad and lights the lights automatically.<br>
 * <br>
 * So, {@link OwnedLaunchpad#setLedOn(int, int, int, String)} is called with
 * owner 'a' and color 5. The button will light up with color 5. If than a
 * person 'b' calls {@link OwnedLaunchpad#setLedOn(int, int, int, String)} with
 * color 6, the button will light up with color 6. Than person 'b' will call
 * {@link OwnedLaunchpad#setLedOff(int, int, String)} with owner 'b'. Than the
 * button will light up in color 5 because color 6 is removed.<br>
 * <br>
 * In short: this Launchpad controller gives every button more layers where
 * every layer is owned by a owner. The top color will be shown on the pad. If
 * the top color is removed, the color beneath there will be shown.
 * 
 * @author Sinius15
 * 
 */
public class OwnedLaunchpad extends BufferedLaunchpad {

	/**
	 * all the buttons on the pad.
	 */
	private Button[][] buttons = new Button[9][9];// [row][col]

	/**
	 * see {@link OwnedLaunchpad} for the difference between a Standared
	 * Launchpad and a Owned Launchpad.
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
	public OwnedLaunchpad(String midiDeviceName) throws LaunchpadException {
		super(midiDeviceName);
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				buttons[row][col] = new Button();
			}
		}
	}

	/**
	 * Sets on a led with the defined owner.
	 * 
	 * @param column
	 *            the column on the Launchpad where the left column is 0 and the
	 *            right column with the round buttons is 8
	 * @param row
	 *            the row on the Launchpad where the top row with the round
	 *            buttons is 0 and the bottom row is 8
	 * @param owner
	 *            The owner of the color.
	 * @param color
	 *            the color of the button to set.
	 * @see {@link OwnedLaunchpad} for information about the usage of this
	 *      function.
	 * @author Sinius15
	 */
	public void setLedOn(int column, int row, int color, String owner) {
		if(color == Launchpad.COLOR_OFF){
			setLedOff(column, row, owner);
			return;
		}
		buttons[row][column].owners.add(new Layer(owner, color));
		super.setLedOn(column, row, color);
	}

	/**
	 * Turns off all the color layers with this owner on the button.
	 * 
	 * @param column
	 *            the column on the Launchpad where the left column is 0 and the
	 *            right column with the round buttons is 8
	 * @param row
	 *            the row on the Launchpad where the top row with the round
	 *            buttons is 0 and the bottom row is 8
	 * @param owner
	 *            The owner of the color..
	 * @see {@link OwnedLaunchpad} for information about the usage of this
	 *      function.
	 * @author Sinius15
	 */
	public void setLedOff(int column, int row, String owner) {
		buttons[row][column].removeAllFromOnwer(owner);
		super.setLedOn(column, row, buttons[row][column].getLastColor());
	}
	
	public void clearLed(int column, int row) {
		buttons[row][column].owners.clear();
		super.setLedOn(column, row, buttons[row][column].getLastColor());
	}

	/**
	 * Should not use this function! Use {@link OwnedLaunchpad#setLedOff(int, int, String)} instead!
	 */
	@Deprecated
	@Override
	public void setLedOff(int colomn, int row) {
		throw new IllegalArgumentException("should not be here!");
	}

	/**
	 * Should not use this function! Use {@link OwnedLaunchpad#setLedOn(int, int, int, String)} instead!
	 */
	@Deprecated
	@Override
	public void setLedOn(int colomn, int row, int color) {
		throw new IllegalArgumentException("should not be here!");
	}

	/**
	 * Not implemented yet
	 * @param color
	 */
	@Deprecated
	@Override
	public void setFullLaunchpadColor(int color) {
		super.setFullLaunchpadColor(color);
	}

	/**
	 * Not implemented yet.
	 */
	@Deprecated
	@Override
	public void LEDTest() {
		super.LEDTest();
	}

	/**
	 * Every physical button on the pad has a instance of this class. 
	 * @author Sinius15
	 */
	private class Button {
		public ArrayList<Layer> owners = new ArrayList<>();

		public void removeAllFromOnwer(String owner) {
			ArrayList<Layer> toRemove = new ArrayList<>();
			for (Layer c : owners) {
				if (c.owner.equals(owner))
					toRemove.add(c);
			}
			owners.removeAll(toRemove);
		}

		public int getLastColor() {
			return owners.size() > 0 ? owners.get(owners.size() - 1).color
					: COLOR_OFF;
		}
	}

	/**
	 * Every {@link Button} has multaple layers. A layer has a owner
	 * and a color.
	 * @author Sinius15
	 */
	private class Layer {
		public String owner;
		public int color;

		public Layer(String owner, int color) {
			this.owner = owner;
			this.color = color;
		}
	}
}
