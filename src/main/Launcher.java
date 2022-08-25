package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;
import processing.sound.SinOsc;
import processing.sound.SoundFile;



public class Launcher extends PApplet {

	//have it generate the entire file first as a single wave and then play it
	// implied chord is very rudamentary right now
	// simple place to start would be with cadences
	
	//make a new class called a weight that represents a scale degree and a weight, that is used to generate the random sequence	
	
	// 1-7
	
	public ArrayList<Part> parts = new ArrayList<Part>();
	public ArrayList<Float> beatWidths = new ArrayList<Float>();
	public ArrayList<Float> nextPlay = new ArrayList<Float>();
	public ArrayList<Float> volumes = new ArrayList<Float>();
	public ArrayList<Integer> currentPlay = new ArrayList<Integer>();
	
	public ArrayList<SinOsc> oscillators = new ArrayList<SinOsc>();
	
	public ArrayList<SoundFile> pianoNotes = new ArrayList<SoundFile>();
	
	public float time = 0; // in seconds
	public float bpm = 200;
	public boolean playing = false;
	public float decay = (float)0.9;
	
	public float startDrawingExtraUI;
	
	public static void main(String[] args) {
		System.out.println("-------------\nAI Composer\n-------------\n");
		PApplet.main("main.Launcher");
	}

	public void settings() {
		size(1440, 800,P2D);
	}

