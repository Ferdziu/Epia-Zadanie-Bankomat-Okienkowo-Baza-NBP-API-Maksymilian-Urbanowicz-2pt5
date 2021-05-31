package XDDD;

import org.json.simple.parser.ParseException;


import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;


public class Bank extends GUI {
    public int x;
    public  final int[] errors = {0};
    public boolean koniec, goNext, havePin, went;
    public int kasa, pin, position, id, pinUser, idUser, transNumb;
    public float stanKontaUser;
    public java.util.List<Integer> uzytkownicy;
    public java.util.List<Integer> password;
    public java.util.List<Integer> blocked1;
    public java.util.List<Float> stanKonta;
    public String awizoNazwa;
    public float awizoKwota;
    public float awizoStanKonta;


    //private AnimationClass ac = new AnimationClass();

    Bank() throws InterruptedException, SQLException, ClassNotFoundException, IOException {
        super();

        adds();
        this.x=0;
        this.position = -1;
        this.kasa = 0;
        this.pin = 0;
        this.id = 0;
        this.pinUser = 0;
        this.idUser = 0;
        this.stanKontaUser=0;
        this.transNumb = 0;
        this.koniec = false;
        this.goNext = true;
        this.havePin = false;
        this.went=false;
        this.uzytkownicy = new ArrayList();
        this.password = new ArrayList();
        this.stanKonta = new ArrayList();
        this.blocked1 = new ArrayList();

        this.awizoNazwa="brak";
        this.awizoKwota = 0;
        this.awizoStanKonta = 0;
    }

