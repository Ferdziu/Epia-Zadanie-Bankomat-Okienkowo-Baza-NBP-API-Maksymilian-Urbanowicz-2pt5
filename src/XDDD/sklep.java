package XDDD;

public class sklep {
    private int id;
    private String imie;
    private String nazwisko;

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getImie() {return imie;}
    public void setImie(String imie) {this.imie = imie;}

    public String getNazwisko() {return nazwisko;}
    public void setNazwisko(String nazwisko) {this.nazwisko = nazwisko;}

    public sklep(){}
    public sklep(int id, String imie, String nazwisko){
        this.nazwisko = nazwisko;
        this.imie = imie;
        this.id = id;
    }
    @Override
    public String toString() {return"[" + id + "] - " + imie + " - " + nazwisko;}
}


