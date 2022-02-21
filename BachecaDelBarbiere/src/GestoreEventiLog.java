import java.io.*;
import java.net.*;

public class GestoreEventiLog{
    public static void inviaLog(String nomeEvento, String ipClient, String ipServerLog, int portaServerLog){
        EventoLog eventoLog = new EventoLog(nomeEvento, ipClient);
        inviaAlServer(eventoLog, ipServerLog, portaServerLog);
    }
    
    private static void inviaAlServer(EventoLog log, String ipServerLog, int portaServerLog){
         try (Socket socket = new Socket(ipServerLog, portaServerLog);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());) {
            if (GestoreFileXML.valida(log.serializza(), "schema_log.xsd")) {
                outputStream.writeObject(log.serializza());
            } else {
                System.out.println("Errore di validazione evento di log");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
