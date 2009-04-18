package org.kde9.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JTextField;

public class SearchTextField
extends JTextField {
	BufferedImage image;
	TexturePaint paint;
	
	public SearchTextField(File file) 
	throws FileNotFoundException, IOException {
		super();
		image = ImageIO.read(file);
		Rectangle rect = new Rectangle(0, 0,
				image.getWidth(), image.getHeight());
		paint = new TexturePaint(image, rect);
		setOpaque(false);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(paint);
		g.fillRect(2, 2, getWidth()-2, getHeight()-2);
		super.paintComponent(g);
	}
	
}
