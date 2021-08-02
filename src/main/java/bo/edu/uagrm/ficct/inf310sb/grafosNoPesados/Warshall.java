package bo.edu.uagrm.ficct.inf310sb.grafosNoPesados;

public class Warshall {
    Grafo grafo;


    /**
     *  11. Para un grafo dirigido implementar el algoritmo de Wharsall,
     *  que luego muestre entre que vértices hay camino
     *
     * */

    public Warshall(Grafo unGrafo) {
        this.grafo = unGrafo;
    }

    public int[][] algoritmoWharsall (){
        int matriz[][] = matrizDeAdyacencia();
        for (int k = 0; k < grafo.cantidadDeVertices(); k++){
            for (int i = 0; i < grafo.cantidadDeVertices(); i++){
                for (int j = 0; j < grafo.cantidadDeVertices(); j++){
                    matriz[i][j] = matriz[i][j] | (matriz[k][j] & matriz[i][k]);
                }
            }
        }
        return matriz;
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
    private void Inicializar(int[][] matriz, int tamaño) {
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                matriz[i][j] = 0;
            }
        }
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
}




