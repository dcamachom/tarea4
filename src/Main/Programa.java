package Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Programa {
    
    public static void main(String [] args) throws IOException{

        boolean salir = false;
        Scanner input= new Scanner(System.in);

        while(!salir){
            System.out.println("Ingrese el algoritmo que desea usar. Para salir oprima Enter");
            System.out.println("1. DIJSKTRA");
            System.out.println("2. FLOYD-WARSHALL");
            System.out.println("3. BELLMAN-FORD");
            System.out.println("4. BFS para encontrar componentes conectados");
            System.out.println("5. DFS para determinar si existen ciclos");
            int i=0;
            try{
                i= Integer.parseInt(input.nextLine());
            }catch(Exception e){
                salir = true;
                continue;
            }
           
            System.out.println("Ingrese la ruta del archivo txt que contiene la matriz");
            String ruta= input.nextLine();

            int[][] matriz= readInput(ruta);
            int [][] res = new int[matriz.length][matriz.length]; //formato de respuesta para la parte 1.
            Integer[] rta2 = new Integer[matriz.length]; //formato de respuesta para la parte 2.
            boolean rta3=false;
            long tInicio = -1;
            long tFin = 0;
            String alg="";
            switch(i){
                case 1:
                    tInicio = System.currentTimeMillis();
                    res = dijsktraGeneral(matriz);
                    tFin = System.currentTimeMillis();
                    alg="DIJSKTRA";
                break;
                case 2:
                    tInicio = System.currentTimeMillis();
                    res = floydWarschall(matriz);
                    tFin = System.currentTimeMillis();
                    alg="FLOYD-WARSHALL";
                break;
                case 3:
                    tInicio =  System.currentTimeMillis();
                    res = bellmanFord(matriz);
                    tFin =  System.currentTimeMillis();
                    alg = "BELLMAN-FORD";
                break;
                case 4:
                    tInicio =  System.currentTimeMillis();
                    rta2 = bfsComCon(matriz);
                    tFin =  System.currentTimeMillis();
                    alg = "BFS para encontrar componentes conectados";
                break;
                case 5:
                    tInicio =  System.currentTimeMillis();
                    rta3 = cycle(matriz); 
                    tFin =  System.currentTimeMillis();
                    alg = "DFS para determinar si existen ciclos";
                    
                break;
                default:
                    System.out.println("Debe seleccionar un numero entre 1 y 5.");
                    salir = true;
                    continue;
            }
            if(i == 1 || i == 2 || i == 3){  //formato de impresion para la parte 1.
                printMatriz(res);
                System.out.println("\n--------------------------------------\ntiempo de ejecucion del algoritmo: " + alg +" es: "
                                    + (tFin-tInicio) + " milisegundos\n--------------------------------------\n");
            }
            else if (i==4) {  //formato de impresion para la parte 2.
                printList(rta2, Collections.max(Arrays.asList(rta2)));
                System.out.println("\n--------------------------------------\ntiempo de ejecucion del algoritmo: " + alg +" es: "
                                    + (tFin-tInicio) + " milisegundos\n--------------------------------------\n");
            } 
            else{
                if (rta3)
                    System.out.println("\nEl grafo tiene al menos un ciclo");
                else
                    System.out.println("\nEl grafo no tiene ciclos");
                System.out.println("\n--------------------------------------\ntiempo de ejecucion del algoritmo: " + alg +" es: "
                                    + (tFin-tInicio) + " milisegundos\n--------------------------------------\n");
            }
        }
        input.close();
    }


    //-------------------------------------------------------------------------
    // FUNCION PARA IMPRIMIR LA MATRIZ (PARTE 1)
    //-------------------------------------------------------------------------
    public static void printMatriz(int[][]m){
        for(int i=0;i<m[0].length;i++){
            for (int j=0;j<m[0].length;j++){
                System.out.print(m[i][j]+"\t");
            }
            System.out.println();
            
        }
    }

    //-------------------------------------------------------------------------
    // FUNCION PARA IMPRIMIR LA LISTA (PARTE 2)
    //-------------------------------------------------------------------------

    public static void printList(Integer[] lista, int num){

        System.out.print("\n{");
        for (int i = 1; i <= num; i++) {
            System.out.print("{");
            for (int j = 0; j< lista.length; j++) {
                if(lista[j] == i){
                    System.out.print(j+ ",");
                }
            }
            System.out.print("}");
            if(i!= num)
                System.out.print(",");
        }
        System.out.print("}");
    }

    
    //-------------------------------------------------------------------------
    // FUNCION PARA LEER LA MATRIZ DE ENTRADA
    //-------------------------------------------------------------------------
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
                    
                    try{
                        if(Integer.parseInt(f[i])==-1){
                            m[fila][i]= Integer.MAX_VALUE;
                        }
                        else{
                            m[fila][i]=Integer.parseInt(f[i]);
                        }
                    }
                    catch(NumberFormatException e){

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
    

    //-------------------------------------------------------------------------
    // ALGORITMO DE DIJSKTRA
    //-------------------------------------------------------------------------
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
        int[] list=new int[m[0].length];
        List<Integer> vUsados= new ArrayList<>();
        vUsados.add(f);
        for(int j=0;j<m[0].length;j++){
           list[j]=m[f][j];
        }
        while (vUsados.size()<=m[0].length){
            int w=f;
            for (int i=0;i<list.length;i++){
                if (!vUsados.contains(i) && ( w==f ||list[i]< list[w])){
                    w=i;
                }
            }
            vUsados.add(w);            
            for (int i=0; i<m[0].length;i++){
                if(!vUsados.contains(i) && m[w][i]!=Integer.MAX_VALUE){
                    list[i]= Math.min(list[i], list[w]+m[w][i]);
                }
            }
        }
        return list;
    }

    //-------------------------------------------------------------------------
    // ALGORITMO DE FLOYD-WARSHALL
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


    //-------------------------------------------------------------------------
    // ALGORITMO DE BELLMAN FORD
    //-------------------------------------------------------------------------
    public static int[][] bellmanFord(int[][] m){
        int [][] cMin = new int [m.length][m.length];
        for (int i=0; i<m[0].length;i++){
            int [] list= bellmanFordFuenteUnica(m, i);
            for (int j=0; j<list.length;j++){
                cMin[i][j]=list[j];
            }
        }
        return cMin;
     }

     public static int [] bellmanFordFuenteUnica (int [][] m, int f){
        int [] res= new int [m.length];
        int s=m.length;
        for (int i=0;i<m.length;i++){
            res[i]=Integer.MAX_VALUE;
        }
        res[f]=0;
        for (int i=0;i<m.length-1;i++){
            for (int u=0;u<m.length;u++){
                for (int v=0;v<m.length;v++){
                    if ((m[u][v] != Integer.MAX_VALUE && res[u] != Integer.MAX_VALUE)){
                        res[v]=Math.min(res[v], res[u]+m[u][v]);
                    }  
                }
            }
        }
        return res;
     }

    //-------------------------------------------------------------------------
    // --------------------------PARTE 2 Y 3-----------------------------------
    //-------------------------------------------------------------------------


    //-------------------------------------------------------------------------
    // ALGORITMO DE BFS PARA ENCONTRAR COMPONENTES CONECTADOS
    //-------------------------------------------------------------------------

    public static Integer[] bfsComCon(int[][] matrix){

        Integer[] rta = new Integer[matrix.length];

        //para saber si ese vertice ya esta en un componente conectado o no.
        for (int i = 0; i < rta.length; i++) {
            rta[i] =-1;
        }
        int cont =1;

        
        for (int i = 0; i < matrix.length; i++) {
            if(rta[i] != -1) //ya esta marcado.
                continue;
            else{
                bfs(i,rta,cont,matrix);
                cont ++;
            }
        }
        return rta;
    }

    public static void bfs(int v, Integer[] marcados, int cont, int[][] matrix){

        LinkedList<Integer> cola = new LinkedList<>();

        //marca el vertice y lo encola
        marcados[v] = cont;
        cola.add(v);

        while(cola.size() != 0){
            int adj = cola.poll();
            for (int i = 0; i < marcados.length; i++) {
                if(matrix[adj][i] < Integer.MAX_VALUE){
                    
                    if(marcados[i] ==-1){
                        marcados[i] = cont;
                        cola.add(i);
                    }
                }   
            }
        }
    }

    //-------------------------------------------------------------------------
    // ALGORITMO DE DFS PARA SABER SI EXISTEN CICLOS
    //-------------------------------------------------------------------------
    public static boolean cycle_aux(int[][] matrix, ArrayList<Integer>vistos,ArrayList<Integer> pila,int i){
        vistos.add(i);
        pila.add(i);
        boolean tmp=false;
        int j=0;
        while (j<matrix[0].length && !tmp){
            if (vistos.contains(j) && matrix[i][j]!=Integer.MAX_VALUE && pila.contains(j) && j!=i)
                tmp= true;

            else if(!vistos.contains(j) && matrix[i][j]!=Integer.MAX_VALUE){
                tmp= tmp || cycle_aux(matrix,vistos,pila,j);
            }
            j++;
            
        }
        pila.remove(pila.size()-1);
        return tmp;
    }

    public static boolean cycle(int[][] matrix){
        boolean res= false;
        int i=0;
        ArrayList<Integer> pila= new ArrayList<>();
        ArrayList<Integer> list= new ArrayList<>();
        while(!res && i<matrix.length){
            res= cycle_aux(matrix,list,pila,i);
            i++;
        }
        return res;
    }

}
