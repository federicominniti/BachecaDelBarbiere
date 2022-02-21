import java.util.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.util.*;

public class TabellaAppuntamenti{
    private Label etichettaAppuntamenti;
    private ObservableList<Appuntamento> listaAppuntamenti;
    private static final String sceltaOre[] = {"09", "10", "11", "12", "15", "16", "17", "18", "19"}; //00
    private static final String sceltaMinuti[] = {"00", "15", "30", "45"}; //01
    private TableView<Appuntamento> tabellaAppuntamenti;
    private TableColumn<Appuntamento, String> colonnaCliente, colonnaServizio, colonnaOre, colonnaMinuti, colonnaFatto;
    private TableColumn<Appuntamento, Date> colonnaData;
    private VBox colonnaAppuntamenti;
    
    public TabellaAppuntamenti(ConfigurazioneLocaleXML configurazioneXml){
        etichettaAppuntamenti = new Label("Appuntamenti");
        colonnaCliente = new TableColumn("Cliente");
        colonnaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente")); //02
        colonnaCliente.setCellFactory(TextFieldTableCell.<Appuntamento>forTableColumn()); //03
        colonnaCliente.setOnEditCommit((CellEditEvent<Appuntamento,String> t) -> {((Appuntamento)t.getTableView()
                .getItems().get(t.getTablePosition().getRow())).setCliente(t.getNewValue());}); //04
        colonnaData = new TableColumn("Data");
        Callback<TableColumn<Appuntamento, Date>, TableCell<Appuntamento, Date>> dateCellFactory
                = (TableColumn<Appuntamento, Date> param) -> new CellaDatePicker(); //05
        colonnaData.setCellValueFactory(cellaData -> cellaData.getValue().proprietaDataAppuntamento());
        colonnaData.setCellFactory(dateCellFactory);
        colonnaData.setOnEditCommit((TableColumn.CellEditEvent<Appuntamento, Date> t) -> {((Appuntamento)t.getTableView()
                .getItems().get(t.getTablePosition().getRow())).setDataAppuntamento(t.getNewValue());});
        colonnaServizio = new TableColumn("Servizio");
        colonnaServizio.setCellValueFactory(new PropertyValueFactory<>("servizio"));
        colonnaServizio.setCellFactory(ChoiceBoxTableCell.forTableColumn(configurazioneXml.serviziDisponibili.servizio)); //06
        colonnaServizio.setOnEditCommit((TableColumn.CellEditEvent<Appuntamento, String> t) -> {t.getTableView()
                .getItems().get(t.getTablePosition().getRow()).setServizio(t.getNewValue());});
        colonnaOre = new TableColumn("Ore");
        colonnaOre.setCellValueFactory(new PropertyValueFactory<>("oraAppuntamento"));
        colonnaOre.setCellFactory(ChoiceBoxTableCell.forTableColumn(sceltaOre));
        colonnaOre.setOnEditCommit((TableColumn.CellEditEvent<Appuntamento, String> t) -> {t.getTableView()
                .getItems().get(t.getTablePosition().getRow()).setOraAppuntamento(t.getNewValue());});
        colonnaMinuti = new TableColumn("Minuti");
        colonnaMinuti.setCellValueFactory(new PropertyValueFactory<>("minutiAppuntamento"));
        colonnaMinuti.setCellFactory(ChoiceBoxTableCell.forTableColumn(sceltaMinuti));
        colonnaMinuti.setOnEditCommit((TableColumn.CellEditEvent<Appuntamento, String> t) -> {t.getTableView()
                .getItems().get(t.getTablePosition().getRow()).setMinutiAppuntamento(t.getNewValue());});
        colonnaFatto = new TableColumn("Fatto");
        colonnaFatto.setCellValueFactory(new PropertyValueFactory<>("fatto"));
        colonnaFatto.setCellFactory(TextFieldTableCell.<Appuntamento>forTableColumn());
        colonnaFatto.setOnEditCommit((CellEditEvent<Appuntamento,String> t) -> {((Appuntamento)t.getTableView().
                getItems().get(t.getTablePosition().getRow())).setFatto(t.getNewValue());});
        listaAppuntamenti = FXCollections.observableArrayList();
        tabellaAppuntamenti = new TableView(listaAppuntamenti);
        tabellaAppuntamenti.getColumns().addAll(colonnaCliente,colonnaData, colonnaOre, 
                colonnaMinuti, colonnaServizio, colonnaFatto);
        
        colonnaAppuntamenti = new VBox();
        colonnaAppuntamenti.getChildren().addAll(etichettaAppuntamenti, tabellaAppuntamenti);
        setStileTabella(configurazioneXml.numeroAppuntamentiVisibili, configurazioneXml.dimensioniFinestra.larghezza);
    }
    
