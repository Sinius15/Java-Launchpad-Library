package com.sinius15.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import com.sinius15.launchpad.LaunchListener;
import com.sinius15.launchpad.Launchpad;
import com.sinius15.launchpad.LaunchpadPattern;
import com.sinius15.launchpad.LaunchpadPatternFactory;

public class LaunchTester implements LaunchListener{

	public static String name = "Launchpad S";
	Launchpad s;
	LaunchpadPattern patt;
	int what = 5;
	
	public LaunchTester(){
		try {
			s = new Launchpad(name);
			s.addListener(this);
			s.open();
			if(what == 0){
				LaunchpadPatternFactory fac = new LaunchpadPatternFactory(s);
				fac.startRecording();
				
				Scanner scanner = new Scanner(System.in);
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

					System.out.println("case '"+c+"': return new LaunchpadPattern(LETTER_CAPITAL_"+(""+c).toUpperCase()+");");
				}
			}
			if(what == 4){
				s.showText("Java Launchpad Library (JLPL)".toUpperCase(), 500, Launchpad.COLOUR_GREEN_FULL);
			}
			if(what == 5){
				Scanner scanner = new Scanner(System.in);
				LaunchpadPatternFactory fac = new LaunchpadPatternFactory(s);
				ArrayList<String> cases = new ArrayList<>();
				fac.startRecording();
				String txt;
				while(!(txt = scanner.nextLine()).equals("quit")){
					LaunchpadPattern p = fac.stopRecording();
					System.out.println("public static final int[][] LETTER_SMALL_"+txt.toUpperCase()+" = "+p.toString()+";");
					cases.add("case '"+txt+"': return new LaunchpadPattern(LETTER_SMALL_"+txt.toUpperCase()+");");
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

			s.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onButtonDown(int row, int colomn) {
		//s.setLedOn(colomn, row, Launchpad.COLOUR_AMBER_FULL);
		if(what == 1)
			s.showPattern(patt);
	}

	@Override
	public void onButtonUp(int row, int colomn) {
		if(what == 1)
			s.reset();
		//s.setLedOff(colomn, row);
	}
	
	public static void main(String[] args) {
		new LaunchTester();
	}
	
}
