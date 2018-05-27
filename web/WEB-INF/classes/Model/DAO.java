package Model;
import java.sql.*;

public class DAO {

    private final String url = "jdbc:postgresql://localhost:5432/";
    private final String tableUsuario;
    private final String tablePet;
    private final String password;
    private Connection connection;
    private final String database;
    private final String user;
    private Statement command;

    public DAO (String database, String tableUsuario, String tablePet, String user, String password) throws SQLException, ClassNotFoundException{
        Class.forName("org.postgresql.Driver");

        this.tableUsuario = tableUsuario;
        this.tablePet = tablePet;
        this.password = password;
        this.database = database;
        this.user = user;
    }

    public void insertUsuario(String usuario, String senha) throws SQLException{
        getConnection();
        String sql = "insert into " + tableUsuario + " (usuario, senha) VALUES (?, ?)";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, usuario);
        stm.setString(2, senha);

        stm.executeUpdate();
        closeConnection();
    }

    public void insertPet(String dono, String nome) throws SQLException{
        // este comando eh o insert, so vamos dar um insert quando for um cadastro
        // entao os outros parametros - hunger, healt ... - passa tudo como 100%.
        long timestampj = System.currentTimeMillis();
        insertPet(dono, nome, 100, 1000000, true, 100, 100, 100, true, timestampj);
    }

    private void insertPet(String dono, String nome, int felicidade, int qtdToques, boolean lampada, int saude, int vida, int fome, boolean status, long ultimoAcesso) throws SQLException{
        getConnection();

        String sql = "insert into " + tablePet + " (nome, ultimoAcesso, felicidade, qtdToques, dono, lampada, saude, vida, fome, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stm = connection.prepareStatement(sql);

        stm.setString(1, nome);
        stm.setLong(2, ultimoAcesso);
        stm.setInt(3, felicidade);
        stm.setInt(4, qtdToques);
        stm.setString(5, dono);
        stm.setBoolean(6, lampada);
        stm.setInt(7, saude);
        stm.setInt(8, vida);
        stm.setInt(9, fome);
        stm.setBoolean(10, status);

        // insere no banco
        stm.executeUpdate();
        System.out.println("Executado o insert");
        closeConnection();
    }

    private void getConnection() throws SQLException{
        connection = DriverManager.getConnection(url+database, user, password);
        command = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    private void closeConnection() throws SQLException {
        connection.close();
    }

    public ResultSet getCommand(String s) throws SQLException {
        return command.executeQuery(s);
    }

    public boolean login(String user, String pass) throws SQLException{
        getConnection();

        String comand = "select usuario, senha from " + tableUsuario + " where usuario = '" + user + "' and senha = '" + pass + "';";
        ResultSet res = getCommand(comand);

        closeConnection();
        return res.next();
    }

    public void update(int fome, int saude, int felicidade, String status, long agora, int id){
        try{
            getConnection();

            String sql = "update pet set fome = " + fome + ", saude = " + saude + ", felicidade = " + felicidade + ", status = '" + status + "', ultimoAcesso = " + agora + " where id = " + id + ";";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.executeUpdate();

            closeConnection();
        } catch (Exception ex) {
            System.out.println("Erro ao atualizar no banco: " + ex);
        }
    }
}