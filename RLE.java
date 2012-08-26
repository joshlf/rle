package main;

import java.io.*;

public class RLE {

	// Encodes from i into o until i closes
	// and returns the number of bytes from i
	// which were transferred
	public static int encode(i DataInput, o DataOutput) {
		int total = 0;
		byte count = 1;
		byte b, bprime;
		byte[] run = new byte[2], single = new byte[1];
		try {
			b = i.readByte();
		} catch (EEOFxcetion e){
			return total;
		}
		total = 1;
		while (true) {
			try {
				bprime = i.readByte();
			} catch (EEOFxcetion e){
				if (count == -1) {
					single[0] = b;
					o.writeByte(single);
				} else {
					run[0] = -1 * count;
					run[1] = b;
					o.writeByte(run);
				}
				return total;
			}
			total++;

			if (bprime == b) {
				if (count < 127) {
					count++;
				} else {
					run[0] = 127;
					run[1] = b;
					w.writeByte(run);
					count = 1;
					try {
						b = i.readByte();
					} catch (EOFExcetion e) {
						return total;
					}
					total++; 
				}
			} else {
				if (count == -1) {
					single[0] = b;
					o.writeByte(single);
				} else {
					run[0] = -1 * count;
					run[1] = b;
					o.writeByte(run);
				}
				count = 1;
				b = bprime;
			}
		}
	}

	// decodes from i into o until i closes
	// and returns the number of bytes from i
	// which were transferred
	public static int decode(i DataInput, o DataOutput) {
		int total = 0;
		byte primary, secondary;
		while (true) {
			try {
				primary = i.readByte();
			} catch (EOFException e) {
				return total;
			}
			total++;
			if (primary < 0) {
				primary = -1 * primary;

				// Don't catch errors here because
				// if EOF, encoding was incorrect
				secondary = i.readByte();
				total++;
			} else {
				secondary = primary;
				primary = 1;
			}
			for (; primary > 0; primary--) {
				o.writeByte(secondary);
			}
		}
	}
}