    public void adds() throws InterruptedException {
        JLabel label  = new JLabel();
        label.setBounds(0,0,1280,1000);
        label.setText("");
        label.setBackground(Color.gray);
        label.setOpaque(true);
        frame.add(label);

        //label.setText("<html><p style='text-align:center;'>Tu powinna być reklama</p></html>");

        //setText("Tu powinna być reklama", 500, 500, 1280, 100, 0x00ff00,"Arial", 85, label);

        button.setBounds(0,860,1280,100);
        button.setText("Naciśnij przycisk aby przejść do logowania");
        button.setFont(new Font("Arial", Font.PLAIN,50));

        ImageIcon img1;
        img1 = new ImageIcon("src\\xd.png");
        label.setVerticalAlignment(JLabel.TOP);
        label.setIcon(img1);


        Timer timer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon img1;
                img1 = new ImageIcon("src\\xd.png");
                label.setIcon(img1);
            }
        });

        Timer timer2 = new Timer(6000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon img2;
                img2 = new ImageIcon("src\\xd1.png");
                label.setIcon(img2);
            }
        });

        Timer timer3 = new Timer(4000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon img3;
                img3 = new ImageIcon("src\\xd2.png");
                label.setIcon(img3);
            }
        });

       timer.setRepeats(true);
       timer.start();

       timer2.setRepeats(true);
       timer2.start();

       timer3.setRepeats(true);
       timer3.start();

       label.add(button);
       button.addActionListener(e -> {
            //button.setBounds(0,860,0,0);
            timer3.stop();
            timer2.stop();
            timer.stop();

            label.setBounds(0,-15,0,0);
            frame.getContentPane().removeAll();

            try {

                loginIN();

            } catch (FileNotFoundException | SQLException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });

    }

    public void loginIN() throws FileNotFoundException, SQLException {
        getUsers2();

        if(goNext == true) {
            JLabel label  = new JLabel();
            label.setBounds(0,-15,1280,900);
            label.setText("");
            frame.add(label);
            setTexts("Panel logowania", 300, 150, 1280, 100, 0x00ff00,"Arial", 80, label);

            setTexts("Nr klienta", 100, 300, 1280, 100, 0x00ff00,"Arial", 50, label);

            JTextField Tid = new JTextField();
            Tid.setBounds( 350,300,525,100);
            Tid.setFont(font);
            label.add(Tid);

            setTexts("PIN", 200, 450, 1280, 100, 0x00ff00,"Arial", 50, label);

            JTextField Tpin = new JTextField();
            Tpin.setBounds( 350,450,525,100);
            Tpin.setFont(font);
            label.add(Tpin);

            JButton btnSEND = new JButton("Zaloguj");
            label.add(btnSEND);
            btnSEND.setFont(font);
            btnSEND.setBounds( 350,650,525,100);
            btnSEND.addActionListener(e ->{
               id = Integer.parseInt(Tid.getText());
               Tid.setText("");
               pin = Integer.parseInt(Tpin.getText());
               Tpin.setText("");
                label.setBounds(0,-15,0,0);
                frame.getContentPane().removeAll();
                try {
                    check();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
        }
    }

    public void check() throws FileNotFoundException, SQLException {

        //System.out.println(pin +" "+ id);
        boolean user = false;
        for(int i=0; i < uzytkownicy.size(); i++){

            if(( id ==  uzytkownicy.get(i))&&( pin == password.get(i))){
                position = i;
                user=true;
                if(blocked1.get(i) == 0) {
                    System.out.println("Brawo użytkowniku o id: " + id + " zalogowałeś sie do banku\n Twój pin to: " + pin);
                    koniec = true;
                    idUser = id;
                    pinUser = pin;
                    stanKontaUser = stanKonta.get(i);
                    opts();
                }else{
                    blocked();
                }
            }
        }
        if(user==false){
            loginIN();
        }
        if(koniec==false){
            System.out.println("!!!Błędny pin, nie ma cię w bazie danych!!!");

        }
    }
    // wersja z plikiem //
    /*public void getUsers() throws FileNotFoundException {
        File file = new File("src\\XDDD\\klienci.txt");
        boolean exists = file.exists();

        //System.out.println(file.getName() );

        if( exists == true){
            Scanner input = new Scanner(file);
            while(input.hasNext()) {
                //w jednej linie: id pin stanKonta
                int l1 = input.nextInt();
                uzytkownicy.add(l1);

                int l2 = input.nextInt();
                password.add(l2);
                String l23= input.next();
                float l3 = Float.parseFloat(l23);
                stanKonta.add(l3);

                int l4 = input.nextInt();
                blocked1.add(l4);

                System.out.println(l1 +", "+l2 + ",  " + l3 +", "+l4+",");
            }
        }else{
            goNext = false;
            JLabel label = new JLabel();
            System.out.println("Przepraszamy, mamy chwilowe problemy techniczne!\n Prosze spróbować za chwilę.");
            label.setText("<html><p style='text-align:center;'>Przepraszamy, mamy chwilowe problemy techniczne!<br/> Prosze spróbować za chwilę.</p></html>");
            label.setBounds(0,-15,1280,900);
            frame.add(label);
        }
    }*/

    public void getUsers2() throws SQLException {

        if(x==0){
        ResultSet rs = stat.executeQuery("Select * from User;");
        while (rs.next()) {
            String nr_user = rs.getString("nr_user");
            String pin = rs.getString("pin");
            String stan_konta = rs.getString("stan_konta");
            String zablokowany = rs.getString("zablokowany");
            //    System.out.println(nr_user + ", " + pin + ", " + Float.parseFloat( stan_konta) + ", " + zablokowany + ",");

            int l1 = Integer.parseInt(nr_user);
            uzytkownicy.add(l1);

            int l2 = Integer.parseInt(pin);
            password.add(l2);

            float l3 = Float.parseFloat(stan_konta);
            stanKonta.add(l3);

            int l4 = Integer.parseInt(zablokowany);
            blocked1.add(l4);
            x++;
        }


        }
        System.out.println(uzytkownicy+"\n"+blocked1+"\n"+stanKonta+"\n"+password );
    }

    public void opts(){
        JLabel label  = new JLabel();
        label.setBounds(0,-15,1280,900);
        label.setText("");
        frame.add(label);

        JLabel title = new JLabel();
        title.setText("Wybież jedną z opcji:");
        title.setBounds(200,150,1280,100);
        title.setFont(new Font("Arial", Font.PLAIN,90));
        title.setForeground(new Color(0x00ff00));
        label.add(title);

        JButton btn1 = new JButton("Wypłata środków");
        label.add(btn1);
        btn1.setFont(font);
        btn1.setBounds( 0,300,500,100);
        btn1.addActionListener(e ->{
            label.setBounds(0,-15,0,0);
            frame.getContentPane().removeAll();
            giveMoney();
        });

        JButton btn2 = new JButton("Transakcje");
        label.add(btn2);
        btn2.setFont(font);
        btn2.setBounds( 800,300,500,100);
        btn2.addActionListener(e ->{
            label.setBounds(0,-15,0,0);
            frame.getContentPane().removeAll();
            partTrans();
        });

        JButton btn3 = new JButton("Wpłata środków");
        label.add(btn3);
        btn3.setFont(font);
        btn3.setBounds( 0,450,500,100);
        btn3.addActionListener(e ->{
            label.setBounds(0,-15,0,0);
            frame.getContentPane().removeAll();
            getMoney();
        });

        JButton btn4 = new JButton("Zmiana pinu");
        label.add(btn4);
        btn4.setFont(font);
        btn4.setBounds( 800,450,500,100);
        btn4.addActionListener(e ->{
            label.setBounds(0,-15,0,0);
            frame.getContentPane().removeAll();
            try {
                changePin();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        JButton btn5 = new JButton("Stan konta");
        label.add(btn5);
        btn5.setFont(font);
        btn5.setBounds( 000,600,500,100);
        btn5.addActionListener(e ->{
            label.setBounds(0,-15,0,0);
            frame.getContentPane().removeAll();
            status();
        });

        JButton btn6 = new JButton("Przewalutowanie");
        label.add(btn6);
        btn6.setFont(font);
        btn6.setBounds( 800,600,500,100);
        btn6.addActionListener(e ->{
            label.setBounds(0,-15,0,0);
            frame.getContentPane().removeAll();
            exchangeMoney();
        });
    }

    public void getMoney(){
        //1. //kasa++ // goodTrans()
        JLabel label  = new JLabel();
        label.setBounds(0,-15,1280,900);
        label.setText("");
        frame.add(label);

        setTexts("Wpłata:", 400, 150, 1280, 100, 0x00ff00,"Arial", 100, label);

        setTexts("Kwota: ", 200, 400, 200, 100, 0x00ff00,"Arial", 50, label);

        setTexts("PLN", 900, 400, 200, 100, 0x00ff00,"Arial", 50, label);

        JTextField TKW = new JTextField();
        TKW.setBounds( 350,400,525,100);
        TKW.setFont(font);
        label.add(TKW);

        JButton btnNEXT = new JButton("Dalej");
        label.add(btnNEXT);
        btnNEXT.setFont(font);
        btnNEXT.setBounds( 350,600,500,100);
        btnNEXT.addActionListener(e ->{
            for(int i=0; i < uzytkownicy.size(); i++){
                if((id == uzytkownicy.get(i))&&(pin == password.get(i))){
                    koniec = true;
                    kasa = Integer.parseInt(TKW.getText());
                    stanKontaUser = stanKonta.get(i);
                    stanKontaUser+= kasa;
                    stanKonta.set(i, stanKontaUser);

                    String query = ("UPDATE User SET stan_konta = "+stanKonta.get(position)+" WHERE id = "+idUser+" AND pin = "+pinUser);
                    try {
                        stat.executeUpdate(query);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    System.out.println("Twój stan konta to: "+ stanKonta.get(position) + " PLN");
                    System.out.println("Dodano środki : "+ kasa + " PLN");
                    label.setBounds(0,-15,0,0);
                    frame.getContentPane().removeAll();
                    setAwizo("wpłata", (float) kasa, stanKonta.get(position));
                    putMoney();
                    break;
                }
            }
            if(koniec==false){
                System.out.println("ERRORRR");

            }
        });
    }

    public void giveMoney(){
        //2. //kasa-- // takeMoney() or badTrans()\
        JLabel label  = new JLabel();
        label.setBounds(0,-15,1280,900);
        label.setText("");
        frame.add(label);

        setTexts("Wypłata:", 400, 150, 1280, 100, 0x00ff00,"Arial", 100, label);

        setTexts("Kwota: ", 200, 400, 200, 100, 0x00ff00,"Arial", 50, label);

        setTexts("PLN", 900, 400, 200, 100, 0x00ff00,"Arial", 50, label);

        JTextField TKW = new JTextField();
        TKW.setBounds( 350,400,525,100);
        TKW.setFont(font);
        label.add(TKW);

        JButton btnNEXT = new JButton("Dalej");
        label.add(btnNEXT);
        btnNEXT.setFont(font);
        btnNEXT.setBounds( 350,600,500,100);
        btnNEXT.addActionListener(e ->{
            for(int i=0; i < uzytkownicy.size(); i++){
                if((id == uzytkownicy.get(i))&&(pin == password.get(i))){
                    koniec = true;
                    float stanKontaUser = stanKonta.get(i);

                    if((stanKontaUser <=0) ||(( stanKontaUser-= kasa)<=0)){
                        //System.out.println("Zam mało środków, aby dokonać operaję");
                        label.setBounds(0,-15,0,0);
                        frame.getContentPane().removeAll();
                        badTrans();
                        break;
                    }else {
                        kasa = Integer.parseInt(TKW.getText());
                        stanKontaUser -= kasa;
                        stanKonta.set(i, stanKontaUser);

                        String query = ("UPDATE User SET stan_konta = "+stanKonta.get(position)+" WHERE id = "+idUser+" AND pin = "+pinUser);
                        try {
                            stat.executeUpdate(query);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                        System.out.println("Twój stan konta to: " + stanKonta.get(position) + " PLN");
                        System.out.println("Zabrano środki : " + kasa + " PLN");
                        label.setBounds(0,-15,0,0);
                        frame.getContentPane().removeAll();
                        setAwizo("wypłata", (float) kasa, stanKonta.get(position));
                        takeMoney();
                        break;
                    }
                }
            }
            if(koniec==false){
                System.out.println("ERRORRR");

            }

        });
    }

    public void takeMoney(){
        //Prosze odebrac kase // goodTrans()
        JLabel label  = new JLabel();
        label.setBounds(0,-15,1280,900);
        label.setText("");
        frame.add(label);

        setTexts("Prosze odebrać kasę", 100, 400, 1280, 100, 0x00ff00,"Arial", 100, label);

        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setBounds(0,-15,0,0);
                frame.getContentPane().removeAll();
                goodTrans();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void putMoney(){
        //Prosze odebrac kase // goodTrans()
        JLabel label  = new JLabel();
        label.setBounds(0,-15,1280,900);
        label.setText("");
        frame.add(label);

        setTexts("Prosze włożyć kasę", 100, 400, 1280, 100, 0x00ff00,"Arial", 100, label);

        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setBounds(0,-15,0,0);
                frame.getContentPane().removeAll();
                goodTrans();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

   /* public void trans(){
        //3. //ilośc trans //nazwa / kwota // jak braknie kasy to stopTrans()
        JLabel label  = new JLabel();
        label.setBounds(0,-15,1280,900);
        label.setText("");
        frame.add(label);

        setTexts("Transakcje", 400, 150, 1280, 100, 0x00ff00,"Arial", 100, label);

        setTexts("Ilość: ", 200, 400, 200, 100, 0x00ff00,"Arial", 50, label);

        JTextField Tnumb = new JTextField();
        Tnumb.setBounds( 350,400,525,100);
        Tnumb.setFont(font);
        label.add(Tnumb);

        JButton btnSEND = new JButton("Dalej");
        label.add(btnSEND);
        btnSEND.setFont(font);
        btnSEND.setBounds( 350,650,525,100);
        btnSEND.addActionListener(e ->{
            transNumb= Integer.parseInt(Tnumb.getText());
            label.setBounds(0,-15,0,0);
            frame.getContentPane().removeAll();
            partTrans();
        });
    }*/

    public void partTrans(){
        JLabel label  = new JLabel();
        label.setBounds(0,-15,1280,900);
        label.setText("");
        frame.add(label);

        setTexts("Transakcje", 400, 150, 1280, 100, 0x00ff00,"Arial", 100, label);

        setTexts("Nazwa: ", 200, 300, 200, 100, 0x00ff00,"Arial", 50, label);

        JTextField Tname = new JTextField();
        Tname.setBounds( 350,300,525,100);
        Tname.setFont(font);
        label.add(Tname);

        setTexts("Kwota: ", 200, 450, 200, 100, 0x00ff00,"Arial", 50, label);

        JTextField Tkw = new JTextField();
        Tkw.setBounds( 350,450,525,100);
        Tkw.setFont(font);
        label.add(Tkw);

       // final int[] j = {0};

        JButton btnSEND = new JButton("Dalej");
        label.add(btnSEND);
        btnSEND.setFont(font);
        btnSEND.setBounds( 350,650,525,100);
        btnSEND.addActionListener(e ->{
            for(int i=0; i < uzytkownicy.size(); i++){
                if((id == (int)uzytkownicy.get(i))&&(pin == (int)password.get(i))){
                    koniec = true;
                    Boolean isKasa = true;
                    //if(j[0]<transNumb){
                        String nameTrans = Tname.getText();
                        Float moneyTransaction= Float.parseFloat(Tkw.getText());

                        if(stanKontaUser-moneyTransaction<0){
                            isKasa=false;
                            break;
                        }else{
                            stanKontaUser-= moneyTransaction;
                            stanKonta.set(i, stanKontaUser);
                            String query = ("UPDATE User SET stan_konta = "+stanKonta.get(position)+" WHERE id = "+idUser+" AND pin = "+pinUser);
                            try {
                                stat.executeUpdate(query);
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            Tname.setText(null);
                            Tkw.setText(null);
                            //j[0]++;
                        }
                    //}

                    if(isKasa == true )/*&& j[0] ==transNumb)*/{
                        //System.out.println("Transakcje wykonane pomyślnie");
                        label.setBounds(0,-15,0,0);
                        frame.getContentPane().removeAll();
                        setAwizo(nameTrans, moneyTransaction, stanKonta.get(position));
                        goodTrans();
                        break;
                    }else if(isKasa == false){
                        //System.out.println("Na twojim koncie zabrakło środków więc postanowliliśmy przerwać wykonywanie dalszych transakcji ");
                        label.setBounds(0,-15,0,0);
                        frame.getContentPane().removeAll();
                        setAwizo(nameTrans, (float) 0, stanKonta.get(position));
                        badTrans();
                        break;
                    }
                }
            }
            if(koniec==false){
                System.out.println("ERRORRR");

            }
        });
    }

    public void status(){
        JLabel label  = new JLabel();
        label.setBounds(0,-15,1280,900);
        label.setText("");
        frame.add(label);

        setTexts("Dostępne środki na koncie:", 50, 200, 1280, 110, 0x00ff00,"Arial", 100, label);

        setTexts(stanKontaUser+" PLN", 250, 500, 1280, 110, 0x00ff00,"Arial", 100, label);

        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setBounds(0,-15,0,0);
                frame.getContentPane().removeAll();
                opts();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void changePin() throws SQLException {
        //4. // takePin()

        if(blocked1.get(position) == -1) {
            blocked();

        }else{
            if (went == false) {
                takePin();

            } else if (havePin == true) {
                JLabel label  = new JLabel();
                label.setBounds(0,-15,1280,900);
                label.setText("");
                frame.add(label);

                setTexts("Podaj nowy pin: ", 0, 200, 500, 100, 0x00ff00,"Arial", 60, label);

                JTextField Tpin = new JTextField();
                Tpin.setBounds(350, 400, 525, 100);
                Tpin.setFont(font);
                label.add(Tpin);

                JButton btnNEXT = new JButton("Dalej");
                label.add(btnNEXT);
                btnNEXT.setFont(font);
                btnNEXT.setBounds(350, 600, 500, 100);
                btnNEXT.addActionListener(e -> {

                    int pinNew = Integer.parseInt(Tpin.getText());
                    System.out.println("Podaj nowy pin: " + pinNew);

                    if (pinNew == pinUser) {
                        Tpin.setText(null);
                        pin = 0;
                    } else {
                        label.setBounds(0,-15,0,0);
                        frame.getContentPane().removeAll();
                        password.set(position, pinNew);
                        went = false;
                        String query = ("UPDATE User SET pin = "+password.get(position)+" WHERE id = "+idUser+" AND pin = "+pinUser);
                        try {
                            stat.executeUpdate(query);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        goodPin();
                    }
                });

            }else{
                havePin =false;
                went=false;
                blocked();
            }
        }
    }

    public void goodPin(){
        //Prosze odebrac kase // goodTrans()
        JLabel label  = new JLabel();
        label.setBounds(0,-15,1280,900);
        label.setText("");
        frame.add(label);

        setTexts("Pin został zmieniony", 100, 400, 1280, 100, 0x00ff00,"Arial", 100, label);

        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setBounds(0,-15,0,0);
                frame.getContentPane().removeAll();
                try {
                    endTrans();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void takePin(){
        //go back trans() or blocked()
        final int[] x = {-1};
        JLabel label  = new JLabel();
        label.setBounds(0,-15,1280,900);
        label.setText("");
        frame.add(label);

        setTexts("Podaj obecny pin: ", 0, 200, 500, 100, 0x00ff00,"Arial", 60, label);

        JTextField Tpin = new JTextField();
        Tpin.setBounds( 350,400,525,100);
        Tpin.setFont(font);
        label.add(Tpin);

        JButton btnNEXT = new JButton("Dalej");
        label.add(btnNEXT);
        btnNEXT.setFont(font);
        btnNEXT.setBounds( 350,600,500,100);
        btnNEXT.addActionListener(e -> {
            pin = Integer.parseInt(Tpin.getText());

            if(errors[0] <3 && pin != pinUser){//0->1, 1->2, 2->3
                errors[0] +=1;
                System.out.println(errors[0]);
                Tpin.setText(null);
                pin=0;
            }

            if(errors[0] ==3){
                x[0] =1;
            }

            if(x[0] == 1){
                System.out.println(x[0]);
                label.setBounds(0,-15,0,0);
                frame.getContentPane().removeAll();
                havePin =false;
                went=true;
                try {
                    changePin();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }else if(x[0]!=1 && pin == pinUser){
                label.setBounds(0,-15,0,0);
                frame.getContentPane().removeAll();
                havePin =true;
                went=true;
                try {
                    changePin();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    public void blocked() throws SQLException {
        JLabel label  = new JLabel();
        label.setBounds(0,-15,1280,900);
        label.setText("");
        frame.add(label);

        setTexts("Konto jest zablokowane ", 100, 400, 1280, 110, 0x00ff00,"Arial", 100, label);
        blocked1.set(position, -1);

        String query = ("UPDATE User SET zablokowany = "+blocked1.get(position)+" WHERE id = "+idUser+" AND pin = "+pinUser);
        stat.executeUpdate(query);

        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setBounds(0,-15,0,0);
                frame.getContentPane().removeAll();
                try {
                    endTrans();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void exchangeMoney(){
        //takeAwizo() or endTrans()

        JLabel label = new JLabel();
        frame.add(label);
        label.setBounds(0,-15,1280,1000);

        setTexts("Wybierz jedną z opcji?", 10, 250, 1280, 250, 0x00ff00,"Arial", 85, label);

        JButton btnTAK = new JButton("Wpłata");
        label.add(btnTAK);
        btnTAK.setFont(font);
        btnTAK.setBounds( 0,600,400,100);
        btnTAK.addActionListener(e ->{
            label.setBounds( 0,600,0,0);
            label.setBounds(0,-15,0,0);
            frame.getContentPane().removeAll();
            getExchangeMoney();
        });

        JButton btnNIE = new JButton("Wypłata");
        label.add(btnNIE);
        btnNIE.setFont(font);
        btnNIE.setBounds( 870,600,400,100);
        btnNIE.addActionListener(e ->{
            label.setBounds(0,-15,0,0);
            frame.getContentPane().removeAll();
            giveExchangeMoney();
        });
    }

    public void getExchangeMoney(){
        //5. //wpłata w przewalutowaniu kasy
        JLabel label  = new JLabel();
        label.setBounds(0,-15,1280,900);
        label.setText("");
        frame.add(label);

        setTexts("Wpłata w wymianie:", 100, 150, 1280, 100, 0x00ff00,"Arial", 100, label);

        setTexts("Kwota: ", 200, 400, 200, 100, 0x00ff00,"Arial", 50, label);

        //setTexts("PLN", 900, 400, 200, 100, 0x00ff00,"Arial", 50, label);

        JTextField TKW = new JTextField();
        TKW.setBounds( 350,400,525,100);
        TKW.setFont(font);
        label.add(TKW);

        setTexts("Waluta: ", 180, 550, 200, 100, 0x00ff00,"Arial", 50, label);

        JComboBox select = new JComboBox();
        select.setBounds( 350,550,525,100);
        select.addItem("usd");
        select.addItem("chf");
        select.addItem("eur");
        select.setFont(font);
        label.add(select);

        JButton btnNEXT = new JButton("Dalej");
        label.add(btnNEXT);
        btnNEXT.setFont(font);
        btnNEXT.setBounds( 350,700,500,100);
        btnNEXT.addActionListener(e ->{
            kasa = Integer.parseInt(TKW.getText());

            for(int i=0; i < uzytkownicy.size(); i++){
                if((id == uzytkownicy.get(i))&&(pin == password.get(i))){
                    koniec = true;
                    float stanKontaUser = stanKonta.get(i);
                    try {
                        elowina(select.getSelectedItem().toString());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                    float przewal = kasa*mnoznik;
                    int przewalint = (int)przewal;
                    System.out.println("Po przewalutowaniu: "+przewalint);

                    System.out.println("Tyle z konta: "+stanKontaUser + " + "+ przewal + " = "+ (stanKontaUser +=przewal));
                    //stanKontaUser = stanKontaUser - essa;

                    System.out.println("Tyle do konta weszło: "+przewal);
                    System.out.println("Tyle z konta: "+stanKontaUser);
                    stanKonta.set(i, stanKontaUser);

                    //stanKontaUser += przewal;


                        //System.out.println("Twój stan konta to: " + stanKontaUser + " PLN");
                        //System.out.println("Dodano środki : " + kasa + " PLN");
                    label.setBounds(0,-15,0,0);
                    frame.getContentPane().removeAll();
                    String query = ("UPDATE User SET stan_konta = "+stanKonta.get(position)+" WHERE id = "+idUser+" AND pin = "+pinUser);
                    try {
                        stat.executeUpdate(query);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                        setAwizo("wpłata w "+select.getSelectedItem().toString(), (float) kasa, stanKonta.get(position));
                        putMoney();
                        break;
                    }
            }
            if(koniec==false){
                System.out.println("ERRORRR");

            }
        });
    }

    public void giveExchangeMoney(){
        //5. //wypłata w przewalutowaniu kasy
        JLabel label  = new JLabel();
        label.setBounds(0,-15,1280,900);
        label.setText("");
        frame.add(label);

        setTexts("Wypłata w wymianie:", 100, 150, 1280, 100, 0x00ff00,"Arial", 100, label);

        setTexts("Kwota: ", 200, 400, 200, 100, 0x00ff00,"Arial", 50, label);

        //setTexts("PLN", 900, 400, 200, 100, 0x00ff00,"Arial", 50, label);

        JTextField TKW = new JTextField();
        TKW.setBounds( 350,400,525,100);
        TKW.setFont(font);
        label.add(TKW);

        setTexts("Waluta: ", 180, 550, 200, 100, 0x00ff00,"Arial", 50, label);

        JComboBox select = new JComboBox();
        select.setBounds( 350,550,525,100);
        select.addItem("usd");
        select.addItem("chf");
        select.addItem("eur");
        select.setFont(font);
        label.add(select);

        JButton btnNEXT = new JButton("Dalej");
        label.add(btnNEXT);
        btnNEXT.setFont(font);
        btnNEXT.setBounds( 350,700,500,100);
        btnNEXT.addActionListener(e ->{
            kasa = Integer.parseInt(TKW.getText());

            for(int i=0; i < uzytkownicy.size(); i++){
                if((id == uzytkownicy.get(i))&&(pin == password.get(i))){
                    koniec = true;
                    float stanKontaUser = stanKonta.get(i);
                    try {
                        elowina(select.getSelectedItem().toString());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                    float przewal = kasa*mnoznik;
                    int przewalint = (int)przewal;
                    System.out.println("Po przewalutowaniu: "+przewalint);
                    System.out.println("Po przewalutowaniu: "+przewalint);
                    if((stanKontaUser <=0) ||(( stanKontaUser -= przewal)<=0)){
                        //System.out.println("Zam mało środków, aby dokonać operaję");
                        label.setBounds(0,-15,0,0);
                        frame.getContentPane().removeAll();
                        badTrans();
                        break;
                    }else {
                        System.out.println("Tyle z konta: "+stanKontaUser + " - "+ przewal + " = "+ (stanKontaUser -= przewal));
                        //stanKontaUser = stanKontaUser - essa;

                        System.out.println("Tyle z konta zeszło: "+ przewal);
                        System.out.println("Tyle z konta: "+stanKontaUser);
                        stanKonta.set(i, stanKontaUser);

                        //System.out.println("Twój stan konta to: " + stanKontaUser + " PLN");
                        //System.out.println("Dodano środki : " + kasa + " PLN");
                        label.setBounds(0,-15,0,0);
                        frame.getContentPane().removeAll();
                        String query = ("UPDATE User SET stan_konta = "+stanKonta.get(position)+" WHERE id = "+idUser+" AND pin = "+pinUser);
                        try {
                            stat.executeUpdate(query);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        setAwizo("wypłata w "+select.getSelectedItem().toString(), (float) kasa, stanKonta.get(position));
                        takeMoney();
                        break;
                    }
                }
            }
            if(koniec==false){
                System.out.println("ERRORRR");

            }
        });
    }

    public void goodTrans(){
        //Transakcja przebiegła pomyślnie // awizo()
        JLabel label  = new JLabel();
        label.setBounds(0,-15,1280,900);
        label.setText("");
        frame.add(label);

        setTexts("Tranzakcja przebiegła", 100, 400, 1280, 110, 0x00ff00,"Arial", 100, label);

        setTexts("pomyślnie :)", 250, 500, 1280, 110, 0x00ff00,"Arial", 100, label);


        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setBounds(0,-15,0,0);
                frame.getContentPane().removeAll();
                awizo();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void badTrans(){
        //Za mało środków // go to endTrans()
        JLabel label  = new JLabel();
        label.setBounds(0,-15,1280,900);
        label.setText("");
        frame.add(label);

        setTexts("Za mało środków", 100, 400, 1280, 110, 0x00ff00,"Arial", 100, label);

        setTexts("na koncie", 250, 500, 1280, 110, 0x00ff00,"Arial", 100, label);

        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                awizo();
                label.setBounds(0,-15,0,0);
                frame.getContentPane().removeAll();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void awizo(){
        //takeAwizo() or endTrans()

        JLabel label = new JLabel();
        frame.add(label);
        label.setBounds(0,-15,1280,1000);

        setTexts("Czy wydrukować potwierdzenie?", 10, 250, 1280, 250, 0x00ff00,"Arial", 85, label);

        JButton btnTAK = new JButton("TAK");
        label.add(btnTAK);
        btnTAK.setFont(font);
        btnTAK.setBounds( 0,600,400,100);
        btnTAK.addActionListener(e ->{
            label.setBounds( 0,600,0,0);
            label.setBounds(0,-15,0,0);
            frame.getContentPane().removeAll();
            makeAwizo();
        });

        JButton btnNIE = new JButton("NIE");
        label.add(btnNIE);
        btnNIE.setFont(font);
        btnNIE.setBounds( 870,600,400,100);
        btnNIE.addActionListener(e ->{
            label.setBounds(0,-15,0,0);
            frame.getContentPane().removeAll();
            try {
                endTrans();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
    }

    public void setAwizo(String nm, Float kw, Float st){
        awizoNazwa=nm;
        awizoKwota=kw;
        awizoStanKonta=st;

    }

    public void makeAwizo(){
        //drukowanie awizo // go to endTrans()
        JLabel label  = new JLabel();
        label.setBounds(0,0,1280,1000);
        label.setText("");
        frame.add(label);

        JLabel square  = new JLabel();
        square.setBounds(50,150,1200,800);
        square.setText("");
        square.setBackground(Color.gray);
        //square.setBorder();
        square.setOpaque(true);
        label.add(square);

        Calendar date = Calendar.getInstance();
        SimpleDateFormat formatDate = new SimpleDateFormat("YYYY-MM-dd");

        Calendar time = Calendar.getInstance();
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");

        setTexts("POTWIERDZENIE", 120, 50, 800, 100, 000000,"Arial", 80, square);

        setTexts("Operacja", 100, 130, 400, 100, 000000,"Arial", 60, square);
        setTexts(awizoNazwa, 100, 230, 400, 100, 000000,"Arial", 60, square);

        setTexts("Kwota", 450, 130, 200, 100, 000000,"Arial", 60, square);
        setTexts(String.valueOf(awizoKwota), 450, 230, 400, 100, 000000,"Arial", 60, square);

        setTexts("Stan konta", 700, 130, 500, 100, 000000,"Arial", 60, square);
        setTexts(String.valueOf(awizoStanKonta), 700, 230, 400, 100, 000000,"Arial", 60, square);

        setTexts("Nr Bankomatu: 1150BYZ", 50, 350, 800, 100, 000000,"Arial", 60, square);

        String DATE = "Data: "+formatDate.format(date.getTime());
        setTexts(DATE, 50, 500, 500, 100, 000000,"Arial", 60, square);

        String TIME = "Czas: "+formatTime.format(time.getTime());
        setTexts(TIME, 50, 600, 800, 100, 000000,"Arial", 60, square);

        //setTexts("na koncie", 250, 500, 1280, 110, 0x00ff00,"Arial", 100, label);

        Timer timer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    square.setOpaque(false);
                    label.setBounds(0,-15,0,0);
                    frame.getContentPane().removeAll();

                   takeAwizo();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void takeAwizo() throws InterruptedException {
        JLabel label  = new JLabel();
        label.setBounds(0,-15,1280,900);
        label.setText("");
        frame.add(label);

        setTexts("Prosze odebrać potwierdzenie", 10, 250, 1480, 250, 0x00ff00,"Arial", 90, label);

        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setBounds(0,-15,0,0);
                frame.getContentPane().removeAll();
                try {
                    endTrans();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void endTrans() throws InterruptedException {
        JLabel label  = new JLabel();
        label.setBounds(0,-15,1280,900);
        label.setText("");
        frame.add(label);

        setTexts("Dziękujemy za skorzystanie z", 50, 400, 1280, 110, 0x00ff00,"Arial", 90, label);

        setTexts("bankomatu firmy Euronet", 100, 500, 1280, 110, 0x00ff00,"Arial", 90, label);

        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setBounds(0,-15,0,0);
                frame.getContentPane().removeAll();
                try {
                    clearingLists();
                   // frame.setVisible(false);
                    adds();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

   /* public void writer() throws FileNotFoundException {

        File filew =new File("src\\XDDD\\klienci.txt");
        PrintWriter writer=new PrintWriter(filew);
        for (int j = 0; j < uzytkownicy.size(); j++) {
            writer.println(uzytkownicy.get(j)+" "+password.get(j)+" "+stanKonta.get(j)+" "+blocked1.get(j));
        }
        writer.close();
    }*/

    public void clearingLists(){
        uzytkownicy.clear();
        password.clear();
        stanKonta.clear();
        blocked1.clear();
        x=0;
        System.out.println(uzytkownicy+"\n"+blocked1+"\n"+stanKonta+"\n"+password );
    }

    public static void main(String[] args) throws InterruptedException, SQLException, ClassNotFoundException, IOException {
        Bank B1 = new Bank();
    }

}
