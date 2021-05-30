package poliformizm;

class figura2D {
    public int a;
    public int b;
    figura2D(int a, int b){
        this.a=a;
        this.b=b;
    }
    public float pole2D(){
        System.out.println(a*b);
        return a*b;
    }
}

class figura3D extends figura2D{
        public int c;
        figura3D(int a, int b, int c){
            super(a,b);
            this.c=c;
        }
        public void pole3D(){
            float Pp = pole2D();
            float Pba = c*a;
            float Pbb = c*b;
            float Pc = 2*Pp + 2* Pba + 2*Pbb;
            System.out.println("2*"+Pp+" + 2*"+Pba+" + 2*"+Pbb+" = "+ Pc);
        }
}

public class main {
    public static void main(String[] args){
       figura3D f1= new figura3D(1,2,3);
       f1.pole3D();

    }
}
