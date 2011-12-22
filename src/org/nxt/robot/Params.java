/**
 * 
 */
package org.nxt.robot;

import java.io.IOException;

import lejos.util.PilotProps;

/**
 * @author Blaž Šnuderl
 * 
 */
public class Params {

	public void Props() {
		// Change this to match your robot
		PilotProps p = new PilotProps();
		p.setProperty(PilotProps.KEY_WHEELDIAMETER, "5.6");
		p.setProperty(PilotProps.KEY_TRACKWIDTH, "16.0");
		p.setProperty(PilotProps.KEY_LEFTMOTOR, "A");
		p.setProperty(PilotProps.KEY_RIGHTMOTOR, "C");
		p.setProperty(PilotProps.KEY_REVERSE, "true");


		try {
			p.storePersistentValues();
		} catch (IOException e) {
			System.out.println("Unable to store persistent values.");
		}

	}

}
