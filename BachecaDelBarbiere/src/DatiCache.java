import java.io.*;
import java.time.*;
import java.util.*;

public class DatiCache implements Serializable{ //00
    private LocalDate dataInizio, dataFine;
    private int appuntamentoSelezionato;
    private String cliente, servizio, oraAppuntamento, minutiAppuntamento;
    private Date dataAppuntamento;
    
    private final static LocalDate DATA_INIZIO_DEFAULT = null, 
                                   DATA_FINE_DEFAULT = null;
    private final static int SELEZIONE_DEFAULT = -1;
    private final static Date DATA_DEFAULT = null;
    private final static String CLIENTE_DEFAULT = "",
                                SERVIZIO_DEFAULT = "",
                                ORA_DEFAULT = "", 
                                MINUTI_DEFAULT = "";
    public DatiCache(InterfacciaBachecaDelBarbiere interfacciaBachecaDelBarbiere) {
        dataInizio = interfacciaBachecaDelBarbiere.periodo.getDataInizio();
        dataFine = interfacciaBachecaDelBarbiere.periodo.getDataFine();
        appuntamentoSelezionato = interfacciaBachecaDelBarbiere.tabellaPrenotazioni.getTabellaAppuntamenti()
                                    .getSelectionModel().getSelectedIndex();
        int indiceUltimoAppuntamento = 
                interfacciaBachecaDelBarbiere.tabellaPrenotazioni.getTabellaAppuntamenti().getItems().size();
        interfacciaBachecaDelBarbiere.tabellaPrenotazioni.getTabellaAppuntamenti().requestFocus();
        interfacciaBachecaDelBarbiere.tabellaPrenotazioni.getTabellaAppuntamenti().getSelectionModel()
                .select(indiceUltimoAppuntamento - 1);  
        Appuntamento ultimoAppuntamento = interfacciaBachecaDelBarbiere.tabellaPrenotazioni.getTabellaAppuntamenti()
                                                .getSelectionModel().getSelectedItem().getAppuntamentoAttivo();
        cliente = ultimoAppuntamento.getCliente();
        dataAppuntamento = ultimoAppuntamento.getDataAppuntamento();
        oraAppuntamento = ultimoAppuntamento.getOraAppuntamento();
        minutiAppuntamento = ultimoAppuntamento.getMinutiAppuntamento();
        servizio = ultimoAppuntamento.getServizio();
    }

    public DatiCache(){
        dataInizio = DATA_INIZIO_DEFAULT;
        dataFine = DATA_FINE_DEFAULT;
        appuntamentoSelezionato = SELEZIONE_DEFAULT;
        cliente = CLIENTE_DEFAULT;
        servizio = SERVIZIO_DEFAULT;
        oraAppuntamento = ORA_DEFAULT;
        minutiAppuntamento = MINUTI_DEFAULT;
        dataAppuntamento = DATA_DEFAULT;
    }
    
    public LocalDate getDataInizio(){ return dataInizio; }
    public LocalDate getDataFine(){ return dataFine; }
    public int getSelezione(){ return appuntamentoSelezionato; }
    public String getCliente(){ return cliente; }
    public String getServizio(){ return servizio; }
    public String getOraAppuntamento(){ return oraAppuntamento; }
    public String getMinutiAppuntamento(){ return minutiAppuntamento; }
    public Date getDataAppuntamento(){ return dataAppuntamento; }
}

/*
Note:

00 Dati che vanno a formare la cache locale, vengono salvati alla chiusura 
   dell'applicazione e ricaricati quando viene nuovamente aperta 
*/