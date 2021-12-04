package actividad8reinas;
import java.io.*;
import java.util.ArrayList;
public class Actividad8reinas {
//Made by: Fernando Núñez Bejarano   al02758861
    
    private int awa; //Auxiliar
    private int[] solucion; 
    private boolean ExisteLaSolucion; //Si existe solución?
    private ArrayList LaSolucion = new ArrayList();
    private boolean[] DiagonalArriba; 
    private boolean[] DiagonalAbajo; 
    private boolean[] horizontal; //Para las filas
    private boolean[] vertical;  //Para las columnas
    
    
    public void ImprimeResultado(){ //Imprime el resultado
        int count = 0;
        int a;
        int b;
        char[][] tableroR = {      //Crea el tablero real
        {'V', 'V', 'V', 'V', 'V', 'V', 'V', 'V'},
        {'V', 'V', 'V', 'V', 'V', 'V', 'V', 'V'},
        {'V', 'V', 'V', 'V', 'V', 'V', 'V', 'V'},
        {'V', 'V', 'V', 'V', 'V', 'V', 'V', 'V'},
        {'V', 'V', 'V', 'V', 'V', 'V', 'V', 'V'},
        {'V', 'V', 'V', 'V', 'V', 'V', 'V', 'V'},
        {'V', 'V', 'V', 'V', 'V', 'V', 'V', 'V'},
        {'V', 'V', 'V', 'V', 'V', 'V', 'V', 'V'},};
        
                for (int i = 0 ; i < LaSolucion.size(); i++){
            int[] aux  = (int[]) LaSolucion.get(i);
            System.out.println("Eh encontrado que las reinas se pueden colocar en las coordenadas:");
            for (int j = 0; j<aux.length;j++){
                System.out.print("(" + (j+1) + "," + (aux[j]+1) + ")");
                a = j;
                b = aux[j];
                tableroR [a][b] = 'R';
            }
            System.out.println("");
            System.out.println("");
        } 
        System.out.println("Tomando en cuenta que: ");
        System.out.println("- La letra V, significa VACIO");
        System.out.println("- La letra R, significa REINA");
        System.out.println("");
        System.out.println("El tablero se vería asi: ");
        System.out.println("---------------------------------------");
        count = 0;    
    for (int i = 0; i < tableroR.length; ++i) {  //Imprime el tablero de disponibilidad
      for(int j = 0; j < tableroR[i].length; ++j) {
          count++;
        System.out.print("  " + tableroR[i][j] + "  ");
        if (count == 8){
            count = 0;
            System.out.println("");
            System.out.println("");
        }
      }

    }
    System.out.println("----------------------------------------");
        }

 
    public Actividad8reinas(int tamanio){   //Establecer el tamaño de el tablero
        if (tamanio < 4) throw new NullPointerException();
        this.awa = tamanio;
        
        Empezar();
    }
 
    private void Empezar(){    //Hace que todo el tablero este "disponible" antes de empezar a colocar las reinas
        this.horizontal = new boolean[awa];
        this.vertical = new boolean[awa];
        this.solucion = new int[awa];
        for (int i = 0; i<awa; i++){
            this.horizontal [i] = true;
            this.vertical [i] = true;
            this.solucion [i] = -1;
        }
        this.DiagonalAbajo = new boolean[2*awa-1];
        this.DiagonalArriba = new boolean[2*awa-1];
        for (int i = 0; i<2*awa-1;i++){
            this.DiagonalAbajo[i] = true;
            this.DiagonalArriba[i] = true;
        }
        ExisteLaSolucion = false; //Indica que no existe solucion debido a que se acaba de llenar el tablero
    }
 
    private void buscarSolucion(int fila){  
        int columna = 0; //Este queda por default
        while (columna < awa && !ExisteLaSolucion){ //Corre mientras que nuestra columna sea menor que nuestra auxiliar y que no haya una solucion ya encontrada
            if (horizontal[fila] && vertical[columna] && DiagonalAbajo[columna-fila+awa-1] && DiagonalArriba[columna+fila]){
 
                solucion[fila] = columna; 
                horizontal[fila] = false;
                vertical[columna] = false;
                DiagonalAbajo[columna-fila+awa-1] = false;
                DiagonalArriba[columna+fila] = false;
 
                if (fila == awa-1 && solucionNueva(this.solucion)){ //Si existe la solución indicara que es verdadero
                    ExisteLaSolucion = true;
                }else{                                       //En caso de que no sea asi, prosedera a la recursividad.
                    if (fila+1 < awa ){ //Si la fila mas uno es menor a nuestra auxiliar tomara este camino
                        buscarSolucion(fila+1); //Recursividad
                    }
                    if (!ExisteLaSolucion){ //Si aun no existe la solucuion      
                        solucion[fila] = -1;
                        horizontal[fila] = true;
                        vertical[columna] = true;
                        DiagonalAbajo[columna-fila+awa-1] = true;
                        DiagonalArriba[columna+fila] = true;
 
                    }
                }
            }
            columna++;
        }
    }
    
 

    private void agregarSolucion(){ //Nos guarda la solución
        LaSolucion.add(this.solucion);  
    }
    private boolean solucionNueva(int[] nuevaSolucion){ //Checara si la solución es nueva
        if (nuevaSolucion[0] == -1) return false;
        boolean esNueva = true;
        int i = 0;
        while (i<LaSolucion.size() && esNueva){ //Correra mientras i sea menor al tamaño de la solucion y que esNueva sea verdadera
            int[] unaSolucion = (int[]) LaSolucion.get(i);
            esNueva = !sonIguales(unaSolucion,nuevaSolucion);
            i++;
        }

        return esNueva;
    }
    private  boolean sonIguales (int[] a, int[] b){ //funcion para revisar si dos cosas son iguales
        int i = 0;
        int j = 0;
        boolean indicador = true;        
        while ((i<a.length) && (j<b.length)){
            if(a[i] != b[j]){
                return false;
            }
            i++;
            j++;            
        }
        return indicador;
    }
     
    public ArrayList ObtenerSolucion(){ //Obtiene la solucion y la regresa a nosotros
        return this.LaSolucion;
    }
    
        public void BuscarLaSolucion(){ //Este es el metodo que mandaremos a llamar
        buscarSolucion(0); //Fila donde empezaremos
        agregarSolucion();
    }
        
        
    
    
 //-----------------------------( FUNCIÖN MAIN)--------------------------------------------------------------------
    
    public static void main(String[] args) {
        System.out.println("Bienvenido, permiteme encontrar una solución al problema de las 8 reinas");
        System.out.println("");
        
        Actividad8reinas reinas = new Actividad8reinas(8); //No mover el numero de 8 hahahah.
        
        reinas.BuscarLaSolucion();
        
        ArrayList LaSolucion = reinas.ObtenerSolucion();
        
        reinas.ImprimeResultado();

}
}


