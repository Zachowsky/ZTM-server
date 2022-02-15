import java.io.Serializable;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Klasa serwera implementujaca Serializable. Znajduja sie w niej
 * wszystkie metody i obiekty, ktore sluza do sprawnej komunikacji
 * klient-server.
 */
public class Server implements Serializable {

    /** * Lista przechowujaca wszstkie loginy */
    private ArrayList<String> login = new ArrayList<>();
    /** * Lista przechowujaca wszstkie hasla */
    private ArrayList<String> haslo = new ArrayList<>();
    /** * Lista przechowujaca wszstkie id */
    private ArrayList<Integer> id = new ArrayList<>();
    /** * Lista przechowujaca wszstkie imona */
    private ArrayList<String> name = new ArrayList<>();
    /** * Lista przechowujaca wszstkie nazwiska */
    private ArrayList<String> surname = new ArrayList<>();
    /** * Lista przechowujaca wszstkie id kart pasazerow */
    private ArrayList<String> cardid = new ArrayList<>();
    /** * Lista przechowujaca wszstkie nazwy miast */
    private ArrayList<String> city = new ArrayList<>();
    /** * Lista przechowujaca wszstkie kody pocztowe */
    private ArrayList<String> postal_code = new ArrayList<>();
    /** * Lista przechowujaca wszstkie adresy uzytkownikow */
    private ArrayList<String> address = new ArrayList<>();
    /** * Lista przechowujaca wszstkie numery telefonow uzytkownikow */
    private ArrayList<String> phone = new ArrayList<>();
    /** * Lista przechowujaca wszstkie emaile uzytkownikow */
    private ArrayList<String> email = new ArrayList<>();
    /** * Lista przechowujaca wszstkie rodzaje kont */
    private ArrayList<String> accounttype = new ArrayList<>();
    /** * Lista przechowujaca wszstkie USERID */
    private ArrayList<String> dob = new ArrayList<>();
    /** * Lista przechowujaca stan konta kazdego z uzytkownikow */
    private ArrayList<String> accountbalance = new ArrayList<>();
    /** * Pomocnicza lista przechowujaca wszstkie userid */
    private ArrayList<Integer> user_id = new ArrayList<>();
    /** * Lista przechowujaca wszstkie godziny odjazdow kazdej linii */
    private ArrayList<String> timee = new ArrayList<>();
    /** * Lista przechowujaca wszstkie nazwy przystankow kazdej linii */
    private ArrayList<String> busstopp = new ArrayList<>();
    /** * Pomocnicza lista przechowujaca wszstkie godziny odjazdow kazdej linii */
    private ArrayList<String > time = new ArrayList<>();
    /** * Pomocnicza lista przechowujaca wszstkie nazwy przystankow kazdej linii */
    private ArrayList<String > busstop = new ArrayList<>();

    /** *Pole typu ObjectOutputStream */
    private ObjectOutputStream wyslij ;
    /** *Pole typu ObjectInputStream */
    private ObjectInputStream odbierz;
    /** *Pole typu Socket */
    private Socket connection;
    /** *Pole typu ServerSocket */
    private ServerSocket socket;

    /***
     * Metoda main uruchamiajaca Server.
     * @param args tablica znakow przechowujaca dane argumenty
     * @throws SQLException rzucacnie wyjatkow SQL
     * @throws IOException rzucanie wyjatkow IO
     * @throws ClassNotFoundException rzucanie wyjatkow ClassNotFound
     */
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException{
        Server s = new Server();
        s.Server();
    }

    /***
     * Metoda odpowiadajaca za logowanie uzytkownika. Jesli odebrane od klienta haslo i login zgadzaja sie
     * z danymi znalezionymi w bazie server wysyla potrzebne informacje do klienta. Jesli sie nie zgadzaja, klient dostaje
     * stosowny komunikat.
     * @param conn Parametr przechowujacy informacje o laczeniu z baza.
     * @throws IOException rzucanie wyjatkow IO
     * @throws ClassNotFoundException rzucanie wyjatkow ClassNotFound
     * @throws SQLException rzucacnie wyjatkow SQL
     */

