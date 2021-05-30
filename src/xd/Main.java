package xd;
import java.util.Scanner;


class Klasa_02 { /*klasa programu*/
    private int x,y;
    public Klasa_02(int X, int Y){ /*konstruktor*/
        this.x = X;
        this.y = Y;
    }
    public void display(){
        System.out.println("X: " + x);
        System.out.println("Y: " + y);
    }


}

public class Main {
    public static void main(String[] args){
        System.out.println("xddddd"); //wyświetla tekst w konsoli

        Scanner sc = new Scanner(System.in);

        var tekst = sc.nextLine();
        System.out.println("Siema " + tekst);

        System.out.println("--------Klasy----------");

        Klasa_02 k02 = new Klasa_02(5,7);
        k02.display();

        Klasa_03 k03 = new Klasa_03(5);
        k03.display();



    }
}