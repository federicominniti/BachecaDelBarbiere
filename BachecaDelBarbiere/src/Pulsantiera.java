import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class Pulsantiera{  
    private Button bottoneInserisci;
    private Button bottoneElimina;
    private Button bottoneFatto;
    private HBox rigaBottoni;
            
    public Pulsantiera(ConfigurazioneLocaleXML configurazioneXml){
        bottoneInserisci = new Button("Inserisci");
        bottoneElimina = new Button("Elimina");
        bottoneFatto = new Button("Fatto");
        rigaBottoni = new HBox();
        rigaBottoni.getChildren().addAll(bottoneInserisci, bottoneElimina, bottoneFatto);
        setStilePulsantiera(configurazioneXml.sfondoInserisci, configurazioneXml.sfondoElimina,
                                configurazioneXml.sfondoFatto);
    }
    
    private void setStilePulsantiera(String sfondoInserisci, String sfondoElimina, String sfondoFatto){
        bottoneInserisci.setStyle("-fx-background-color:" + sfondoInserisci);
        bottoneElimina.setStyle("-fx-background-color:" + sfondoElimina);
        bottoneFatto.setStyle("-fx-background-color:" + sfondoFatto);
        rigaBottoni.setSpacing(20);
        rigaBottoni.setAlignment(Pos.CENTER);
    }
    
    public HBox getElementoPulsantiera(){ return rigaBottoni; }
    public Button getBottoneInserisci(){ return bottoneInserisci; }
    public Button getBottoneElimina(){ return bottoneElimina; }
    public Button getBottoneFatto(){ return bottoneFatto; }
}

