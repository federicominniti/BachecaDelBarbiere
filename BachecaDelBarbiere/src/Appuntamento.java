import java.util.*;
import javafx.beans.property.*;

public class Appuntamento{ //00

    private final SimpleStringProperty cliente;
    private final SimpleObjectProperty<Date> dataAppuntamento;
    private final SimpleStringProperty servizio;
    private final SimpleStringProperty oraAppuntamento;
    private final SimpleStringProperty minutiAppuntamento;
    private final SimpleStringProperty fatto;
    
    private final static String CLIENTE_DEFAULT = "",
                                SERVIZIO_DEFAULT = "",
                                ORA_DEFAULT = "",
                                MINUTI_DEFAULT = "",
                                FATTO_DEFAULT = "NO";
    private final static Date DATA_DEFAULT = new Date();
    
    public Appuntamento(String cliente, String servizio, Date dataAppuntamento,
                            String oraAppuntamento, String minutiAppuntamento, String fatto){
        this.cliente = new SimpleStringProperty(cliente);
        this.servizio = new SimpleStringProperty(servizio);
        this.dataAppuntamento = new SimpleObjectProperty(dataAppuntamento);
        this.oraAppuntamento = new SimpleStringProperty(oraAppuntamento);
        this.minutiAppuntamento = new SimpleStringProperty(minutiAppuntamento);
        this.fatto = new SimpleStringProperty(fatto);
    }
    
    public Appuntamento(){ //01
        this(CLIENTE_DEFAULT, SERVIZIO_DEFAULT, DATA_DEFAULT, ORA_DEFAULT, MINUTI_DEFAULT, FATTO_DEFAULT);
    }
    
    public String getCliente(){
        return cliente.get();
    }
    
    public void setCliente(String cliente){
        this.cliente.set(cliente);
    }
    
    public StringProperty proprietaCliente(){
        return cliente;
    }
    
    public Date getDataAppuntamento(){
        return (Date)dataAppuntamento.get();
    }
    
    public void setDataAppuntamento(Date dataAppuntmento){
        this.dataAppuntamento.set(dataAppuntmento);
    }
    
    public ObjectProperty proprietaDataAppuntamento(){
        return dataAppuntamento;
    }
    
    public void setServizio(String servizio){
        this.servizio.set(servizio);
    }
    
    public String getServizio(){
        return servizio.get();
    }
    
    public StringProperty proprietaServizio(){
        return servizio;
    }
    
    public void setOraAppuntamento(String oraAppuntamento){
        this.oraAppuntamento.set(oraAppuntamento);
    }
    
    public String getOraAppuntamento(){
        return oraAppuntamento.get();
    }
    
    public StringProperty proprietaOraAppuntamento(){
        return oraAppuntamento;
    }
    
    public void setMinutiAppuntamento(String minutiAppuntamento){
        this.minutiAppuntamento.set(minutiAppuntamento);
    }
    
    public String getMinutiAppuntamento(){
        return minutiAppuntamento.get();
    }
    
    public StringProperty proprietaMinutiAppuntamento(){
        return minutiAppuntamento;
    }
    
    public String getFatto(){
        return fatto.get();
    }
    
    public void setFatto(String fatto){
        this.fatto.set(fatto);
    }
    
    public StringProperty proprietaFatto(){
        return fatto;
    }
    
    public Appuntamento getAppuntamentoAttivo(){
        Appuntamento appuntamentoAttivo;
        String cliente, oraAppuntamento, minutiAppuntamento, servizio, fatto;
        Date dataAppuntamento;
        cliente = this.cliente.getValue();
        dataAppuntamento = this.dataAppuntamento.getValue();
        oraAppuntamento = this.oraAppuntamento.getValue();
        minutiAppuntamento = this.minutiAppuntamento.getValue();
        servizio = this.servizio.getValue();
        fatto = this.fatto.getValue();
        appuntamentoAttivo = new Appuntamento(cliente, servizio, dataAppuntamento,
                                                oraAppuntamento, minutiAppuntamento, fatto);
        return appuntamentoAttivo;
    }   
}

/*
Note:

00 Bean che rappreseta un appuntamento prenotato
01 Creazione appuntamento vuoto
*/