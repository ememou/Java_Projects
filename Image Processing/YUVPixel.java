public class YUVPixel {
	private int yuv;
	short Y;
	short U;
	short V;
	
	public YUVPixel(short y, short u, short v){
		Y = y;
		U = u;
		V = v;
		yuv = getYUV();
	}
	
	public YUVPixel(YUVPixel pixel){
		this(pixel.getY(), pixel.getU(), pixel.getV());
	}
	
	public YUVPixel(RGBPixel pixel){
		Y = (short)(((66*pixel.getRed() + 129*pixel.getGreen() +  25*pixel.getBlue() + 128) >> 8) +  16);
		U = (short)(((-38*pixel.getRed() - 74*pixel.getGreen() + 112*pixel.getBlue() + 128) >> 8) + 128);
		V = (short)(((112*pixel.getRed() - 94*pixel.getGreen() - 18*pixel.getBlue() + 128) >> 8) + 128);
		yuv = getYUV();
	}
	
	short getY(){return Y;}
	short getU(){return U;}
	short getV(){return V;}
	void setY(short y){
		Y = y;
		yuv = getYUV();
	}
	void setU(short u){
		U = u;
		yuv = getYUV();
	}
	void setV(short v){
		V = v;
		yuv = getYUV();
	}
	
	public int getYUV(){
		yuv = (Y << 16) + (U << 8) + V;
		return yuv;
	}
	
	public void setYUV(int yuv){ 
		this.yuv = yuv;
		V = (short)(yuv & 0xFF);
		U = (short)((yuv >> 8) & 0xFF);
		Y = (short)((yuv >> 16) & 0xFF);
	}
	
}
