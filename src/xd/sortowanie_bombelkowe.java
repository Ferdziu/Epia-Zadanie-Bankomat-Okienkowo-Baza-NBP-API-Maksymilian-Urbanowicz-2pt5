package xd;

public class sortowanie_bombelkowe {

        public static void main(String[] args) {

            int t []= {9,7,5,11,8,5,1,10,9,10};
            int n = t.length;

            System.out.println("ilosc liczb do posortowania: " + n);
            System.out.println("kolejnosc liczb przed sortowaniem: ");
            for(int k = 0; k<t.length; k++){ System.out.print("t[" + k + "]: " + t[k] + " ; "); }
            //-----
            int  pomoc;
            for(int i = 1; i<=n-1; i++){
                for(int j=n-1; j>=i; --j){
                    if(t[j-1]>t[j]){ pomoc = t[j-1]; t[j-1]=t[j]; t[j]=pomoc;}
                }
            }
            //-----
            System.out.println("\nkolejnosc liczb po sortowaniu: ");
            for(int k = 0; k<t.length; k++){ System.out.print(t[k] + " ; "); }
        }

}
