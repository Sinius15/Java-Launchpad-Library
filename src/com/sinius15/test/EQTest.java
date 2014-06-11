package com.sinius15.test;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import com.sinius15.launchpad.Launchpad;

public class EQTest {
	
	static Launchpad pad;
	//[row][col]
	static boolean[][] grid = new boolean[8][8];
	static SourceDataLine line;
	static AudioInputStream stream;
	
	public static void main(String[] argv) throws Exception {
		
		pad = new Launchpad("Launchpad S");
		pad.open();
		
		stream = AudioSystem.getAudioInputStream(new File("C:\\Users\\Sinius\\Desktop\\test\\song.wav"));
		
		AudioFormat format = stream.getFormat();
		if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
			format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(),
					format.getSampleSizeInBits() * 2, format.getChannels(),
					format.getFrameSize() * 2, format.getFrameRate(), true);
			stream = AudioSystem.getAudioInputStream(format, stream);
		}
		
		SourceDataLine.Info info = new DataLine.Info(SourceDataLine.class, stream.getFormat(),
				((int) stream.getFrameLength() * format.getFrameSize()));
		line = (SourceDataLine) AudioSystem.getLine(info);
		line.open(stream.getFormat());
		line.start();
		
		Thread player = new Thread(new Runnable() {
			
			@Override
			public void run() {
				int numRead = 0;
				byte[] buf = new byte[line.getBufferSize()/1000];
				try {
					while ((numRead = stream.read(buf, 0, buf.length)) >= 0) {
						int offset = 0;
						while (offset < numRead) {
							offset += line.write(buf, offset, numRead - offset);;
						}
						int avg = Math.abs(getAvg(buf));
						moveRight(grid);
						for(int i = 0; i <= (avg/10-1); i++)
							grid[i][0] = true;
						
					}
				} catch (IOException e) {
					e.printStackTrace();
				}	
				line.drain();
				line.stop();
				pad.close();
				System.exit(0);
			}
		});
		player.start();
		
		Thread displayer = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					draw();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		displayer.start();
		
		
	}
	
	public static void moveRight(boolean[][] in){
		for(int row = 0; row<8; row++)
			for(int col = 7; col>0; col--)
				in[row][col] = in[row][col-1];
		for(int i = 0; i < 8; i++)
			in[i][0] = false;
	}
	
	public static void draw(){
		pad.reset();
		for(int col = 0; col<8; col++){
			for(int row = 0; row<8; row++){
				if(grid[row][col]){
					pad.setLedOn(col, row+1, Launchpad.COLOUR_GREEN_FULL);
				}
			}
		}
	}
	
	public static int getAvg(byte[] in){
		int tot = 0;
		for(byte b : in)
			tot+= b;
		return tot/in.length;
	}
	
}
