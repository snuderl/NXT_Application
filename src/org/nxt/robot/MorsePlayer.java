/**
 * 
 */
package org.nxt.robot;

import lejos.nxt.Sound;

/**
 * @author Blaž Šnuderl
 * 
 */
public class MorsePlayer {
	public final int shortTime = 500;
	public final int longTime = 1000;

	private MorsePlayer() {

	}

	private static MorsePlayer player = null;

	public static MorsePlayer getPlayer() {
		if (player == null)
			player = new MorsePlayer();
		return player;
	}

	public void playMorse(String s) {
		Player p = new Player(s);
		p.run();
	}

	private class Player extends Thread {
		String text;

		public Player(String code) {
			text = code;
		}

		public void run() {
			for (int i = 0; i < text.length(); i++) {
				int duration = text.charAt(i) == '.' ? shortTime : longTime;
				Sound.playTone(4000, duration);
				try {
					Thread.sleep(duration);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
