import java.io.*;
import java.util.*;

public class YUVImage {
	
	YUVPixel image[][];
	int width;
	int height;
	Histogram histogram;
	
	public YUVImage(int width, int height){
		image = new YUVPixel [height][width];
		this.width=width;
		this.height=height;
		
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++){
				image[i][j] = new YUVPixel((short) 16, (short) 128, (short) 128);
			}
		}
	}
	
	public YUVImage(YUVImage copyImg){
		this(copyImg.width, copyImg.height);
		image = new YUVPixel[height][width];
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				image[i][j] = copyImg.image[i][j];
			}
		}
	}
	
	public YUVImage(RGBImage RGBImg){
		width = RGBImg.width;
		height = RGBImg.height;
		image = new YUVPixel [height][width];
		
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				image[i][j] = new YUVPixel(RGBImg.image[i][j]);
			}
		}
	}
	
	public YUVImage(java.io.File file) throws FileNotFoundException, UnsupportedFileFormatException {
		
		try(Scanner sc = new Scanner(new FileReader(file))) {
			
			if (sc.hasNext()) { 
				String str = sc.next();
				if(!str.equals("YUV3")){
					throw new UnsupportedFileFormatException();
				}
			}
			
			if(sc.hasNext()){
				width = Integer.valueOf(sc.next());
			}
			
			if(sc.hasNext()){
				height = Integer.valueOf(sc.next());
			}
			
			image = new YUVPixel[height][width];
			
			
			for(int i=0; i<height; i++){
				for(int j=0; j<width; j++){
					short Y, U, V;
					
					if(sc.hasNext()){
						Y = Short.valueOf(sc.next());
						U = Short.valueOf(sc.next());
						V = Short.valueOf(sc.next());
						image[i][j] = new YUVPixel(Y, U, V);
					}
				}
			}
		}
	}
	
	public String toString(){
		String str = "YUV3 " + String.valueOf(width) + " " + String.valueOf(height) + " ";
		
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				str +=  String.valueOf(image[i][j].getY()) + " " + String.valueOf(image[i][j].getU()) + " " + String.valueOf(image[i][j].getV()) + " ";
			}
		}
		return str;
	}
	
	public void toFile(java.io.File file){
		
		try {
			FileWriter writer = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(writer);
			
			out.write("YUV3");
			out.newLine();
			out.write(String.valueOf(width) + " " + String.valueOf(height));
			out.newLine();
			
			for(int i=0; i<height; i++){
				for(int j=0; j<width; j++){
					out.write(String.valueOf(image[i][j].getY()) + " " + String.valueOf(image[i][j].getU()) + " " + String.valueOf(image[i][j].getV()) + " ");
					out.newLine();
				}
			}
			out.close();
		} catch(IOException ex) {
			System.err.println("Unable to open file for reading!");
		}
	}
	
	public void equalize(){
		
		histogram = new Histogram(this);
		
		histogram.equalize();
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				image[i][j].setY(histogram.getEqualizedLuminocity(image[i][j].getY())); 
			}
		}
	}
	
}
