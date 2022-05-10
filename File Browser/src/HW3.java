import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class HW3 extends javax.swing.JFrame {
    int widthMainPanel = 370;
    FileFunctions function;
    File curFile;
    File selectedFile = null;
    File pasteFileTo = null;
    File cutFile = null;
    File copyFile = null;
    JMenuItem cut;
    JMenuItem copy;
    JMenuItem paste;
    JMenuItem rename;
    JMenuItem delete;
    JMenuItem addToFav;
    Component c;
    Component oldC = null;
    int width, height;
    
    public HW3() {
        curFile = new File(System.getProperty("user.home"));
        function = new FileFunctions();
        addMenu();
        initComponents();
    }
    
    private void initComponents() {
        favScrollPane = new javax.swing.JScrollPane();
        favPane = new javax.swing.JPanel();
        textSrch = new javax.swing.JTextField();
        buttonSrch = new javax.swing.JButton();
        curFilesPanel = new javax.swing.JPanel();
        pathPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        favPane = getFavPanel();
        favPane.setLayout(new javax.swing.BoxLayout(favPane, javax.swing.BoxLayout.Y_AXIS));
        
        favScrollPane.setViewportView(favPane);
        
        textSrch.setEnabled(false);
        textSrch.setVisible(false);
        textSrch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSrchActionPerformed(evt);
            }
        });

        buttonSrch.setText("Search");
        buttonSrch.setEnabled(false);
        buttonSrch.setVisible(false);
        buttonSrch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSrchActionPerformed(evt);
            }
        });
        
        curFilesPanel = addCurFiles(curFile);
        curFilesPanel.addComponentListener(new ComponentAdapter( ) {
            public void componentResized(ComponentEvent ev) {
                curFilesPanelComponentListener(ev);
            }
        });
        javax.swing.GroupLayout curFilesPanelLayout = new javax.swing.GroupLayout(curFilesPanel);
        curFilesPanel.setLayout(curFilesPanelLayout);
        curFilesPanelLayout.setHorizontalGroup(
            curFilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, width, Short.MAX_VALUE)
        );
        curFilesPanelLayout.setVerticalGroup(
            curFilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, height, Short.MAX_VALUE)
        );
        mainScrollPane = new javax.swing.JScrollPane(curFilesPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainScrollPane.setPreferredSize(new Dimension(370,370));
        mainScrollPane.setViewportView(curFilesPanel);
        
        pathPanel = getPath(curFile);
        pathPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(favScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textSrch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonSrch, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(mainScrollPane)
                    .addComponent(pathPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textSrch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSrch, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pathPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainScrollPane))
            .addComponent(favScrollPane)
        );

        pack();
    }
    
    private void curFilesPanelComponentListener(ComponentEvent ev){
        if(widthMainPanel != curFilesPanel.getWidth()){
            changeCurFiles(curFile);
        }
    }
    
    private void buttonSrchActionPerformed(java.awt.event.ActionEvent evt) {
        
        Search S = new Search(textSrch.getText() , curFile);
        curFilesPanel = createSearchPanel(S.getFileList());
        curFilesPanel.setLayout(new javax.swing.BoxLayout(curFilesPanel, javax.swing.BoxLayout.Y_AXIS));
        mainScrollPane.setViewportView(curFilesPanel);
    }

    private void textSrchActionPerformed(java.awt.event.ActionEvent evt) {
        buttonSrchActionPerformed(evt);
    }
    
    private void pathButtonActionPerformed(java.awt.event.ActionEvent evt) {
        while(! curFile.getName().equals(evt.getActionCommand())){
            curFile = curFile.getAbsoluteFile().getParentFile();
            if(curFile == null){
                File[] root = File.listRoots();
                for (File file : root){
                    if(file.getPath().equals(evt.getActionCommand()+ File.separator)){
                        curFile = file;
                    }
                }
                break;
            }
        }
        changeCurFiles(curFile);
        changePath(curFile);
    }
    
    private void curFilesActionPerformed(java.awt.event.MouseEvent evt, File file) throws IOException{
        c = evt.getComponent();
        if((SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 1) || (SwingUtilities.isRightMouseButton(evt) && evt.getClickCount() == 1)){
            selectedFile = file;
            cut.setEnabled(true);
            copy.setEnabled(true);
            rename.setEnabled(true);
            delete.setEnabled(true);
            addToFav.setEnabled(true);
            if(cutFile!=null || copyFile != null){
                pasteFileTo = selectedFile;
            }
            if(oldC==null){
                c.setBackground(Color.gray);
                oldC = c;
            }
            else{
                oldC.setBackground(null);
                c.setBackground(Color.gray);
                oldC = c;
            }
            if(SwingUtilities.isRightMouseButton(evt) && evt.getClickCount() == 1){
                JPopupMenu editPopup = new JPopupMenu();
                addPopupMenu(editPopup);
                editPopup.show(c, evt.getX(), evt.getY());
            }
        }
        else if(SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() > 1){
            if(file.isDirectory()){
                curFile = file;
                selectedFile = null;
                if(cutFile == null && copyFile == null){
                    cut.setEnabled(false);
                    copy.setEnabled(false);
                    rename.setEnabled(false);
                    delete.setEnabled(false);
                    addToFav.setEnabled(false);
                }
                changeCurFiles(curFile);
                changePath(curFile);
            }
            else if(file.isFile()){
                function.openThisFile(file);
            }
        }
    }
    
    public static void main(String args[]) {
       java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HW3().setVisible(true);
            }
        });
    }
    
    private javax.swing.JButton buttonSrch;
    private javax.swing.JPanel curFilesPanel;
    private javax.swing.JPanel favPane;
    private javax.swing.JScrollPane favScrollPane;
    private javax.swing.JScrollPane mainScrollPane;
    private javax.swing.JPanel pathPanel;
    private javax.swing.JTextField textSrch;
     
    public void addMenu() {
        JMenuBar bar = new JMenuBar();
        
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        bar.add(file);
        
        JMenuItem newWindow = new JMenuItem("New Window");
        JMenuItem exit = new JMenuItem("Exit");
        file.add(newWindow);
        file.add(exit);
        
        newWindow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new HW3().setVisible(true);
                    }
                });
            }
        });
        
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
        
        JMenu edit = new JMenu("Edit");
        edit.setMnemonic(KeyEvent.VK_E);
        bar.add(edit);
        
        cut = new JMenuItem("Cut");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");
        rename = new JMenuItem("Rename");
        delete = new JMenuItem("Delete");
        addToFav = new JMenuItem("Add to Favourites");
        cut.setEnabled(false);
        copy.setEnabled(false);
        rename.setEnabled(false);
        delete.setEnabled(false);
        addToFav.setEnabled(false);
        JMenuItem prop = new JMenuItem("Properties");
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        paste.setEnabled(false);
        edit.add(rename);
        edit.add(delete);
        edit.add(addToFav);
        edit.add(prop);
        
        cut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                cutFile = selectedFile;
                c.setBackground(Color.lightGray);
                cut.setEnabled(false);
                copy.setEnabled(false);
                rename.setEnabled(false);
                delete.setEnabled(false);
                addToFav.setEnabled(false);
                paste.setEnabled(true);
            }
        });
        
        copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                copyFile = selectedFile;
                c.setBackground(Color.lightGray);
                cut.setEnabled(false);
                copy.setEnabled(false);
                rename.setEnabled(false);
                delete.setEnabled(false);
                addToFav.setEnabled(false);
                paste.setEnabled(true);
            }
        });
        
        paste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                File file;
                String str;
                if(pasteFileTo == null){
                    pasteFileTo = curFile;
                }
                if(cutFile != null){
                    file = cutFile;
                    str = "cut";
                }
                else{
                    file = copyFile;
                    str = "copy";
                }
                try {
                    Paste pas = new Paste();
                    Boolean op = pas.replaceOption(file, pasteFileTo, str);
                    if(op){
                        pas.replace(file, pasteFileTo, str);
                    }
                } catch (IOException e) {
                    System.out.println("Paste Message: ");
                    e.printStackTrace();
                }
                changeCurFiles(curFile);
                cutFile = null;
                copyFile = null;
                selectedFile = null;
                pasteFileTo = null;
                cut.setEnabled(false);
                copy.setEnabled(false);
                rename.setEnabled(false);
                delete.setEnabled(false);
                addToFav.setEnabled(false);
                paste.setEnabled(false);
            }
        });
        
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Delete d = new Delete(selectedFile);
                if(d.getMessage(selectedFile)){
                    changeCurFiles(curFile);
                    selectedFile = null;
                    cut.setEnabled(false);
                    copy.setEnabled(false);
                    rename.setEnabled(false);
                    delete.setEnabled(false);
                    addToFav.setEnabled(false);
                }
            }
        });
        
        rename.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Rename r = new Rename(selectedFile);
                if(r.getNewName()!= null){
                    File newName = new File(selectedFile.getParentFile().getPath() + File.separator + r.getNewName());
                    selectedFile.renameTo(newName);
                    changeCurFiles(curFile);
                    selectedFile = null;
                }
            }
        });
        
        prop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                boolean change = true; 
                if(selectedFile == null){
                    selectedFile = curFile;
                    change = false;
                }
                Properties p = new Properties(selectedFile);
                p.setPermissions(selectedFile);
                if(change){
                    changeCurFiles(curFile);
                }
                else{
                    curFile=curFile.getParentFile();
                    changeCurFiles(curFile);
                    changePath(curFile);
                }
                selectedFile = null;
            }
        });
        
        addToFav.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if(selectedFile == null){
                    selectedFile = curFile;
                }
                Favourites F = new Favourites();
                F.addFavFile(selectedFile);
                changeFavPanel();
                selectedFile = null;
            }
        });
        
        JMenuItem search = new JMenuItem("Search");
        search.setMnemonic(KeyEvent.VK_S);
        bar.add(search);
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if(buttonSrch.isEnabled() && textSrch.isEnabled()){
                    buttonSrch.setEnabled(false);
                    textSrch.setEnabled(false);
                    buttonSrch.setVisible(false);
                    textSrch.setVisible(false);
                }
                else if(!buttonSrch.isEnabled() && !textSrch.isEnabled()){
                    buttonSrch.setEnabled(true);
                    textSrch.setEnabled(true);
                    buttonSrch.setVisible(true);
                    textSrch.setVisible(true);
                }
            }
        });
        
        setJMenuBar(bar);
    }
    
    public JPanel addCurFiles(File file) {
        width = 0;
        height = 0;
        JPanel curFiles = new JPanel();
        JLabel fileLabel;
        String description;
        ImageIcon icon;
        File[] f;
        
        f = function.alphabetical (file.listFiles());
        
        for (File pathname : f){
            if((pathname.isFile() || pathname.listFiles()!=null)){
                description = pathname.getName().substring(pathname.getName().lastIndexOf('.') + 1);
                description += ".png";
                icon = new ImageIcon("./icons/" + description);
                if (!function.iconExist(description)) {
                    icon = new ImageIcon("./icons/question.png");
                }
                if(pathname.isDirectory()){
                    icon = new ImageIcon("./icons/folder.png");
                }
                fileLabel = new javax.swing.JLabel();
                fileLabel.setOpaque(true);
                fileLabel.setIcon(icon);
                fileLabel.setText(pathname.getName());
                fileLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
                fileLabel.setHorizontalTextPosition(JLabel.CENTER);
                fileLabel.setVerticalTextPosition(JLabel.BOTTOM);
                fileLabel.setBounds(width, height, 100, 90);
                
                width += fileLabel.getWidth();
                width += 5;
                if((width + fileLabel.getWidth()) > widthMainPanel){
                   height += fileLabel.getHeight();
                    width = 0;
                }
                
                fileLabel.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent evt) {
                        try {
                            curFilesActionPerformed(evt, pathname);
                        } catch (IOException e) {
                            System.out.println("Message AddCurFiles: ");
                            e.printStackTrace();
                        }
                    }
                });
                
                curFiles.add(fileLabel);
            }
        }
        curFiles.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if(selectedFile!=null && pasteFileTo==null){
                    selectedFile = null;
                    cutFile=null;
                    cut.setEnabled(false);
                    copy.setEnabled(false);
                    rename.setEnabled(false);
                    delete.setEnabled(false);
                    addToFav.setEnabled(false);
                    c.setBackground(null);
                }
            }
        });
        return curFiles;
    }
    
    public JPanel getPath(File file) {
        JPanel absolutePath = new JPanel();
        absolutePath.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        String path;
        JButton pathButton;
        String absPath = file.getAbsolutePath();
        absPath = absPath.replaceAll("/", " >");        //Linux
        String[] splitPath = absPath.split("\\\\");        //Windows
        absPath = absPath.replaceAll("\\\\", " >");
        while(absPath.length() != 0){
            path = absPath.split(" >")[0];
            
            if(absPath.length()>= (path.length() + 2)){
                absPath = absPath.substring(path.length() + 2);
            }
            else{
                absPath = absPath.substring(path.length());
            }
            if(path.length() != 0){
                pathButton = new JButton(path);
                absolutePath.add(pathButton);
                pathButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        pathButtonActionPerformed(evt);
                    }
                });
                if(absPath.length()!=0){
                    absolutePath.add(new JLabel(" >"));
                }
            }
        }
        return absolutePath;
    }
    
    public void changeCurFiles(File file){
        widthMainPanel = curFilesPanel.getWidth();
        
        curFilesPanel = addCurFiles(file);
        
        curFilesPanel.addComponentListener(new ComponentAdapter( ) {
            public void componentResized(ComponentEvent ev) {
                curFilesPanelComponentListener(ev);
            }
        });
        
        javax.swing.GroupLayout curFilesPanelLayout = new javax.swing.GroupLayout(curFilesPanel);
        curFilesPanel.setLayout(curFilesPanelLayout);
        curFilesPanelLayout.setHorizontalGroup(
            curFilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, width, Short.MAX_VALUE)
        );
        curFilesPanelLayout.setVerticalGroup(
            curFilesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, height, Short.MAX_VALUE)
        );
        
        mainScrollPane.setViewportView(curFilesPanel);
    }
    
    public void changeFavPanel(){
        favPane = getFavPanel();
        favPane.setLayout(new javax.swing.BoxLayout(favPane, javax.swing.BoxLayout.Y_AXIS));
        favScrollPane.setViewportView(favPane);
    }
    
    public void changePath(File file){
        pathPanel.removeAll();
        pathPanel.repaint();
        pathPanel.revalidate();
        
        pathPanel.add(getPath(file));
        pathPanel.repaint();
        pathPanel.revalidate();
        pathPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
    }
    
    public void addPopupMenu(JPopupMenu editPopup){
        JMenuItem cutPopup = new JMenuItem("Cut");
        JMenuItem copyPopup = new JMenuItem("Copy");
        JMenuItem pastePopup = new JMenuItem("Paste");
        JMenuItem renamePopup =new JMenuItem("Rename");
        JMenuItem deletePopup =new JMenuItem("Delete");
        JMenuItem addToFavPopup =new JMenuItem("Add to Favourites");
        JMenuItem propPopup  = new JMenuItem("Properties");
        cutPopup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                cutFile = selectedFile;
                c.setBackground(Color.lightGray);
                cut.setEnabled(false);
                copy.setEnabled(false);
                rename.setEnabled(false);
                delete.setEnabled(false);
                addToFav.setEnabled(false);
                paste.setEnabled(true);
            }
        });
        copyPopup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                copyFile = selectedFile;
                c.setBackground(Color.lightGray);
                cut.setEnabled(false);
                copy.setEnabled(false);
                rename.setEnabled(false);
                delete.setEnabled(false);
                addToFav.setEnabled(false);
                paste.setEnabled(true);
            }
        });
        pastePopup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                File file;
                String str;
                pasteFileTo = curFile;//panta epikollhsh ston curFile
                if(cutFile != null){
                    file = cutFile;
                    str = "cut";
                }
                else{
                    file = copyFile;
                    str = "copy";
                }
                try {
                    Paste pas = new Paste();
                    Boolean op = pas.replaceOption(file, pasteFileTo, str);
                    if(op){
                        pas.replace(file, pasteFileTo, str);
                    }
                } catch (IOException e) {
                    System.out.println("Paste Message: ");
                    e.printStackTrace();
                }
                changeCurFiles(curFile);
                cutFile = null;
                copyFile = null;
                selectedFile = null;
                pasteFileTo = null;
                cut.setEnabled(false);
                copy.setEnabled(false);
                rename.setEnabled(false);
                delete.setEnabled(false);
                addToFav.setEnabled(false);
                paste.setEnabled(false);
            }
        });
        deletePopup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Delete d = new Delete(selectedFile);
                if(d.getMessage(selectedFile)){
                    changeCurFiles(curFile);
                    selectedFile = null;
                    cut.setEnabled(false);
                    copy.setEnabled(false);
                    rename.setEnabled(false);
                    delete.setEnabled(false);
                    addToFav.setEnabled(false);
                }
            }
        });
        renamePopup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Rename r = new Rename(selectedFile);
                if(r.getNewName()!= null){
                    File newName;
                    newName = new File(selectedFile.getParentFile().getPath() + File.separator + r.getNewName());
                    selectedFile.renameTo(newName);
                    changeCurFiles(curFile);
                    selectedFile = null;
                }
            }
        });
        propPopup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                boolean change = true; 
                if(selectedFile == null){
                    selectedFile = curFile;
                    change = false;
                }
                Properties p = new Properties(selectedFile);
                p.setPermissions(selectedFile);
                if(change){
                    changeCurFiles(curFile);
                }
                else{
                    curFile=curFile.getParentFile();
                    changeCurFiles(curFile);
                    changePath(curFile);
                }
                selectedFile = null;
            }
        });
        
        addToFavPopup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if(selectedFile == null){
                    selectedFile = curFile;
                }
                Favourites F = new Favourites();
                F.addFavFile(selectedFile);
                changeFavPanel();
                selectedFile = null;
            }
        });
        
        editPopup.add(cutPopup);
        editPopup.add(copyPopup);
        editPopup.add(pastePopup);
        editPopup.add(renamePopup);
        editPopup.add(deletePopup);
        editPopup.add(addToFavPopup);
        editPopup.add(propPopup);
        
        if(cutFile == null && copyFile == null){
            pastePopup.setEnabled(false);
        }
    }
    
    public JPanel createSearchPanel(File[] files){
        JPanel searchPanel = new JPanel();
        JLabel fileLabel;
        String description;
        ImageIcon icon;
        for (File f : files) {
            if((f.isFile() || f.listFiles() != null)){
                description = f.getName().substring(f.getName().lastIndexOf('.') + 1);
                description += ".png";
                icon = new ImageIcon("./icons/" + description);
                if (!function.iconExist(description)) {
                    icon = new ImageIcon("./icons/question.png");
                }
                if(f.isDirectory()){
                    icon = new ImageIcon("./icons/folder.png");
                }
                fileLabel = new javax.swing.JLabel();
                fileLabel.setOpaque(true);
                fileLabel.setIcon(icon);
                fileLabel.setText(f.getPath());
                fileLabel.setHorizontalTextPosition(JLabel.RIGHT);
                fileLabel.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent evt) {
                        if(f.isFile()){
                            try {
                                function.openThisFile(f);
                            } catch (IOException e) {
                                System.out.println("Message Open: ");
                                e.printStackTrace();
                            }
                        }
                        else{
                            curFile = f;
                            changeCurFiles(curFile);
                            changePath(curFile);
                        }
                    }
                });
                
                searchPanel.add(fileLabel);
            }
        }
        return searchPanel;
    }
    
    public JPanel getFavPanel(){
        JPanel favPanel = new JPanel();
        JLabel label;
        Favourites F = new Favourites();
        String str = F.geXMLData();
        String filePath;
        JLabel favLabel = new JLabel();
        favLabel.setFont(new java.awt.Font("Arial", 1, 14));
        favLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        favLabel.setText("Favourites");
        favLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        favLabel.setAlignmentY(0.0F);
        favPanel.add(favLabel);
        
        while(str.length() != 0){
            filePath = str.split(" > ")[0];
            label = new JLabel("File: " + filePath);
            label.setOpaque(true);
            label.setName(filePath);
            label.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    if(SwingUtilities.isLeftMouseButton(evt)){
                        Component com = evt.getComponent();
                        com.setBackground(Color.gray);
                        curFile = new File(com.getName());
                        changeCurFiles(curFile);
                        changePath(curFile);
                        com.setBackground(null);
                    }
                    else if(SwingUtilities.isRightMouseButton(evt)){
                        String deleleFilePath = evt.getComponent().getName();
                        JPopupMenu favPopup = new JPopupMenu();
                        addFavPopup(favPopup, deleleFilePath);
                        favPopup.show(evt.getComponent(), evt.getX(), evt.getY());
                    }
                }
            });
            favPanel.add(label);
            
            str = str.substring(filePath.length() + 3);
        }
        return favPanel;
    }
    
    public void addFavPopup(JPopupMenu favPopup, String deleleFilePath){
        JMenuItem deleteFav = new JMenuItem("Delete");
        deleteFav.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Favourites F = new Favourites();
                F.deleteFavFile(deleleFilePath);
                changeFavPanel();
            }
        });
        favPopup.add(deleteFav);
    }
}
