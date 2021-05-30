package Bankomat;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/* TO DO:
* Zamiana pliku na bazę SQLLITE
* Zapytanie czy wydrukować potwierdzenie?
* wypłącanie w banknotach, ilość
 ( Banknot     Ilość
   10          1
   20          2
)
* Zabezpieczenie, jeśli nie ma odpowiedniej ilości banknotów to nie wypłacasz i wylogowuje cj i drukujesz potwierdzenie
* Przewalutowanie i wypłata gotówki w odpowiedniej jednostce
*rejestracja użytkownika
* Transakcje / operacje
* Wersja okienkowa
* */

class Bank
{
    public Boolean koniec, goNext;
    public int kasa;
    public int pin, id, pinUser, idUser;
    public List <Integer> uzytkownicy;
    public List <Integer> password;
    public List <Float> stanKonta;
    public JFrame frame = new JFrame();
    public JButton button = new JButton();

    public Bank(){


        frame.setSize(500, 500);
        frame.setLocation(50,50);
        frame.setTitle("siema");
        //frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setBackground(Color.green);

        frame.setVisible(true);


        this.kasa = 0;
        this.pin = 0;
        this.id = 0;
        this.pinUser = 0;
        this.idUser = 0;
        this.koniec = false;
        this.goNext = true;
        this.uzytkownicy = new ArrayList();
        this.password = new ArrayList();
        this.stanKonta = new ArrayList();


    }
    public void getUsers() throws FileNotFoundException {

        File file = new File("src\\Bankomat\\klienci.txt");

        boolean exists = file.exists();
        System.out.println(file.getName() );
        if( exists == true){
            Scanner input = new Scanner(file);
            while(input.hasNext()) {
                //w jednej linie: id pin stanKonta
                int l1 = input.nextInt();
                uzytkownicy.add(l1);
                int l2 = input.nextInt();
                password.add(l2);
                float l3 = input.nextFloat();
                stanKonta.add(l3);
                System.out.println(l1 +" ,\n"+l2 + " ,\n " + l3);
            }
        }else{
            goNext = false;
            System.out.println("Przepraszamy, mamy chwilowe problemy techniczne!\n Prosze spróbować za chwilę.");
        }
    }

    public void pin() throws FileNotFoundException {
        getUsers();
        if(goNext == true) {
            System.out.println("Witaj w bankomacie firmy Euronet");
            Scanner scan = new Scanner(System.in);
            System.out.println("Podaj id: ");
            id = scan.nextInt();
            System.out.println("Podaj pin: ");
            pin = scan.nextInt();
            check();
        }
    }
    private void changePin(){
        int errors = 0;
        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj obecny pin: ");
        pin = scan.nextInt();
        while(errors<3 && pin != pinUser){
            errors+=1; //0->1, 1->2, 2->3
            if(errors==3){
                System.out.println("Twoje konto zostało zablokowane.\nSkontaktuj się z najbliższym oddziałem twojego Banku.");
                break;
            }
            System.out.println("Podano zły pin.\nPodaj pin jeszcze raz: ");
            pin = scan.nextInt();

        }
        if((pin == pinUser)&&(errors<3)){
            System.out.println("Podaj nowy pin: ");
            int pinNew = scan.nextInt();
            while(pinNew == pin){
                System.out.println("Pin jest ten sam\n Podaj go jeszcze raz: ");
                pinNew = scan.nextInt();
            }
                System.out.println("Brawo zmieniłeś pin.\n Twój nowy pin to: "+ pinNew);
            }
        }


    public void check(){
        Scanner scan = new Scanner(System.in);
        System.out.println(pin +" "+ id);

        for(int i=0; i < uzytkownicy.size(); i++){

            if(( id ==  (int)uzytkownicy.get(i))&&( pin == (int)password.get(i))){
                System.out.println("Brawo użytkowniku o id: "+ id +" zalogowałeś sie do banku\n Twój pin to: "+pin);
                idUser = id;
                pinUser = pin;

                koniec = true;
                int chose = -1;

                System.out.println("Wybież jedną z opcji:\n 1. Wypłata środków\n 2. Wpłata środków\n 3. Zmiana pinu\n 4. Wykonanie transakcji");
                chose = scan.nextInt();

                switch(chose){
                    case 1:
                        getMoney();
                        break;
                    case 2:
                        giveMoney();
                        break;
                    case 3:
                        changePin();
                        break;
                    case 4:
                        transaction();
                        break;
                    default:
                        break;
                }
            }
        }
        if(koniec==false){
            System.out.println("!!!Błędny pin, nie ma cię w bazie danych!!!");

        }
    }


    private void getMoney(){
        Scanner scan = new Scanner(System.in);

        System.out.println("Podaj kwotę: ");
        kasa = scan.nextInt();

        for(int i=0; i < uzytkownicy.size(); i++){
            if((id == (int)uzytkownicy.get(i))&&(pin == (int)password.get(i))){
                koniec = true;
                float stanKontaUser = stanKonta.get(i);
                if((stanKontaUser <=0) ||(( stanKontaUser-= kasa)<=0)){
                    System.out.println("Zam mało środków aby dokonać operaję");

                }else{
                stanKontaUser-= kasa;
                stanKonta.set(i, stanKontaUser);
                System.out.println("Twój stan konta to: "+ stanKontaUser + " PLN");
                System.out.println("Pobrano środki : "+ kasa + " PLN");
            }}}
        if(koniec==false){

            System.out.println("ERRORRR");
        }
        potwierdzenie();
    }


