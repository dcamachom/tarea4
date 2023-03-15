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

        String line= input.nextLine();
        int i= Integer.parseInt(line);

        switch(i){
            case 1:
            // input= new Scanner(System.in);
            System.out.println("Ingrese la ruta del archivo txt que contiene la matriz");
            String ruta= input.nextLine();
            int[][] m= readInput(ruta);
            int [][] res = dijsktraGeneral(m);
            break;
        }

        input.close();
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
                    m[fila][i]=Integer.parseInt(f[i]);
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
            list[i]=10000000;
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
        for(int j=0;j<m[0].length;j++){
            if (list[j]>m[f][j] && m[f][j]!=1){
                list[j]=m[f][j];
            }
        }
        return list;
    }


}
