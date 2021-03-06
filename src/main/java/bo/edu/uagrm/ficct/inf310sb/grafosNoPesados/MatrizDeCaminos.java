package bo.edu.uagrm.ficct.inf310sb.grafosNoPesados;

import java.util.LinkedList;

public class MatrizDeCaminos {
    private Grafo grafo;

    public MatrizDeCaminos(Grafo unGrafo) {
        this.grafo = unGrafo;
    }

    public int[][] matrizDeAdyacencia() {
        int tamaño = this.grafo.cantidadDeVertices();
        int[][] matriz = new int[tamaño][tamaño];
        Inicializar(matriz, tamaño);
        for (int i = 0; i < tamaño; i++) {
            for (int j : grafo.listaDeAdyacencia.get(i)) {
                matriz[i][j] = 1;
            }
        }
        return matriz;
    }

    public int[][] matrizDeCaminos() {
        int[][] matriz = matrizDeAdyacencia();
        LinkedList<int[][]> potencias = listaDePotencias(matriz);
        int[][] suma = matriz;
        for (int i = 0; i < potencias.size(); i++) {
            suma = sumar(suma, potencias.get(i));
        }
        return suma;
    }
    public LinkedList<int[][]> listaDePotencias(int[][] matriz) {
        int tamaño = matriz.length;
        LinkedList<int[][]> Lista = new LinkedList();
        for(int i=2; i<tamaño+1;i++){
            Lista.add(matrizElevada(matriz,i));
        }
        return Lista;
    }
    public int[][] sumar(int[][] A, int[][] B) {
        int[][] suma = new int[A.length][A.length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                suma[i][j] = A[i][j] + B[i][j];
            }
        }
        return suma;
    }

    public void showMatriz(int[][] matriz) {
        int tamaño = grafo.cantidadDeVertices();
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void Inicializar(int[][] matriz, int tamaño) {
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                matriz[i][j] = 0;
            }
        }
    }

    public int[][] matrizElevada(int[][] matriz, int n) {
        int[][] resultado = matriz;
        for (int i = 1; i < n; i++) {
            resultado = multiplicar(matriz, resultado);
        }
        return resultado;
    }

    public int[][] multiplicar(int[][] A, int[][] B) {
        int casilla = 0;
        int[][] resultado = new int[A.length][B.length];
        for (int i = 0; i < A.length; i++) { //que fila
            for (int j = 0; j < A.length; j++) { //que columna
                for (int k = 0; k < A.length; k++) { //va recorriedo la fila y la columna
                    int fico = A[i][k] * B[k][j];
                    casilla = casilla + fico;
                }
                resultado[i][j] = casilla;
                casilla = 0;
            }
        }
        return resultado;
    }

}
