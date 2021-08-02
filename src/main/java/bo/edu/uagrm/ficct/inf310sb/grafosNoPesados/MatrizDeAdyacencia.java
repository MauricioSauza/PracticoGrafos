package bo.edu.uagrm.ficct.inf310sb.grafosNoPesados;

import java.util.List;

public class MatrizDeAdyacencia {
    Grafo grafo;

    public MatrizDeAdyacencia(Grafo unGrafo) {
       grafo = unGrafo;
    }

    private void llenarCeros(int m[][]) {
        for(int i = 0; i < m.length; i++) {
            for(int j = 0; j < m.length; j++) {
                m[i][j] = 0;
            }
        }
    }

    public int[][] matrizDeAdyacencia(Grafo unGrafo) {
        int m[][] = new int[unGrafo.listaDeAdyacencia.size()][unGrafo.listaDeAdyacencia.size()];
        llenarCeros(m);
        for(int i = 0; i < unGrafo.listaDeAdyacencia.size(); i++) {
            List<Integer> listaAdyacente = unGrafo.listaDeAdyacencia.get(i);
            for(int k = 0; k < listaAdyacente.size(); k++) {
                int pos = listaAdyacente.get(k);
                for(int j = 0; j < unGrafo.listaDeAdyacencia.size(); j++) {
                    if(pos == j) {
                        m[i][j] = 1;
                    }
                }
            }
        }
        return m;
    }


    public String toString(int m[][]) {
        String matriz = "";
        for(int i = 0; i < m.length; i++) {
            for(int j = 0; j < m.length; j++) {
                matriz = matriz + m[i][j] + " " + ",";
            }
            matriz = matriz + "\n";
        }
        return matriz;
    }


}
