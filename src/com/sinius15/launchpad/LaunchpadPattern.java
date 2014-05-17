package com.sinius15.launchpad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class LaunchpadPattern implements Serializable {
	
	/**
	 * Generated serial.
	 * 
	 * @author Sinius15
	 */
	private static final long serialVersionUID = 7537344835411411667L;
	
	/**
	 * the data-array that stores all the colour codes.<br>
	 * -1=off<br>
	 * The other nubers are the acctual valocity numbers ready to be send to the
	 * launchpad.
	 * 
	 * @author Sinius15
	 */
	int[][] data = new int[9][9];
	
	/**
	 * Empty constructor
	 * 
	 * @author Sinius15
	 */
	public LaunchpadPattern() {}
	
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
	
}
