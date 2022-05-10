import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


public class FileFunctions {
    
    FileFunctions(){}
    
    public File[] alphabetical(File[] files){
        Collection<File> dirList  =new ArrayList<File>();
        Collection<File> fileList  =new ArrayList<File>();
        File temp;
        
        for(File f : files){
            if(f.isFile()){
                fileList.add(f);
            }
            else{
                dirList.add(f);
            }
        }
        
        File[] folders = dirList.toArray(new File[dirList.size()]);
        File[] file = fileList.toArray(new File[fileList.size()]);
        
        for(int i=0; i<file.length; i++){
            for(int j=1; j<(file.length-i); j++){
                if(file[j-1].getName().compareToIgnoreCase(file[j].getName())>0){
                    temp = file[j-1];
                    file[j-1] = file[j];
                    file[j] = temp;
                }
            }
        }
        for(int i=0; i<folders.length; i++){
            for(int j=1; j<(folders.length-i); j++){
                if(folders[j-1].getName().compareToIgnoreCase(folders[j].getName())>0){
                    temp = folders[j-1];
                    folders[j-1] = folders[j];
                    folders[j] = temp;
                }
            }
        }
        Collection<File> list  =new ArrayList<File>();
        for(int i=0; i<folders.length; i++){
            list.add(folders[i]);
        }
        for(int i=0; i<file.length; i++){
            list.add(file[i]);
        }
        File[] allfiles = list.toArray(new File[list.size()]);
        
        return allfiles;
    }
    
    public void openThisFile(File file) throws IOException{
        if(!Desktop.isDesktopSupported()){
            System.out.println("Desktop is not supported");
            return;
        }
        Desktop desktop = Desktop.getDesktop();
        desktop.open(file);
    }
    
    public boolean iconExist(String description){
        File file = new File("icons");
        String[] icons = file.list();
        
        for (String name : icons) {
            if(description.equals(name)){
                return true;
            }
        }
        return false;
    }
}
