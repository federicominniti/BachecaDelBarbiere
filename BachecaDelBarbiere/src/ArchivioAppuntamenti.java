import java.sql.*;
import java.util.*;
import javafx.scene.chart.*;

public class ArchivioAppuntamenti {
    private static Connection connessioneArchivio;

    public ArchivioAppuntamenti(String ipArchivio, int portaArchivio, String usernameArchivio, String passwordArchivio){
        try {
            connessioneArchivio = DriverManager.getConnection("jdbc:mysql://" + ipArchivio
                    + ":" + portaArchivio + "/bacheca_del_barbiere", usernameArchivio, passwordArchivio);
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    
    public void inserisciAppuntamento(String cliente, java.util.Date dataAppuntamento, String oraAppuntamento, 
                                        String minutiAppuntamento, String servizio, String fatto){
        try {
            PreparedStatement inserisciRecord = 
                connessioneArchivio.prepareStatement("INSERT INTO appuntamenti(cliente, dataAppuntamento, "
                        + "oraAppuntamento, servizio, fatto) values (?, ?, ?, ?, ?);");
            inserisciRecord.setString(1, cliente);
            inserisciRecord.setDate(2, new java.sql.Date(dataAppuntamento.getTime()));
            inserisciRecord.setTime(3, java.sql.Time.valueOf(oraAppuntamento + ":" + minutiAppuntamento + ":00"));
            inserisciRecord.setString(4, servizio);
            inserisciRecord.setString(5, fatto);
            int risultato = inserisciRecord.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
        
    public List<Appuntamento> getAppuntamentiNelPeriodo(java.util.Date dataInizio, java.util.Date dataFine){
        List<Appuntamento> appuntamentoNelPeriodo = new ArrayList<>();
        Time orarioAppuntamento;
        String oraAppuntamento, minutiAppuntamento;
        try{
            PreparedStatement selezioneAppuntamenti = 
                connessioneArchivio.prepareStatement("SELECT * FROM appuntamenti "
                    + "WHERE dataAppuntamento BETWEEN ? AND ? "
                    + "ORDER BY dataAppuntamento DESC, oraAppuntamento ASC;");
            selezioneAppuntamenti.setDate(1, new java.sql.Date(dataInizio.getTime()));
            selezioneAppuntamenti.setDate(2, new java.sql.Date(dataFine.getTime()));
            ResultSet appuntamentiPeriodo = selezioneAppuntamenti.executeQuery();
            while(appuntamentiPeriodo.next()){
                orarioAppuntamento = appuntamentiPeriodo.getTime("oraAppuntamento");
                oraAppuntamento = orarioAppuntamento.toString().substring(0,2);
                minutiAppuntamento = orarioAppuntamento.toString().substring(3,5);
                appuntamentoNelPeriodo.add(new Appuntamento(
                    appuntamentiPeriodo.getString("cliente"), appuntamentiPeriodo.getString("servizio"), 
                    appuntamentiPeriodo.getDate("dataAppuntamento"), oraAppuntamento, minutiAppuntamento,
                    appuntamentiPeriodo.getString("fatto")));
            }   
        } catch(SQLException e){
            System.err.println(e.getMessage());
        }
        return appuntamentoNelPeriodo;
    }
    
    public List<PieChart.Data> getServiziFatti(java.util.Date dataInizio, java.util.Date dataFine){
        List<PieChart.Data> listaServiziFatti = new ArrayList<>();
        try{
            PreparedStatement selezionaServiziFatti = 
                    connessioneArchivio.prepareStatement("SELECT servizio, COUNT(*)  "
                            + "FROM appuntamenti "
                            + "WHERE fatto = ? AND dataAppuntamento BETWEEN ? and ? "
                            + "GROUP BY servizio");    
            selezionaServiziFatti.setString(1, "SI");
            selezionaServiziFatti.setDate(2, new java.sql.Date(dataInizio.getTime()));
            selezionaServiziFatti.setDate(3, new java.sql.Date(dataFine.getTime()));
            ResultSet recordServiziFatti = selezionaServiziFatti.executeQuery();
            while(recordServiziFatti.next()){   
                listaServiziFatti.add(
                        new PieChart.Data(recordServiziFatti.getString("servizio"), 
                                            recordServiziFatti.getInt("COUNT(*)")));   
            }
        } catch(SQLException e){
            System.err.println(e.getMessage());
        } 
        return listaServiziFatti; 
    }
    
    public void eliminaAppuntamento(String cliente, java.util.Date dataAppuntamento, String oraAppuntamento, 
                                        String minutiAppuntamento) {
        try {
            PreparedStatement eliminaAppuntamento = connessioneArchivio.prepareStatement("DELETE FROM appuntamenti "
                    + "WHERE cliente = ? "
                    +   "AND dataAppuntamento = ? "
                    +   "AND oraAppuntamento = ? "
                    +   "AND fatto='NO';");
            eliminaAppuntamento.setString(1, cliente);
            eliminaAppuntamento.setDate(2, new java.sql.Date(dataAppuntamento.getTime()));
            eliminaAppuntamento.setTime(3, java.sql.Time.valueOf(oraAppuntamento + ":" + minutiAppuntamento + ":00"));
            int appuntamentiRimossi = eliminaAppuntamento.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void appuntamentoFatto(String cliente, java.util.Date dataAppuntamento, String oraAppuntamento, 
                                        String minutiAppuntamento){
        try {
            PreparedStatement aggiornaAppuntamento = connessioneArchivio.prepareStatement("UPDATE appuntamenti "
                    + "SET fatto = 'SI' "
                    + "WHERE cliente = ? "
                    +       "AND dataAppuntamento = ? "
                    +       "AND oraAppuntamento = ? ;");
            aggiornaAppuntamento.setString(1, cliente);
            aggiornaAppuntamento.setDate(2, new java.sql.Date(dataAppuntamento.getTime()));
            aggiornaAppuntamento.setTime(3, java.sql.Time.valueOf(oraAppuntamento + ":" + minutiAppuntamento + ":00"));
            int appuntamentiAggiornati = aggiornaAppuntamento.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }   
}
