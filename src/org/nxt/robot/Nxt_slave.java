package org.nxt.robot;

import java.io.IOException;
import lejos.nxt.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.PilotProps;

public class Nxt_slave implements NXT_Commands {


	public Nxt_slave(DifferentialPilot dPilot) {
		pilot = dPilot;
	}

	//protected DataInputStream dataIn;
	//protected DataOutputStream dataOut;
	protected String _command = "no command";
	protected float _param1 = 0;
	protected float _param2 = 0;
	protected boolean _immediate = true;
	protected String _morseString = "no input";
	protected DifferentialPilot pilot;
	protected int clawAngle = 45;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		PilotProps pp = new PilotProps();
    	pp.loadPersistentValues();
    	float wheelDiameter = Float.parseFloat(pp.getProperty(PilotProps.KEY_WHEELDIAMETER, "2.2"));
    	float trackWidth = Float.parseFloat(pp.getProperty(PilotProps.KEY_TRACKWIDTH, "5.2"));
    	RegulatedMotor leftMotor = PilotProps.getMotor(pp.getProperty(PilotProps.KEY_LEFTMOTOR, "A"));
    	RegulatedMotor rightMotor = PilotProps.getMotor(pp.getProperty(PilotProps.KEY_RIGHTMOTOR, "C"));
    	boolean reverse = Boolean.parseBoolean(pp.getProperty(PilotProps.KEY_REVERSE,"false"));
    	DifferentialPilot dp = new DifferentialPilot(wheelDiameter, trackWidth, leftMotor, rightMotor, reverse);
    	Nxt_slave pilot = new Nxt_slave(dp);
    	BluetoothConnection btConnection = new BluetoothConnection(pilot);
    	Thread btThread = new Thread(btConnection);
    	btThread.start();
    	while (true)
        {
    		pilot.execute();
        }
    	

	}

//	protected void connect() {
//		BTConnection connection;
//		boolean loop = true;
//		System.out.println("Waiting for connection...");
//		connection = Bluetooth.waitForConnection(0, NXTConnection.RAW);
//		dataIn = connection.openDataInputStream();
//		dataOut = connection.openDataOutputStream();
//		Sound.beep();
//		System.out.println("Connected");
//
//		while (loop) {
//			execute();
//
//		}	
//	}
	
	protected void execute(){

		if (_command == FORWARD) {
			pilot.travel(_param1);
			System.out.println("C: " + _command);
			System.out.println("p1: " + _param1);
		} else if (_command == BACKWARD) {
			pilot.setTravelSpeed(_param1);
			pilot.backward();
			System.out.println("C: " + _command);
			System.out.println("p1: " + _param1);
		} else if (_command == STOP) {
			pilot.stop();
			System.out.println("C: " + _command);

		} else if (_command == ARC) {
			pilot.arc(_param1, _param2, _immediate);
			System.out.println("C: " + _command);
			System.out.println("p1: " + _param1);
			System.out.println("p2: " + _param2);

		} else if (_command == STEER) {
			pilot.steer(_param1, _param2, _immediate);
			System.out.println("C: " + _command);
			System.out.println("p1: " + _param1);
			System.out.println("p2: " + _param2);
		} else if (_command == MORSE) {
			//MorsePlayer player = MorsePlayer.getPlayer();
			///player.playMorse(_morseString);
			System.out.println("morse: " + _morseString);
		}
		else if (_command == CLAWS){
			System.out.println("Activating claws!");
			Motor.B.rotateTo(clawAngle);
			Motor.B.rotateTo(-clawAngle);
		}

	}
//	protected void send(int data) throws IOException {
//		dataOut.writeInt(data);
//	}
//
//	protected void recieve() throws IOException {
//
//		_command = dataIn.readInt();
//		if(_command==FORWARD||_command==STEER||_command==ARC || _command==BACKWARD){
//			_param1 = dataIn.readFloat();
//		}
//		if(_command==ARC||_command==STEER){
//			_param2 = dataIn.readFloat();
//        }
//        //_immediate = dataIn.readBoolean();
//	}
}

