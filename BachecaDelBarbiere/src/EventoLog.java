import com.thoughtworks.xstream.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class EventoLog implements Serializable{ //00
    private String nomeApplicazione, ipClient, timestamp, nomeEvento;
    
    public EventoLog(String nomeEvento, String ipClient) {
        nomeApplicazione = "BachecaDelBarbiere";
        this.ipClient = ipClient;
        timestamp = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date());
        this.nomeEvento = nomeEvento;
    }
    
    public String serializza(){
        XStream xs = new XStream();
        xs.useAttributeFor(EventoLog.class, "nomeApplicazione");
        xs.useAttributeFor(EventoLog.class, "ipClient");
        return xs.toXML(this) + "\n";
    }
}

/*
Note:

00 Elemento di log da inviare al server di log
*/