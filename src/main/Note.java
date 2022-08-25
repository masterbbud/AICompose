package main;

public class Note {

	public int value; // 0 = c0, 7 = c1, 14 = c2 etc.
	public char name;
	public int octave;
	public accidental acc;
	public float length;
	
	public Note(int v, accidental a, double l) {
		this.value = v;
		this.acc = a;
		this.length = (float)l;
		System.out.println(l);
		initLong(v);
	}
	public Note(char n, int o, accidental a, double l) {
		this.name = n;
		this.octave = o;
		this.acc = a;
		this.length = (float)l;
		System.out.println(l);
		initValue(n,o);
	}
	public Note(int v, accidental a) {
		this.value = v;
		this.acc = a;
		this.length = 1;
		initLong(v);
	}
	public Note(char n, accidental a) {
		this.name = n;
		this.octave = 1;
		this.acc = a;
		this.length = 1;
		initValue(n,1);
	}
	public Note(int v) {
		this.value = v;
		this.acc = accidental.natural;
		this.length = 1;
		initLong(v);
	}
	public Note(char n) {
		this.name = n;
		this.octave = 1;
		this.acc = accidental.natural;
		this.length = 1;
		initValue(n,1);
	}
	public void initLong(int v) {
		if (value % 7 == 0) {
			name = 'c';
		}
		else if (value % 7 == 1) {
			name = 'd';
		}
		else if (value % 7 == 2) {
			name = 'e';
		}
		else if (value % 7 == 3) {
			name = 'f';
		}
		else if (value % 7 == 4) {
			name = 'g';
		}
		else if (value % 7 == 5) {
			name = 'a';
		}
		else if (value % 7 == 6) {
			name = 'b';
		}
		octave = (int)Math.floor(value / 7);
	}
	public void initValue(char n, int o) {
		value = o * 7;
		if (name == 'c') {
			value += 0;
		}
		else if (name == 'd') {
			value += 1;
		}
		else if (name == 'e') {
			value += 2;
		}
		else if (name == 'f') {
			value += 3;
		}
		else if (name == 'g') {
			value += 4;
		}
		else if (name == 'a') {
			value += 5;
		}
		else if (name == 'b') {
			value += 6;
		}
	}
}
