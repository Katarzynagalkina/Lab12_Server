import org.w3c.dom.*;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.parsers.ParserConfigurationException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import static javax.xml.transform.OutputKeys.*;



public class Connector {


    static void createXML(String XMLFileName, String menu)  throws ParserConfigurationException, IOException, SAXException, TransformerException
    {
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        DocumentBuilder builder= factory.newDocumentBuilder();
        Document document= builder.newDocument();

        Element message=document.createElement("message");
        Element data=document.createElement("menu");

        document.appendChild(message);
        message.appendChild(data);
        data.setAttribute("menu",menu);

        Transformer t= TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(document),new StreamResult(new FileOutputStream(XMLFileName)));

//в строку

//        DOMImplementation implementation=document.getImplementation();
//        DOMImplementationLS implementationLS=(DOMImplementationLS) implementation.getFeature("LS","3.0");
//        LSSerializer ser=implementationLS.createLSSerializer();
//        ser.getDomConfig().setParameter("format-pretty-print", true);
//        String str=ser.writeToString(document);


    }



     static String readXML(String XMLFileName) throws ParserConfigurationException, IOException, SAXException
     {

        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        DocumentBuilder builder= factory.newDocumentBuilder();
        Document document=builder.parse(new File(XMLFileName));

         SchemaFactory schemaFactoryfactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
         Source schemaFile = new StreamSource(new File("fromClient.xsd"));
         Schema schema=schemaFactoryfactory.newSchema(schemaFile);
         Validator validator = schema.newValidator();

         try {
             validator.validate(new DOMSource(document));
             System.out.println("Successful XML validating");
         } catch (SAXException e) {
               e.printStackTrace();
         }

         String order=null;
        Element element=document.getDocumentElement();
        System.out.println(element.getTagName());
        NodeList nodelist=element.getChildNodes();

        for(int i=0;i<nodelist.getLength();i++) {
            if (nodelist.item(i) instanceof Element) {
              //  System.out.println(((Element) nodelist.item(i)).getTagName()); //выводятся имена тегов
                if(((Element) nodelist.item(i)).hasAttribute("address"))
                    System.out.println( "Client address : " + ((Element) nodelist.item(i)).getAttribute("address"));
                if(((Element) nodelist.item(i)).hasAttribute("number")) {
                    order = ((Element) nodelist.item(i)).getAttribute("number");
                    System.out.println("Numbers of dishes : " + order);
                }
            }
        }
      return order;
     }
}

