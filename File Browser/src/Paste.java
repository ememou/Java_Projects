import javax.swing.JOptionPane;
import javax.swing.JDialog;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.*;

public class Paste {
    
    public Paste(){}
        
    public boolean replaceOption(File file, File pasteFileTo, String op) throws IOException{
        if(op == "cut"){
            if(fileExists(file, pasteFileTo)){
                return true;
            }
            else{
                file.renameTo(new File(pasteFileTo.getPath() + File.separator + file.getName()));
            }
        }
        else{
            if(fileExists(file, pasteFileTo)){
                return true;
            }
            else{
                if(file.isFile()){
                    copy(file, new File(pasteFileTo.getPath() + File.separator + file.getName()));
                }
                else{
                    copyFolder(file, new File(pasteFileTo.getPath() + File.separator + file.getName()));
                }
            }
        }
        return false;
    }
    
    public void copyFolder(File source, File destination) throws IOException{
        if (source.isDirectory()) {
            if (!destination.exists()){
                destination.mkdir();
            }
            String files[] = source.list();
            for (String file : files){
                File srcFile = new File(source, file);
                File destFile = new File(destination, file);
                copyFolder(srcFile, destFile);
            }
        }
        else{
            copy(source, destination);
        }
    }
    
    public void copy(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }
    
    public boolean fileExists(File file, File pasteFileTo){
        String files[] = pasteFileTo.list();
        
        for (String f : files){
            if(f.equals(file.getName())){
                return true;
            }
        }
        return false;
    }
    
    public void replace(File file, File pasteFileTo, String op) throws IOException{
        JOptionPane pane;
        JLabel message = new JLabel("The file already exists. Do you want to overwrite it?");
        pane = new JOptionPane(message, 
                JOptionPane.PLAIN_MESSAGE, 
                JOptionPane.YES_NO_OPTION, 
                null);
        
        JDialog dialog = pane.createDialog("Overwrite");
        dialog.addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        message.requestFocusInWindow();
                    }
                });
            }
        });
        
        dialog.setVisible(true);
        dialog.dispose();
        
        Object value = pane.getValue();
        int result;
        
        if (value instanceof Integer) {
            result = (int)value;
            if (result == JOptionPane.YES_OPTION) {
                Delete delete = new Delete();
                File oldFile = new File(pasteFileTo.getPath() + File.separator + file.getName());
                delete.deleteFile(oldFile);
                if(op == "cut"){
                    file.renameTo(new File(pasteFileTo.getPath() + File.separator + file.getName()));
                }
                else{
                     if(file.isFile()){
                         copy(file, new File(pasteFileTo.getPath() + File.separator + file.getName()));
                    }
                    else{
                         copyFolder(file, new File(pasteFileTo.getPath() + File.separator + file.getName()));
                    }
                }
            }
        }
    }
}
