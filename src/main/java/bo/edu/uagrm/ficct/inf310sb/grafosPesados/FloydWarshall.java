package bo.edu.uagrm.ficct.inf310sb.grafosPesados;

import java.util.List;

/**
 *  12. Para un grafo dirigido usando la implementación del algoritmo de Floyd-Wharsall encontrar
 *  los caminos de costo mínimo entre un vértice a y el resto.
 *  Mostrar los costos y cuáles son los caminos
 *
 * */
public class FloydWarshall {
    private DiGrafoPesado digrafo;
    int INFINITO = Integer.MAX_VALUE;

    public FloydWarshall (DiGrafoPesado digrafo) {
        this.digrafo = digrafo;
    }

    void floydWarshall() {
        int tamaño = digrafo.cantidadDeVertices();
        int dist[][] = minimasDistancias();
        int i, j, k;
        for (k = 0; k < tamaño; k++) {
            // Pick all vertices as source one by one
            for (i = 0; i < tamaño; i++) {
                // Pick all vertices as destination for the
                // above picked source
                for (j = 0; j < tamaño; j++) {
                    // If vertex k is on the shortest path from
                    // i to j, then update the value of dist[i][j]
                    if (dist[i][k] + dist[k][j] < dist[i][j])
                        dist[i][j] = dist[i][k] + dist[k][j];
                }
            }
        }
        showMatriz(dist);
    }
    public int[][] minimasDistancias() {
        int tamaño = this.digrafo.cantidadDeVertices();
        int[][] matriz = new int[tamaño][tamaño];
        //inicializa con ceros e infinitos
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                if (i == j) {
                    matriz[i][j] = 0;
                } else {
                    matriz[i][j] = INFINITO;
                }
            }
        }
        for (int i = 0; i < tamaño; i++) {
            List<AdyacentesConPeso> adyacentesDeUnVertice = digrafo.listaDeAdyacencia.get(i);
            for (AdyacentesConPeso posDeAdyacente : adyacentesDeUnVertice) {
                matriz[i][posDeAdyacente.indiceVertice] = (int) posDeAdyacente.getPeso();
            }
        }
        return matriz;
    }

    public void showMatriz(int[][] matriz) {
        int tamaño = digrafo.cantidadDeVertices();
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
