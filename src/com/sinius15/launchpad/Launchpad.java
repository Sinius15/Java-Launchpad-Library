package com.sinius15.launchpad;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

import javax.management.RuntimeErrorException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Transmitter;

import org.jsresources.MidiCommon;

public class Launchpad implements Receiver{
	
	private ArrayList<LaunchListener> listeners = new ArrayList<>();
	private MidiDevice inputDevice = null, outputDevice = null;
	private Receiver transmitter = null;
	private String name;
	
	public static final int COLOUR_OFF = 12;
	public static final int COLOUR_RED_LOW = 13;
	public static final int COLOUR_RED_FULL = 15;
	public static final int COLOUR_AMBER_LOW = 29;
	public static final int COLOUR_AMBER_FULL = 63;
	public static final int COLOUR_YELLOW_FULL = 62;
	public static final int COLOUR_GREEN_LOW = 28;
	public static final int COLOUR_GREEN_FULL = 60;
	
	/**
	 * @author Sinius15
	 * @param midiDeviceName
	 *            The name of the device. To see all the avalable devices, use
	 *            <code>
	 * MidiCommon.listDevices(true, true);
	 * </code>
	 * @throws LaunchpadException
	 *             when stuff goes wrong. See github-documentation for possible
	 *             explinations.
	 */
	public Launchpad(String midiDeviceName) throws LaunchpadException {
		this.name = midiDeviceName;
		
		MidiDevice.Info info = MidiCommon.getMidiDeviceInfo(this.name, false);
		if (info == null)
			throw new LaunchpadException(this.name, "no launchpad found with name " + this.name);
		try {
			inputDevice = MidiSystem.getMidiDevice(info);
		} catch (MidiUnavailableException e) {
			throw new LaunchpadException(this.name,
					"an error occurred when loading Launchpad. Please read documentation for help.");
		}
		if (inputDevice == null)
			throw new LaunchpadException(this.name, "no launchpad found with name " + this.name);
		
		info = MidiCommon.getMidiDeviceInfo(this.name, true);
		if (info == null)
			throw new LaunchpadException(this.name, "no launchpad found with name " + this.name);
		try {
			outputDevice = MidiSystem.getMidiDevice(info);
		} catch (MidiUnavailableException e) {
			throw new LaunchpadException(this.name,
					"an error occurred when loading Launchpad. Please read documentation for help.");
		}
		if (outputDevice == null)
			throw new LaunchpadException(this.name, "no launchpad found with name " + this.name);
	}
	
