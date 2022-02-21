import java.util.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RichiestaServizi{
    private ObservableList<PieChart.Data> serviziFatti; //00
    private Label etichettaRichiestaServizi;
    private PieChart graficoRichiestaServzi; //01
    private VBox colonnaRichiestaServizi;
    
    public RichiestaServizi(ConfigurazioneLocaleXML configurazioneXml){
        serviziFatti = FXCollections.observableArrayList();
        graficoRichiestaServzi = new PieChart(serviziFatti);
        etichettaRichiestaServizi = new Label("Richiesta servizi");
        colonnaRichiestaServizi = new VBox();
        colonnaRichiestaServizi.getChildren().addAll(etichettaRichiestaServizi, graficoRichiestaServzi);
        setStileRichiestaServizi(configurazioneXml.dimensioniFinestra.larghezza);
    }
    
    private void setStileRichiestaServizi(double larghezza){
        colonnaRichiestaServizi.setAlignment(Pos.CENTER);
        colonnaRichiestaServizi.setSpacing(15);
        etichettaRichiestaServizi.setStyle("-fx-font-size: 21;");
        graficoRichiestaServzi.setMaxSize(0.38 * larghezza, 0.38 * larghezza);
    }
    
    public void refreshRichiestaServizi(List<PieChart.Data> serviziFatti){ //02
        this.serviziFatti.clear();
        this.serviziFatti.addAll(serviziFatti);
    }
    
    public VBox getElementoRichiestaServizi(){ return colonnaRichiestaServizi; }
    public ObservableList<PieChart.Data> getServiziFatti(){ return serviziFatti; }
}

/*
Note: 

00 Classe per un elemento dati del grafico a torta che ne rappresenta una sezione  
   https://docs.oracle.com/javase/8/javafx/api/javafx/scene/chart/PieChart.Data.html
01 Classe per la creazione di un grafico a torta. Il contenuto del grafico viene
   popolato da sezioni di torta.
   https://docs.oracle.com/javase/8/javafx/api/javafx/scene/chart/PieChart.html
02 Aggiornamento del grafico a torta dei servizi effettuati sulla base dei dati 
   che gli vengono passati
*/