package com.sinius15.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsresources.MidiCommon;

import com.sinius15.launchpad.LaunchListener;
import com.sinius15.launchpad.Launchpad;
import com.sinius15.launchpad.LaunchpadPattern;
import com.sinius15.launchpad.LaunchpadPatternFactory;

public class LaunchTester implements LaunchListener{

	public static final String name = "Launchpad S";
	Launchpad s;
	LaunchpadPattern patt;
	int what = 4;
	
	public LaunchTester(){
		try {
			for(String s: MidiCommon.listDevices(true, true)) {
				System.out.println(s);
			}
			s = new Launchpad(name);
			s.addListener(this);
			s.open();
			if(what == 0){
				LaunchpadPatternFactory fac = new LaunchpadPatternFactory(s);
				fac.startRecording();
				
				Scanner scanner = new Scanner(System.in, "UTF-8");
				String text = scanner.nextLine();
				
				LaunchpadPattern p = fac.stopRecording();
				s.reset();

				scanner.close();
				
				p.saveToFile(new File("alphabet\\" + text + ".ser"));
				System.out.println(p.toString());
			}
			if(what == 1){
				String text = "Q";
				patt = new LaunchpadPattern(new File("alphabet\\" + text + ".ser"));
				s.showPattern(patt);
				System.in.read();
			}
			if(what == 2){
				String txt = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
				for (int i = 0; i < txt.length(); i++){
					char c = txt.charAt(i); 
					File f = new File("alphabet\\" + c + ".ser");
					LaunchpadPattern p = new LaunchpadPattern(f);
					System.out.println("public static final int[][] LETTER_CAPITAL_"+c+" = "+p.toString()+";");
				}
			}
			if(what == 3){
				String txt = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
				for (int i = 0; i < txt.length(); i++){
					char c = txt.charAt(i); 

					System.out.println("case '"+c+"': return new LaunchpadPattern(NUMBER_"+(""+c).toUpperCase()+");");
				}
			}
			if(what == 4){
				s.showText("Hello world".toUpperCase(), 500, Launchpad.COLOUR_GREEN_FULL);
			}
			if(what == 5){
				Scanner scanner = new Scanner(System.in);
				LaunchpadPatternFactory fac = new LaunchpadPatternFactory(s);
				ArrayList<String> cases = new ArrayList<>();
				fac.startRecording();
				String txt;
				while(!(txt = scanner.nextLine()).equals("quit")){
					LaunchpadPattern p = fac.stopRecording();
					System.out.println("public static final int[][] NUMBER_"+txt.toUpperCase()+" = "+p.toString()+";");
					cases.add("case '"+txt+"': return new LaunchpadPattern(NUMBER_"+txt.toUpperCase()+");");
					fac = new LaunchpadPatternFactory(s);
					fac.startRecording();
				}
				fac.stopRecording();
				s.reset();
				System.out.println("-------------");
				for (String s : cases) {
					System.out.println(s);
				}
				scanner.close();
			}
			if(what == 6){
				System.in.read();
			}
			if(what == 7){
				LaunchpadPatternFactory fac = new LaunchpadPatternFactory(s);
				fac.startRecording();
				System.in.read();
				LaunchpadPattern pat = fac.stopRecording();
				s.reset();
				Thread.sleep(1000);
				s.showPattern(pat);
				Thread.sleep(1000);
			}
			s.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onButtonDown(int row, int colomn) {
		if(what == 6)
			s.setLedOn(colomn, row, Launchpad.COLOUR_RED_FULL);
		if(what == 1)
			s.showPattern(patt);
	}

	@Override
	public void onButtonUp(int row, int colomn) {
		if(what == 1)
			s.reset();
		if(what == 6)
			s.setLedOff(colomn, row);
	}
	
	public static void main(String[] args) {
		new LaunchTester();
	}
	
}