    public void logIn(Connection conn) throws IOException, ClassNotFoundException, SQLException{

        String loginOdebrane, hasloOdebrane;
        boolean logowanie = false;
        String name1= "";
        String surname1="";
        Integer id1 = 0;
        String cardid1="";
        String city1="";
        String postalcode1="";
        String address1="";
        String phone1="";
        String email1="";
        String accounttype1="";
        String username1="";
        String dob1="";
        String accountbalance1="";

        loginOdebrane = (String) odbierz.readObject();
        hasloOdebrane = (String) odbierz.readObject();

        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery("select * from users");
        while(resultSet.next()) {
            login.add(resultSet.getString("username").toLowerCase());
            haslo.add(resultSet.getString("password"));
            name.add(resultSet.getString("name"));
            surname.add(resultSet.getString("surname"));
            id.add(resultSet.getInt("id"));
            cardid.add(resultSet.getString("cardid"));
            city.add(resultSet.getString("city"));
            postal_code.add(resultSet.getString("postal_code"));
            address.add(resultSet.getString("address"));
            phone.add(resultSet.getString("phone"));
            email.add(resultSet.getString("email"));
            accounttype.add(resultSet.getString("account_type"));
            dob.add(resultSet.getString("dob"));
            accountbalance.add(resultSet.getString("account_balance"));
        }

        for (int i = 0; i < login.size(); i++) {
            if (loginOdebrane.equals(login.get(i)) && hasloOdebrane.equals(haslo.get(i))) {
                logowanie = true;

                id1 = Integer.valueOf((id.get(i)));
                name1 = name.get(i);
                surname1 = surname.get(i);
                cardid1 = cardid.get(i);
                city1 = city.get(i);
                postalcode1 = postal_code.get(i);
                address1 = address.get(i);
                phone1 = phone.get(i);
                email1 = email.get(i);
                accounttype1 = accounttype.get(i);
                username1 = login.get(i);
                dob1 = dob.get(i);
                accountbalance1 = accountbalance.get(i);
            }
        }

        wyslij.writeObject(logowanie);
        wyslij.flush();
        wyslij.writeObject(id1);
        wyslij.flush();
        wyslij.writeObject(name1);
        wyslij.flush();
        wyslij.writeObject(surname1);
        wyslij.flush();

        wyslij.writeObject(cardid1);
        wyslij.flush();
        wyslij.writeObject(city1);
        wyslij.flush();
        wyslij.writeObject(postalcode1);
        wyslij.flush();
        wyslij.writeObject(address1);
        wyslij.flush();
        wyslij.writeObject(phone1);
        wyslij.flush();

        wyslij.writeObject(email1);
        wyslij.flush();
        wyslij.writeObject(accounttype1);
        wyslij.flush();
        wyslij.writeObject(username1);
        wyslij.flush();
        wyslij.writeObject(dob1);
        wyslij.flush();
        wyslij.writeObject(accountbalance1);
        wyslij.flush();
        login.clear();
        haslo.clear();
        name.clear();
        surname.clear();
        cardid.clear();
        city.clear();
        postal_code.clear();
        address.clear();
        phone.clear();
        email.clear();
        dob.clear();
        accountbalance.clear();

    }

    /***
     * Metoda odpowiadajaca za rejestracje nowego uzytkownika. Odczytuje ona prawidlowo juz wpisane dane przez klienta
     * i wstawia je do bazy danych
     * @param conn Parametr przechowujacy informacje o laczeniu z baza.
     * @throws IOException rzucanie wyjatkow IO
     * @throws ClassNotFoundException rzucanie wyjatkow ClassNotFound
     * @throws SQLException rzucacnie wyjatkow SQL
     */

    public void regIn(Connection conn) throws IOException, ClassNotFoundException, SQLException{
        String name;
        String surname;
        String cardid;
        String address;
        String postal;
        String city;
        String phone;
        String email;
        String dob;
        String username;
        String password;

        int end_id = 0;

        name = (String) odbierz.readObject();
        surname = (String) odbierz.readObject();
        cardid = (String) odbierz.readObject();
        address = (String) odbierz.readObject();
        postal = (String) odbierz.readObject();
        city = (String) odbierz.readObject();
        phone = (String) odbierz.readObject();
        email = (String) odbierz.readObject();
        dob = (String) odbierz.readObject();
        username = (String) odbierz.readObject();
        password = (String) odbierz.readObject();

        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery("select id from users");
        while (resultSet.next()) {
            user_id.add(resultSet.getInt("id"));
        }
        for (int i = 0; i < user_id.size(); i++) {
            end_id = user_id.get(i);
        }
        end_id += 1;
        String sql = "INSERT INTO users VALUES ( '" + end_id + "','" + name + "', '" + surname + "', '" + cardid + "', '" + city + "', '" + postal + "', '" + address + "','" + phone + "', '" + email + "', 'default_user','" + dob + "', '" + username + "', '" + password + "', '0')";
        st.executeUpdate(sql);

    }

