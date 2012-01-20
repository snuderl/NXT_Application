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

	public void run() {
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
			try {
				String input = dataIn.readUTF();
				Packet pack = new Packet(input);
				System.out.println(pack.command);
				synchronized (pilot) {
					pilot.execute(pack);
				}
				//System.out.println(pack.packet);

			} catch (Exception e) {
				System.out.println("Exception in receiving parameters!");
			}
		}

	}

	public BluetoothConnection(Nxt_slave p) {
		pilot = p;
	}
}