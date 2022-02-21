import java.io.*;
import javafx.application.*;

public class GestoreCache{
    public static void salvaSuFileBinario(InterfacciaBachecaDelBarbiere interfacciaBachecaDelBarbiere){ 
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("cache_locale.bin"))) { //02
            DatiCache datiCache = new DatiCache(interfacciaBachecaDelBarbiere);
            outputStream.writeObject(datiCache);
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static void caricaDaFileBinario(InterfacciaBachecaDelBarbiere interfacciaBachecaDelBarbiere,
                                                ConfigurazioneLocaleXML configurazioneXml){ 
        DatiCache datiDaCaricare = null;
        int rigaAppuntamento;
        Appuntamento ultimoAppuntamento;
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("cache_locale.bin"))){
            datiDaCaricare = (DatiCache)inputStream.readObject();
        } catch(IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        if(datiDaCaricare == null){ //00
            datiDaCaricare = new DatiCache();
            ultimoAppuntamento = new Appuntamento();
        }
        else{
            ultimoAppuntamento = new Appuntamento(datiDaCaricare.getCliente(), datiDaCaricare.getServizio(), 
                    datiDaCaricare.getDataAppuntamento(), datiDaCaricare.getOraAppuntamento(), 
                    datiDaCaricare.getMinutiAppuntamento(), "NO");
        }
        rigaAppuntamento = datiDaCaricare.getSelezione();
        interfacciaBachecaDelBarbiere.periodo.setPeriodo(configurazioneXml, datiDaCaricare.getDataInizio(), 
                                                            datiDaCaricare.getDataFine());
        interfacciaBachecaDelBarbiere.tabellaPrenotazioni.inserisciUltimoAppuntamento(ultimoAppuntamento);
        if(datiDaCaricare.getSelezione() != -1){
            Platform.runLater(() -> { //01
                interfacciaBachecaDelBarbiere.tabellaPrenotazioni.getTabellaAppuntamenti().requestFocus();
                interfacciaBachecaDelBarbiere.tabellaPrenotazioni.getTabellaAppuntamenti().getSelectionModel()
                        .select(rigaAppuntamento); 
            });
        }
    }
}

/* 
Note:

00 Se non è presente la cache locale, verrà aggiunta una riga vuota per l'inserimento
   di nuovi appuntamenti
01 Esegui il Runnable specificato sul thread dell'applicazione JavaFX in un 
   momento non specificato in futuro. Questo metodo, che può essere chiamato da 
   qualsiasi thread, invierà il Runnable a una coda di eventi e quindi tornerà 
   immediatamente al chiamante. 
   Nel nostro caso il Runnable consiste nel richiedere il focus sulla tabella e 
   selezionare la riga della tabella che era stata selezionata nella precedente 
   sessione di utilizzo dell'applicazione
   https://docs.oracle.com/javase/8/javafx/api/javafx/application/Platform.html#runLater-java.lang.Runnable-
*/
