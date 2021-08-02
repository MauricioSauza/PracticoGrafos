package bo.edu.uagrm.ficct.inf310sb.grafosNoPesados;

import java.util.Iterator;

/**
 * 7. Para un grafo dirigido implementar un algoritmo para encontrar si es débilmente conexo
 *
 * */

public class DebilMenteConexo {
    private DFS recorrido;
    private DiGrafo digrafo;
    private int SIN_VERTICE_NO_MARCADO = -1;

    public DebilMenteConexo(DiGrafo unDigrafo) {
        this.digrafo = unDigrafo;
    }

    public boolean esDebilMenteConexo() {
        recorrido = new DFS(digrafo, 0);
        int proximoVerticeMarcado = 0;
        while(proximoVerticeMarcado != SIN_VERTICE_NO_MARCADO) {
            proximoVerticeMarcado = verticeNoMarcadoConAdyacenteMarcado();
            if(proximoVerticeMarcado != SIN_VERTICE_NO_MARCADO) {
                recorrido.procesarDFS(proximoVerticeMarcado);
            }
        }
        return recorrido.hayCaminoATodos();
    }

    private int verticeNoMarcadoConAdyacenteMarcado() {
        boolean existeVertice = false;
        int proximoVerticeNoMarcado = 0;
        while(proximoVerticeNoMarcado < digrafo.cantidadDeVertices() && !existeVertice) {
            if(!recorrido.hayCaminoAVertice(proximoVerticeNoMarcado)) {
                Iterator<Integer> adyacentesVertice = digrafo.adyancentesDeVertice(proximoVerticeNoMarcado).iterator();
                while(adyacentesVertice.hasNext() && !existeVertice) {
                    existeVertice = recorrido.hayCaminoAVertice(adyacentesVertice.next());
                }
                if(!existeVertice) {
                    proximoVerticeNoMarcado++;
                }
            } else {
                proximoVerticeNoMarcado++;
            }
        }
        if(proximoVerticeNoMarcado >= digrafo.cantidadDeVertices()) {
            return SIN_VERTICE_NO_MARCADO;
        } else {
            return proximoVerticeNoMarcado;
        }
    }


}
