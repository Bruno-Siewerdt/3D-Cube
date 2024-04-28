package Simulation;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Simulation extends Canvas implements Runnable, MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	public static int WIDTH = 1200;
	public static int HEIGHT = 700;
	public List<Dot> dots;
	public List<Area> areas;
	public int X_init, Y_init;
	Matrix aux;
	double Vangx, Vangy, angx, angy;
	
	public BufferedImage layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	public Simulation() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addMouseListener(this);
		addMouseMotionListener(this);
		dots = new ArrayList<Dot>();
		areas = new ArrayList<Area>();
		createCube();
		double[][] auxMat = new double[4][dots.size()];
		for(int i = 0; i < dots.size(); i++) {
			auxMat[0][i] = dots.get(i).x;
			auxMat[1][i] = dots.get(i).y;
			auxMat[2][i] = dots.get(i).z;
			auxMat[3][i] = 1;
		}
		aux = new Matrix(auxMat);
	}
	
	public static void main(String[] args) {
		Simulation game = new Simulation();
		JFrame frame = new JFrame("Cube");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		new Thread(game).start();
	}
	
	public void tick() {
		for(int i = 0; i < dots.size(); i++) {
			dots.get(i).tick();
		}
		ArrayList<Area> AuxAreas = new ArrayList<Area>();
		AuxAreas.clear();
		for(int i = 0; i < areas.size(); i++) {
			if(i == 0) {
				AuxAreas.add(areas.get(0));
			} else {
				boolean added = false;
				for(int c = 0; c < AuxAreas.size(); c++) {
					if(areas.get(i).meanZ() > AuxAreas.get(c).meanZ()) {
						AuxAreas.add(c, areas.get(i));
						added = true;
						c = AuxAreas.size();
					}
				}
				if(!added) {
					AuxAreas.add(areas.get(i));
				}
			}
		}
		areas.clear();
		for(int i = 0; i < AuxAreas.size(); i++) {
			areas.add(AuxAreas.get(i));
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs==null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = layer.getGraphics();
		
		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		for(int i = 0; i < dots.size(); i++) {
			dots.get(i).render(g);
		}
		for(int i = 0; i < areas.size(); i++) {
			areas.get(i).render(g);
		}
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(layer, 0, 0, WIDTH, HEIGHT, null);
		bs.show();
	}
	
	@Override
	public void run() {
		requestFocus();
		while(true) {
			render();
			tick();
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void createCube() {
		dots.add(new Dot(150, 150, 150));
		dots.add(new Dot(-150, 150, 150));
		dots.add(new Dot(150, -150, 150));
		dots.add(new Dot(-150, -150, 150));
		dots.add(new Dot(150, 150, -150));
		dots.add(new Dot(-150, 150, -150));
		dots.add(new Dot(150, -150, -150));
		dots.add(new Dot(-150, -150, -150));
		dots.get(0).dots.add(dots.get(1));
		dots.get(0).dots.add(dots.get(2));
		dots.get(0).dots.add(dots.get(4));
		dots.get(7).dots.add(dots.get(6));
		dots.get(7).dots.add(dots.get(5));
		dots.get(7).dots.add(dots.get(3));
		dots.get(1).dots.add(dots.get(5));
		dots.get(1).dots.add(dots.get(3));
		dots.get(2).dots.add(dots.get(3));
		dots.get(2).dots.add(dots.get(6));
		dots.get(4).dots.add(dots.get(5));
		dots.get(4).dots.add(dots.get(6));
		
		int opacity = 80;
		Color[] colors= {new Color(255, 0, 0, opacity), new Color(255, 255, 0, opacity), new Color(0, 255, 0, opacity), new Color(0, 255, 255, opacity), new Color(0, 0, 255, opacity), new Color(255, 0, 255, opacity)};
		Dot[] d = {dots.get(0), dots.get(1), dots.get(3), dots.get(2)};
		areas.add(new Area(d, colors[0]));
		Dot[] d2 = {dots.get(0), dots.get(4), dots.get(6), dots.get(2)};
		areas.add(new Area(d2, colors[1]));
		Dot[] d3 = {dots.get(0), dots.get(1), dots.get(5), dots.get(4)};
		areas.add(new Area(d3, colors[2]));
		Dot[] d4 = {dots.get(7), dots.get(6), dots.get(2), dots.get(3)};
		areas.add(new Area(d4, colors[3]));
		Dot[] d5 = {dots.get(7), dots.get(6), dots.get(4), dots.get(5)};
		areas.add(new Area(d5, colors[4]));
		Dot[] d6 = {dots.get(7), dots.get(5), dots.get(1), dots.get(3)};
		areas.add(new Area(d6, colors[5]));
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		X_init = e.getX();
		Y_init = e.getY();
		double[][] auxMat = new double[4][dots.size()];
		for(int i = 0; i < dots.size(); i++) {
			auxMat[0][i] = dots.get(i).x;
			auxMat[1][i] = dots.get(i).y;
			auxMat[2][i] = dots.get(i).z;
			auxMat[3][i] = 1;
			dots.get(i).Vangx = 0;
			dots.get(i).Vangy = 0;
		}
		aux.matrix = auxMat;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		double[][] auxMat = new double[4][dots.size()];
		for(int i = 0; i < dots.size(); i++) {
			auxMat[0][i] = dots.get(i).x;
			auxMat[1][i] = dots.get(i).y;
			auxMat[2][i] = dots.get(i).z;
			auxMat[3][i] = 1;
			dots.get(i).Vangx = Vangx*10;
			dots.get(i).Vangy = Vangy*10;
		}
		aux.matrix = auxMat;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		double prop = 200;
		Vangx = ((e.getY()-Y_init)/prop)-angx;
		Vangy = ((X_init-e.getX())/prop)-angy;
		angx = (e.getY()-Y_init)/prop;
		angy = (X_init-e.getX())/prop;
		for(int i = 0; i < dots.size(); i++) {
			double[][] matP = {{aux.matrix[0][i]}, {aux.matrix[1][i]}, {aux.matrix[2][i]}, {aux.matrix[3][i]}};
			Matrix matPM = new Matrix(matP);
			double[] Rot1 = {Math.cos(angy), Math.sin(angy)*Math.sin(angx), Math.sin(angy)*Math.cos(angx), 0};
			double[] Rot2 = {0, Math.cos(angx), -Math.sin(angx), 0};
			double[] Rot3 = {-Math.sin(angy), Math.cos(angy)*Math.sin(angx), Math.cos(angy)*Math.cos(angx), 0};
			double[] Rot4 = {0, 0, 0, 1};
			double[][] Rot = {Rot1, Rot2, Rot3, Rot4};
			Matrix RotM = new Matrix(Rot);
			Matrix matNewP = Matrix.Mult(RotM, matPM);
			dots.get(i).x = matNewP.matrix[0][0];
			dots.get(i).y = matNewP.matrix[1][0];
			dots.get(i).z = matNewP.matrix[2][0];
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
}
