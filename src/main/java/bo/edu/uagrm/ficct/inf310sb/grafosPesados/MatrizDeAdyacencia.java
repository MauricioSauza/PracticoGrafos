package bo.edu.uagrm.ficct.inf310sb.grafosPesados;

import bo.edu.uagrm.ficct.inf310sb.grafosNoPesados.Grafo;

import java.util.List;

public class MatrizDeAdyacencia {
    GrafoPesado grafoPesado;

    public MatrizDeAdyacencia(GrafoPesado unGrafoPesado) {
        grafoPesado = unGrafoPesado;
    }

    private void llenarCeros(int m[][]) {
        for(int i = 0; i < m.length; i++) {
            for(int j = 0; j < m.length; j++) {
                m[i][j] = 0;
            }
        }
    }

    public int[][] matrizDeAdyacencia(GrafoPesado unGrafoPesado) {
        int m[][] = new int[unGrafoPesado.listaDeAdyacencia.size()][unGrafoPesado.listaDeAdyacencia.size()];
        llenarCeros(m);
        for(int i = 0; i < unGrafoPesado.listaDeAdyacencia.size(); i++) {
            List<AdyacentesConPeso> listaAdyacente = unGrafoPesado.listaDeAdyacencia.get(i);
            for(int k = 0; k < listaAdyacente.size(); k++) {
                AdyacentesConPeso componente = listaAdyacente.get(k);
                int pos = componente.getIndiceVertice();
                for(int j = 0; j < unGrafoPesado.listaDeAdyacencia.size(); j++) {
                    if(pos == j) {
                        m[i][j] = (int)componente.getPeso();
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
