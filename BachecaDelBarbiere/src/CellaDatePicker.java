import java.text.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import javafx.event.*;
import javafx.scene.control.*;

public class CellaDatePicker<S, T> extends TableCell<Appuntamento, Date>{ //00
    private DatePicker inserimentoData; //01
    
    public CellaDatePicker(){
        creaDatePicker();
    }
            
    public void creaDatePicker(){ //02
        inserimentoData = new DatePicker(getData());
        inserimentoData.setEditable(true);
        inserimentoData.setOnAction(new EventHandler(){ //03
            public void handle(Event evento){
                LocalDate data = inserimentoData.getValue();
                try{
                    SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy"); //04
                    Calendar calendario = Calendar.getInstance(); //05
                    calendario.set(Calendar.DAY_OF_MONTH, data.getDayOfMonth());
                    calendario.set(Calendar.MONTH, data.getMonthValue()-1);
                    calendario.set(Calendar.YEAR, data.getYear());
                    setText(formatoData.format(calendario.getTime()));
                    commitEdit(calendario.getTime()); //06
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }catch(NullPointerException e) {
                   System.err.println("Il campo data è vuoto");
                }
            }
        });
    }
    
    @Override
    public void updateItem(Date data, boolean vuoto){
        super.updateItem(data, vuoto);
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        if (vuoto) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if(inserimentoData != null){
                    inserimentoData.setValue(getData());
                }
                setText(null);
                setGraphic(inserimentoData);
            } else {
                setInserimentoData(formatoData.format(data));
                setText(formatoData.format(data)); //07
                setGraphic(inserimentoData); //08
                setContentDisplay(ContentDisplay.TEXT_ONLY); //09
            }
        }
    }
    
    public void setInserimentoData(String stringData){
        LocalDate data = null;
        int giorno = 0, mese = 0, anno = 0;
        try{
            giorno = Integer.parseInt(stringData.substring(0, 2));
            mese = Integer.parseInt(stringData.substring(3, 5));
            anno = Integer.parseInt(stringData.substring(6, stringData.length()));
        }catch(NumberFormatException errore){
            System.err.println("Errore" + errore);
        }
        data = LocalDate.of(anno, mese, giorno);
        inserimentoData.setValue(data);
    }

    @Override
    public void startEdit(){
        if(!isEmpty()){
            super.startEdit();
            if(inserimentoData == null){
                creaDatePicker();
            }
            setText(null);
            setGraphic(inserimentoData);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
    
    @Override
    public void cancelEdit(){
        super.cancelEdit();
        if(inserimentoData.getValue() != null){
            setText(inserimentoData.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))); //10
        }
        setGraphic(null);
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }
    
    public LocalDate getData(){ //11
        return getItem() == null ? 
            LocalDate.now() : getItem().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}

/*
Note:
00 Classe per l'inizializzazione e  modifica delle celle che sono nella tabella 
   degli appuntamenti contenenti una data settabile tramite DatePicker
01 Classe per creare il calendario a comparsa
   https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/DatePicker.html
02 Metodo per la creazione degli elementi DatePicker, creati uno per ogni cella
03 Gestrore eventi compiuti sul DatePicker
04 Classe per formattare le date secondo un certo pattern e analizzarle in riferimento 
   alle impostazioni locali.
   https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html
05 Classe per manipolare i campi del calendario
   https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html
06 Metodo usato quando si ha un'interazione dell'utente dell'interfaccia relativamente
   alla modifica della cella. Inizia la transizione da uno stato di modifica 
   a uno stato di non modifica.
07 Metodo per settare l'elemento 'text' del DatePicker
08 Metodo per la visualizzazione dell'elemento DatePicker
09 Metodo per scegliere il tipo di visualizzazione; di default verrà visualizzato
   il DatePicker con accanto l'elemento 'text' associato al DatePicker. Tramite 
   le costante TEXT_ONLY si può scegliere di visualizzare solo l'elemento di tipo
   'text' mentre con la costante GRAPHIC_ONLY si visualizzerà solo l'elemento 
   DatePicker
10 Conversione da LocalDate a String
11 Restituisce la data nel DatePicker (se è null restituisce quella odierna) e la
   converte da Date a LocalDate
*/