    private void setStileTabella(int appuntamentiVisibili, double larghezza){
        colonnaData.setMinWidth(140);
        colonnaCliente.setStyle("-fx-alignment: CENTER");
        colonnaFatto.setStyle("-fx-alignment: CENTER");
        colonnaData.setStyle("-fx-alignment: CENTER");
        colonnaMinuti.setStyle("-fx-alignment: CENTER-LEFT");
        colonnaOre.setStyle("-fx-alignment: CENTER-RIGHT");
        colonnaServizio.setStyle("-fx-alignment: CENTER");
        tabellaAppuntamenti.setEditable(true);
        tabellaAppuntamenti.setFixedCellSize(39);
        tabellaAppuntamenti.prefHeightProperty().set((appuntamentiVisibili + 1) * 39);
        tabellaAppuntamenti.prefWidthProperty().set(0.473 * larghezza);
        colonnaAppuntamenti.setSpacing(15);
        colonnaAppuntamenti.setAlignment(Pos.CENTER);
        etichettaAppuntamenti.setStyle("-fx-font-size: 21;");
    }
    
    public void refreshTabellaAppuntamenti(List<Appuntamento> appuntamentiPeriodo){ //07
        listaAppuntamenti.clear();
        listaAppuntamenti.addAll(appuntamentiPeriodo);
    }
    
    public void rimuoviAppuntamentoEliminato(int appuntamentoDaRimuovere){ //08
        listaAppuntamenti.remove(appuntamentoDaRimuovere);
    }
    
    public void inserisciUltimoAppuntamento(Appuntamento ultimoAppuntamento){
        listaAppuntamenti.add(ultimoAppuntamento); 
    }
    
    public void inserisciAppuntamenti(List<Appuntamento> appuntamentiPeriodo){
        listaAppuntamenti.addAll(0, appuntamentiPeriodo);  
    }
    
    public VBox getElementoAppuntamenti(){ return colonnaAppuntamenti; }
    public TableView<Appuntamento> getTabellaAppuntamenti(){ return tabellaAppuntamenti; }
    public int getAppuntamentoSelezionato(){ return tabellaAppuntamenti.getSelectionModel().getSelectedIndex(); }
    public ObservableList<Appuntamento> getAppuntamentiTabella(){ return listaAppuntamenti; }
}

/*
Note:

00 Orari disponibili per i quali sarà possibile fissare un appuntamento
01 Scelta dei minuti disponibili associata agli orari in cui sarà possibile
   fissare un appuntamento
02 Viene impostata questa proprietà per specificare il popolamento di tutte le 
   celle all'interno della colonna 'Cliente' che hanno associate un elemento 
   cliente del bean Appuntamento.
03 Proprietà per specificare il tipo con il quale vengono costruite le celle 
   della colonna (in questo caso una TextFieldTableCell). E' inoltre responsabile 
   del rendering dei dati contenuti in ogni TableCell per una singola colonna 
   della tabella.
04 Quando viene fatto il commit (invio/selezione) del valore inserito nella cella
   viene settato il rispettivo valore, associato alla colonna, del bean
05 Il cell factory è responsabile del rendering dei dati contenuti in ogni TableCell 
   per una singola colonna di tabella. Questa implementazione può anche essere 
   personalizzata tramite Callback. In questo caso si crea una colonna di celle
   di tipo DatePicker contenenti quindi una data 
   https://docs.oracle.com/javase/8/docs/api/javax/security/auth/callback/Callback.html
06 Proprietà per specificare il tipo con il quale vengono costruite le celle 
   della colonna (in questo caso una ChoiceBoxTableCell ovvero con un menù a 
   tendina nel quale si può selezionare una tra le opzioni proposte). 
07 Aggiornamento del tabella degli appuntamenti sulla base dei dati passati
08 Rimozione dalla tabella di un appuntamento che viene eliminato (del quale viene
   passato l'indice)
*/