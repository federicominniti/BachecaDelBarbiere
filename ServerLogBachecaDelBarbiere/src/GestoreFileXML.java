import java.io.*;
import java.nio.file.*;
import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class GestoreFileXML {
    public static void salva(Object oggetto, String file, boolean append){
        try{
            File fileLog = new File(file); //00
            if(!fileLog.exists()){ //01
                Object intestazione = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!-- " + file + " -->\n";
                fileLog.createNewFile();
                Files.write((Paths.get(file)), intestazione.toString().getBytes(), StandardOpenOption.APPEND);
            }
            Files.write((Paths.get(file)), oggetto.toString().getBytes(), StandardOpenOption.APPEND);
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    public static String carica(String file) {
        try {
            return new String(Files.readAllBytes(Paths.get(file)));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public static boolean valida(String xml, String schemaXsd){
        try {
            DocumentBuilder documentBuilder = 
                    DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Document document;
            if(schemaXsd.compareTo("schema_configurazione_locale.xsd") == 0){ //02
                document = documentBuilder.parse(new File(xml));
            } else{
                document = documentBuilder.parse(new InputSource(new StringReader(xml)));
            }
            Schema schema = schemaFactory.newSchema(new StreamSource(new File(schemaXsd)));
            schema.newValidator().validate(new DOMSource(document));
        } catch(ParserConfigurationException | SAXException | IOException e) {
            if(e instanceof SAXException)
                System.err.println("Errore: impossibile validare. " + e.getMessage()); 
            else
                System.err.println(e.getMessage());
            return false; 
        }
        return true;
    }
}

/*
Note:

00 La classe File classe presenta una visione astratta e indipendente dal sistema 
   dei nomi di percorso gerarchici.
   https://docs.oracle.com/javase/8/docs/api/java/io/File.html
01 Se il file cercato non esiste, questo viene creato
02 Si fa un controllo sullo schema per vedere se l'XML e' da prelevare in un 
   file o meno
*/