import java.io.File;
        import javax.xml.parsers.DocumentBuilder;
        import javax.xml.parsers.DocumentBuilderFactory;
        import javax.xml.parsers.ParserConfigurationException;
        import javax.xml.transform.Transformer;
        import javax.xml.transform.TransformerException;
        import javax.xml.transform.TransformerFactory;
        import javax.xml.transform.dom.DOMSource;
        import javax.xml.transform.stream.StreamResult;
        import org.w3c.dom.Attr;
        import org.w3c.dom.Document;
        import org.w3c.dom.Element;

public class XmlFile  {

    public static final String xmlFilePath = System.getProperty("user.dir")+"/xmlfile.xml";

    public DocumentBuilderFactory documentFactory ;
    public DocumentBuilder documentBuilder ;
    public Document document ;
    public static Element root ;

    public XmlFile(String rootName) throws ParserConfigurationException {
         documentFactory = DocumentBuilderFactory.newInstance();

         documentBuilder = documentFactory.newDocumentBuilder();

         document = documentBuilder.newDocument();

        root = document.createElement(rootName);
        document.appendChild(root);
    }

    public  Element appendXMLroot(Element root, String element){
        // employee element
        Element sociale = document.createElement(element);
        root.appendChild(sociale);
        return  sociale;
    }

    public void appendXMLattribut(Element element, String attribut, String values){
        Attr attr = document.createAttribute(attribut);
        attr.setValue(values);
        element.setAttributeNode(attr);
    }

    public void buildXMLfile(){
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

}