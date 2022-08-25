package main;

import java.util.ArrayList;

import processing.core.PApplet;

public class Part {
		
	// drawing bars should make the slope such that ALL note stems are at least s*7 instead of just the outer ones. (base it off of minimum lengths)
	// alto clef?
	
	// 8 16 16 doesn't work with the right slope
	
	
	public ArrayList<Note> notes = new ArrayList<Note>();
	public String voice;
	public int timeTop;
	public int timeBot;
	public String key;
	public boolean compound;
	public clef myClef;
	public float width = 0;
	
	public Part(String voice, clef clef, int tT, int tB, boolean c, String key) {
		this.voice = voice;
		this.myClef = clef;
		this.timeTop = tT;
		this.timeBot = tB;
		this.compound = c;
		this.key = key;
	}
	public Part(ArrayList<Note> notes, String voice, clef clef, int tT, int tB, boolean c, String key) {
		this.notes = notes;
		this.voice = voice;
		this.myClef = clef;
		this.timeTop = tT;
		this.timeBot = tB;
		this.compound = c;
		this.key = key;
	}
	public void draw(PApplet p, float x, float y, float s, ArrayList<Float> bL, float wScale) {
		drawClef(p,x,y,s);
		float keyWidth = drawKeySignature(p,x+12*s,y,s);
		drawTime(p,x+10*s + keyWidth, y + 2*s, s, timeTop);
		drawTime(p,x+10*s + keyWidth, y + 6*s, s, timeBot);
		drawStaff(p,x,x + width,y,s);
		
		float distance = x+20*s + keyWidth;
		float since = 0;
		ArrayList<Note> barred = new ArrayList<Note>();
		float beatNum = 0;
		for (int i = 0; i < notes.size(); i++) {
			//System.out.println(i);
			Note n = notes.get(i);
			since += n.length;
			barred.add(n);
			if (since >= 1) {
				distance = drawBeat(p,barred,distance,since-notes.get(i).length,y,s,bL.get((int)beatNum),wScale);
				//System.out.println((distance - (x + 4*s)) % (timeTop / timeBot * 4)+"                 A");
				
				barred = new ArrayList<Note>();
				since = 0;
			}
			beatNum += n.length;
			if (beatNum % (timeTop / timeBot * 4) == 0 && distance > 1 && i < notes.size()-1) {
				p.stroke(0);
				p.strokeWeight(2);
				p.line(distance - wScale*s/4,y-s*4,distance - wScale*s/4,y+s*4);
				distance += wScale * s / 2;
			}
			
		}
		p.stroke(0);
		p.strokeWeight(2);
		p.line(distance - wScale*s/4,y-s*4,distance - wScale*s/4,y+s*4);
		//p.strokeWeight(6);
		p.rect(distance-4,y-4*s, 6, s*8);
		if (width == 0) {
			width = distance - x;
		}
	}
	public void drawClef(PApplet p, float x, float y, float s) {
		if (myClef == clef.bass) {
			p.noStroke();
			p.beginShape();
			float x2 = x + s;
			p.vertex(x2+(float)(-0.014492754)*s,y+(float)(2.3333333)*s);
			p.bezierVertex(x2+(float)(-0.014492754)*s,y+(float)(2.3333333)*s,x2+(float)(1.3043479)*s,y+(float)(1.4202899)*s,x2+(float)(1.3043479)*s,y+(float)(1.4202899)*s);
			p.bezierVertex(x2+(float)(2.463768)*s,y+(float)(0.5797101)*s,x2+(float)(3.231884)*s,y+(float)(-0.8115942)*s,x2+(float)(3.2608695)*s,y+(float)(-2.0289855)*s);
			p.bezierVertex(x2+(float)(3.2608695)*s,y+(float)(-2.5072465)*s,x2+(float)(3.1304348)*s,y+(float)(-3.7536232)*s,x2+(float)(1.9710145)*s,y+(float)(-3.7391305)*s);
			p.bezierVertex(x2+(float)(1.8695652)*s,y+(float)(-3.7391305)*s,x2+(float)(0.7826087)*s,y+(float)(-3.6376812)*s,x2+(float)(0.6956522)*s,y+(float)(-2.8985507)*s);
			p.bezierVertex(x2+(float)(0.6956522)*s,y+(float)(-2.8985507)*s,x2+(float)(0.6956522)*s,y+(float)(-2.652174)*s,x2+(float)(1.2028985)*s,y+(float)(-2.826087)*s);
			p.bezierVertex(x2+(float)(1.2028985)*s,y+(float)(-2.826087)*s,x2+(float)(1.942029)*s,y+(float)(-2.8695652)*s,x2+(float)(1.9855072)*s,y+(float)(-2.1594203)*s);
			p.bezierVertex(x2+(float)(1.9855072)*s,y+(float)(-2.0724638)*s,x2+(float)(1.9855072)*s,y+(float)(-1.4637681)*s,x2+(float)(1.1449275)*s,y+(float)(-1.4347826)*s);
			p.bezierVertex(x2+(float)(0.9130435)*s,y+(float)(-1.4347826)*s,x2+(float)(0.23188406)*s,y+(float)(-1.5362319)*s,x2+(float)(0.20289855)*s,y+(float)(-2.2608695)*s);
			p.bezierVertex(x2+(float)(0.1594203)*s,y+(float)(-2.710145)*s,x2+(float)(0.46376812)*s,y+(float)(-3.8985507)*s,x2+(float)(2.2173913)*s,y+(float)(-3.9855072)*s);
			p.bezierVertex(x2+(float)(2.8550725)*s,y+(float)(-4.0434785)*s,x2+(float)(4.3913045)*s,y+(float)(-3.536232)*s,x2+(float)(4.3913045)*s,y+(float)(-1.8550725)*s);
			p.bezierVertex(x2+(float)(4.3913045)*s,y+(float)(-1.057971)*s,x2+(float)(3.942029)*s,y+(float)(0.10144927)*s,x2+(float)(2.2753623)*s,y+(float)(1.2608696)*s);
			p.bezierVertex(x2+(float)(1.9855072)*s,y+(float)(1.4492754)*s,x2+(float)(0.5217391)*s,y+(float)(2.3333333)*s,x2+(float)(0.028985508)*s,y+(float)(2.5072465)*s);
			p.bezierVertex(x2+(float)(0.028985508)*s,y+(float)(2.5072465)*s,x2+(float)(-0.028985508)*s,y+(float)(2.347826)*s,x2+(float)(-0.028985508)*s,y+(float)(2.347826)*s);
			p.vertex(x2+(float)(-0.014492754)*s,y+(float)(2.3333333)*s);
			p.endShape();
			p.ellipse(x2+(float)(5.2)*s,y-3*s,s*(float)(0.8),s*(float)(0.8));
			p.ellipse(x2+(float)(5.2)*s,y-s,s*(float)(0.8),s*(float)(0.8));
		}
		else if (myClef == clef.treble) {
			p.noStroke();
			p.beginShape();
			float x2 = x + s;
			p.vertex(x2+(float)(1.8809524)*s,y+(float)(6.547619)*s);
			p.bezierVertex(x2+(float)(2.047619)*s,y+(float)(6.571429)*s,x2+(float)(2.642857)*s,y+(float)(6.5)*s,x2+(float)(2.642857)*s,y+(float)(5.8333335)*s);
			p.bezierVertex(x2+(float)(2.642857)*s,y+(float)(5.8333335)*s,x2+(float)(2.6190476)*s,y+(float)(5.0238094)*s,x2+(float)(1.8809524)*s,y+(float)(5.0238094)*s);
			p.bezierVertex(x2+(float)(1.8809524)*s,y+(float)(5.0238094)*s,x2+(float)(1.1190476)*s,y+(float)(5.0238094)*s,x2+(float)(1.0952381)*s,y+(float)(6.0)*s);
			p.bezierVertex(x2+(float)(1.0952381)*s,y+(float)(6.0)*s,x2+(float)(1.1428572)*s,y+(float)(7.095238)*s,x2+(float)(2.452381)*s,y+(float)(7.095238)*s);
			p.bezierVertex(x2+(float)(3.5714285)*s,y+(float)(7.095238)*s,x2+(float)(4.071429)*s,y+(float)(6.214286)*s,x2+(float)(3.9285715)*s,y+(float)(5.428571)*s);
			p.bezierVertex(x2+(float)(3.9285715)*s,y+(float)(5.428571)*s,x2+(float)(2.1904762)*s,y+(float)(-3.0714285)*s,x2+(float)(2.1904762)*s,y+(float)(-3.0714285)*s);
			p.bezierVertex(x2+(float)(2.0952382)*s,y+(float)(-3.4761906)*s,x2+(float)(2.3095238)*s,y+(float)(-5.3095236)*s,x2+(float)(3.1190476)*s,y+(float)(-5.3333335)*s);
			p.bezierVertex(x2+(float)(3.1190476)*s,y+(float)(-5.3333335)*s,x2+(float)(3.4761906)*s,y+(float)(-5.357143)*s,x2+(float)(3.5)*s,y+(float)(-4.857143)*s);
			p.bezierVertex(x2+(float)(3.5714285)*s,y+(float)(-2.3809524)*s,x2+(float)(0.14285715)*s,y+(float)(-1.8333334)*s,x2+(float)(0.04761905)*s,y+(float)(1.1666666)*s);
			p.bezierVertex(x2+(float)(0.023809524)*s,y+(float)(1.9285715)*s,x2+(float)(0.6666667)*s,y+(float)(3.9761906)*s,x2+(float)(2.6666667)*s,y+(float)(3.9285715)*s);
			p.bezierVertex(x2+(float)(3.1904762)*s,y+(float)(3.9047618)*s,x2+(float)(4.785714)*s,y+(float)(3.8095238)*s,x2+(float)(4.8333335)*s,y+(float)(1.9523809)*s);
			p.bezierVertex(x2+(float)(4.857143)*s,y+(float)(1.3333334)*s,x2+(float)(4.428571)*s,y+(float)(0.04761905)*s,x2+(float)(3.0952382)*s,y+(float)(-0.023809524)*s);
			p.bezierVertex(x2+(float)(3.0952382)*s,y+(float)(-0.023809524)*s,x2+(float)(1.5952381)*s,y+(float)(0.0952381)*s,x2+(float)(1.5238096)*s,y+(float)(1.6428572)*s);
			p.bezierVertex(x2+(float)(1.5238096)*s,y+(float)(1.6428572)*s,x2+(float)(1.4761904)*s,y+(float)(2.952381)*s,x2+(float)(2.6190476)*s,y+(float)(3.047619)*s);
			p.bezierVertex(x2+(float)(2.6190476)*s,y+(float)(3.047619)*s,x2+(float)(2.642857)*s,y+(float)(2.9047618)*s,x2+(float)(2.642857)*s,y+(float)(2.9047618)*s);
			p.bezierVertex(x2+(float)(2.642857)*s,y+(float)(2.9047618)*s,x2+(float)(2.0)*s,y+(float)(2.857143)*s,x2+(float)(1.9761904)*s,y+(float)(1.9523809)*s);
			p.bezierVertex(x2+(float)(1.9523809)*s,y+(float)(1.5714285)*s,x2+(float)(2.452381)*s,y+(float)(0.95238096)*s,x2+(float)(3.0)*s,y+(float)(0.9285714)*s);
			p.bezierVertex(x2+(float)(3.1904762)*s,y+(float)(0.85714287)*s,x2+(float)(4.214286)*s,y+(float)(1.1190476)*s,x2+(float)(4.3333335)*s,y+(float)(2.2142856)*s);
			p.bezierVertex(x2+(float)(4.285714)*s,y+(float)(2.357143)*s,x2+(float)(4.452381)*s,y+(float)(3.7142856)*s,x2+(float)(2.547619)*s,y+(float)(3.7142856)*s);
			p.bezierVertex(x2+(float)(2.0952382)*s,y+(float)(3.6904762)*s,x2+(float)(0.64285713)*s,y+(float)(3.3095238)*s,x2+(float)(0.5952381)*s,y+(float)(1.9047619)*s);
			p.bezierVertex(x2+(float)(0.64285713)*s,y+(float)(-0.97619045)*s,x2+(float)(3.7857144)*s,y+(float)(-0.7619048)*s,x2+(float)(3.8333333)*s,y+(float)(-4.3095236)*s);
			p.bezierVertex(x2+(float)(3.857143)*s,y+(float)(-5.3095236)*s,x2+(float)(3.4285715)*s,y+(float)(-6.1666665)*s,x2+(float)(3.047619)*s,y+(float)(-6.547619)*s);
			p.bezierVertex(x2+(float)(2.3333333)*s,y+(float)(-6.4761906)*s,x2+(float)(1.6190476)*s,y+(float)(-4.928571)*s,x2+(float)(1.9761904)*s,y+(float)(-2.7857144)*s);
			p.bezierVertex(x2+(float)(1.9761904)*s,y+(float)(-2.7857144)*s,x2+(float)(3.642857)*s,y+(float)(5.285714)*s,x2+(float)(3.642857)*s,y+(float)(5.285714)*s);
			p.bezierVertex(x2+(float)(3.8809524)*s,y+(float)(6.785714)*s,x2+(float)(2.4047618)*s,y+(float)(7.1904764)*s,x2+(float)(1.8809524)*s,y+(float)(6.547619)*s);
			p.vertex(x2+(float)(1.8809524)*s,y+(float)(6.547619)*s);
			
			p.endShape();
		}
	}
	public float drawKeySignature(PApplet p, float x, float y, float s) {
		ArrayList<Note> kSN = keySigNotes(key,myClef);
		float d = 0;
		for (Note n : kSN) {
			p.translate(x+d,y - s * (n.value - getClefOffset()));
			drawAcc(p,n,s);
			p.translate(-x-d, -y + s * (n.value - getClefOffset()));
			d += s*(float)(1.5);
		}
		return d;
	}
	public void drawTime(PApplet p, float x, float y, float s, int t) {
		if (t == 4) {
			p.noStroke();
			p.beginShape();
			float x2 = x;
			p.vertex(x2+(float)(0.05263158)*s,y+(float)(-3.131579)*s);
			p.bezierVertex(x2+(float)(0.05263158)*s,y+(float)(-3.131579)*s,x2+(float)(1.6842105)*s,y+(float)(-3.131579)*s,x2+(float)(1.6842105)*s,y+(float)(-3.1578948)*s);
			p.bezierVertex(x2+(float)(1.6842105)*s,y+(float)(-2.8157895)*s,x2+(float)(1.6842105)*s,y+(float)(-2.5)*s,x2+(float)(1.2368422)*s,y+(float)(-2.4736843)*s);
			p.bezierVertex(x2+(float)(1.2368422)*s,y+(float)(-2.4736843)*s,x2+(float)(1.2368422)*s,y+(float)(-2.3157895)*s,x2+(float)(1.2368422)*s,y+(float)(-2.3157895)*s);
			p.bezierVertex(x2+(float)(1.2368422)*s,y+(float)(-2.3157895)*s,x2+(float)(3.2105262)*s,y+(float)(-2.3157895)*s,x2+(float)(3.2105262)*s,y+(float)(-2.3157895)*s);
			p.bezierVertex(x2+(float)(3.2105262)*s,y+(float)(-2.3157895)*s,x2+(float)(3.2105262)*s,y+(float)(-2.4736843)*s,x2+(float)(3.2105262)*s,y+(float)(-2.4736843)*s);
			p.bezierVertex(x2+(float)(2.9473684)*s,y+(float)(-2.4210527)*s,x2+(float)(2.7368422)*s,y+(float)(-2.5)*s,x2+(float)(2.7631578)*s,y+(float)(-3.131579)*s);
			p.bezierVertex(x2+(float)(2.7631578)*s,y+(float)(-3.131579)*s,x2+(float)(3.2631578)*s,y+(float)(-3.1052632)*s,x2+(float)(3.2631578)*s,y+(float)(-3.1052632)*s);
			p.bezierVertex(x2+(float)(3.2631578)*s,y+(float)(-3.1052632)*s,x2+(float)(3.2631578)*s,y+(float)(-3.4473684)*s,x2+(float)(3.2631578)*s,y+(float)(-3.4473684)*s);
			p.bezierVertex(x2+(float)(3.2631578)*s,y+(float)(-3.4473684)*s,x2+(float)(2.7631578)*s,y+(float)(-3.4736843)*s,x2+(float)(2.7631578)*s,y+(float)(-3.4736843)*s);
			p.bezierVertex(x2+(float)(2.7631578)*s,y+(float)(-3.4736843)*s,x2+(float)(2.7368422)*s,y+(float)(-5.3421054)*s,x2+(float)(2.7368422)*s,y+(float)(-5.3421054)*s);
			p.bezierVertex(x2+(float)(2.7368422)*s,y+(float)(-5.3421054)*s,x2+(float)(1.7105263)*s,y+(float)(-4.368421)*s,x2+(float)(1.7105263)*s,y+(float)(-4.368421)*s);
			p.bezierVertex(x2+(float)(1.7105263)*s,y+(float)(-4.368421)*s,x2+(float)(1.6578947)*s,y+(float)(-3.5)*s,x2+(float)(1.6842105)*s,y+(float)(-3.5)*s);
			p.bezierVertex(x2+(float)(1.6842105)*s,y+(float)(-3.5)*s,x2+(float)(0.65789473)*s,y+(float)(-3.5)*s,x2+(float)(0.65789473)*s,y+(float)(-3.5)*s);
			p.bezierVertex(x2+(float)(0.68421054)*s,y+(float)(-4.105263)*s,x2+(float)(2.4473684)*s,y+(float)(-5.3157897)*s,x2+(float)(2.4473684)*s,y+(float)(-5.9736843)*s);
			p.bezierVertex(x2+(float)(2.4473684)*s,y+(float)(-5.9736843)*s,x2+(float)(1.0789474)*s,y+(float)(-5.9210525)*s,x2+(float)(1.0789474)*s,y+(float)(-5.9473686)*s);
			p.bezierVertex(x2+(float)(1.0263158)*s,y+(float)(-4.763158)*s,x2+(float)(0.078947365)*s,y+(float)(-3.4736843)*s,x2+(float)(0.078947365)*s,y+(float)(-3.4736843)*s);
			p.bezierVertex(x2+(float)(0.078947365)*s,y+(float)(-3.4736843)*s,x2+(float)(0.05263158)*s,y+(float)(-3.131579)*s,x2+(float)(0.05263158)*s,y+(float)(-3.131579)*s);
			p.vertex(x2+(float)(0.05263158)*s,y+(float)(-3.131579)*s);
			p.endShape();
		}
	}
	public float drawBeat(PApplet p, ArrayList<Note> barred, float distance, float since, float y, float s, float w, float wScale) {
		ArrayList<Note> toBar = new ArrayList<Note>();
		for (int i = 0; i < barred.size(); i++) {
			if (barred.get(i).length >= 1 && toBar.size() > 0) {
				distance = drawBarred(p,toBar,distance,since,y,s,w,wScale);
				toBar = new ArrayList<Note>();
			}
			toBar.add(barred.get(i));
			if (barred.get(i).length >= 1) {
				distance = drawBarred(p,toBar,distance,since,y,s,w,wScale);
				toBar = new ArrayList<Note>();
			}
		}
		if (toBar.size() > 0) {
			distance = drawBarred(p,toBar,distance,since,y,s,w,wScale);
		}
		
		return distance;
	}
	public float drawBarred(PApplet p, ArrayList<Note> barred, float distance, float since, float y, float s, float w, float wScale) {
		if (barred.size() > 1) {
			float ogD = distance;
			float avgH = 0;
			for (int z = 0; z < barred.size(); z++) {
				avgH += barred.get(z).value - getClefOffset();
			}
			boolean right = false;
			if (avgH <= 0) {
				right = true;
			}
			float slope = (barred.get(barred.size()-1).value - barred.get(0).value) / ((barred.size()-1) * wScale);
			//System.out.println(slope+" slope");
			//System.out.println(barred.get(barred.size()-1).value - barred.get(0).value+" not");
			//System.out.println(right+" slope");
			float thisX = 0;
			for (int z = 0; z < barred.size(); z++) {
				
				// h = left x + slope * current x (0-1) 
				float h;
				if (right) {
					h = s*7 + slope * thisX -(barred.get(z).value - barred.get(0).value)*s;
				}
				else {
					h = s*7 - slope * thisX +(barred.get(z).value - barred.get(0).value)*s;
				}
				drawNote(p,barred.get(z),distance,y,s, right, h);
				if (z == barred.size()-1) {
					if (right) {
						drawBar(p,distance - thisX+(float) (s*1.195),y - s * (barred.get(0).value - getClefOffset())-s*7,distance+(float) (s*1.195),y - s * (barred.get(z).value - getClefOffset())-s*7, true);
						
						//System.out.println(thisX);
						
					}
					else {
						drawBar(p,distance - thisX-(float) (s*1.195),y - s * (barred.get(0).value - getClefOffset())+s*7,distance-(float) (s*1.195),y - s * (barred.get(z).value - getClefOffset())+s*7, false);
						// fix this
					}
					float l = ogD;
					float d = l;
					float len = barred.get(0).length;
					int iLast = 0;
					for (int a = 0; a < barred.size(); a++) {
						if (barred.get(a).length != len) {
							if (len == 0.25) {
								if (right) {
									drawBar(p,
											l+(float) (s*1.195),
											y - (s*7 + slope * (l - ogD) -(barred.get(iLast).value - barred.get(0).value)*s + s * (barred.get(iLast).value - getClefOffset()))+8,
											d-wScale * s+(float) (s*1.195),
											y - (s*7 + slope * (d - ogD - wScale * s) -(barred.get(a).value - barred.get(0).value)*s + s * (barred.get(a).value - getClefOffset()))+8,
											true);
									
									//System.out.println(thisX);
									
								}
								else {
									drawBar(p,
											l-(float) (s*1.195),
											y + (s*7 - slope * (l - ogD) +(barred.get(iLast).value - barred.get(0).value)*s - s * (barred.get(iLast).value - getClefOffset()))-8,
											d-wScale * s-(float) (s*1.195),
											y + (s*7 - slope * (d - ogD - wScale * s) +(barred.get(a).value - barred.get(0).value)*s - s * (barred.get(a).value - getClefOffset()))-8,
											false);
									// fix this
								}
								//drawBar(p,l,y - s * (barred.get(0).value - getClefOffset())-s*7+8,d-wScale * s,y - s * (barred.get(z).value - getClefOffset())-s*7+8, true);
							}
							l = d;
							iLast = a;
						}
						len = barred.get(a).length;
						d += wScale * s;
					}
					if (len == 0.25) {
						if (right) {
							//drawBar(p,l+(float) (s*1.195),y - s * (barred.get(0).value - getClefOffset())-s*7+8,d-wScale * s+(float) (s*1.195),y - s * (barred.get(z).value - getClefOffset())-s*7+8, true);
							drawBar(p,
									l+(float) (s*1.195),
									y - (s*7 + slope * (l - ogD) -(barred.get(iLast).value - barred.get(0).value)*s + s * (barred.get(iLast).value - getClefOffset()))+8,
									d-wScale * s+(float) (s*1.195),
									y - s * (barred.get(z).value - getClefOffset())-s*7+8,
									true);
							//s*7 + slope * thisX -(barred.get(z).value - barred.get(0).value)*s
							//System.out.println(thisX);
							
						}
						else {
							drawBar(p,
									l-(float) (s*1.195),
									y + (s*7 - slope * (l - ogD) +(barred.get(iLast).value - barred.get(0).value)*s - s * (barred.get(iLast).value - getClefOffset()))-8,
									d-wScale * s-(float) (s*1.195),
									y - s * (barred.get(z).value - getClefOffset())+s*7-8,
									false);
							// fix this
						}
					}
					
					// draw extra bars for 16ths
						// look through notes. if a note is a 16th, add it to a list. once you get a non-16th, you know where the bar should go.
						// if the length of the list is only 1, then you have to add a little flag (this is also true for single eights so include them).
						// flag should go to the right if the note after it is longer than twice its length, and left if the note before it is
				}
				
				thisX += wScale * s;
				distance += wScale * s;
			}
			
			
		}
		else {
			//System.out.println(barred.size());

			
			drawNote(p,barred.get(0),distance,y,s, barred.get(0).value - getClefOffset() <= 0, s*7);
			distance += barred.get(0).length * wScale * s;
			
		}
		
		return distance;
	}
	public void drawStaff(PApplet p, float x, float xf, float y, float s) {
		p.stroke(0);
		p.strokeWeight(2);
		p.line(x, -4*s+y, xf, -4*s+y);
		p.line(x, -2*s+y, xf, -2*s+y);
		p.line(x, 0+y, xf, 0+y);
		p.line(x, 2*s+y, xf, 2*s+y);
		p.line(x, 4*s+y, xf, 4*s+y);
	}
	public void drawNote(PApplet p, Note n, float x, float y, float s, boolean right, float h) {
		//y represents middle line of the staff
		//x represents left side of the staff
		//s represents the height of each space / 2
		float z = y - s * (n.value - getClefOffset());
		if (n.length == 1 || n.length == 0.5 || n.length == 0.25 || n.length == 1.5 || n.length == 0.75) { // quarter note, eighth
			
			p.translate(x,z);
			p.rotate((float) (-Math.PI/6));
			p.fill(0);
			p.stroke(0);
			p.ellipse(0, 0, s*13/5, s*8/5);
			p.rotate((float)(Math.PI/6));
			p.strokeWeight(2);
			p.strokeCap(PApplet.SQUARE);
			/*if (n.value - getClefOffset() <= 0) {
				p.line((float) (s*1.195), (float)(-s*0.381), (float) (s*1.195), -s*7);
			}
			else {
				p.line(-(float) (s*1.195),(float) (s*0.381), -(float) (s*1.195), s*7);
			}*/
			if (right) {
				p.line((float) (s*1.195), (float)(-s*0.381), (float) (s*1.195), -h);
			}
			else {
				p.line(-(float) (s*1.195),(float) (s*0.381), -(float) (s*1.195), h);
			}
			
		}
		else if (n.length == 2) { // half note
			p.translate(x,z);
			p.rotate((float) (-Math.PI/6));
			p.fill(0);
			p.stroke(0);
			//p.ellipse(0, 0, s*13/5, s*8/5);
			p.beginShape();
			p.vertex(-s*13/10,0);
			p.bezierVertex(-s*13/10, -s*16/15, s*13/10, -s*16/15, s*13/10, 0);
			p.bezierVertex(s*13/10, -s*16/25, -s*13/10, -s*16/25, -s*13/10, 0);
			p.endShape();
			p.beginShape();
			p.vertex(-s*13/10,0);
			p.bezierVertex(-s*13/10, s*16/25, s*13/10, s*16/25, s*13/10, 0);
			p.bezierVertex(s*13/10, s*16/15, -s*13/10, s*16/15, -s*13/10, 0);
			p.endShape();
			p.rotate((float)(Math.PI/6));
			p.strokeWeight(2);
			p.strokeCap(PApplet.SQUARE);
			/*if (n.value - getClefOffset() <= 0) {
				p.line((float) (s*1.195), (float)(-s*0.381), (float) (s*1.195), -s*7);
			}
			else {
				p.line(-(float) (s*1.195),(float) (s*0.381), -(float) (s*1.195), s*7);
			}*/
			if (right) {
				p.line((float) (s*1.195), (float)(-s*0.381), (float) (s*1.195), -h);
			}
			else {
				p.line(-(float) (s*1.195),(float) (s*0.381), -(float) (s*1.195), h);
			}
		}
		else if (n.length == 4) {
			p.translate(x,z);
			//p.rotate((float) (-Math.PI/6));
			p.fill(0);
			p.stroke(0);
			//p.ellipse(0, 0, s*13/5, s*8/5);
			
			p.beginShape();
			p.vertex(0,s*9/10);
			p.bezierVertex(-s*30/15, s*9/10, -s*30/15, -s*9/10, 0,-s*9/10);
			p.bezierVertex(-s*30/30, -s*15/10, -s*30/30, s*9/10, 0,s*9/10);
			p.endShape();
			p.beginShape();
			p.vertex(0,s*9/10);
			p.bezierVertex(s*30/15, s*9/10, s*30/15, -s*9/10, 0,-s*9/10);
			p.bezierVertex(s*30/30, -s*9/10, s*30/30, s*15/10, 0,s*9/10);
			p.endShape();
			/*
			p.beginShape();
			p.vertex(0,-s*18/15);
			p.bezierVertex(-s*15/10, s*18/25, s*15/10, s*18/25, s*15/10, 0);
			p.bezierVertex(s*15/10, s*18/15, -s*15/10, s*18/15, -s*15/10, 0);
			p.endShape();
			*/
			//p.rotate((float)(Math.PI/6));
		}
		if (n.length == 0.75 || n.length == 1.5 || n.length == 3 || n.length == 6) {
			if (right) {
				p.ellipse(5/2*s, s/2, s*2/5, s*2/5);
			}
			else {
				p.ellipse(5/2*s, -s/2, s*2/5, s*2/5);
			}
		}
		if (shouldDrawAcc(notes,notes.indexOf(n))) {
			drawAcc(p,n,s);
		}
		
		p.translate(-x, -z);
		p.stroke(0);
		for (int i = 6; i <= Math.abs(n.value - getClefOffset()); i += 2) {
			if (n.value < getClefOffset()) {
				p.line(x - (float)1.5*s, y + s*i, x + (float)1.5*s, y + s*i);
			}
			else {
				p.line(x - (float)1.5*s, y - s*i, x + (float)1.5*s, y - s*i);
			}
		}
		
		
	}
	public void drawAcc(PApplet p, Note n, float s) {
		if (n.acc == accidental.flat) {
			p.stroke(0);
			p.strokeWeight(2);
			p.line(-3*s,-s*3,-3*s,s);
			
			p.beginShape();
			p.vertex(-3*s,-s*(float)(0.2));
			//p.vertex(-3*s,s);
			p.bezierVertex(-s*(float)(1.5),-(float)(2)*s,-s,s/2,-3*s,s);
			//p.bezierVertex(-3*s, s, -3*s, s, -3*s, s);
			p.bezierVertex(-s*(float)(1.2),s/4,-(float)(2.5)*s,-(float)(1)*s,-3*s,-s*(float)(0.2));
			p.endShape();
		}
		else if (n.acc == accidental.doubleflat) {
			p.stroke(0);
			p.strokeWeight(2);
			p.line(-3*s,-s*3,-3*s,s);
			
			p.beginShape();
			p.vertex(-3*s,-s*(float)(0.2));
			//p.vertex(-3*s,s);
			p.bezierVertex(-s*(float)(1.5),-(float)(2)*s,-s,s/2,-3*s,s);
			//p.bezierVertex(-3*s, s, -3*s, s, -3*s, s);
			p.bezierVertex(-s*(float)(1.2),s/4,-(float)(2.5)*s,-(float)(1)*s,-3*s,-s*(float)(0.2));
			p.endShape();
			
			p.line(-4*s,-s*3,-4*s,s);
			
			p.beginShape();
			p.vertex(-4*s,-s*(float)(0.2));
			//p.vertex(-3*s,s);
			p.bezierVertex(-s*(float)(3),-(float)(2)*s,-(float)(2.5)*s,s/2,-4*s,s);
			//p.bezierVertex(-3*s, s, -3*s, s, -3*s, s);
			p.bezierVertex(-s*(float)(1.7),s/4,-(float)(4)*s,-(float)(1)*s,-4*s,-s*(float)(0.2));
			p.endShape();
		
		}
		else if (n.acc == accidental.sharp) {
			p.noStroke();
			p.fill(0);
			p.rect(-(float)(3.5)*s,-2*s+2,2,4*s-2);
			p.rect(-(float)(2.8)*s,-2*s,2,4*s-2);
			p.quad(-4*s, -s, -(float)(2.3)*s+2, -s-5, -(float)(2.3)*s+2, -s, -4*s, -s+5);
			p.quad(-4*s, s, -(float)(2.3)*s+2, s-5, -(float)(2.3)*s+2, s, -4*s, s+5);
		}
		else if (n.acc == accidental.doublesharp) {
			p.noStroke();
			p.fill(0);
			p.beginShape();
			p.vertex(-4*s,-s);
			p.vertex(-4*s+4,-s);
			p.bezierVertex(-4*s+4,-s/2,-2*s-s/2,s-4,-2*s,s-4);
			p.vertex(-2*s,s);
			p.vertex(-2*s-4,s);
			p.bezierVertex(-2*s-4,s/2,-4*s+s/2,-s+4,-4*s,-s+4);
			p.vertex(-4*s,-s);
			p.endShape();
			p.beginShape();
			p.vertex(-2*s,-s);
			p.vertex(-2*s-4,-s);
			p.bezierVertex(-2*s-4,-s/2,-4*s+s/2,s-4,-4*s,s-4);
			p.vertex(-4*s,s);
			p.vertex(-4*s+4,s);
			p.bezierVertex(-4*s+4,s/2,-2*s-s/2,-s+4,-2*s,-s+4);
			p.vertex(-2*s,-s);
			p.endShape();
		}
		else if (n.acc == accidental.natural) {
			p.noStroke();
			p.fill(0);
			p.rect(-(float)(3.5)*s,-3*s+2,2,4*s-2);
			p.rect(-(float)(2.5)*s,-s,2,4*s-2);
			p.quad(-(float)(3.5)*s, -s, -(float)(2.5)*s+2, -s-5, -(float)(2.5)*s+2, -s, -(float)(3.5)*s, -s+5);
			p.quad(-(float)(3.5)*s, s, -(float)(2.5)*s+2, s-5, -(float)(2.5)*s+2, s, -(float)(3.5)*s, s+5);
		}
	}
	public void drawBar(PApplet p, float x, float y, float x2, float y2, boolean right) {
		//n1 is the first note to bar, n2 is the second note to bar
		//x and y of middle-bar for n1
		p.noStroke();
		if (!right) {
			p.quad(x, y, x, y-6, x2, y2-6, x2, y2);
		}
		else {
			p.quad(x, y, x, y+6, x2, y2+6,x2, y2);
		}
		
	}
	public int getClefOffset() {
		//returns the integer representing the value of the note on the middle line of the staff
		if (myClef == clef.treble) {
			return 34;
		}
		if (myClef == clef.alto) {
			return 28;
		}
		if (myClef == clef.tenor) {
			return 26;
		}
		if (myClef == clef.bass) {
			return 22;
		}
		return 0;
	}
	public float getPartLength() {
		float total = 0;
		for (Note n : notes) {
			total += n.length;
		}
		return total;
	}
	public float sumWidth(ArrayList<Float> widths) {
		float total = 0;
		for (Float f : widths) {
			total += f;
		}
		return total;
	}
	public boolean shouldDrawAcc(ArrayList<Note> notes, int index) {
		// ahhhh shit this is wrong
		// instead, look backwards from the note until you either find a DIFFERENT accidental on the same note OR the beginning of a measure
			// if you find the beginning of a measure, draw it if it contradicts with the key signature.
		float dur = 0;
		for (int i = 0; i < index; i++) {
			dur += notes.get(i).length;
		}
		int noCount = (int) (Math.floor(dur / timeTop) * timeTop); // number of notes in full measures before index
		dur = 0;
		int f = 0;
		for (int i = 0; i < index; i++) {
			dur += notes.get(i).length;
			if (dur > noCount) {
				f = i;
			}
		}
		accidental shouldBe = noteInKey(notes.get(index).name,key);
		for (int i = f; i < index; i++) {
			if (notes.get(i).value == notes.get(index).value) {
				shouldBe = notes.get(i).acc;
			}
		}
		if (shouldBe == notes.get(index).acc) {
			return false;
		}
		return true;
	}
	public static accidental noteInKey(char c, String key) {
		if (accInKey(key).contains(c)) {
			if (sharpKey(key)) {
				return accidental.sharp;
			}
			else {
				return accidental.flat;
			}
		}
		return accidental.natural;
	}
	public static boolean sharpKey(String key) {
		if (key.equals("CM") || key.equals("GM") || key.equals("DM") || key.equals("AM") || key.equals("EM") || key.equals("BM") || key.equals("F#M") || key.equals("C#M") || key.equals("Am") || key.equals("Em") || key.equals("Bm") || key.equals("F#m") || key.equals("C#m") || key.equals("G#m") || key.equals("D#m") || key.equals("A#m")) {
			return true;
		}
		return false;
	}
	public static ArrayList<Character> accInKey(String key){
		ArrayList<Character> all = new ArrayList<Character>();
		switch (key) {
		case "CM":
			break;
		case "GM":
			all.add('f');
			break;
		case "DM":
			all.add('f');
			all.add('c');
			break;
		case "AM":
			all.add('f');
			all.add('c');
			all.add('g');
			break;
		case "EM":
			all.add('f');
			all.add('c');
			all.add('g');
			all.add('d');
			break;
		case "BM":
			all.add('f');
			all.add('c');
			all.add('g');
			all.add('d');
			all.add('a');
			break;
		case "F#M":
			all.add('f');
			all.add('c');
			all.add('g');
			all.add('d');
			all.add('a');
			all.add('e');
			break;
		case "C#M":
			all.add('f');
			all.add('c');
			all.add('g');
			all.add('d');
			all.add('a');
			all.add('e');
			all.add('b');
			break;
		case "FM":
			all.add('b');
			break;
		case "BbM":
			all.add('b');
			all.add('e');
			break;
		case "EbM":
			all.add('b');
			all.add('e');
			all.add('a');
			break;
		case "AbM":
			all.add('b');
			all.add('e');
			all.add('a');
			all.add('d');
			break;
		case "DbM":
			all.add('b');
			all.add('e');
			all.add('a');
			all.add('d');
			all.add('g');
			break;
		case "GbM":
			all.add('b');
			all.add('e');
			all.add('a');
			all.add('d');
			all.add('g');
			all.add('c');
			break;
		case "CbM":
			all.add('b');
			all.add('e');
			all.add('a');
			all.add('d');
			all.add('g');
			all.add('c');
			all.add('f');
			break;
		case "Am":
			break;
		case "Em":
			all.add('f');
			break;
		case "Bm":
			all.add('f');
			all.add('c');
			break;
		case "F#m":
			all.add('f');
			all.add('c');
			all.add('g');
			break;
		case "C#m":
			all.add('f');
			all.add('c');
			all.add('g');
			all.add('d');
			break;
		case "G#m":
			all.add('f');
			all.add('c');
			all.add('g');
			all.add('d');
			all.add('a');
			break;
		case "D#m":
			all.add('f');
			all.add('c');
			all.add('g');
			all.add('d');
			all.add('a');
			all.add('e');
			break;
		case "A#m":
			all.add('f');
			all.add('c');
			all.add('g');
			all.add('d');
			all.add('a');
			all.add('e');
			all.add('b');
			break;
		case "Dm":
			all.add('b');
			break;
		case "Gm":
			all.add('b');
			all.add('e');
			break;
		case "Cm":
			all.add('b');
			all.add('e');
			all.add('a');
			break;
		case "Fm":
			all.add('b');
			all.add('e');
			all.add('a');
			all.add('d');
			break;
		case "Bbm":
			all.add('b');
			all.add('e');
			all.add('a');
			all.add('d');
			all.add('g');
			break;
		case "Ebm":
			all.add('b');
			all.add('e');
			all.add('a');
			all.add('d');
			all.add('g');
			all.add('c');
			break;
		case "Abm":
			all.add('b');
			all.add('e');
			all.add('a');
			all.add('d');
			all.add('g');
			all.add('c');
			all.add('f');
			break;
		}
		return all;
	}
	public static ArrayList<Note> keySigNotes(String key, clef myClef) {
		ArrayList<Note> n = new ArrayList<Note>();
		if (sharpKey(key)) {
			if (myClef == clef.treble) {
				for (Character c : accInKey(key)) {
					int oct = 0;
					if (c == 'a' || c == 'b') {
						oct = 4;
					}
					else {
						oct = 5;
					}
					n.add(new Note(c,oct,accidental.sharp,1));
				}
			}
			else if (myClef == clef.bass) {
				for (Character c : accInKey(key)) {
					int oct = 0;
					if (c == 'b' || c == 'a') {
						oct = 2;
					}
					else {
						oct = 3;
					}
					n.add(new Note(c,oct,accidental.sharp,1));
				}
			}
		}
		else {
			if (myClef == clef.treble) {
				for (Character c : accInKey(key)) {
					int oct = 0;
					if (c == 'c' || c == 'd' || c == 'e') {
						oct = 5;
					}
					else {
						oct = 4;
					}
					n.add(new Note(c,oct,accidental.flat,1));
				}
			}
			else if (myClef == clef.bass) {
				for (Character c : accInKey(key)) {
					int oct = 0;
					if (c == 'c' || c == 'd' || c == 'e') {
						oct = 3;
					}
					else {
						oct = 2;
					}
					n.add(new Note(c,oct,accidental.flat,1));
				}
			}
		}
		return n;
	}
}
