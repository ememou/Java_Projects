import java.awt.GridLayout;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import javax.swing.*;

public class Properties{
    JOptionPane pane;
    long sFolder = 0;
    JCheckBox read;
    JCheckBox write;
    JCheckBox execute;
    JCheckBox hidden;
    
    public Properties(File file){
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Title: " + file.getName());
        JLabel pos = new JLabel("Position: " + file.getAbsolutePath());
        JLabel size;
        JLabel sizeOnDisk;
        
        if(file.isFile()){
            long s = 0;
            while(s < file.length()){
                s +=4096;
            }
            size = new JLabel("Size: " + file.length() + " bytes");
            sizeOnDisk = new JLabel("Size On Disk: " + s + " bytes");
        }
        else{
            size = new JLabel("Size: " + folderSize(file) + " bytes");
            sizeOnDisk = new JLabel("Size On Disk: " + sFolder + " bytes");
        }
        
        JPanel permissions = new JPanel();
        JLabel per = new JLabel("Permissions: ");
        
        JPanel permissionsPanel = new JPanel();
        permissionsPanel.setLayout(new GridLayout(4, 1));
        
        execute = new JCheckBox("Execute", file.canExecute());
        if(file.setExecutable(!file.canExecute())){
           file.setExecutable(!file.canExecute());
        }
        else{
            execute.setEnabled(false);
        }
        
        read = new JCheckBox("Read", file.canRead());
        if(file.setReadable(!file.canRead())){
            file.setReadable(!file.canRead());
        }
        else{
            read.setEnabled(false);
        }
        
        write = new JCheckBox("Write", file.canWrite());
        if(file.setWritable(!file.canWrite())){
            file.setWritable(!file.canWrite());
        }
        else{
            write.setEnabled(false);
        }
        
        hidden  = new JCheckBox("Hidden", file.isHidden());
        
        
        permissionsPanel.add(execute);
        permissionsPanel.add(read);
        permissionsPanel.add(write);
        permissionsPanel.add(hidden);
        permissions.add(permissionsPanel);
        info.add(title);
        info.add(pos);
        info.add(size);
        info.add(sizeOnDisk);
        info.add(per);
        info.add(permissions);
        pane = new JOptionPane(info, 
                JOptionPane.PLAIN_MESSAGE, 
                JOptionPane.OK_CANCEL_OPTION, 
                null);
        
        JDialog dialog = pane.createDialog("Properties");
        
        dialog.addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        info.requestFocusInWindow();
                    }
                });
            }
        });
        
        dialog.setVisible(true);
        dialog.dispose();
    }
    
    public long folderSize(File directory) {
        long length = 0;
        long s = 0;
        File[] fileList;
        
        fileList = directory.listFiles();
        
        if(fileList == null){
            return 0;
        }
        
        for (File file : fileList) {
            if (file.isFile()){
                while(s < file.length()){
                    s +=4096;
                }
                length += file.length();
                sFolder += s;
                s = 0;
            }
            else{
                length += folderSize(file);
            }
         }
        return length;
    }
    
    public void setPermissions(File file){
        Object value = pane.getValue();
        int result;
        if (value instanceof Integer){
            result = (int)value;
            if (result == JOptionPane.OK_OPTION){
                file.setExecutable(execute.isSelected());
                file.setReadable(read.isSelected());
                file.setWritable(write.isSelected());
                
                if((hidden.isSelected() && !file.isHidden()) || (!hidden.isSelected() && file.isHidden())){
                   setFileHidden(file,hidden.isSelected());
                }
            }
        }
    }
    
    public void setFileHidden(File file, boolean goHidden){
        if (file.getPath().contains("/")) { // a '.' prefix makes the file hidden
            String fileName = file.getName();
            if (goHidden) {
                fileName = "." + fileName;
            }
            else{
                fileName = fileName.substring(1);
            }
            File newName = new File(file.getParentFile().getPath() + File.separator + fileName);
            file.renameTo(newName);
        }
        else{
            try {
                Files.setAttribute(file.toPath(), "dos:hidden", goHidden, LinkOption.NOFOLLOW_LINKS);
            } catch (IOException e) {
                System.out.println("Set Hidden Message: ");
                e.printStackTrace();
            }
        }
    }
}
