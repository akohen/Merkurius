package fr.kohen.alexandre.framework.engine.ai;

public class Relation {
	public int attachment, animosity, fear, status, trust;
	public Relation(int attachment, int animosity, int fear, int status, int trust) {
		this.attachment = attachment;
		this.animosity 	= animosity;
		this.fear 		= fear;
		this.status 	= status;
		this.trust 		= trust;
	}
}
