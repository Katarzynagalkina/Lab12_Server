import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    public static void main(String[] args) {
        try (ServerSocket server= new ServerSocket(3345)){
            while(true){
                Socket client = server.accept();
                new Thread(()->{
                    try {
                        String menu="Our menu:\n" +
                                    "\t\t1.Beef steak\n\t" +
                                    "\t2.Pancake\n" +
                                    "\t\t3.Borsch\n" +
                                    "\t\t4.Bouillon\n" +
                                    "\t\t5.Open sandwich\n" +
                                    "\t\t6.Curd\n" +
                                    "\t\t7.Beetroot salad\n" +
                                    "\t\t8.Hamburger\n";

                        Connector.createXML("toClient1.xml", menu);
                        System.out.println("Delivery info: ");
                        String order= Connector.readXML("fromClient.xml");
                        Connector.createXML("toClient2.xml",order);
                    } catch (IOException | ParserConfigurationException | TransformerException | SAXException e) {
                        e.printStackTrace();
                    }
                }).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}