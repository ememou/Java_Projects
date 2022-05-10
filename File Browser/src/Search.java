import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class Search {
    FileFunctions function;
    String text, type;
    Collection<File> list;
    public Search(String input, File cur){
        function = new FileFunctions();
        text = input.split(" ")[0];
        list =new ArrayList<File>();
        input = input.substring(text.length());
        if(input.length()!=0){
            input = input.substring(" type:".length());
            type = input;
        }
        else{
            type = null;
        }
        searchresults(cur);
    }
    
    public void searchresults(File cur){
        
        if(cur.getName().toLowerCase().contains(text.toLowerCase())){
            if(type==null){
                list.add(cur);
            }
            else if(correctType(cur)){
                list.add(cur);
            }
        }
        if(cur.isDirectory()){
            File[] f;
            f = cur.listFiles();
            for(File file : f){
                if(file.isFile() || file.listFiles() != null){
                    searchresults(file);
                }
            }
        }
    }
    
    public boolean correctType(File cur){
        String description = cur.getName().substring(cur.getName().lastIndexOf('.') + 1);
        if(description.equals(type)){
            return true;
        }
        else if(type.equals("dir") && cur.isDirectory()){
            return true;
        }
        
        return false;
    }
    
    public File[] getFileList(){
        File[] fileList = list.toArray(new File[list.size()]);
        return fileList;
    }
}
