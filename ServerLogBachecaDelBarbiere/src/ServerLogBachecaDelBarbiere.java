import java.io.*;
import java.net.*;

public class ServerLogBachecaDelBarbiere{

    public static void main(String[] args){
        System.out.println("SERVER ATTIVO \n");
        try(ServerSocket serverSocket = new ServerSocket(8080)){  
            while(true){
                try(Socket socket = serverSocket.accept();
                        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream()); ){
                    String stringaLog = (String) inputStream.readObject();
                    System.out.println(stringaLog);
                    if(GestoreFileXML.valida(stringaLog, "schema_log.xsd")){
                        GestoreFileXML.salva(stringaLog, "log.xml", true); 
                    }
                }
            } 
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
