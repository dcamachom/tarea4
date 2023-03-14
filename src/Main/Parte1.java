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

    //*Funcion para inicializar la lista con infinitos */
    public float[] inicializarList(int size){
        float[] list= new float[size];
        for (int i=0; i<size;i++){
            list[i]=Float.POSITIVE_INFINITY;
        }
        return list;
    }
    
    //*Funciones para implementar el algortimo de Dikjstra */
    public float[][] dijsktraGeneral(float [][] m){
        float[][] cMin= new float [m[0].length] [m[0].length];
        for (int i=0;i<m[0].length;i++){
            float[] list= dijkstraFuenteUnica(m, i);
            for (int j=0;j<list.length;j++){
                cMin[i][j]=list[j];
            }
        }
        return cMin;
    }

    public float[] dijkstraFuenteUnica (float [][] m, int f){
        float[] list= inicializarList(m[0].length);
        for(int j=0;j<m[0].length;j++){
            if (list[j]>m[f][j]){
                list[j]=m[f][j];
            }
        }
        return list;
    }


}