    /***
     * Metoda odpowiadajaca za doladowywanie konta uzytkownika okreslona kwota. Pobiera ona kwote oraz username klienta
     * w celu poprawnego wpisania danych do bazy.
     * @param conn Parametr przechowujacy informacje o laczeniu z baza.
     * @throws IOException rzucanie wyjatkow IO
     * @throws ClassNotFoundException rzucanie wyjatkow ClassNotFound
     * @throws SQLException rzucacnie wyjatkow SQL
     */
    public void recharge(Connection conn) throws IOException, ClassNotFoundException, SQLException{

        double valueDou;
        String valueStr;
        String username;
        String actual_balance = "";

        username = (String) odbierz.readObject();
        valueStr = (String) odbierz.readObject();
        valueDou = Double.valueOf(valueStr);

        Statement st = conn.createStatement();
        String sql = "UPDATE users SET account_balance = account_balance + '"+valueDou+"'  WHERE username LIKE'"+username+"'";
        st.executeUpdate(sql);
        sql = "SELECT account_balance from users WHERE username = '"+username+"'";
        ResultSet resultSet = st.executeQuery(sql);
        while(resultSet.next()) {
            actual_balance = resultSet.getString("account_balance");
        }
        wyslij.writeObject(actual_balance);
        wyslij.flush();

    }

    /***
     * Metoda odpowiadajaca za zmiane hasla konta klienta. Sprawdza nazwe uzytkownika, ktory prosi o zmiane hasla
     * a nastepnie zmienia haslo w bazie danych na to odebrane od kliena.
     * @param conn Parametr przechowujacy informacje o laczeniu z baza.
     * @throws IOException rzucanie wyjatkow IO
     * @throws ClassNotFoundException rzucanie wyjatkow ClassNotFound
     * @throws SQLException rzucacnie wyjatkow SQL
     */
    public void changePassword(Connection conn)throws IOException, ClassNotFoundException, SQLException{

        String password;
        String username;

        username = (String) odbierz.readObject();
        password = (String) odbierz.readObject();


        Statement st = conn.createStatement();
        String sql = "UPDATE users SET password = '"+password+"'  WHERE username LIKE'"+username+"'";
        st.executeUpdate(sql);
    }


    /***
     * Metoda odpowiadajaca za wyswietlanie informacji o danej linii. Przyjmuje ona parametr line_number
     * od klienta, ktory okresla jaka linie chce zobaczyc klient.
     * @param line_number parametr przehowujacy informacje o numerze linii
     * @param conn Parametr przechowujacy informacje o laczeniu z baza.
     * @throws IOException rzucanie wyjatkow IO
     * @throws SQLException rzucacnie wyjatkow SQL
     */
    public void lineGet(Connection conn, int line_number) throws SQLException, IOException {

        Statement st = conn.createStatement();
        String sql = "";
        if(line_number == 1) {
             sql = "select * from linia_1";
        }
        else if(line_number == 2 ){
             sql = "select * from linia_2";
        }
        else if(line_number == 3){
             sql = "select * from linia_3";
        }
        else if(line_number == 4){
             sql = "select * from linia_4";
        }
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            time.add(rs.getString("godzina"));
            busstop.add(rs.getString("przystanek"));
        }


