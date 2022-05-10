import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Favourites {
    File xmlFile;
    
    public Favourites(){
        File fav = new File(System.getProperty("user.home"));
        File jFB = new File(fav.getPath() + File.separator +".java-file-browser");
        xmlFile = new File(jFB.getPath() + File.separator + "properties.xml");
        if (!jFB.exists() || !xmlFile.exists()){
            jFB.mkdir();
            createXML(fav.getPath() + " > ");
        }
    }
    
    public void createXML(String data){
        String path;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.newDocument();
            Element e = d.createElement("favourites");
            d.appendChild(e);
            
            while(data.length() != 0){
                path = data.split(" > ")[0];
                Element file = d.createElement("file");
                Element name = d.createElement("name");
                name.appendChild(d.createTextNode(path));
                file.appendChild(name);
                e.appendChild(file);
                data = data.substring(path.length() + 3);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(d);
            StreamResult streamResult = new StreamResult(xmlFile);
            transformer.transform(domSource, streamResult);
        } catch (Exception e) {
            System.out.println("Message Create XMl: ");
            e.printStackTrace();
        }
    }
    
    public void addFavFile(File file){
        String data = geXMLData();
        data += file.getPath() + " > ";
        createXML(data);
    }
    
    public String geXMLData(){
        String data = "";
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse(xmlFile);
            NodeList list = d.getElementsByTagName("file");
            for(int i=0; i<list.getLength(); i++){
                Node node = list.item(i);
                Element e = (Element) node;
                data += e.getElementsByTagName("name").item(0).getTextContent();
                data += " > ";
            }
        } catch (Exception e) {
            System.out.println("Message GetXML: ");
            e.printStackTrace();
        }
        return data;
    }
    
    public void deleteFavFile(String filePath){
        String xmlData = geXMLData();
        String deleteData = filePath + " > ";
        if(!filePath.equals(System.getProperty("user.home"))){
            xmlData = xmlData.replaceAll(deleteData, "");
        }
        createXML(xmlData);
    }
}
