package org.nxt.robot;

import java.util.ArrayList;

import javax.microedition.lcdui.List;

/*
 * Da prebereš is stream uporabiš inputstream.readUTF();
 * To ti vrne string. Ta string vstaviš v paketek Packet p = new Packet(string)
 * Pol pa maš metodo p.getCommand al pa p.command ki ti vrne strign kira komanda je
 * Druga metoda p.values pa vrne array vseh parametrov.
 * Pošiljat pa za enkrat tak nerabiš še nič:)
 */

class Packet {


	final String packet;
	final String command;
	final String[] values;

	public String[] split(String s, String exp) {
		ArrayList<String> list = new ArrayList<String>();
		if(s.indexOf(exp)==-1){
			return new String[]{s};
		}
		while (s.indexOf(exp) >= 0) {
			int index = s.indexOf(exp);
			if (index == 0) {
				s = s.substring(2);
			} else {
				list.add(s.substring(0, index));
				s = s.substring(index+2);
			}
		}
		list.add(s);
		String[] polje = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			polje[i] = list.get(i);
		}
		return polje;
	}

	public Packet(String packet) {
		this.packet = packet;
		String[] polje = split(packet, "||");
		command = polje[0];
		values = split(polje[1], "::");
	}

	public String getCommand() {
		return command;
	}

	public String[] getValues() {
		return values;
	}
}
