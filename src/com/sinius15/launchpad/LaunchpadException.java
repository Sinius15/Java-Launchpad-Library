package com.sinius15.launchpad;

public class LaunchpadException extends Exception{

	private static final long serialVersionUID = 134211231693663328L;

	private String launchpadName;

	public LaunchpadException(String launchpadName, String message) {
		super(message);
		this.launchpadName = launchpadName;
		
	}
	
	public String getLaunchpadName() {
		return launchpadName;
	}

	public void setLaunchpadName(String launchpadName) {
		this.launchpadName = launchpadName;
	}
}
