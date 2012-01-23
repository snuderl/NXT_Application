package org.nxt.robot;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import lejos.nxt.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.PilotProps;

public class Nxt_slave implements NXT_Commands {

	public Nxt_slave(DifferentialPilot dPilot) {
		pilot = dPilot;
	}

	protected boolean _immediate = true;
	protected DifferentialPilot pilot;
	protected int clawAngle = 45;
	protected static boolean connectionLost = false;
	protected static BluetoothConnection btConnection;
	protected static Thread btThread;
	protected static Timer connectionCheckTimer;
	

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		PilotProps pp = new PilotProps();
		pp.loadPersistentValues();
		float wheelDiameter = Float.parseFloat(pp.getProperty(
				PilotProps.KEY_WHEELDIAMETER, "2.2"));
		float trackWidth = Float.parseFloat(pp.getProperty(
				PilotProps.KEY_TRACKWIDTH, "5.2"));
		RegulatedMotor leftMotor = PilotProps.getMotor(pp.getProperty(
				PilotProps.KEY_LEFTMOTOR, "A"));
		RegulatedMotor rightMotor = PilotProps.getMotor(pp.getProperty(
				PilotProps.KEY_RIGHTMOTOR, "C"));
		boolean reverse = Boolean.parseBoolean(pp.getProperty(
				PilotProps.KEY_REVERSE, "false"));
		DifferentialPilot dp = new DifferentialPilot(wheelDiameter, trackWidth,
				leftMotor, rightMotor, reverse);
		Nxt_slave pilot = new Nxt_slave(dp);
		btConnection = new BluetoothConnection(pilot);
		btThread = new Thread(btConnection);
		btThread.start();
		connectionCheckTimer = new Timer();
		connectionCheckTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("Checking connection...");
				if(connectionLost){
					System.out.println("Connection lost!");
					btThread = new Thread(btConnection);
					btThread.start();
				}
				else{
					connectionLost=true;
					System.out.println("Connection OK!");
				}
				System.out.println("Exiting check!");
					

			}
		}, 1000, 5000);
	}

	protected void execute(Packet p) {
		connectionLost = false;
		String _command = p.command;
		System.out.println("com: " + _command);
		if (_command.equals(STOP)) {
			pilot.stop();
		} else if (_command.equals(STEER)) {
			float speed = Float.parseFloat(p.values[0]);
			float steer = Float.parseFloat(p.values[1]);

			pilot.setTravelSpeed(speed);
			System.out.println(steer);
			if (speed < 0) {
				pilot.steerBackward(steer);
			} else {
				pilot.steer(steer);
			}
		} else if (_command.equals(MORSE)) {
			MorsePlayer player = MorsePlayer.getPlayer();
			player.playMorse(p.values[0]);
		} else if (_command.equals(CLAWS)) {
			//System.out.println("Activating claws!");
			Motor.B.rotateTo(-clawAngle);
			Motor.B.rotateTo(clawAngle);
		}

	}
	// protected void send(int data) throws IOException {
	// dataOut.writeInt(data);
	// }
	//
	// protected void recieve() throws IOException {
	//
	// _command = dataIn.readInt();
	// if(_command==FORWARD||_command==STEER||_command==ARC ||
	// _command==BACKWARD){
	// _param1 = dataIn.readFloat();
	// }
	// if(_command==ARC||_command==STEER){
	// _param2 = dataIn.readFloat();
	// }
	// //_immediate = dataIn.readBoolean();
	// }
}