        wyslij.writeObject(time);
        wyslij.flush();
        wyslij.writeObject(busstop);
        wyslij.flush();
        time.clear();
        busstop.clear();
    }

    /***
     * Metoda odpowiadajaca za wyswietlenie poprawnych informacji o danycm uzytkowniku
     * ktory zamierza edytowac swoje dane.
     * @param conn Parametr przechowujacy informacje o laczeniu z baza.
     * @throws IOException rzucanie wyjatkow IO
     * @throws ClassNotFoundException rzucanie wyjatkow ClassNotFound
     * @throws SQLException rzucacnie wyjatkow SQL
     */
    public void userEditAccount(Connection conn) throws SQLException, IOException, ClassNotFoundException {
        String editCheckUsername;
        String editCheckCardId;

        editCheckUsername = (String) odbierz.readObject();
        editCheckCardId = (String) odbierz.readObject();

        String name1= "";
        String surname1="";
        String cardid1="";
        String city1="";
        String postalcode1="";
        String address1="";
        String phone1="";
        String email1="";
        String dob1="";
        String username1="";

        Statement st = conn.createStatement();
        ResultSet resultSet = st.executeQuery("select * from users WHERE username LIKE '"+editCheckUsername+"' AND cardid LIKE '"+editCheckCardId+"'");
        while(resultSet.next()) {
            name1 = resultSet.getString("name");
            surname1 = resultSet.getString("surname");
            cardid1 = resultSet.getString("cardid");
            city1 = resultSet.getString("city");
            postalcode1 = resultSet.getString("postal_code");
            address1 = resultSet.getString("address");
            phone1 = resultSet.getString("phone");
            email1 = resultSet.getString("email");
            username1 = resultSet.getString("username");
            dob1 = resultSet.getString("dob");
        }

        wyslij.writeObject(name1);
        wyslij.flush();
        wyslij.writeObject(surname1);
        wyslij.flush();
        wyslij.writeObject(cardid1);
        wyslij.flush();
        wyslij.writeObject(city1);
        wyslij.flush();
        wyslij.writeObject(postalcode1);
        wyslij.flush();
        wyslij.writeObject(address1);
        wyslij.flush();
        wyslij.writeObject(phone1);
        wyslij.flush();
        wyslij.writeObject(email1);
        wyslij.flush();
        wyslij.writeObject(dob1);
        wyslij.flush();
        wyslij.writeObject(username1);
        wyslij.flush();
    }

    /***
     * Metoda pozwalajaca na edycje danych klienta. Odbiera nowo wpisane dane przez uzytkownika
     * i wstawia je w odpowiednie miejsce do bazy danych.
     * @param conn Parametr przechowujacy informacje o laczeniu z baza.
     * @throws IOException rzucanie wyjatkow IO
     * @throws ClassNotFoundException rzucanie wyjatkow ClassNotFound
     * @throws SQLException rzucacnie wyjatkow SQL
     */
    public void editDatabase(Connection conn) throws SQLException, IOException, ClassNotFoundException{
        String name;
        String surname;
        String cardid;
        String address;
        String postal;
        String city;
        String phone;
        String email;
        String dob;
        String username;


        name = (String) odbierz.readObject();
        surname = (String) odbierz.readObject();
        cardid =  (String) odbierz.readObject();
        address =  (String) odbierz.readObject();
        postal =  (String) odbierz.readObject();
        city =  (String) odbierz.readObject();
        phone =  (String) odbierz.readObject();
        email =  (String) odbierz.readObject();
        dob = (String) odbierz.readObject();
        username = (String) odbierz.readObject();

        Statement st = conn.createStatement();
        String sql = "UPDATE users SET name = '"+name+"', surname = '"+surname+"'," +
                "cardid = '"+cardid+"',city = '"+city+"', address = '"+address+"', postal_code = '"+postal+"'," +
                "phone = '"+phone+"', email = '"+email+"', dob = '"+dob+"' WHERE username LIKE '"+username+"'";
        st.executeUpdate(sql);
    }

    /***
     * Metoda pozwalajaca na edycje oraz usuwanie przystankow w wybranej przez administratora linii.
     * @param line_number Parametr przehowujacy informacje o numerze linii
     * @param conn Parametr przechowujacy informacje o laczeniu z baza.
     * @throws IOException rzucanie wyjatkow IO
     * @throws ClassNotFoundException rzucanie wyjatkow ClassNotFound
     * @throws SQLException rzucacnie wyjatkow SQL
     */
    public void editTimetableValues(Connection conn, int line_number) throws SQLException, IOException, ClassNotFoundException{

        timee = (ArrayList<String>) odbierz.readObject();
        busstopp = (ArrayList<String>) odbierz.readObject();

        Statement st = conn.createStatement();
        String sql = "";

        if(line_number == 1) {
            sql = "DELETE from linia_1";
            st.executeUpdate(sql);
            for(int i = 0; i< timee.size(); i++) {
                sql = "INSERT INTO linia_1 VALUES('"+timee.get(i)+"', '"+busstopp.get(i)+"')";
                st.executeUpdate(sql);
            }
        }
        else if(line_number == 2 ){
            sql = "DELETE from linia_2";
            st.executeUpdate(sql);
            for(int i = 0; i< timee.size(); i++) {
                sql = "INSERT INTO linia_2 VALUES('"+timee.get(i)+"', '"+busstopp.get(i)+"')";
                st.executeUpdate(sql);
            }
        }
        else if(line_number == 3){
            sql = "DELETE from linia_3";
            st.executeUpdate(sql);
            for(int i = 0; i< timee.size(); i++) {
                sql = "INSERT INTO linia_3 VALUES('"+timee.get(i)+"', '"+busstopp.get(i)+"')";
                st.executeUpdate(sql);
            }
        }
        else if(line_number == 4){
            sql = "DELETE from linia_4";
            st.executeUpdate(sql);
            for(int i = 0; i< timee.size(); i++) {
                sql = "INSERT INTO linia_4 VALUES('"+timee.get(i)+"', '"+busstopp.get(i)+"')";
                st.executeUpdate(sql);
            }
        }
    }

    /***
     * Metoda sprawdzajaca czy odebrany od klienta username znajduje sie juz w bazie.
     * Wysyla true jesli juz jest taki uzytkownik
     * Wysyla false jesli takiego nie ma
     * @param conn Parametr przechowujacy informacje o laczeniu z baza.
     * @throws SQLException rzucanie wyjatkow SQL
     * @throws IOException rzucanie wyjatkow IO
     * @throws ClassNotFoundException rzucanie wyjatkow ClassNotFound
     */

    public void checkUserExists(Connection conn) throws SQLException, IOException, ClassNotFoundException {
        String usernameOdebrane;
        String usernameIfExist = "";
        boolean exists;
        usernameOdebrane = (String) odbierz.readObject();

        Statement st = conn.createStatement();
        ResultSet resultSet2 = st.executeQuery("SELECT username FROM users WHERE username LIKE '" + usernameOdebrane + "'");

        while (resultSet2.next()) {
            usernameIfExist = resultSet2.getString("username").toLowerCase();
        }

        if(!(usernameIfExist.equals(usernameOdebrane.toLowerCase()))){
            exists = true;
        }
        else{
            exists = false;
        }
        wyslij.writeObject(exists);
    }


    /***
     * Metoda odpowiadajaca za lacznosc z baza i wywowlywanie wszystkich metod o ktore prosi
     * uzytkownik badz administrator.
     * @throws SQLException rzucanie wyjatkow SQL
     * @throws IOException rzucanie wyjatkow IO
     * @throws ClassNotFoundException rzucanie wyjatkow ClassNotFound
     */
    public void Server() throws SQLException, IOException, ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
        if (conn != null) {
            System.out.println("Pomyslnie polaczono z baza danych");

        }else{
            System.out.println("Nie mozna polaczyc sie z baza");
        }
        socket = new ServerSocket(9999);

        while (true) {

            connection = socket.accept();
            wyslij = new ObjectOutputStream(connection.getOutputStream());
            odbierz = new ObjectInputStream(connection.getInputStream());

            int window = (int) odbierz.readObject();

            if (window == 1) {
                regIn(conn);
            } else if (window == 2) {
                logIn(conn);
            } else if (window == 3) {
                recharge(conn);
            }else if (window == 4){
                changePassword(conn);
            }else if(window == 5){
                int line_number = (int) odbierz.readObject();
                lineGet(conn, line_number);
            }else if(window == 6){
                userEditAccount(conn);
            }else if(window == 7){
                editDatabase(conn);
            }else if(window == 8){
                int line_number = (int) odbierz.readObject();
                editTimetableValues(conn, line_number);
            }
            else if(window == 9){
                checkUserExists(conn);
            }
        }
    }
}