import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class InterfacciaBachecaDelBarbiere {
    private Label etichettaTitolo;
    public Periodo periodo;
    public TabellaAppuntamenti tabellaPrenotazioni;
    public RichiestaServizi richiestaServizi;
    public Pulsantiera pulsantiera;
    public VBox interfacciaFinestra;
    private HBox corpoFinestra;
    private VBox colonnaSinistra;
    private VBox colonnaDestra;
    
    public InterfacciaBachecaDelBarbiere(ConfigurazioneLocaleXML configurazioneXml){
        etichettaTitolo = new Label("Bacheca del Barbiere");
        periodo = new Periodo();
        tabellaPrenotazioni = new TabellaAppuntamenti(configurazioneXml);
        richiestaServizi = new RichiestaServizi(configurazioneXml);
        pulsantiera = new Pulsantiera(configurazioneXml);
        
        colonnaSinistra = new VBox();
        colonnaSinistra.getChildren().addAll(periodo.getElementoPeriodo(), 
                                                tabellaPrenotazioni.getElementoAppuntamenti());
        colonnaDestra = new VBox();
        colonnaDestra.getChildren().addAll(richiestaServizi.getElementoRichiestaServizi(), 
                                                pulsantiera.getElementoPulsantiera());
        corpoFinestra = new HBox();
        corpoFinestra.getChildren().addAll(colonnaSinistra, colonnaDestra);
        interfacciaFinestra = new VBox();
        interfacciaFinestra.getChildren().addAll(etichettaTitolo, corpoFinestra);
        setStileInterfaccia(configurazioneXml.dimensioneFont, configurazioneXml.font, configurazioneXml.sfondoFinestra,
                                configurazioneXml.dimensioniFinestra.larghezza, configurazioneXml.dimensioniFinestra.altezza);
    }
    
    public void setStileInterfaccia(double dimensioneFont, String font, String sfondoFinestra, double altezza,
                                        double larghezza){
        colonnaSinistra.setAlignment(Pos.CENTER);
        colonnaSinistra.setSpacing(20);
        colonnaDestra.setAlignment(Pos.CENTER);
        colonnaDestra.setSpacing(20);
        corpoFinestra.setAlignment(Pos.CENTER);
        corpoFinestra.setSpacing(20);
        interfacciaFinestra.setAlignment(Pos.CENTER);
        interfacciaFinestra.setSpacing(20);
        interfacciaFinestra.setMinSize(larghezza,altezza);
        interfacciaFinestra.setStyle("-fx-font-size: " + dimensioneFont + "; -fx-font-family: '" + font + "'"
                                        + "; -fx-background-color:" + sfondoFinestra); 
        etichettaTitolo.setStyle("-fx-font-size: 41;");
    }
}

