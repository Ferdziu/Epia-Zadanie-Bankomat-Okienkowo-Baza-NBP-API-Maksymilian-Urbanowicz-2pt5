package XDDD;

import groovy.json.JsonBuilder;
import groovy.json.JsonParser;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;

import javax.json.*;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;



import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GUI {
    public Connection connection;
    public Statement stat;

    public float mnoznik;

    public JFrame frame = new JFrame();
    public JButton button = new JButton();
    public Font font = new Font("SansSerif", Font.BOLD, 50);

    public GUI() throws SQLException, InterruptedException, ClassNotFoundException, IOException {
        DataBase();

        stat = connection.createStatement();

        this.mnoznik=1;

        openWindow();

    }


    public void openWindow() throws InterruptedException {
        frame.setSize(1280, 1000);
        frame.setLocation(250,10);
        frame.setTitle("EURONET");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.blue);

        frame.setLayout(null);

        frame.setVisible(true);

    }

    public void setTexts(String name, int x, int y, int width, int height, int color, String font, int size, JLabel labels){
        JLabel labels2 = new JLabel();
        labels2.setText(name);

        labels2.setFont(new Font(font, Font.PLAIN,size));
        labels2.setForeground(new Color(color));
        labels2.setBounds(x,y,width,height);

        labels.add(labels2);

    }


    public void DataBase() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:Euronet.db");
    }
    /*public String kodSQL(String plik_zKodemSQL) throws IOException {
        BufferedReader bfdR = new BufferedReader(new FileReader(plik_zKodemSQL));
        String liniaPliku;
        String out = "";
        while ( (liniaPliku = bfdR.readLine() ) != null){ out+=liniaPliku;}
        return out;
    }
*/
    public void elowina(String name) throws IOException, ParseException {


    String url = "http://api.nbp.pl/api/exchangerates/rates/a/"+name+"/";
    URL obj = new URL(url);
    HttpURLConnection connect = (HttpURLConnection) obj.openConnection();
    connect.setRequestMethod("GET");

    int code = connect.getResponseCode();
    System.out.println(code);
    if(code == 200){
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connect.getInputStream())
        );
        String input;
        StringBuffer response = new StringBuffer();
        while((input = in.readLine()) != null){
            response.append(input);
            System.out.println(response);


        }
        String xd = response.toString();
        in.close();

        JSONParser parser =  new JSONParser();
        Object objecn = parser.parse(xd);

        //


        JSONObject b = (JSONObject) objecn;

        Object g = parser.parse(b.get("rates").toString());

        JSONArray a = (JSONArray) g;

        Object d = parser.parse(a.get(0).toString());
        JSONObject c = (JSONObject) d;

        mnoznik=Float.parseFloat(c.get("mid").toString());
        System.out.println( c.get("effectiveDate")+" "+b.get("currency")+" "+c.get("mid"));

    }
    }
    public static void main(String[] args) throws InterruptedException, SQLException, ClassNotFoundException, IOException, ParseException {
        GUI i = new GUI();
        i.elowina("usd");
    }
}
