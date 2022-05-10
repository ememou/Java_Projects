import javax.swing.JOptionPane;
import javax.swing.JDialog;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class Delete {
    JOptionPane pane;
    public Delete(){}
    
    public Delete(File file){
        JLabel d = new JLabel("Do You Want to Delete this File?");
        
        pane = new JOptionPane(d, 
                JOptionPane.PLAIN_MESSAGE, 
                JOptionPane.YES_NO_OPTION, 
                null);
        JDialog dialog = pane.createDialog("Delete");
        
        dialog.addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        d.requestFocusInWindow();
                    }
                });
            }
        });
        
        dialog.setVisible(true);
        dialog.dispose();
    }
    
    public void deleteFile(File file){
        if (file.isDirectory()) {
            File files[] = file.listFiles();
            for (File f : files) {
                 deleteFile(f);
            }
            file.delete();
        }
        else{
            file.delete();
        }
    }
    
    public boolean getMessage(File file){
        Object value = pane.getValue();
        int result;
        
        if (value instanceof Integer) {
            result = (int)value;
            if (result == JOptionPane.YES_OPTION) {
               deleteFile(file);
               return true;
            }
        }
        return false;
    }
}
