import java.time.*;
import java.util.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class Periodo{
    private Label etichettaPeriodo;
    private Label etichettaDal;
    private DatePicker dataInizio;
    private Label etichettaAl;
    private DatePicker dataFine;
    private HBox rigaPeriodo;
    private VBox colonnaPeriodo; 
    
    public Periodo(){
        etichettaPeriodo = new Label("Periodo");
        etichettaDal = new Label("Dal");
        dataInizio = new DatePicker();
        etichettaAl = new Label("Al");
        dataFine = new DatePicker();
        rigaPeriodo = new HBox();
        colonnaPeriodo = new VBox();
        rigaPeriodo.getChildren().addAll(etichettaDal, dataInizio, etichettaAl, dataFine);
        colonnaPeriodo.getChildren().addAll(etichettaPeriodo, rigaPeriodo);
        setStilePeriodo();
    }
    
    public void setPeriodo(ConfigurazioneLocaleXML configurazioneXml, LocalDate inizio, LocalDate fine){ //00
        if(inizio == null)
            dataInizio.setValue(LocalDate.now().minusDays(configurazioneXml.giorniPeriodoDefault));
        else 
            dataInizio.setValue(inizio);
        if(fine == null)
            dataFine.setValue(LocalDate.now());
        else
            dataFine.setValue(fine);
    }
    
    public LocalDate getDataInizio(){ 
        return dataInizio.getValue(); 
    }
     
    public LocalDate getDataFine(){    
        return dataFine.getValue(); 
    }
    
    public Date getDateDataInizio(){ //01
        return Date.from(getDataInizio().atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
     
    public Date getDateDataFine(){ //01
        return Date.from(getDataFine().atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
    private void setStilePeriodo(){
        rigaPeriodo.setAlignment(Pos.CENTER);
        colonnaPeriodo.setAlignment(Pos.CENTER);
        rigaPeriodo.setSpacing(15);
        dataInizio.setPrefWidth(157);
        dataFine.setPrefWidth(157);
        colonnaPeriodo.setSpacing(15);
        rigaPeriodo.setSpacing(15);
        etichettaPeriodo.setStyle("-fx-font-size: 21;");
    }
    
    public VBox getElementoPeriodo(){ return colonnaPeriodo; }
    
    public DatePicker getCasellaDataInizio(){ return dataInizio; }
    public DatePicker getCasellaDataFine(){ return dataFine; }
}

/*
Note:

00 Metodo per impostare i DatePicker con i valori passati o se quest'ultimi non 
   ci sono con valori di default
01 Ottiene un'istanza di Date da un oggetto Instant. Utile per la conversione da
   LocalDate a date
   https://docs.oracle.com/javase/8/docs/api/java/util/Date.html#from-java.time.Instant-
*/
