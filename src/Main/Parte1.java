package Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Parte1 {
    
    static public void main(String [] args) throws IOException{

        System.out.println("Ingrese el algoritmo que desea usar");
        Scanner input= new Scanner(System.in);
        System.out.println("1. DIJSKTRA");
        System.out.println("2. FLOYD-WARSHALL");
        System.out.println("3. BELLMAN-FORD");
        int i= Integer.parseInt(input.nextLine());

        System.out.println("Ingrese la ruta del archivo txt que contiene la matriz");
        String ruta= input.nextLine();

        int[][] matriz= readInput(ruta);
        int [][] res = new int[matriz.length][matriz.length];
        long tInicio = -1;
        long tFin = 0;
        switch(i){
            case 1:
                tInicio = System.currentTimeMillis();
                res = dijsktraGeneral(matriz);
                tFin = System.currentTimeMillis();
            break;
            case 2:
                tInicio = System.currentTimeMillis();
                res = floydWarschall(matriz);
                tFin = System.currentTimeMillis();
            break;
            case 3:
            break;
            default:
                System.out.println("Debe ingresar un numero entre 1 y 3.");
        }
        printMatriz(res);
        System.out.println("\n--------------------------------------\ntiempo de ejecucion del algoritmo de DIJSKTRA: " 
                            + (tFin-tInicio) + " milisegundos\n--------------------------------------\n");
        input.close();
    }

    //-------------------------------------------------------------------------
    // FUNCION DE IMPRESION DE LA MATRIZ 
    //-------------------------------------------------------------------------
    public static void printMatriz(int[][]m){
        for(int i=0;i<m[0].length;i++){
            for (int j=0;j<m[0].length;j++){
                System.out.print(m[i][j]+"\t");
            }
            System.out.println();
            
        }
    }
    
    //función para leer la matriz de entrada
    public static int[][] readInput(String ruta) throws IOException{
        
        int [][] m = new int[0][0];
        try {
            
            BufferedReader br= new BufferedReader(new FileReader(ruta));
            String linea = br.readLine().trim();

            String [] f=linea.split("\t");
			int longitud = f.length;
			m = new int[longitud][longitud];
            int fila=0;
            while (linea!=null){
                f=linea.split("\t");
                for (int i=0; i<f.length;i++){

                    if(Integer.parseInt(f[i])==-1){
                        m[fila][i]= Integer.MAX_VALUE;
                    }
                    else{
                        m[fila][i]=Integer.parseInt(f[i]);
                    }
                }
                fila++;
                linea=br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se encuenta el archivo");
            e.printStackTrace();
        }

        return m;
    }

    //*Funcion para inicializar la lista con infinitos */
    public static int[] inicializarList(int size){
        int[] list= new int[size];
        for (int i=0; i<size;i++){
            list[i]=Integer.MAX_VALUE;
        }
        return list;
    }
    
    //*Funciones para implementar el algortimo de Dikjstra */
    public static int[][] dijsktraGeneral(int [][] m){
        int[][] cMin= new int [m[0].length] [m[0].length];
        for (int i=0;i<m[0].length;i++){
            int[] list= dijkstraFuenteUnica(m, i);
            for (int j=0;j<list.length;j++){
                cMin[i][j]=list[j];
            }
        }
        return cMin;
    }

    public static int[] dijkstraFuenteUnica (int [][] m, int f){
        int[] list= inicializarList(m[0].length);
        int [] vUsados= new int[m[0].length];
        vUsados[0]=f;
        for(int j=0;j<m[0].length;j++){
            if (list[j]>m[f][j] && m[f][j]!=-1){
                list[j]=m[f][j];
            }
        }
        int cont=0;
        int cont2=0;
        while (cont2<m[0].length){
            int w=f;
            for (int i=0;i<list.length;i++){
                if (!contains(vUsados,list[i]) && ( w==f ||list[i]< list[w])){
                    w=i;
                }
            }
            cont+=1;
            vUsados[cont2]=w;            
            for (int i=0; i<m[0].length;i++){
                if(!contains(vUsados, i) && m[w][i]!=Integer.MAX_VALUE){
                    list[i]= Math.min(list[i], list[w]+m[w][i]);
                }
            }
        cont2+=1;
        }
        return list;
    }

    public static boolean contains (int[] list, int obj){
        for (int i=0;i<list.length;i++){
            if (list[i]==obj){
                return true;
            }
        }
        return false;
    }


    //-------------------------------------------------------------------------
    // ALGORITMO DE Floyd-Warschall
    //-------------------------------------------------------------------------

    public static int[][] floydWarschall(int[][] matrix){

        for(int k = 0; k < matrix.length; k++){

            for (int i = 0; i < matrix.length; i++) {

                for (int j = 0; j < matrix.length; j++) {
                    
                    try{
                        if(Math.addExact(matrix[i][k],  matrix[k][j])< matrix[i][j]){
                            matrix[i][j] = matrix[i][k] + matrix[k][j];
                        }

                    }catch(ArithmeticException e){
                    }

                }
            }
        }

        return matrix;

    }
   

}
