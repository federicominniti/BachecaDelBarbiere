import com.thoughtworks.xstream.*;

class ServizioDisponibile{
    public String servizio[];
            
    public ServizioDisponibile(String servizio[]){
        this.servizio = servizio;
    }
}

class DimensioniFinestra{
    public double larghezza;
    public double altezza;
        
    public DimensioniFinestra(double larghezza, double altezza){
        this.larghezza = larghezza;
        this.altezza = altezza;
    }
        
    public DimensioniFinestra(){
        larghezza = 1000.0;
        altezza = 700.0;
    }
}

class DatiAccessoArchivio{
    public String ipArchivio;
    public String usernameArchivio;
    public String passwordArchivio;
    public int portaArchivio;
      
    public DatiAccessoArchivio(String ipArchivio, String usernameArchivio, String passwordArchivio, int portaArchivio){
        this.ipArchivio = ipArchivio;
        this.usernameArchivio = usernameArchivio;
        this.passwordArchivio = passwordArchivio;
        this.portaArchivio = portaArchivio;
    }
        
    public DatiAccessoArchivio(){
        this.ipArchivio = "127.0.0.1";
        this.usernameArchivio = "root";
        this.passwordArchivio = "";
        this.portaArchivio = 3306;
    }
}

public class ConfigurazioneLocaleXML{
    private final static int APPUNTAMENTI_VISIBILI_DEFAULT = 9,
                             PORTA_SERVER_LOG_DEFAULT = 6789, 
                             DIMENSIONE_FONT_DEFAULT = 11,
                             GIORNI_PERIODO_DEFAULT = 90;
    private final static double DIMENSIONI_FINESTRA_DEFAULT[] = {1000.0, 700.0};
    private final static String IP_CLIENT_DEFAULT = "127.0.0.1",
                                IP_SERVER_LOG_DEFAULT = "127.0.0.1",
                                FONT_DEFAULT = "Arial",
                                SFONDO_FINESTRA_DEFAULT = "LIGHTGRAY",
                                SFONDO_INSERISCI_DEFAULT = "LIGHTGREEN",
                                SFONDO_ELIMINA_DEFAULT = "LIGHTCORAL",
                                SFONDO_FATTO_DEFAULT = "LIGHTBLUE",
                                SERVIZI_DISPONIBILI[] = {"Taglio", "Barba", "Colore"} ;
    
    public final int numeroAppuntamentiVisibili, portaServerLog, giorniPeriodoDefault;
    public final double dimensioneFont;
    public final String font, sfondoFinestra, sfondoInserisci, sfondoElimina, sfondoFatto,
                        ipClient, ipServerLog;
    public final DimensioniFinestra dimensioniFinestra;
    public final ServizioDisponibile serviziDisponibili;
    public final DatiAccessoArchivio datiAccessoArchivio;
    
    public ConfigurazioneLocaleXML(String xml) {
        if(xml != null){ //00
            ConfigurazioneLocaleXML contenutoXML = 
                    (ConfigurazioneLocaleXML)costruisciOggettoXStream().fromXML(xml); 
            numeroAppuntamentiVisibili = contenutoXML.numeroAppuntamentiVisibili;
            font = contenutoXML.font;
            dimensioneFont = contenutoXML.dimensioneFont;
            dimensioniFinestra = new DimensioniFinestra(contenutoXML.dimensioniFinestra.larghezza,
                                                            contenutoXML.dimensioniFinestra.altezza);
            sfondoFinestra = contenutoXML.sfondoFinestra;
            sfondoInserisci = contenutoXML.sfondoInserisci;
            sfondoElimina = contenutoXML.sfondoElimina;
            sfondoFatto = contenutoXML.sfondoFatto;
            ipClient = contenutoXML.ipClient;
            ipServerLog = contenutoXML.ipServerLog;
            portaServerLog = contenutoXML.portaServerLog;
            giorniPeriodoDefault = contenutoXML.giorniPeriodoDefault;
            datiAccessoArchivio = new DatiAccessoArchivio(contenutoXML.datiAccessoArchivio.ipArchivio,
                                                            contenutoXML.datiAccessoArchivio.usernameArchivio,
                                                            contenutoXML.datiAccessoArchivio.passwordArchivio,
                                                            contenutoXML.datiAccessoArchivio.portaArchivio);
            serviziDisponibili = new ServizioDisponibile(contenutoXML.serviziDisponibili.servizio);
        } else{
            numeroAppuntamentiVisibili = APPUNTAMENTI_VISIBILI_DEFAULT;
            font = FONT_DEFAULT;
            dimensioneFont = DIMENSIONE_FONT_DEFAULT;
            dimensioniFinestra = new DimensioniFinestra();
            sfondoFinestra = SFONDO_FINESTRA_DEFAULT;
            sfondoInserisci = SFONDO_INSERISCI_DEFAULT;
            sfondoElimina = SFONDO_ELIMINA_DEFAULT;
            sfondoFatto = SFONDO_FATTO_DEFAULT;
            ipClient = IP_CLIENT_DEFAULT;
            ipServerLog = IP_SERVER_LOG_DEFAULT;
            portaServerLog = PORTA_SERVER_LOG_DEFAULT;
            giorniPeriodoDefault = GIORNI_PERIODO_DEFAULT;
            datiAccessoArchivio = new DatiAccessoArchivio();
            serviziDisponibili = new ServizioDisponibile(SERVIZI_DISPONIBILI);
        }
    }

    public final XStream costruisciOggettoXStream() {
        XStream xs = new XStream();
        xs.useAttributeFor(ConfigurazioneLocaleXML.class, "ipClient");
        xs.useAttributeFor(ConfigurazioneLocaleXML.class, "ipServerLog");
        xs.useAttributeFor(ConfigurazioneLocaleXML.class, "portaServerLog");
        xs.useAttributeFor(ConfigurazioneLocaleXML.class, "numeroAppuntamentiVisibili");
        xs.useAttributeFor(ConfigurazioneLocaleXML.class, "giorniPeriodoDefault");
        xs.addImplicitCollection(ServizioDisponibile.class, "servizio");
        return xs;
    }
}

/* 
Note:

00 Se il file XML è stato validato, il costruttore usa i parametri del file XML 
   altrimenti se il file di configuazione manca o non viene validato il 
   costruttore usa i valori di default.
*/