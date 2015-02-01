package cat.imar.ipussy.game;

import java.io.Serializable;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Region;

public class Clitoris implements Serializable {

	private static final long serialVersionUID = -6287519981570789171L;

	private float x;
	private float y;
	private float radi = 15;
	private float radiMax = 22;
	private float radiMin = 12;
	private int color = Color.WHITE;
	private Region regionMovemnt;
	private float move = 1;
	private boolean flagAnimate = true;

	public Clitoris() {
		super();
	}

	public Clitoris(int x, int y, float radi, int color, Region regionMovemnt) {
		super();
		this.x = x;
		this.y = y;
		this.radi = radi;
		this.color = color;
		this.regionMovemnt = regionMovemnt;

	}

	public void changeValues(Float x, Float y, float radi, Integer color,
			Region regionMovemnt) {
		if (x != null) {
			setX(x);
		}
		if (y != null) {
			setY(y);
		}
		if(radi != 0){
			setRadi(radi);	
		}
		
		
		if (color != null) {
			setColor(color);
		}
		if (regionMovemnt != null) {
			setRegionMovemnt(regionMovemnt);
		}
	}

	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getRadi() {
		return radi;
	}

	public void setRadi(float radi) {
		this.radi = radi;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Region getRegionMovemnt() {
		return regionMovemnt;
	}

	public void setRegionMovemnt(Region regionMovemnt) {
		this.regionMovemnt = regionMovemnt;
	}

	public boolean isInRegion(int x, int y) {
		return regionMovemnt.contains((int)getX(), (int)getY());
	}

	public Paint getPaint() {
		Paint p = new Paint();
		p.setStyle(Style.FILL);
		p.setColor(getColor());
		return p;
	}

	public float getMove() {
		return move;
	}

	public void setMove(float move) {
		this.move = move;
	}

	public float getRadiMax() {
		return radiMax;
	}

	public void setRadiMax(float radiMax) {
		this.radiMax = radiMax;
	}

	public float getRadiMin() {
		return radiMin;
	}

	public void setRadiMin(float radiMin) {
		this.radiMin = radiMin;
	}

	public boolean isFlagAnimate() {
		return flagAnimate;
	}

	public void setFlagAnimate(boolean flagAnimate) {
		this.flagAnimate = flagAnimate;
	}
}
