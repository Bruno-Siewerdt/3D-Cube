package Simulation;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Dot {
	
	public double x, y, z;
	public List<Dot> dots;
	public List<Dot[]> dotsSquare;
	double Vangx = 0;
	double Vangy = 0;
	
	public Dot(double px, double py, double pz) {
		this.x = px;
		this.y = py;
		this.z = pz;
		dots = new ArrayList<Dot>();
		dotsSquare = new ArrayList<Dot[]>();
	}
	
	public int getX() {
		return (int)x;
	}
	
	public int getY() {
		return (int)y;
	}
	
	public int getZ() {
		return (int)z;
	}
	
	double module(double x) {
		if(x < 0) {
			return -x;
		}
		return x;
	}
	
	public void tick() {
		if(module(Vangx) > 0.001) {
			Vangx*= 0.98;
		} else {
			Vangx = 0;
		}
		if(module(Vangy) > 0.001) {
			Vangy*= 0.98;
		} else {
			Vangy = 0;
		}
		double angx = Vangx;
		double angy = Vangy;
		double[][] matP = {{this.x}, {this.y}, {this.z}, {1}};
		Matrix matPM = new Matrix(matP);
		double[] Rot1 = {Math.cos(angy), Math.sin(angy)*Math.sin(angx), Math.sin(angy)*Math.cos(angx), 0};
		double[] Rot2 = {0, Math.cos(angx), -Math.sin(angx), 0};
		double[] Rot3 = {-Math.sin(angy), Math.cos(angy)*Math.sin(angx), Math.cos(angy)*Math.cos(angx), 0};
		double[] Rot4 = {0, 0, 0, 1};
		double[][] Rot = {Rot1, Rot2, Rot3, Rot4};
		Matrix RotM = new Matrix(Rot);
		Matrix matNewP = Matrix.Mult(RotM, matPM);
		this.x = matNewP.matrix[0][0];
		this.y = matNewP.matrix[1][0];
		this.z = matNewP.matrix[2][0];
	}
	
	public void render(Graphics g) {
		/*g.setColor(Color.white);
		for(int i = 0; i < dots.size(); i++){
			Dot d = dots.get(i);
			g.drawLine(this.getX()+Simulation.WIDTH/2, this.getY()+Simulation.HEIGHT/2, d.getX()+Simulation.WIDTH/2, d.getY()+Simulation.HEIGHT/2);
		}*/
	}
}
