package com.sinius15.launchpad.events;

import com.sinius15.launchpad.Launchpad;

/**
 * 
 * @author Sinius15
 * @see www.sinius15.com
 */
public interface PadListener {
	
	/**
	 * Invoked when a Launchpad is opend. This method is called <b> after
	 * </b>the pad is opend.
	 */
	public void padOpen();
	
	/**
	 * Invoked when a Launchpad is closed. This method is called <b> after
	 * </b>the pad is closed.<br>
	 * <br>
	 * <b>Note: </b> Because a Launchpad is {@link AutoCloseable} this function
	 * wil not always be called! Only when {@link Launchpad#close()} is called!
	 */
	public void padClose();
	
	/**
	 * Invoked when a Launchpad is discconnected. The source of disconnection is
	 * most of the time a disconnection of a phisical wire. This method is
	 * called <b>after</b> the pad is disconnected.<br>
	 * <br>
	 * 
	 * <b>Note: </b> This function is only called when the launchpad-midi-device
	 * is not avalable to the Java Midi API!!
	 */
	public void padDisconnected();
	
}
