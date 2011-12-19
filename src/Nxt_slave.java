import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import lejos.nxt.Sound;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

/**
 * 
 */

/**
 * @author Bla� �nuderl
 * 
 */
public class Nxt_slave {

	protected DataInputStream dataIn;
	protected DataOutputStream dataOut;

	int recived = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	protected void connect() {
		BTConnection connection;
		boolean loop = true;
		System.out.println("Waiting for connection...");
		connection = Bluetooth.waitForConnection();

		dataIn = connection.openDataInputStream();
		dataOut = connection.openDataOutputStream();
		Sound.beep();
		System.out.println("Connected");

		while (loop) {
			try {
				recieve();
				send(-recived);
				if (recived == 111) {
					loop=false;
				}
			} catch (Exception e) {
				System.out.println("Error in recieve.");
			}
		}

	}

	protected void send(int data) throws IOException {
		dataOut.writeInt(data);
	}

	protected void recieve() throws IOException {
		recived = dataIn.readInt();
	}
}