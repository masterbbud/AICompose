package main;

import java.util.ArrayList;

public class Chord {

	public Note root;
	public accidental rootacc;
	public quality q;
	public int inv;
	
	public Chord(Note r, quality q, int inv) {
		this.root = r;
		this.q = q;
		this.inv = inv;
	}
	public Chord(Note r, quality q) {
		this.root = r;
		this.q = q;
		this.inv = 0;
	}
	public Chord (ArrayList<Note> n) {
		//unfinished
		initFromNotes(n);
	}
	public void initFromNotes(ArrayList<Note> n) {
		if (n.size() == 3) {
			initTriad(n);
		}
	}
	public void initTriad(ArrayList<Note> n) {
		int i1 = Launcher.semiBetween(n.get(0),n.get(1));
		int i2 = Launcher.semiBetween(n.get(1),n.get(2));
		if (i1 == 4 && i2 == 3) {
			this.root = n.get(0);
			this.q = quality.major;
			this.inv = 0;
		}
		else if (i1 == 3 && i2 == 7) {
			this.root = n.get(2);
			this.q = quality.major;
			this.inv = 1;
		}
		else if (i1 == 7 && i2 == 4) {
			this.root = n.get(1);
			this.q = quality.major;
			this.inv = 2;
		}
		else if (i1 == 3 && i2 == 4) {
			this.root = n.get(0);
			this.q = quality.minor;
			this.inv = 0;
		}
		else if (i1 == 4 && i2 == 7) {
			this.root = n.get(2);
			this.q = quality.minor;
			this.inv = 1;
		}
		else if (i1 == 7 && i2 == 3) {
			this.root = n.get(1);
			this.q = quality.minor;
			this.inv = 2;
		}
	}
	public ArrayList<Note> notes() {
		// unfinished
		ArrayList<Note> n = new ArrayList<Note>();
		if (q == quality.major) {
			if (inv == 0) {
				n.add(root);
				n.add(Launcher.getNoteIntervalFromNote(root,"M3",true));
				n.add(Launcher.getNoteIntervalFromNote(root,"P5",true));
			}
			else if (inv == 1) {
				n.add(Launcher.getNoteIntervalFromNote(root,"M3",true));
				n.add(Launcher.getNoteIntervalFromNote(root,"P5",true));
				n.add(root);
			}
			else if (inv == 2) {
				n.add(Launcher.getNoteIntervalFromNote(root,"P5",true));
				n.add(root);
				n.add(Launcher.getNoteIntervalFromNote(root,"M3",true));
			}
		}
		else if (q == quality.minor){
			if (inv == 0) {
				n.add(root);
				n.add(Launcher.getNoteIntervalFromNote(root,"m3",true));
				n.add(Launcher.getNoteIntervalFromNote(root,"P5",true));
			}
			else if (inv == 1) {
				n.add(Launcher.getNoteIntervalFromNote(root,"m3",true));
				n.add(Launcher.getNoteIntervalFromNote(root,"P5",true));
				n.add(root);
			}
			else if (inv == 2) {
				n.add(Launcher.getNoteIntervalFromNote(root,"P5",true));
				n.add(root);
				n.add(Launcher.getNoteIntervalFromNote(root,"m3",true));
			}
		}
		return n;
	}
	public ArrayList<Integer> intervals() {
		//first interval is the semitones between the root and the first note of the chord, so forth
		ArrayList<Integer> i = new ArrayList<Integer>();
		if (q == quality.major) {
			i.add(0);
			i.add(4);
			i.add(7);
		}
		else if (q == quality.minor) {
			i.add(0);
			i.add(3);
			i.add(7);
		}
		else if (q == quality.augmented) {
			i.add(0);
			i.add(4);
			i.add(8);
		}
		else if (q == quality.diminished) {
			i.add(0);
			i.add(3);
			i.add(6);
		}
		else if (q == quality.major7) {
			i.add(0);
			i.add(4);
			i.add(7);
			i.add(11);
		}
		else if (q == quality.minor7) {
			i.add(0);
			i.add(3);
			i.add(7);
			i.add(10);
		}
		else if (q == quality.dominant7) {
			i.add(0);
			i.add(4);
			i.add(7);
			i.add(10);
		}
		else if (q == quality.halfdiminished7) {
			i.add(0);
			i.add(3);
			i.add(6);
			i.add(10);
		}
		else if (q == quality.fulldiminished7) {
			i.add(0);
			i.add(3);
			i.add(6);
			i.add(9);
		}
		for (int a = 0; a < inv; a++) {
			int og = i.get(0);
			i.remove(0);
			i.add(og+12);
		}
		return i;
	}
}
