import java.io.*;
import java.util.*;

public class RGBPixel {
	
	private int rgb;
	short red;
	short green;
	short blue;
	
	public RGBPixel(short red, short green, short blue){
		this.red=red;
		this.green=green;
		this.blue=blue;
		rgb = getRGB();
	}
	
	public RGBPixel(RGBPixel pixel){
		this(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
	}
	
	public RGBPixel(YUVPixel pixel){
		int C = pixel.getY() - 16;
		int D = pixel.getU() - 128;
		int E = pixel.getV() - 128;
		
		red = (short)clip(( 298 * C + 409 * E + 128) >> 8);
		green = (short)clip(( 298 * C - 100 * D - 208 * E + 128) >> 8);
		blue = (short)clip(( 298 * C + 516 * D + 128) >> 8);
		rgb = getRGB();
	}
	
	public short getRed(){return red;}
	public short getGreen(){return green;}
	public short getBlue(){return blue;}
	public void setRed(short r){
		red=r;
		
		rgb = getRGB();
	}
	public void setGreen(short g){
		green=g;
		rgb = getRGB();
	}
	public void setBlue(short b){
		blue=b;
		rgb = getRGB();
	}
	public int getRGB(){
		rgb = (red << 16) + (green << 8) + blue;
		return rgb;
	}
	
	public void setRGB(int value){
		rgb=value;
		blue = (short)(rgb & 0xFF);
		green = (short)((rgb >> 8) & 0xFF);
		red = (short)((rgb >> 16) & 0xFF);
	}
	
	public final void setRGB(short red, short green, short blue){
		this.red= red;
		this.green= green;
		this.blue= blue;
		rgb = getRGB();
	}
	
	public String toString(){
		String str = String.valueOf(red) + " " + String.valueOf(green) + " " + String.valueOf(blue); 
		return str;
	}
	
	public int clip(int val){
		
		if(val<0){
			val=0;
		}
		else if(val>255){
			val=255;
		}
		return val;
	}
}
