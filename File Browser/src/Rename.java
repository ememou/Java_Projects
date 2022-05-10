import javax.swing.JOptionPane;
import javax.swing.JDialog;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class Rename{
    JOptionPane pane;
    JTextField field;
    
    public Rename(File selectedFile){
        String text = selectedFile.getName();
        field = new JTextField(text, 25);
        field.selectAll();
        
        pane = new JOptionPane(field, 
                JOptionPane.PLAIN_MESSAGE, 
                JOptionPane.OK_CANCEL_OPTION, 
                null);
        JDialog dialog = pane.createDialog("Rename");
        
        dialog.addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        field.requestFocusInWindow();
                    }
                });
            }
        });
        
        dialog.setVisible(true);
        dialog.dispose();
    }
    
    public String getNewName(){
        Object value = pane.getValue();
        int result;
        String newName = null;
        
        if (value instanceof Integer) {
            result = (int)value;
            if (result == JOptionPane.OK_OPTION) {
                newName = field.getText();
            }
        }
        return newName;
    }
}