    private void giveMoney(){
        Scanner scan = new Scanner(System.in);

        System.out.println("Podaj kwotę: ");
        kasa = scan.nextInt();

        for(int i=0; i < uzytkownicy.size(); i++){
            if((id == (int)uzytkownicy.get(i))&&(pin == (int)password.get(i))){
                koniec = true;
                float stanKontaUser = stanKonta.get(i);

                stanKontaUser+= kasa;
                stanKonta.set(i, stanKontaUser);
                System.out.println("Twój stan konta to: "+ stanKontaUser + " PLN");
                System.out.println("Dodano środki : "+ kasa + " PLN");
            }}
        if(koniec==false){

            System.out.println("ERRORRR");
        }

    }

    private void potwierdzenie(){
        Scanner scaner = new Scanner(System.in);
        int what =0;
        System.out.println("Czy wydrukować potwierdzenie ?(1 = Tak)");
        what = scaner.nextInt();
        if (what == 1) {
            System.out.println("Srukowanie potwierdzenia");
        }

        System.out.println("Dowidzenia");
    }

    private void transaction(){
        Scanner scanner = new Scanner(System.in);
        int numberTransaction;

        Float moneyTransaction;
        for(int i=0; i < uzytkownicy.size(); i++){
            if((id == (int)uzytkownicy.get(i))&&(pin == (int)password.get(i))){
                koniec = true;
                Boolean isKasa = true;
                float stanKontaUser = stanKonta.get(i);

                System.out.println("Ile transakcji chcesz wykonać: ");

                numberTransaction = scanner.nextInt();

                for(int j=0; j<numberTransaction; j++){
                    Scanner scan = new Scanner(System.in);
                    System.out.println("Podaj nazwę transakcji: ");
                    String nameTrans = scan.nextLine();
                    System.out.println("Podaj kwotę transakcji: ");
                    moneyTransaction= scanner.nextFloat();

                    if(stanKontaUser-moneyTransaction<0){
                        isKasa=false;
                        break;
                    }else{
                        stanKontaUser-= moneyTransaction;
                        stanKonta.set(i, stanKontaUser);

                   }
                }

                if(isKasa == true){
                    System.out.println("Transakcje wykonane pomyślnie");
                }else{
                    System.out.println("Na twojim koncie zabrakło środków więc postanowliliśmy przerwać wykonywanie dalszych transakcji ");
                }

            }}
        if(koniec==false){

            System.out.println("ERRORRR");
        }
    }
    public void okno(){

        /*
        JButton greenButton = new JButton("Green");
        JButton blueButton = new JButton("Blue");
        JButton redButton = new JButton("Red");

        add(greenButton);
        add(blueButton);
        add(redButton);*/
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        button.setSize(200,300);

        frame.add(button);


        //panele
        JPanel p1 = new JPanel();
        p1.setBackground(Color.black);
        p1.setPreferredSize(new Dimension(100,100));
        frame.add(p1,BorderLayout.EAST);
        p1.setVisible(false); //ukryj

        button.addActionListener(e ->  p1.setVisible(true)); //poka

        //pop-up
        JOptionPane.showConfirmDialog(null, "BłĄD SySTEMU", "xddd",JOptionPane.ERROR_MESSAGE);

        int answer = JOptionPane.showConfirmDialog(null, "czy kontunuować?", "xddd",JOptionPane.YES_NO_CANCEL_OPTION);
        if(answer==0){ //yes --> 0, no --> 1, cancel --> 2
            System.out.println("xd");

        //POLE DO PIASNIA!!!!!!
        JTextField textField = new JTextField();
        textField.setPreferredSize( new Dimension(250,40));
        frame.add(textField);
        JButton button2 = new JButton("Submit");
        frame.add(button2);
        String info;
        JLabel label = new JLabel();


        button2.addActionListener(e -> { label.setText( textField.getText()); });
        frame.add(label);
        }
    }


   /*private void kwota(){
        System.out.println("Podaj kwotę: ");
        Scanner scan = new Scanner(System.in);
        kasa = scan.nextInt();
        System.out.println("Do wyboru");
        System.out.println("1. wypłata środków");
        System.out.println("2. wpłata środków");
        System.out.println("3. stan konta");
        System.out.println("4. przewalutowanie");
        System.out.println("5. wyjście ");
        System.out.println("Co robimy? ");
        do{
            odp = scan.nextLine();
        switch(odp) {
            case "1":
                wplata();
                break;

            case "2":
                wyplata();
                break;
            case "3":
                System.out.println("Twój stan to: " + stanKonta);
                break;
            case "4":
                System.out.println("Jaka waluta?  1 Euro / 2 Dolar:");
                double mnoznik=1;
                int odp2;
                odp2 = scan.nextInt();
                if(odp2 == 1){
                    mnoznik = 4.25;
                }else{
                    mnoznik = 3.75;
                }
                System.out.println("Twój stan to: " + przewalutowanie(kasa, mnoznik));
                break;
            case "5":
                koniec = true;
                break;
            default:
                System.out.println("Nie rozuiemi, spróbuj jeszcze raz");
                break;
        }}while(koniec == false);

    }
    public void wplata(){
        stanKonta += kasa;
        System.out.println("Twój stan to: " +stanKonta);
    }
    public void wyplata(){
        stanKonta -= kasa;
        System.out.println("Twój stan to: " +stanKonta);
    }
    public double przewalutowanie(int kasa, double mnoznik){
        double kwota2 =0;
        kwota2 = kasa * mnoznik;
        return kwota2;
    }


*/
}

public class Bankomat {
    public static void main(String[] args) throws FileNotFoundException {



        Bank B1 = new Bank();
        B1.okno();
        B1.pin();

    }
}
