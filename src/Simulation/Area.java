package Simulation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class Area {
	
	Dot dots[];
	Color color;
	
	public Area(Dot dots[], Color color) {
		this.dots = dots;
		this.color = color;
	}
	
	public int meanZ() {
		return (dots[0].getZ()+dots[1].getZ()+dots[2].getZ()+dots[3].getZ())/4;
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(color);
		int[] xs = {(int)dots[0].x+Simulation.WIDTH/2, (int)dots[1].x+Simulation.WIDTH/2, (int)dots[2].x+Simulation.WIDTH/2, (int)dots[3].x+Simulation.WIDTH/2};
		int[] ys = {(int)dots[0].y+Simulation.HEIGHT/2, (int)dots[1].y+Simulation.HEIGHT/2, (int)dots[2].y+Simulation.HEIGHT/2, (int)dots[3].y+Simulation.HEIGHT/2};
		Polygon p = new Polygon(xs, ys, 4);
		g2.fillPolygon(p);
	}
}
