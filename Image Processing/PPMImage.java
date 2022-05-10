import java.io.*;
import java.util.*;

public class PPMImage extends RGBImage {
	
	public PPMImage(java.io.File file) throws FileNotFoundException, UnsupportedFileFormatException{
		
		super(0, 0, 0);
		
		try(Scanner sc = new Scanner(new FileReader(file))) {
			if (sc.hasNext()) {
				String str = sc.next();
				if(!str.equals("P3")){ throw new UnsupportedFileFormatException();}
			}
			
			if(sc.hasNext()){width = Integer.valueOf(sc.next());}
			if(sc.hasNext()){height = Integer.valueOf(sc.next());}
			
			if(sc.hasNext()){
				colorDepth = Integer.valueOf(sc.next());
				if(colorDepth>255 || colorDepth == 0){
					colorDepth=255;
				}
			}
			
			image = new RGBPixel[height][width];
			
			for(int i=0; i<height; i++){
				for(int j=0; j<width; j++){
					image[i][j] = new RGBPixel((short) 0, (short) 0, (short) 0);
					if(sc.hasNext()){
						image[i][j].setRed(Short.valueOf(sc.next()));
					}
					if(sc.hasNext()){
						image[i][j].setGreen(Short.valueOf(sc.next()));
					}
					if(sc.hasNext()){
						image[i][j].setBlue(Short.valueOf(sc.next()));
					}
				}
			}
		}
	
	}
	
	public PPMImage(RGBImage img){
		super(img);
	}
	
	public PPMImage(YUVImage img){
		super(img);
	}
	
	public String toString(){
		String str = "P3 " + String.valueOf(width) + " " + String.valueOf(height) + " " + String.valueOf(colorDepth) + " ";
		
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				str +=  String.valueOf(image[i][j].getRed()) + " " + String.valueOf(image[i][j].getGreen()) + " " + String.valueOf(image[i][j].getBlue()) + " ";
			}
		}
		return str;
	}
	
	public void toFile(java.io.File file){
		
		try {
			FileWriter writer = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(writer);
			
			out.write("P3");
			out.newLine();
			out.write(String.valueOf(width) + " " + String.valueOf(height));
			out.newLine();
			out.write(String.valueOf(colorDepth));
			out.newLine();
			
			for(int i=0; i<height; i++){
				for(int j=0; j<width; j++){
					out.write(String.valueOf(image[i][j].getRed()) + " " + String.valueOf(image[i][j].getGreen()) + " " + String.valueOf(image[i][j].getBlue()) + " ");
				}
			out.newLine();
			}
			out.close();
		} catch(IOException ex) {
			System.err.println("Unable to open file for reading!");
		}
		
	}
}
