import java.io.*;
import java.util.*;

public class Histogram {
	int []histogram = new int[256];
	int pixels;
	
	public Histogram(YUVImage img){
		
		pixels = img.height*img.width;
		
		for(int i=0; i<256; i++){
			histogram[i]=0;
		}
		
		for(int i=0; i<img.height; i++){
			for(int j=0; j<img.width; j++){
				histogram[img.image[i][j].getY()]++;
			}
		}
	}
	
	public String toString(){
		
		String str="";
		int num;
			for(int i=0; i<256; i++){
				str += Integer.toString(i);
				num = histogram[i];
				for(int j=0; j<histogram[i]; j++){
					if(num >= 1000){
						str +="#";
						num = num-1000;
					}
					else if(num >= 100){
						str +="$";
						num = num-100;
					}
					else{
						str +="*";
						num--;
					}
				}
				str += "\n";
			}
		return(str);
	}
	
	
	public void toFile(File file){
		
		try {
			FileWriter writer = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(writer);
			String str = toString(); 
			out.write(str);
			
			out.close();
		}catch(IOException ex) {
			System.err.println("Unable to open file for reading!");
		}
	}
	
	public void equalize(){
		
		double []probColorDepth = new double[256];
		double []sumProbColorDepth = new double[256];
		int maxColorDepth = 236;
		
		for(int i=0; i<256; i++){
			probColorDepth[i] = ((float)histogram[i])/pixels;
			for(int j=0; j<=i; j++){
				sumProbColorDepth[i] += probColorDepth[j];
			}
		}
		
		for(int i=0; i<256; i++){
			histogram[i] = (int)(sumProbColorDepth[i]*maxColorDepth);
		}
		
	}
	
	public short getEqualizedLuminocity(int luminocity){
		
		short equalizedLuminocity = (short)histogram[luminocity];
		return (equalizedLuminocity);
	}
}
