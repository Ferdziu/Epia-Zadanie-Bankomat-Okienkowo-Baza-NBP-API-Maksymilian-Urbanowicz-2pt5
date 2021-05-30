package Paczka01.zs;

public class main {
    public double stan, procent;
    public static void main(String[] args) {
        main konto = new main();
        konto.procent_konto(120,10).procent_konto(100,10).procent_konto(200,50);

    }
    main(){
        stan=0;
        procent = 0;

    }
    main(double stan, double procent){
        this.stan = stan;
        this.procent = procent;
        System.out.println("stan konta: ___________________ " + stan);
        System.out.println("Procent: ___________________ " + procent);
    }
    public main procent_konto(double stan, double procent){
        double wynik =0;
        System.out.println("Liczba: ___________________ " + stan);
        System.out.println("Procent: ___________________ " + procent);
        procent/=100 ;
        wynik = procent * stan ;
        System.out.println("Wynik: ___________________ "+wynik);
        return this;
    }
}
