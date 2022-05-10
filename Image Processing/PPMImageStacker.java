import java.io.*;
import java.lang.*;
import java.util.*;

public class PPMImageStacker{
	
	List <PPMImage> imageList = new ArrayList<PPMImage>();
	PPMImage image;
	
	public PPMImageStacker(java.io.File dir) throws FileNotFoundException, UnsupportedFileFormatException {
		File myDir = new File(dir.getPath());
		
		if(!(myDir.exists())){
			System.out.println("[ERROR] Directory " + dir.getName() + " does not exist!");
			throw new FileNotFoundException();
		}
		else{
			if(!(myDir.isDirectory())){
				System.out.println("[ERROR] " + dir.getName() + " is not a directory!");
				throw new UnsupportedFileFormatException();
			}
		}
		
		for (File file : myDir.listFiles()) { 
			if (file.isFile()) {
				PPMImage node = new PPMImage(file);
				imageList.add(node);
			}
		}
	}
	
	
	public void stack(){
		
		image = new PPMImage(imageList.get(0));
		int [][]temp = new int[imageList.get(0).height][imageList.get(0).width];
		
		for(int i=0; i<imageList.get(0).height; i++){
			for(int j=0; j<imageList.get(0).width; j++){
				for(int k=0; k<imageList.size(); k++){
					temp[i][j] += imageList.get(k).image[i][j].getRGB();
				}
				image.image[i][j].setRGB(temp[i][j]/imageList.size());
			}
		}
	}
	
	public PPMImage getStackedImage(){
		return image;
	}
	
}

