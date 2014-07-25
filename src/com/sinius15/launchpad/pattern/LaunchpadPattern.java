package com.sinius15.launchpad.pattern;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

import com.sinius15.launchpad.Launchpad;

public class LaunchpadPattern implements Serializable {
	
	/**
	 * Generated serial.
	 * 
	 * @author Sinius15
	 */
	private static final long serialVersionUID = 7537344835411411667L;
	
	/**
	 * the data-array that stores all the colour codes.<br>
	 * {@see Launchpad} for the color values; The other nubers are the acctual
	 * valocity numbers ready to be send to the launchpad.
	 * 
	 * @author Sinius15
	 */
	public int[][] data = new int[9][9];
	
	/**
	 * Could be usefull to give the luanchpad-patterna a name.
	 * 
	 * @author Sinius15
	 */
	public String name;
	
	/**
	 * Constructor. Initialize all the data values to -1.
	 * 
	 * @author Sinius15
	 */
	public LaunchpadPattern() {
		for (int[] a : data)
			Arrays.fill(a, -1);
	}
	
	/**
	 * Constructor
	 * 
	 * @param data
	 * @author Sinius15
	 */
	public LaunchpadPattern(int[][] data) {
		this.data = data;
	}
	
	/**
	 * Constructor
	 * 
	 * @param data
	 * @author Sinius15
	 */
	public LaunchpadPattern(int[][] data, String name) {
		this.data = data;
		this.name = name;
	}
	
	/**
	 * Creates a new launchpadPattern with the selected colour on all the places
	 * where the oldColor is selected.
	 * 
	 * @param oldColor is the old color to replace. If this argument is -99
	 * than all the colors that are not transparent will be set to the new color.
	 * @param newColor is the new color to replace the old color with
	 * @return a new LaunchpadPattern with the new color
	 * @author Sinius15
	 */
	public LaunchpadPattern setColor(int oldColor, int newColor) {
		int[][] out = new int[9][9];
		for (int x = 0; x < data.length; x++) {
			for (int y = 0; y < data[x].length; y++) {
				if (data[x][y] == oldColor)
					out[x][y] = newColor;
				if (oldColor == -99 && data[x][y] != Launchpad.COLOR_TRANSPARANT)
					out[x][y] = newColor;
				
			}
		}
		return new LaunchpadPattern(out);
	}
	
	/**
	 * This constructor loads a LaunchpadPattern from a file.
	 * 
	 * @param file
	 *            the file where the launchpadPattern is saved
	 * @throws IOException
	 *             when stuff goes wrong. See github-documentation for help or
	 *             search on the internet.
	 * @throws ClassNotFoundExceptionwhen
	 *             stuff goes wrong. See github-documentation for help or search
	 *             on the internet.
	 * @author Sinius15
	 */
	public LaunchpadPattern(File file) throws IOException, ClassNotFoundException {
		FileInputStream fileIn = new FileInputStream(file);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		LaunchpadPattern p = (LaunchpadPattern) in.readObject();
		this.data = p.data;
		in.close();
		fileIn.close();
	}
	
	/**
	 * This function saves the LaunchpadPattern to a file. The funciton uses
	 * default object serialization. The extention for LaunchpadPatterns is
	 * '.ser' (this is the standard convention in Java to give to serilized
	 * files)
	 * 
	 * @param file
	 *            where the file is located.
	 * @throws IOException
	 *             when stuff goes wrong. See github-documentation for help or
	 *             search on the internet.
	 */
	public void saveToFile(File file) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(file);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(this);
		out.close();
		fileOut.close();
	}
	
	/**
	 * @return a string representation of the object.
	 * @author Sinius15
	 */
	public String toString() {
		String out = "{";
		for (int[] e : data) {
			out += "{";
			for (int a : e) {
				out += a + ",";
			}
			out = out.substring(0, out.length() - 1);
			out += "},";
		}
		out = out.substring(0, out.length() - 1);
		out += "}";
		return out;
	}
	
	/**
	 * @return a string with the name of the luanchpad. Default is NULL
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the Launchpad-Pattern.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
