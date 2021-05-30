package javaTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

class DataBase {
    Connection connection = null;
    public DataBase() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:Maks_MojeKontakty.db");
    }
    public String kodSQL(String plik_zKodemSQL) throws IOException {
        BufferedReader bfdR = new BufferedReader(new FileReader(plik_zKodemSQL));
        String liniaPliku;
        String out = "";
        while ( (liniaPliku = bfdR.readLine() ) != null){ out+=liniaPliku;}
        return out;
    }
}

public class main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException{
        DataBase db =  new DataBase();
        Connection conn = db.connection;
        Statement stat = conn.createStatement();
        stat.setQueryTimeout(5);

        ResultSet rs = stat.executeQuery("Select * from Kontakty_Telefon;");

        Scanner scan = new Scanner(System.in);

        System.out.println("Podaj nazwę kontaktu: ");
        String nazwa = scan.nextLine();

        System.out.println("Podaj numer telefonu: ");
        String telefon;
        telefon = scan.next();

        if(telefon.length()!=9){
            while(telefon.length()!=9){
                System.out.println("Numer w nieprawidłowym formacie 9 cyfr\n spróbuj jeszcze raz: ");
                telefon = scan.next();
            }
        }

        while (rs.next()){
            String nrtel = rs.getString("Nr_Telefonu");
            if(nrtel.contains(telefon) == true){

                while(nrtel.contains(telefon) == true){
                    System.out.println("Numer się powtórzył\n podaj go jeszcze raz: ");
                    telefon = scan.next();

                    if(telefon.length()!=9){
                        while(telefon.length()!=9){
                            System.out.println("Numer w nieprawidłowym formacie 9 cyfr\n spróbuj jeszcze raz:");
                            telefon = scan.next();
                        }
                    }
                }
            }
        }

        stat.executeUpdate(db.kodSQL("src\\javaTest\\tabela.txt"));

        String query = "insert into Kontakty_Telefon ( ID, Nazwa, Nr_Telefonu)" +
        " values (NULL,'"+nazwa+"','"+telefon+"');";
        stat.executeUpdate(query);
        ResultSet rs2 = stat.executeQuery("Select * from Kontakty_Telefon;");
        while (rs2.next()){
            System.out.print(  rs.getInt("ID")  );
            System.out.print(" ; ");
            System.out.println(  rs.getString("Nazwa")  );
            System.out.print(" ; ");
            System.out.println(  rs.getString("Nr_Telefonu")  );
        }
   }
}

