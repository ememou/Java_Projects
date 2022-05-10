public class RGBImage implements Image{
	public static final int MAX_COLORDEPTH=255;
	
	RGBPixel image[][];
	int width;
	int height;
	int colorDepth;
	
	
	public RGBImage(int width, int height, int colorDepth){
		image = new RGBPixel [height][width];
		this.width=width;
		this.height=height;
		this.colorDepth=colorDepth;
		
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++) {
				image[i][j] = new RGBPixel((short) 0, (short) 0, (short) 0);
			}
		}
	}
	
	public RGBImage(RGBImage copyImg){
		this(copyImg.getWidth(), copyImg.getHeight(), copyImg.getColorDepth());
		image = new RGBPixel[height][width];
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++) {
				image[i][j] = copyImg.image[i][j];
			}
		}
	}
	
	public RGBImage(YUVImage YUVImg){
		width = YUVImg.width;
		height = YUVImg.height;
		colorDepth = 255;
		image = new RGBPixel [height][width];
		
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				image[i][j] = new RGBPixel(YUVImg.image[i][j]);
			}
		}
	}
	
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public int getColorDepth(){return colorDepth;}
	
	public RGBPixel getPixel(int row, int col){return image[row][col];}
	
	public void setPixel(int row, int col, RGBPixel pixel){image[row][col] = pixel;}
	
	public void grayscale(){
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				double gray = getPixel(i,j).getRed() * 0.3 + getPixel(i,j).getGreen() * 0.59 + getPixel(i,j).getBlue() * 0.11;
				getPixel(i,j).setRed((short) gray);
				getPixel(i,j).setGreen((short) gray);
				getPixel(i,j).setBlue((short) gray);
			}
		}
	}
	
	public void doublesize(){
		RGBPixel[][] doubleImage = new RGBPixel [2*height][2*width];
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				doubleImage[2*i][2*j] = image[i][j];
				doubleImage[2*i+1][2*j] = image[i][j];
				doubleImage[2*i][2*j+1] = image[i][j];
				doubleImage[2*i+1][2*j+1] = image[i][j];
			}
		}
		width = 2*width;
		height = 2*height;
		image = doubleImage;
	}
	
	public void halfsize(){
		height=height/2;
		width=width/2;
		RGBPixel [][]half = new RGBPixel [height][width];
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				double redPixel = (getPixel(2*i, 2*j).getRed() + getPixel(2*i+1, 2*j).getRed() + getPixel(2*i, 2*j+1).getRed() + getPixel(2*i+1, 2*j+1).getRed())/4;
				double greenPixel = (getPixel(2*i, 2*j).getGreen() + getPixel(2*i+1, 2*j).getGreen() + getPixel(2*i, 2*j+1).getGreen() + getPixel(2*i+1, 2*j+1).getGreen())/4;
				double bluePixel = (getPixel(2*i, 2*j).getBlue() + getPixel(2*i+1, 2*j).getBlue() + getPixel(2*i, 2*j+1).getBlue() + getPixel(2*i+1, 2*j+1).getBlue())/4;
				half[i][j] = new RGBPixel((short) redPixel, (short) greenPixel, (short) bluePixel);
			}
		}
		image = half;
	}
	
	public void rotateClockwise(){
		RGBPixel [][]rotate = new RGBPixel[width][height];
		int col=height-1;
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				rotate[j][i]=image[col][j];
			}
			col--;
		}
		int temp = height;
		height = width;
		width = temp;
		image = rotate;
	}
}