	/**
	 * Resets the launchpad. All LEDs are turned off, and the mapping mode,
	 * buffer settings, and duty cycle are reset to their default values.
	 * 
	 * @author Sinius15
	 */
	public void reset() {
		try {
			ShortMessage m = new ShortMessage();
			m.setMessage(ShortMessage.CONTROL_CHANGE, 0, 0, 0);
			sendMessage(m);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Turns on a led on the launchpad.<br>
	 * 
	 * @param colomn
	 *            the colomn on the launchpad where the left colomn is 0 and the
	 *            right colomn with the round buttons is 8
	 * @param row
	 *            the row on the launchpad where the top rowh with the round
	 *            buttons is 0 and the bottom row is 8
	 * @param colour
	 *            the colour of the led. Values are found in the static feelds
	 *            in the Launchpad class
	 * @author Sinius15
	 */
	public void setLedOn(int colomn, int row, int colour) {
		try {
			ShortMessage m = new ShortMessage();
			if(row == 0)
				m.setMessage(ShortMessage.CONTROL_CHANGE, 0, coordToData(colomn, row), colour);
			else
				m.setMessage(ShortMessage.NOTE_ON, 0, coordToData(colomn, row), colour);
			sendMessage(m);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Turns off a led on the launchpad. <br>
	 * Remember: It is not possible to turn on/off leds on the top row!
	 * 
	 * @param colomn
	 *            the colomn on the launchpad where the left colomn is 0 and the
	 *            right colomn with the round buttons is 8
	 * @param row
	 *            the row on the launchpad where the top rowh with the round
	 *            buttons is 0 and the bottom row is 8
	 * @author Sinius15
	 */
	public void setLedOff(int colomn, int row) {
		try {
			ShortMessage m = new ShortMessage();
			if(row == 0)
				m.setMessage(ShortMessage.CONTROL_CHANGE, 0, coordToData(colomn, row), COLOUR_OFF);
			else
				m.setMessage(ShortMessage.NOTE_OFF, 0, coordToData(colomn, row), COLOUR_OFF);
			sendMessage(m);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Turns on all leds in 3 different colours: orange-low, green-low, yellow
	 * with a pause of 500 miliseconds. This occupies the thread for 1,5 second.
	 * 
	 * @author Sinius15
	 */
	public void LEDTest() {
		try {
			for (int i = 125; i <= 127; i++) {
				ShortMessage m = new ShortMessage();
				m.setMessage(176, 0, 0, i);
				sendMessage(m);
				Thread.sleep(500);
			}
		} catch (InvalidMidiDataException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends a midi-message directly to the launchpad.
	 * 
	 * @param message
	 * @author Sinius15
	 */
	public void sendMessage(MidiMessage message) {
		transmitter.send(message, -1);
	}
	
	/**
	 * Opens the conneciton to the launchpad. This method needs to be called
	 * before anything can be received or send. From the moment that this method
	 * is called, the listener starts listening to launchpad key input.
	 * 
	 * @throws MidiUnavailableException
	 *             when stuff goes wrong. See documentation on github what the
	 *             cause could be.
	 * @author Sinius15
	 */
	public void open() throws MidiUnavailableException {
		inputDevice.open();
		Transmitter t = inputDevice.getTransmitter();
		t.setReceiver(this);
		outputDevice.open();
		transmitter = outputDevice.getReceiver();
	}
	
	/**
	 * Converts the coördinate on the launchpad to the midi-data.
	 * 
	 * @param colomn
	 *            the colomn on the launchpad where the left colomn is 0 and the
	 *            right colomn with the round buttons is 8
	 * @param row
	 *            the row on the launchpad where the top rowh with the round
	 *            buttons is 0 and the bottom row is 8
	 * @author Sinius15
	 */
	public static int coordToData(int colomn, int row) {
		int data = (row - 1) * 16 + colomn;
		if (data < 0)
			data += 120;
		return data;
	}
	
	/**
	 * Converts the data from the launchpad to the row and colomn op de
	 * launchpad.
	 * 
	 * @param data
	 *            from the launchpad
	 * @param command
	 *            the command from the launchpad
	 * @return a Point where the y is the row, and the x is the colomn
	 * @author Sinius15
	 */
	public static Point dataToCoord(int data, int command) {
		int colomn = data % 16;
		int row = data / 16 + 1;
		if (command == 176) {
			row = 0;
			colomn = colomn - 8;
		}
		return new Point(colomn, row);
	}
	
	/**
	 * Calculates the midi message velocity from two colours. The red and green
	 * meanings:
	 * <table>
	 * <tr>
	 * <td>0</td>
	 * <td>Off</td>
	 * </tr>
	 * <tr>
	 * <td>1</td>
	 * <td>Low brightness</td>
	 * </tr>
	 * <tr>
	 * <td>2</td>
	 * <td>Medium brightness</td>
	 * </tr>
	 * <tr>
	 * <td>3</td>
	 * <td>Full brightness</td>
	 * </tr>
	 * </table>
	 * 
	 * @param green
	 *            the amount of green between 0 and 3
	 * @param red
	 *            the amount of red between 0 and 3
	 * @return the colour velocity
	 * @author Sinius15
	 */
	public static int calculateColour(int green, int red) {
		return (16 * green) + red + 12;
	}
	
	/**
	 * Adds a listener for the launchpad.
	 * 
	 * @param listener
	 *            the listener to add
	 * @return <tt>true</tt> (as specified by {@link Collection#add})
	 * @author Sinius15
	 */
	public boolean addListener(LaunchListener listener) {
		return this.listeners.add(listener);
	}
	
	/**
	 * Removes a listener from the launchpad.
	 * 
	 * @param listener
	 *            the listener to remove
	 * @return <tt>true</tt> if this list contained the specified element
	 * @author Sinius15
	 */
	public boolean removeListener(LaunchListener listener) {
		return this.listeners.remove(listener);
	}
	
	/**
	 * Removes all the listeners.
	 * 
	 * @author Sinius15
	 */
	public void clearListeners() {
		this.listeners.clear();
	}
	
	/**
	 * Closes the connection to the launchpad. This also stops the Listener from
	 * receiving any events. This function calls Launchpad.reset() before
	 * closing the connections.
	 * 
	 * @author Sinius15
	 */
	@Override
	public void close() {
		reset();
		inputDevice.close();
		transmitter.close();
		outputDevice.close();
	}
	
	/**
	 * This funciton sets the lights on the launchpad to the selected pattern
	 * 
	 * @param pattern the pattern to show
	 * @author Sinius15
	 */
	public void showPattern(LaunchpadPattern pattern) {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (pattern.data[row][col] != -1)
					setLedOn(row, col, pattern.data[row][col]);
			}
		}
	}
	
	/**
	 * This funciton sets the lights on the launchpad to the selected pattern
	 * 
	 * @param pattern the pattern to show
	 * @author Sinius15
	 */
	public void showPattern(LaunchpadPattern pattern, int rowShift, int colShift) {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (pattern.data[row][col] != -1){
					if(row+rowShift > 8 || col+colShift > 8 || col+colShift > 8 || col+colShift < 0)
						continue;
					else
						setLedOn(col+colShift, row+rowShift, pattern.data[row][col]);
				}
			}
		}
	}
	
	/**
	 * This function shows a String of text on the launchpad. It uses the default LaunchpadPatterns from the LaunchpadResources class.
	 * @param text the text to show.
	 * @param speed the time to show each letter in mili-seconds
	 */
	public void showText(String text, int speed){
		showText(text, speed, -1);
	}
	
	/**
	 * This function shows a String of text on the launchpad. It uses the default LaunchpadPatterns from the LaunchpadResources class.
	 * @param text the text to show.
	 * @param speed the time to show each letter in mili-seconds
	 * @param color the color to show the text in. If colour is -1 than the original colour is used.
	 */
	public void showText(String text, int speed, int color) {
		LaunchpadPattern p;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			p = LaunchpadResources.getLetterPattern(c);
			if (p == null) 
				throw new RuntimeErrorException(null, "Could not find character " + c);
			reset();
			if(color != -1)
				p = p.setColor(color);
			showPattern(p);
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {}
		}
	}

	/**
	 * This funciton uses "Rapid Subsequent Updates" to turn on all the lights on the launchpad as fast as possible.
	 * By using this rapid method, there 40 messages sent to the launchpad, instead of 80 (when you turn on all
	 * the lights on one by one). If a midi-message is sent to the launchpad on channel 0 and this method is executing,
	 * than this method will probably fail. So make sure this method is the only one sending messages oterwhise your are f***ed 
	 * @param color The color to set the launchpad
	 */
	public void setFullLaunchpadColor(int color){
		try {
			ShortMessage m = new ShortMessage();
			m.setMessage(ShortMessage.NOTE_ON, 2, color, color);
			for(int i = 0; i<40; i++)
				sendMessage(m);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * This method first resets the launchpad, after that it shows
	 * all the colors avalable in the middle of the pad.
	 */
	public void showColorPallette(){
		reset();
		for(int x = 0; x < 4; x++){
			for(int y = 0; y < 4; y++){
				setLedOn(x+2, y+3, Launchpad.calculateColour(x, y));
			}
		}
	}
	
	/**
	 * DO NOT USE! THIS IS AN IMPLEMENTATION FOR THE LAUNCHPAD ITSSELF.
	 * 
	 * @author Sinius15
	 */
	@Override
	public void send(MidiMessage message, long timeStamp) {
		if (message instanceof ShortMessage) {
			ShortMessage m = (ShortMessage) message;
			int row = dataToCoord(m.getData1(), m.getCommand()).y;
			int colomn = dataToCoord(m.getData1(), m.getCommand()).x;
			for (LaunchListener l : listeners) {
				if (m.getData2() == 0)
					l.onButtonUp(row, colomn);
				else
					l.onButtonDown(row, colomn);
			}
		}
	}
	
}
