import java.util.*;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.stage.*;

public class BachecaDelBarbiere extends Application {
    private InterfacciaBachecaDelBarbiere interfacciaBachecaDelBarbiere;
    private ConfigurazioneLocaleXML configurazioneXml;
    private ArchivioAppuntamenti gestoreArchivio;
    
    public void start(Stage stage) {
        configurazioneXml =
            new ConfigurazioneLocaleXML(
                GestoreFileXML.valida("configurazione_locale.xml", "schema_configurazione_locale.xsd") ?
                    GestoreFileXML.carica("configurazione_locale.xml") : null
            );
        gestoreArchivio = new ArchivioAppuntamenti(configurazioneXml.datiAccessoArchivio.ipArchivio,
                                                   configurazioneXml.datiAccessoArchivio.portaArchivio,
                                                   configurazioneXml.datiAccessoArchivio.usernameArchivio,
                                                   configurazioneXml.datiAccessoArchivio.passwordArchivio);
        GestoreEventiLog.inviaLog("AVVIO", configurazioneXml.ipClient, configurazioneXml.ipServerLog, 
                                    configurazioneXml.portaServerLog);
        interfacciaBachecaDelBarbiere = new InterfacciaBachecaDelBarbiere(configurazioneXml);
        GestoreCache.caricaDaFileBinario(interfacciaBachecaDelBarbiere,configurazioneXml);
        aggiornaAppuntamenti(false);
        setEventiInterfaccia();
        stage.setOnCloseRequest((WindowEvent eventoFinestra) -> {
            GestoreEventiLog.inviaLog("TERMINE", configurazioneXml.ipClient, configurazioneXml.ipServerLog,
                                        configurazioneXml.portaServerLog);
            GestoreCache.salvaSuFileBinario(interfacciaBachecaDelBarbiere);
        });
        stage.setTitle("Bacheca del barbiere");
        Scene scene = new Scene(interfacciaBachecaDelBarbiere.interfacciaFinestra, 
                                    configurazioneXml.dimensioniFinestra.larghezza, configurazioneXml.dimensioniFinestra.altezza);
        stage.setScene(scene);
        stage.show();
    }
    
    public void setEventiInterfaccia(){
        interfacciaBachecaDelBarbiere.pulsantiera.getBottoneInserisci().setOnAction((ActionEvent av) -> {
            Appuntamento nuovoAppuntamento = interfacciaBachecaDelBarbiere.tabellaPrenotazioni.getTabellaAppuntamenti()
                                                .getSelectionModel().getSelectedItem().getAppuntamentoAttivo();
            gestoreArchivio.inserisciAppuntamento(nuovoAppuntamento.getCliente(), nuovoAppuntamento.getDataAppuntamento(), 
                                                    nuovoAppuntamento.getOraAppuntamento(), nuovoAppuntamento.getMinutiAppuntamento(),
                                                    nuovoAppuntamento.getServizio(), "NO");
            aggiornaAppuntamenti(true);
            GestoreEventiLog.inviaLog("INSERISCI", configurazioneXml.ipClient, configurazioneXml.ipServerLog,
                                        configurazioneXml.portaServerLog);
        });
        interfacciaBachecaDelBarbiere.pulsantiera.getBottoneElimina().setOnAction((ActionEvent av) -> {
            Appuntamento appuntamentoAttivo = interfacciaBachecaDelBarbiere.tabellaPrenotazioni.getTabellaAppuntamenti()
                                                .getSelectionModel().getSelectedItem().getAppuntamentoAttivo();
            gestoreArchivio.eliminaAppuntamento(appuntamentoAttivo.getCliente(), appuntamentoAttivo.getDataAppuntamento(), 
                    appuntamentoAttivo.getOraAppuntamento(),appuntamentoAttivo.getMinutiAppuntamento());
            aggiornaAppuntamenti(true);
            GestoreEventiLog.inviaLog("ELIMINA", configurazioneXml.ipClient, configurazioneXml.ipServerLog,
                                        configurazioneXml.portaServerLog);
        });  
        interfacciaBachecaDelBarbiere.pulsantiera.getBottoneFatto().setOnAction((ActionEvent av) -> { 
            Appuntamento appuntamentoAttivo = interfacciaBachecaDelBarbiere.tabellaPrenotazioni.getTabellaAppuntamenti()
                                                .getSelectionModel().getSelectedItem().getAppuntamentoAttivo();
            gestoreArchivio.appuntamentoFatto(appuntamentoAttivo.getCliente(), appuntamentoAttivo.getDataAppuntamento(), 
                    appuntamentoAttivo.getOraAppuntamento(),appuntamentoAttivo.getMinutiAppuntamento());
            aggiornaAppuntamenti(true);
            GestoreEventiLog.inviaLog("FATTO", configurazioneXml.ipClient, configurazioneXml.ipServerLog,
                                        configurazioneXml.portaServerLog);
        });
        interfacciaBachecaDelBarbiere.periodo.getCasellaDataInizio().setOnAction((ActionEvent av) -> { 
            aggiornaAppuntamenti(true);
            GestoreEventiLog.inviaLog("SELEZIONE DATA INIZIO", configurazioneXml.ipClient, 
                                        configurazioneXml.ipServerLog, configurazioneXml.portaServerLog);
        });
        interfacciaBachecaDelBarbiere.periodo.getCasellaDataFine().setOnAction((ActionEvent av) -> { 
            aggiornaAppuntamenti(true);
            GestoreEventiLog.inviaLog("SELEZIONE DATA FINE", configurazioneXml.ipClient, 
                                        configurazioneXml.ipServerLog, configurazioneXml.portaServerLog);
        });
    }
    
    public void aggiornaAppuntamenti(boolean evento){ //00
        Date dataInizio = interfacciaBachecaDelBarbiere.periodo.getDateDataInizio();
        Date dataFine = interfacciaBachecaDelBarbiere.periodo.getDateDataFine();
        if(evento)
            interfacciaBachecaDelBarbiere.tabellaPrenotazioni.refreshTabellaAppuntamenti(
                    gestoreArchivio.getAppuntamentiNelPeriodo(dataInizio, dataFine));
        else
            interfacciaBachecaDelBarbiere.tabellaPrenotazioni.inserisciAppuntamenti(
                    gestoreArchivio.getAppuntamentiNelPeriodo(dataInizio, dataFine));
        interfacciaBachecaDelBarbiere.richiestaServizi.refreshRichiestaServizi(
                gestoreArchivio.getServiziFatti(dataInizio, dataFine));
        if(evento){
            interfacciaBachecaDelBarbiere.tabellaPrenotazioni.getTabellaAppuntamenti().getItems()
                .add(new Appuntamento());
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}

/* 
Note:

00 Aggiornamento dell'interfaccia quando viene generato un evento
 */