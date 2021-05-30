package Paczka01;

import java.util.*;

public class kolekc_01 {
    public static void main(String[] args) {
        // zmiennej nadajemy typ interfejsu, nie klasy.
        // np. List<String> ll = new LinkedList<>();
        // List <- interfejs ;  LinkedList <- klasa z implementacja interfejsu List
        // List -   kolejnosc elementow jest istotna;
        //          elementy moga sie powtarzac
        // LISTA:
        List<String> ll = new LinkedList<>();   // korzystamy, gdy czesto usuwamy elementy
        List<String> al = new ArrayList<>();    // szybszy dostep do roznych elementow
        ll.add("ala");
        ll.add("basia");
        System.out.println(ll.get(1));
        //ZBIOR:
        //przechowuje unikaty
        //za pomoca metod hashCode oraz equals sprawdzamy, czy elemet jest juz w zbiorze
        Set<String> s1 = new HashSet<>();
        Set<Integer> ssss = new HashSet();
        Set<String> s2 = new HashSet<>();
        s1.add("aaa");
        s1.add("bbb");
        s2.add("aaa222");
        s2.add("bbb222");
        System.out.println(s1);
        System.out.println(s1.isEmpty());
        System.out.println(s1.contains("aaa"));
        System.out.println(s1.size());
        System.out.println(s1.addAll(s2));
        System.out.println(s1);
        System.out.println(s1.remove("ccc"));
        for(String item : s1){ System.out.print(item + " ; "); }
        System.out.println("\n---");

        //--------------------------------------
        // Klucze sa unikalne
        // W przypadku dodania takiej pary klucz-wartosc, ktorej klucz juz istnieje
        // nadpiszemy poprzedni element.
        Map<String,String> map_s = new HashMap<>(); // inaczej slownik lub tablica asocjacyjna
        Map<Integer, Integer> map_i = new HashMap<>(); // bez typow prostych (nie int, tylko Integer)
        map_s.put("klucz_01", "wart_01");
        System.out.println( map_s.get("klucz_01") );
        System.out.println(map_s.containsKey("klucz_01"));
        System.out.println(map_s.containsValue("wart_01"));
        System.out.println(map_s.containsValue("wart_02"));

        //  Wybrane metody kolekcji:
        //  add         - dodaje element do kolekcji;
        //  addAll      - dodaje inna kolekcje do kolekci (parametrem jest inna kolekcja);
        //  contains    - jako parametr przyjmuje element kolekcji, a zwraca flagę - czy istnieje w kolekcji
        //  isEmpty     - zwraca flage, czy kolekcja jest pusta;
        //  indexOf     - zwraca index pierwszego wystapienia elemntu w kolekcji(element jest argumentem)
        //  lastIndexOf - zwraca index ostatniego wystapienia elementu
        //  size        - zwraca ilosc elementow;
        //  get(x)      - zwraca wartosc elementu listy


    }
}
