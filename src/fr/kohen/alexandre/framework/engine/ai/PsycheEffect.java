package fr.kohen.alexandre.framework.engine.ai;

import java.util.Hashtable;

/**
 * Effect on a NPC Psyche
 * @author Alexandre
 */
public class PsycheEffect {
	public int happiness, stress, fear, guilt;
	public Hashtable<String, Relation> relations;

	public PsycheEffect() {
		this.happiness 	= 0;
		this.stress 	= 0;
		this.fear 		= 0;
		this.guilt 		= 0;
		relations = new Hashtable<String, Relation>();
	}
	
	public PsycheEffect(int happiness, int stress, int fear,  int guilt) {
		this.happiness 	= happiness;
		this.stress 	= stress;
		this.fear 		= fear;
		this.guilt 		= guilt;
		relations = new Hashtable<String, Relation>();
	}
	
	public void changeRelation(String name, int attachment, int animosity, int fear, int status, int trust) {
		relations.put(name, new Relation(attachment, animosity, fear, status, trust));
	}
	
}