	public void setup() {
		/*parts.add(new Part("Treble",clef.treble,4,4,false,"Gm"));
		parts.get(0).notes.add(new Note('d',5,accidental.natural,0.75));
		parts.get(0).notes.add(new Note('e',5,accidental.flat,0.25));
		parts.get(0).notes.add(new Note('c',5,accidental.natural,0.75));
		parts.get(0).notes.add(new Note('d',5,accidental.natural,0.25));
		parts.get(0).notes.add(new Note('e',5,accidental.flat,0.75));
		parts.get(0).notes.add(new Note('e',5,accidental.flat,0.25));
		parts.get(0).notes.add(new Note('d',5,accidental.natural,0.75));
		parts.get(0).notes.add(new Note('e',5,accidental.flat,0.25));
		parts.get(0).notes.add(new Note('c',5,accidental.natural,0.75));
		parts.get(0).notes.add(new Note('a',4,accidental.natural,0.25));
		parts.get(0).notes.add(new Note('b',4,accidental.flat,0.75));
		parts.get(0).notes.add(new Note('c',5,accidental.natural,0.25));
		*/
		/*
		 * parts.get(0).notes.add(new Note('g',4,accidental.natural,2));
		parts.get(0).notes.add(new Note('e',4,accidental.flat,2));
		parts.get(0).notes.add(new Note('f',4,accidental.natural,2));
		parts.get(0).notes.add(new Note('b',3,accidental.flat,2));
		parts.get(0).notes.add(new Note('e',4,accidental.flat,2));
		parts.get(0).notes.add(new Note('c',4,accidental.natural,2));
		parts.get(0).notes.add(new Note('d',4,accidental.natural,1));
		parts.get(0).notes.add(new Note('d',3,accidental.natural,1));
		parts.get(0).notes.add(new Note('g',3,accidental.natural,2));
		 */
		
		parts.add(new Part("Bass",clef.bass,4,4,false,"CM"));
		parts.get(0).notes.add(new Note('c',3,accidental.natural,1));
		parts.get(0).notes.add(new Note('e',3,accidental.natural,1));
		parts.get(0).notes.add(new Note('g',3,accidental.natural,1));
		parts.get(0).notes.add(new Note('e',3,accidental.natural,1));
		parts.get(0).notes.add(new Note('f',3,accidental.natural,1));
		parts.get(0).notes.add(new Note('e',3,accidental.natural,1));
		parts.get(0).notes.add(new Note('d',3,accidental.natural,1));
		parts.get(0).notes.add(new Note('g',2,accidental.natural,1));
		parts.get(0).notes.add(new Note('c',3,accidental.natural,1));
		parts.get(0).notes.add(new Note('e',3,accidental.natural,1));
		parts.get(0).notes.add(new Note('g',3,accidental.natural,1));
		parts.get(0).notes.add(new Note('c',4,accidental.natural,1));
		parts.get(0).notes.add(new Note('f',3,accidental.natural,1));
		parts.get(0).notes.add(new Note('d',3,accidental.natural,1));
		parts.get(0).notes.add(new Note('c',3,accidental.natural,2));
		parts.get(0).notes.add(new Note('c',3,accidental.natural,4));
		
		parts.add(0,writeMelody(parts.get(0)));
		
		
		getBeatWidths();
		for (Part p : parts) {
			currentPlay.add(-1);
			oscillators.add(new SinOsc(this));
			nextPlay.add((float) -1);
			volumes.add((float) 1);
		}
		startDrawingExtraUI = 400 + 200 * parts.size();
	}
	public void draw() {
		background(220);
		for (int i = 0; i < parts.size(); i++) {
			parts.get(i).draw(this, 10, 200+200*i, 10, beatWidths, 6);
			
		}
		//oscillators.get(0).play();
		if (playing) {
			for (int i = 0; i < parts.size(); i++) {
				playPart(i);
				
				
			}
			time += 1 / frameRate;
		}
		
		drawExtraUI(startDrawingExtraUI);
		
		
	}
	public void drawExtraUI(float y) {
		//
	}
	public void printIntervals(Part p1, Part p2) {
		String full = "";
		for (int i = 0; i < p1.notes.size(); i++) {
			full += getInterval(p1.notes.get(i),p2.notes.get(i));
			full += " ";
		}
		System.out.println(full);
	}
	public void keyPressed() {
		startPlaying();
	}
	public void mousePressed() {
		
	}
	public void getBeatWidths() {
		for (Part p : parts) {
			int index = 0;
			float count = 0;
			int num = 0;
			for (int i = 0; i < p.notes.size(); i++) {
				Note n = p.notes.get(i);
				count += n.length;
				num ++;
				float bC = count;
				float bNum = num;
				while (count >= 1) {
					if (beatWidths.size() > index) {
						if (beatWidths.get(index) < (float)bNum / bC) {
							beatWidths.set(index, (float)bNum / bC);
						}
					}
					else {
						beatWidths.add((float)bNum / bC);
					}
					System.out.println((float)bNum / bC+" a");
					count -= 1;
					num = 0;
					index ++;
				}

			}
		}
	}
	public void startPlaying() {
		time = 0;
		playing = true;
		
	}
	public void playPart(Integer part) {
		oscillators.get(part).amp(volumes.get(part));
		volumes.set(part,volumes.get(part)*decay);
		if (time > nextPlay.get(part)) {
			if (currentPlay.get(part) < parts.get(part).notes.size()-1) {
				oscillators.get(part).freq(getFreqOfNote(parts.get(part).notes.get(currentPlay.get(part)+1))); // next note
				oscillators.get(part).amp(1);
				volumes.set(part, (float)1);
				//oscillators.get(part).freq(400);
				//System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA "+getFreqOfNote(parts.get(part).notes.get(currentPlay.get(part)+1)));
				if (!oscillators.get(part).isPlaying()) {
					oscillators.get(part).play();
				}
				//oscillators.get(part).play();
				currentPlay.set(part, currentPlay.get(part)+1);
				nextPlay.set(part, shouldChange(parts.get(part)));
			}
			else {
				oscillators.get(part).stop();
			}
		}
	}
	public float shouldChange(Part p) {
		//returns the time when you should next change the note of part P
		//basically finds the next Time after the current Time where the note changes
		
		// STILL NEEDS TO BE ADJUSTED FOR DIFFERENT TIME SIGNATURES
		
		float bTime = 0;
		for (Note n : p.notes) {
			bTime += n.length * (60 / bpm);
			if (bTime > time) {
				return bTime;
			}
		}
		return -1;
	}
	public float getFreqOfNote(Note n) {
		Integer s = getSemitonesFromA4(n.value, n.acc);
		
		float fr = 440;
		for (int i = 0; i < Math.abs(s); i++) {
			if (s > 0) {
				fr *= 1.059463;
			}
			else {
				fr /= 1.059463;
			}
		}
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAaSDDDDDDDDDDDDDDD "+fr);
		return fr;
	}
	public static int getSemitonesFromA4(int value, accidental acc) {
		// == A4 == 33
		// value % 7 : a = 5, b = 6, c = 0, d = 1, e = 2, f = 3, g = 4
		int unAdj = 0;
		if (value == 33) {
			unAdj = 0;
		}
		else if (value > 33) {
			for (int i = 34; i <= value; i++) {
				if (i % 7 == 0 || i % 7 == 3) {
					unAdj += 1;
				}
				else {
					unAdj += 2;
				}
			}
		}
		else {
			for (int i = 32; i >= value; i--) {
				if (i % 7 == 6 || i % 7 == 2) {
					unAdj -= 1;
				}
				else {
					unAdj -= 2;
				}
			}
		}
		if (acc == accidental.doublesharp) {
			unAdj += 2;
		}
		else if (acc == accidental.sharp) {
			unAdj += 1;
		}
		else if (acc == accidental.flat) {
			unAdj -= 1;
		}
		else if (acc == accidental.doubleflat) {
			unAdj -= 2;
		}
		return unAdj;
	}
	public static int semiBetween(Note n1, Note n2) {
		int s1 = getSemitonesFromA4(n1.value,n1.acc);
		int s2 = getSemitonesFromA4(n2.value,n2.acc);
		return (int)Math.abs(s1-s2);
	}
	public String getInterval(Note n1, Note n2) {
		int s = semiBetween(n1,n2);
		if (s == 0) {
			return "P1";
		}
		switch (s%12) {
		case 0:
			return "P8";
		case 1:
			return "m2";
		case 2:
			return "M2";
		case 3:
			return "m3";
		case 4:
			return "M3";
		case 5:
			return "P4";
		case 6:
			if (Math.abs(n1.value-n2.value) == 3) {
				return "A4";
			}
			else {
				return "d5";
			}
		case 7:
			return "P5";
		case 8:
			return "m6";
		case 9:
			return "M6";
		case 10:
			return "m7";
		case 11:
			return "M7";
		}
		return "";
	}
	public Part writeMelody(Part bass) {
		
		return writeMelody_V2(bass);
	}
	public Chord impliedChord(Part p, int i) {
		return getChordOffDegree(p.key, getScaleDegree(p.notes.get(i),p.key));
	}
	public int getScaleDegree(Note n, String key) {
		// 1 == key, 2 == 2nd, etc. 0 implies not in key
		int s = semiBetween(n,getNoteFromString(key));
		char quality = getQuality(key);
		switch (s%12) {
		case 0:
			return 1;
		case 2:
			return 2;
		case 3:
			if (quality == 'm') {
				return 3;
			}
			return 0;
		case 4:
			if (quality == 'M') {
				return 3;
			}
			return 0;
		case 5:
			return 4;
		case 7:
			return 5;
		case 8:
			if (quality == 'm') {
				return 6;
			}
			return 0;
		case 9:
			if (quality == 'M') {
				return 6;
			}
			return 0;
		case 10:
			if (quality == 'm') { // AND NOT HARMONIC!
				return 7;
			}
			return 0;
		case 11:
			if (quality == 'M') {
				return 7;
			}
			return 0;
		}
		return 0;
	}
	public char getQuality(String key) {
		return key.toCharArray()[key.toCharArray().length-1];
	}
	public Chord getChordOffDegree(String key, int degree) {
		ArrayList<Note> n = new ArrayList<Note>();
		n.add(degreeInKey(key,degree));
		n.add(degreeInKey(key,(degree+1)%7+1));
		n.add(degreeInKey(key,(degree+3)%7+1));
		return new Chord(n);
	}
	public Chord get7OffDegree(String key, int degree) {
		ArrayList<Note> n = new ArrayList<Note>();
		n.add(degreeInKey(key,degree));
		n.add(degreeInKey(key,(degree+1)%7+1));
		n.add(degreeInKey(key,(degree+3)%7+1));
		n.add(degreeInKey(key,(degree+5)%7+1));
		return new Chord(n);
	}
	public boolean getEquivNotes(Note n1, Note n2) {
		//returns true if notes sound the same (ex. E4 == E3, also Fb4 == E3)
		int s = semiBetween(n1,n2);
		if (s % 12 == 0) {
			return true;
		}
		return false;
	}
	public boolean getEquivNotes(Note n1, String n2) {
		//returns true if notes sound the same (ex. E4 == E3, also Fb4 == E3)
		
		int s = semiBetween(n1,getNoteFromString(n2));
		if (s % 12 == 0) {
			return true;
		}
		return false;
	}
	public static Note getNoteFromString(String n) {
		char name = n.toCharArray()[0];
		if (n.toCharArray()[1] == 'b') {
			return new Note(name,0,accidental.flat,1);
		}
		else if (n.toCharArray()[1] == '#') {
			return new Note(name,0,accidental.sharp,1);
		}
		else {
			return new Note(name,0,accidental.natural,1);
		}
	}
	public static Note getNoteIntervalFromNote(Note n, String i, Boolean up) {
		Note ne;
		System.out.println(i);
		if (up) {
			//turn int to char
			ne = new Note(n.value + Character.getNumericValue(i.toCharArray()[1]) -1,accidental.natural,n.length);
			System.out.println(n.value+" "+ne.value);
			while (getSemitonesFromA4(n.value,n.acc) + getSemitonesOfInterval(i) > getSemitonesFromA4(ne.value,ne.acc)) {
				System.out.println(i);
				ne = raise(ne);
			}
			while (getSemitonesFromA4(n.value,n.acc) + getSemitonesOfInterval(i) < getSemitonesFromA4(ne.value,ne.acc)) {
				//System.out.println(n.name+n.acc.toString()+" "+ne.value+ne.name+ne.acc.toString());
				ne = lower(ne);
			}
		}
		else {
			ne = new Note(n.value - Character.getNumericValue(i.toCharArray()[1]) +1,accidental.natural,n.length);
			while (getSemitonesFromA4(n.value,n.acc) - getSemitonesOfInterval(i) > getSemitonesFromA4(ne.value,ne.acc)) {
				System.out.println("i");
				ne = raise(ne);
			}
			while (getSemitonesFromA4(n.value,n.acc) - getSemitonesOfInterval(i) < getSemitonesFromA4(ne.value,ne.acc)) {
				System.out.println("n");
				ne = lower(ne);
			}
		}
		return ne;
	}
	public static int getSemitonesOfInterval(String i) {
		switch (i) {
		case "P1":
			return 0;
		case "m2":
			return 1;
		case "M2":
			return 2;
		case "m3":
			return 3;
		case "M3":
			return 4;
		case "P4":
			return 5;
		case "A4":
			return 6;
		case "d5":
			return 6;
		case "P5":
			return 7;
		case "m6":
			return 8;
		case "M6":
			return 9;
		case "m7":
			return 10;
		case "M7":
			return 11;
		case "P8":
			return 12;
		}
		return 0;
	}
	public static Note lower(Note n) {
		Note n2 = n;
		if (n.acc == accidental.doublesharp) {
			n2.acc = accidental.sharp;
		}
		else if (n.acc == accidental.sharp) {
			n2.acc = accidental.natural;
		}
		else if (n.acc == accidental.natural) {
			n2.acc = accidental.flat;
		}
		else if (n.acc == accidental.flat) {
			n2.acc = accidental.doubleflat;
		}
		return n2;
	}
	public static Note raise(Note n) {
		Note n2 = n;
		if (n.acc == accidental.sharp) {
			n2.acc = accidental.doublesharp;
		}
		else if (n.acc == accidental.natural) {
			n2.acc = accidental.sharp;
		}
		else if (n.acc == accidental.flat) {
			n2.acc = accidental.natural;
		}
		else if (n.acc == accidental.doubleflat) {
			n2.acc = accidental.flat;
		}
		return n2;
	}
	public Note relOctave(Note n, int change) {
		Note n2 = n;
		n2.octave = n.octave + change;
		n2.value += 7 * change;
		return n2;
	}
	public Part writeMelody_V1(Part bass) {
		//writes a new part to serve as a melody to Part p which should be a bassline.
			// further, Part p should have (as of now) only quarter notes.
		Part melody = new Part("Treble",clef.treble,4,4,false,bass.key);
		melody.notes.add(relOctave(getNoteIntervalFromNote(bass.notes.get(0),"M3",true),1));
		int lastInterval = 3;
		int interval;
		for (int i = 1; i < bass.notes.size(); i++) {
			System.out.println("got");
			do {
				interval = randomInterval();
				System.out.println(interval+"  INTERFVAL");
			}
			while (!intervalError(lastInterval,interval));
			lastInterval = interval;
			System.out.println(interval);
			melody.notes.add(relOctave(getNoteIntervalFromNoteInScale(bass.notes.get(i),interval,bass.key),1));
		}
		//int lastInterval = 0;
		printIntervals(bass,melody);
		return melody;
	}
	public Part writeMelody_V2(Part bass) {
		//same as v1 but incorporates the idea of a "current chord", which represents the tonality of the piece at the moment. will only change when it needs to.
		// 3 main chords: I, V, iv
		// in the future, to improve, could include more chords by looking ahead and trying to get chord groups that are as large as possible.
		Part melody = new Part("Treble",clef.treble,4,4,false,bass.key);
		melody.notes.add(relOctave(getNoteIntervalFromNote(bass.notes.get(0),"M3",true),1));
		String lastInterval = "M3";
		String interval;
		int currentChord; // 1, 4, 5
		Note n;
		ArrayList<zone> zones = guessZones_V1(bass);
		System.out.println(zones);
		for (int i = 1; i < bass.notes.size(); i++) {
			System.out.println("got");
			do {
				n = createNote(bass.key,zones.get(i));
				
				interval = getInterval(bass.notes.get(i),n);
				System.out.println(interval+"  INTERFVAL");
			}
			while (!intervalError(lastInterval,interval));
			n.length = bass.notes.get(i).length;
			lastInterval = interval;
			System.out.println(interval);
			melody.notes.add(relOctave(n,4));
		}
		//int lastInterval = 0;
		printIntervals(bass,melody);
		return melody;
	}
	public boolean intervalError(int last, int now) {
		if ((last == 5 && now == 5) || (last == 1 && now == 1)) {
			return false;
		}
		if ((last == 4 || last == 2 || last == 7) && (now != 1 && now != 3 && now != 5)) {
			return false;
		}
		return true;
	}
	public boolean intervalError(String last, String now) {
		if ((last == "P5" && now == "P5") || ((last == "P8" || last == "P1") && (now == "P8" || now == "P1"))) {
			return false;
		}
		return true;
	}
	public int randomInterval() {
		int r = (int) Math.floor(Math.random() * 7);
		return r+1;
	}
	public static Note getNoteIntervalFromNoteInScale(Note n, int i, String key) {
		// up is assumed.
		Note ne = new Note(n.value + i - 1, accidental.natural, n.length);
		if (Part.accInKey(key).contains(ne.name)) {
			System.out.println(Part.accInKey(key) + "  "+ n.name);
			if (Part.sharpKey(key)) {
				ne.acc = accidental.sharp;
			}
			else {
				ne.acc = accidental.flat;
			}
		}
		return ne;
	}
	public static Note degree1(String key) {
		return getNoteFromString(key.substring(0,key.length()));
	}
	public static Note degreeInKey(String key, int degree) {
		return getNoteIntervalFromNoteInScale(degree1(key),degree,key);
	}
	public ArrayList<Note> findNotesInChordThatWork(String key, int chordDegree, Note lastBass, Note lastTreble, Note nowBass){
		// does not include 4 rn
		ArrayList<Note> notes = new ArrayList<Note>();
		Chord c = getChordOffDegree(key, getScaleDegree(degreeInKey(key,chordDegree),key));
		for (int i = 0; i < 3; i++) {
			Note n = c.notes().get(i);
			if (true) {//replace with if (works)
				notes.add(n);
			}
		}
		return notes;
	}
	public Note createNote(String key, zone z) {
		double[] intWeights = new double[7];
		double totalWeight = 0;
		for (int i = 1; i <= 7; i++) {
			intWeights[i-1] = degreeInZoneWeight(i,z);
			totalWeight += intWeights[i-1];
		}
		double rand = Math.random() * totalWeight;
		int a = 0;
		for (int i = 0; i < 7; i++) {
			if (rand < intWeights[i]) {
				a = i;
				break;
			}
			rand -= intWeights[i];
		}
		Note n = degreeInKey(key,a+1);
		System.out.println(z+" "+(a+1));
		return n;
	}
	public ArrayList<zone> guessZones_V1(Part p){
		//currently there's a bit of RANDOM zone guessing in here. there is 0 smoothness (continuity between zones), each note is kind of its own zone.
		ArrayList<zone> zones = new ArrayList<zone>();
		zone cZ = zone.tonic;
		double wT = 0;
		double wD = 0;
		double wPD = 0;
		for (Note n : p.notes) {
			int degree = getScaleDegree(n,p.key);
			wT = degreeInZoneWeight(degree,zone.tonic);
			wD = wT + degreeInZoneWeight(degree,zone.dominant);
			wPD = wD + degreeInZoneWeight(degree,zone.predominant);
			double random = Math.random() * wPD;
			if (random < wT) {
				cZ = zone.tonic;
			}
			else if (random < wD) {
				cZ = zone.dominant;
			}
			else {
				cZ = zone.predominant;
			}
			zones.add(cZ);
		}
		return zones;
	}
	public ArrayList<zone> guessZones_V2(Part p){
		ArrayList<zone> zones = new ArrayList<zone>();
		zone cZ = zone.tonic;
		double wT = 0;
		double wD = 0;
		double wPD = 0;
		for (Note n : p.notes) {
			int degree = getScaleDegree(n,p.key);
			wT = degreeInZoneWeight(degree,zone.tonic);
			if (cZ == zone.tonic) {
				wT += degreeInZoneWeight(degree,zone.tonic) * 7;
			}
			wD = wT + degreeInZoneWeight(degree,zone.dominant);
			if (cZ == zone.dominant) {
				wD += degreeInZoneWeight(degree,zone.dominant) * 7;
			}
			wPD = wD + degreeInZoneWeight(degree,zone.predominant);
			if (cZ == zone.predominant) {
				wPD += degreeInZoneWeight(degree,zone.predominant) * 7;
			}
			double random = Math.random() * wPD;
			if (random < wT) {
				cZ = zone.tonic;
			}
			else if (random < wD) {
				cZ = zone.dominant;
			}
			else {
				cZ = zone.predominant;
			}
			zones.add(cZ);
		}
		return zones;
	}
	/*public boolean isDegreeInZone(int d, zone z, boolean inc7) {
		if (z == zone.tonic) {
			if (d == 1 || d == 3 || d == 5 || (inc7 && d == 7)) {
				return true;
			}
		}
		else if (z == zone.dominant) {
			if (d == 5 || d == 7 || )
		}
		return false;
	}*/
	public double degreeInZoneWeight(int d, zone z) {
		if (z == zone.tonic) {
			switch (d) {
			case 1:
				return 1;
			case 2:
				return 0;
			case 3:
				return 0.9;
			case 4:
				return 0;
			case 5:
				return 0.7;
			case 6:
				return 0;
			case 7:
				return 0;
			}
		}
		else if (z == zone.dominant) {
			switch (d) {
			case 1:
				return 0;
			case 2:
				return 0.7;
			case 3:
				return 0;
			case 4:
				return 0.4;
			case 5:
				return 1;
			case 6:
				return 0;
			case 7:
				return 0.9;
			}
		}
		else if (z == zone.predominant) {
			switch (d) {
			case 1:
				return 0.2;
			case 2:
				return 0.3;
			case 3:
				return 0.05;
			case 4:
				return 1;
			case 5:
				return 0.05;
			case 6:
				return 0.9;
			case 7:
				return 0.05;
			}
		}
		return 0;
	}
}