package com.game.main;

import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;

public class Statistics {
	private DecimalFormat df = new DecimalFormat("0.##");
	private DecimalFormat timedf = new DecimalFormat("0.####");

	// Statistics values
	private int timeSpentInGame = 0;
	private long gameStartTime;
	private long period;

	private static long MAX_STATS_INTERVAL = 1000_000_000L; // record statistics every second
	private long statsInterval = 0L;
	private long prevStatsTime;
	private long totalElapsedTime = 0L;
	private long statsCount = 0;

	private static int NUM_FPS = 10; // number of FPS stored to get average
	private long frameCount = 0;
	private double averageFPS = 0.0;
	private double[] fpsStore;

	private long framesSkipped = 0L;
	private long totalFramesSkipped = 0L;
	private double averageUPS = 0.0;
	private double[] upsStore;
	
	public Statistics(long startTime, long FramePeriod) {
		gameStartTime = startTime;
		prevStatsTime = gameStartTime;
		period = FramePeriod;

		// initialize timing elements
		fpsStore = new double[NUM_FPS];
		upsStore = new double[NUM_FPS];
		for(int i=0; i<NUM_FPS; i++) {
			fpsStore[i] = 0.0;
			upsStore[i] = 0.0;
		}
	}

	public void addSkippedFrames(int skips) {
		framesSkipped += skips;
	}

	public void storeStats(boolean showBeforeStore) {
		frameCount++; // storeStats is called after one frame
		statsInterval += period; // (ms -> ns) accumulate until threshold
		
		if(statsInterval >= MAX_STATS_INTERVAL) {
			long timeNow = System.nanoTime();
			timeSpentInGame = (int) ((timeNow - gameStartTime)/1000_000_000L); // seconds

			long realElapsedTime = timeNow - prevStatsTime; // time since last collection
			totalElapsedTime += realElapsedTime;
			double timingError = ((double) (realElapsedTime - statsInterval) / statsInterval) * 100.0;
			totalFramesSkipped += framesSkipped;

			double actualFPS = 0;
			double actualUPS = 0;
			if(totalElapsedTime > 0) {
				actualFPS = ((double) frameCount / totalElapsedTime) * 1000_000_000L;
				actualUPS = (((double) frameCount+totalFramesSkipped) / totalElapsedTime) * 1000_000_000L;
			}
			
			fpsStore[(int) statsCount % NUM_FPS] = actualFPS;
			upsStore[(int) statsCount % NUM_FPS] = actualUPS;
			statsCount++;
			
			double totalFPS = 0.0;
			double totalUPS = 0.0;
			for(int i=0; i < NUM_FPS; i++) {
				totalFPS += fpsStore[i];
				totalUPS += upsStore[i];
			}
			
			if(statsCount < NUM_FPS) {
				averageFPS = totalFPS/statsCount;
				averageUPS = totalUPS/statsCount;
			}
			else {
				averageFPS = totalFPS/NUM_FPS;
				averageUPS = totalUPS/NUM_FPS;
			}
			
			if(showBeforeStore) {
				System.out.println(
					timedf.format((double) statsInterval/1000_000_000L) + " " + 		// time since last output
					timedf.format((double) realElapsedTime/1000_000_000L) + "s " +		// real time since last
					df.format(timingError) + "% " +										// percentage error
					frameCount + "c " +
					framesSkipped + "/" + totalFramesSkipped + " skip; " +
					df.format(actualFPS) + " " + df.format(averageFPS) + " afps; " +
					df.format(actualUPS) + " " + df.format(averageUPS) + " aups");
			}

			framesSkipped = 0;
			prevStatsTime = timeNow;
			statsInterval = 0L;  // reset
		}
	}
	
	public void displayCurrentStats(Graphics gSrc, int x, int y) {
		gSrc.setColor(Color.BLUE);
		String stat = "Time: " + timeSpent() + " sec";
		stat += ", Average FPS/UPS: " + df.format(averageFPS) + "/" + df.format(averageUPS);
		gSrc.drawString(stat, x, y);
	}
	
	public void printStats() {
		System.out.println("Frame Count/Loss: " + frameCount + " / " + totalFramesSkipped);
		System.out.println("Average FPS: " + df.format(averageFPS));
		System.out.println("Average UPS: " + df.format(averageUPS));
		System.out.println("Time Spent: " + timeSpentInGame + " sec");
	}

	public int timeSpent() {
		return timeSpentInGame;
	}

}
