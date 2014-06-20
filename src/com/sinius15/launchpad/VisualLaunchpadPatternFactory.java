package com.sinius15.launchpad;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class VisualLaunchpadPatternFactory extends JPanel implements LaunchListener{

	//we need this because we extend JPanel
	private static final long serialVersionUID = -6516730926896670057L;
	
	//the launchpad
	private Launchpad l;
	// this is the actual number stored in the data array. -1=off,
	// velocity=colour codes
	private int selectedColour = -1;
	private LaunchpadPattern pattern;
	
	/**
	 * Creates a new VisualLaunchpadPatternFactory.
	 * You are going to edit a empty launchpad pattern.
	 * 
	 * @param launchpad the launchpad.
	 * @author Sinius15
	 * @wbp.parser.constructor
	 */
	public VisualLaunchpadPatternFactory(Launchpad launchpad) {
		this.l = launchpad;
		this.pattern = new LaunchpadPattern();
		
	}
	
	/**
	 * Creates a new VisualLaunchpadPatternFactory. 
	 * You are going to edit the launchpadPattern givven in the argument.
	 * 
	 * @param launchpad the launchpad.
	 * @param pat the LaunchpadPattern you are going to edit.
	 * @author Sinius15
	 */
	public VisualLaunchpadPatternFactory(Launchpad launchpad, LaunchpadPattern pat) {
		this.l = launchpad;
		this.pattern = pat;
	}
	
	/**
	 * This starts the recording of a new pattern. First it resets the
	 * launchpad, than it adds a new listener to the launchpad.<b> Make sure
	 * that other code is not changing the state of the lights!</b> If they do
	 * than the visual feedback will break.<br>
	 * <br>
	 * 
	 * Calling this class wil also initialize the JPanel. To show the JPanel you
	 * have to add this class to a container like a JFrame. In this pannel you 
	 * can select the color. 
	 * 
	 * @author Sinius15
	 */
	public void startRecording() {
		//set lanchpad stuff
		l.reset();
		l.addListener(this);
		
		//set layout and size
		setLayout(null);
		setBounds(0, 0, 220, 220);
		setSize(220, 200);
		setPreferredSize(new Dimension(220, 220));
		
		//add buttons
		for (int green = 0; green < 4; green++) {
			for (int red = 0; red < 4; red++) {
				int lpColor = Launchpad.calculateColour(green, red);
				addButton(lpColor, Launchpad.lpColorToRGB(green, red)).setBounds(green*50+2, red*50+2, 50, 50);
			}
		}
		
		l.showPattern(pattern);
	}
	
	private ButtonGroup butGroup = new ButtonGroup();
	
	/**
	 * adds a butotn to the panel
	 * @param lpColor the color on the launchpad
	 * @param rgbColor the color on the screen
	 */
	private JToggleButton addButton(int lpColor, Color rgbColor){
		JToggleButton b = new JToggleButton("");
		butGroup.add(b);
		
		b.setBorder(BorderFactory.createEmptyBorder());
		
		b.setBackground(rgbColor);
		b.setForeground(rgbColor);
		
		b.setContentAreaFilled(false);
		b.setOpaque(true);
		
		b.addActionListener(new ActionListener() {
			private int lpColor;
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedColour = this.lpColor;
			}
			public ActionListener setCol(int col){
				this.lpColor = col;
				return this;
			}
		}.setCol(lpColor));
		this.add(b);
		return b;
	}
	
	/**
	 * This stops the recording. This does nothing to the JPanel.
	 * You have to remove/dispose the JPaenl by yourself!
	 * @return the recorded pattern.
	 * @author Sinius15
	 */
	public LaunchpadPattern stopRecording() {
		l.removeListener(this);
		return pattern;
	}
	
	/**
	 * DO NOT CALL! THIS IS A IMPLEMENTATION!
	 * 
	 * @author Sinius15
	 */
	@Override
	public void onButtonDown(int row, int colomn) {
		if (selectedColour == -1) {
			l.setLedOff(colomn, row);
			pattern.data[colomn][row] = selectedColour;
		} else {
			l.setLedOn(colomn, row, selectedColour);
			pattern.data[colomn][row] = selectedColour;
		}
	}

	/**
	 * Nothing to do here
	 * 
	 * @author Sinius15
	 */
	@Override
	public void onButtonUp(int row, int colomn) {}
	
}
