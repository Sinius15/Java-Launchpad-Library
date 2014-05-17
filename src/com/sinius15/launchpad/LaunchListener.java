package com.sinius15.launchpad;

public interface LaunchListener {

	/**
	 * Triggered when a button on the launchpad is pushed in. 
	 * @param colomn the colomn on the launchpad where the left colomn is 0 and the right colomn with the round buttons is 8
	 * @param row the row on the launchpad where the top rowh with the round buttons is 0 and the bottom row is 8
	 */
	public void onButtonDown(int row, int colomn);
	
	/**
	 * Triggered when a button on the launchpad is released;
	 * @param colomn the colomn on the launchpad where the left colomn is 0 and the right colomn with the round buttons is 8
	 * @param row the row on the launchpad where the top rowh with the round buttons is 0 and the bottom row is 8
	 */
	public void onButtonUp(int row, int colomn);
	
}
