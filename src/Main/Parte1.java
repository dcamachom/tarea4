package Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Parte1 {
    
    static public void main(String [] args){
        
    }

    //función para leer la matriz de entrada
    public int[][] readInput(String ruta) throws IOException{
        try {
            int [][] m;
            BufferedReader br= new BufferedReader(new FileReader(ruta));
            String linea = br.readLine();
			int longitud = Integer.parseInt(linea);
			m = new int[longitud][longitud];
            int fila=0;
            while (linea!=null){
                String[] f=linea.split(" ");
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
        return null;
    }

    public int[][] dijsktra(int [][] m){

        return null;
    }

    public int[][] BellmanFord(int [][] m){

        return null;
    }

    public int[][] floyd(int[][]m){

        return null;
    } 


}
