package org.kde9.model.card;

import javax.imageio.ImageIO;

import org.kde9.util.Constants;

import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;

public class Image 
implements Constants {
	private BufferedImage image;
	private BufferedImage scaleImage;
	private boolean ready = false;
	
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public BufferedImage getScaleImage() {
		return scaleImage;
	}
	public void setScaleImage(BufferedImage scaleImage) {
		this.scaleImage = scaleImage;
	}
	public boolean isReady() {
		return ready;
	}
	public void setReady(boolean ready) {
		this.ready = ready;
	}
}
