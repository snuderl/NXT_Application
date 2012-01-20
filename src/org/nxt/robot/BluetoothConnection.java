package org.nxt.robot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import lejos.nxt.Sound;

public class BluetoothConnection extends Thread implements NXT_Commands {
	protected Nxt_slave pilot;
	protected DataInputStream dataIn;
	protected DataOutputStream dataOut;
	protected String _command = "no command";
	protected float _param1 = 0;
	protected float _param2 = 0;
	protected String _morseString = "no input";
	
	public void run(){
		connect();
	}
	
	protected void connect() {
		BTConnection connection;
		System.out.println("Waiting for connection...");
		connection = Bluetooth.waitForConnection(0, NXTConnection.RAW);
		dataIn = connection.openDataInputStream();
		dataOut = connection.openDataOutputStream();
		Sound.beep();
		System.out.println("Connected");

		while (true) {
			synchronized (pilot) { // synchronized block
				pilot._command = _command;
				pilot._param1 = _param1;
				pilot._param2 = _param2;
				pilot._morseString = _morseString;
			}
		}

	}
	
	protected void recieve(){
		try {
			String input = dataIn.readUTF();
			Packet pack = new Packet(input);
			_command = pack.getCommand();
			String[] params = pack.getValues();
			if(params.length>=1){
				_param1 = Float.parseFloat(params[0]);
				if(params.length==2){
					_param2 = Float.parseFloat(params[1]);
				}
			}
//			_command = dataIn.readInt();
//			if (_command == FORWARD || _command == STEER || _command == ARC
//					|| _command == BACKWARD) {
//				_param1 = dataIn.readFloat();
//			}
//			if (_command == STEER || _command == ARC) {
//				_param2 = dataIn.readFloat();
//			}
//			if (_command == MORSE) {
//				_morseString = dataIn.readUTF();
//			}
		} catch (Exception e) {
			System.out.println("Exception in receiving parameters!");
		}
	}

	public BluetoothConnection(Nxt_slave p) {
		pilot = p;
	}
}