package fr.kohen.alexandre.framework.spatials;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Vector2f;

import fr.kohen.alexandre.framework.components.Transform;
import fr.kohen.alexandre.framework.engine.Camera;
import fr.kohen.alexandre.framework.engine.Spatial;

public class TextSpatial extends Spatial {
	private UnicodeFont uFont;
	private String 		text;
	private int 		lineWidth = -1;

	@SuppressWarnings("unchecked")
	public TextSpatial() {
		super();
		text = "";
		try {
			this.uFont = new UnicodeFont(new Font("Verdana", Font.PLAIN, 20) , 20, false, false);
			this.uFont.addAsciiGlyphs();   		//Add Glyphs
			this.uFont.addGlyphs(400, 600); 	//Add Glyphs
			this.uFont.getEffects().add(new ColorEffect()); //Add Effects
			this.uFont.loadGlyphs();  			//Load Glyphs
		} catch (SlickException e) { e.printStackTrace(); }
		color = Color.white;
	}
	
	public TextSpatial(String text) {
		this();
		this.text = text;
	}
	
	public TextSpatial(String text, int lineWidth) {
		this(text);
		this.lineWidth = lineWidth;
	}

	/**
	 * Sets the number of characters to display per line
	 * @param width
	 */
	public void setLineWidth(int width) {
		this.lineWidth = width;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public void initalize() { }

	@Override
	public void render(Graphics g, Transform transform, Camera camera, Color color) {
		Vector2f shift = camera.getPosition();
		if( lineWidth > 0 ) {
			int lines = (int) Math.ceil( (float) text.length()/lineWidth);
			for (int line = 0; line<lines; line ++) {
				uFont.drawString(
						transform.getX()+shift.x, 
						transform.getY()+shift.y + uFont.getLineHeight()*line, 
						text.substring(line*lineWidth, Math.min(text.length(),(line+1)*lineWidth)), 
						color);
			}
		}
		else this.uFont.drawString(transform.getX()+shift.x, transform.getY()+shift.y, this.text, color);
		
	}

}
