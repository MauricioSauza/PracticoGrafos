package bo.edu.uagrm.ficct.inf310sb.grafosNoPesados;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class MétodosGrafos {
    private Grafo grafo;
    private Conexo conexo;
    private DFS dfs;
    private UtilsRecorridos controlMarcados;

    public MétodosGrafos (Grafo unGrafo) {
        this.grafo = unGrafo;
        this.conexo = new Conexo(grafo);
        controlMarcados = new UtilsRecorridos(unGrafo.cantidadDeVertices());
    }


    /**
     *  8. Para un grafo no dirigido implementar un algoritmo
     *  para encontrar que en que vértices del grafo hay ciclos
     *
     *
     * @param: vertice de Origen y de destino
     * @return: Doble, el costo minimo para ir del vertice de origen al de destino
     *
     * */

    public LinkedList<Integer> ejercicio8() {
        Grafo grafoAux = new Grafo(grafo.cantidadDeVertices());
        LinkedList<Integer> veticesEnCiclo = new LinkedList<>();
        for(int i = 0; i < grafo.cantidadDeVertices(); i++) {
            if(ciclo(grafoAux, i)) {
                veticesEnCiclo.add(i);
            }
        }
        return veticesEnCiclo;
    }


    public boolean ciclo() {
        for(int i = 0; i < grafo.cantidadDeVertices(); i++) {
            Grafo grafoAux = new Grafo(grafo.cantidadDeVertices());
            if(ciclo(grafoAux, i)) {
                return true;
            }
        }
        return false;
    }

    public boolean ciclo(Grafo grafoAux, int posVertice) {
        controlMarcados.marcarVertice(posVertice);
        Iterable<Integer> adyacentesDeVertice = grafo.listaDeAdyacencia.get(posVertice);
        for (Integer adyacentes : adyacentesDeVertice) {
            if(!controlMarcados.estaVerticeMarcado(adyacentes)) {
                if(!grafoAux.existeAdyacencia(posVertice, adyacentes) && posVertice != adyacentes) {
                    grafoAux.insertarArista(posVertice, adyacentes);
                    ciclo(grafoAux, adyacentes);
                }
            } else {
                if(!grafoAux.existeAdyacencia(posVertice, adyacentes) && posVertice != adyacentes) {
                    return true;
                }
            }
        }

        return false;
    }


    /**
     *  9. Para un grafo no dirigido implementar un algoritmo para
     *  encontrar el número de islas que hay en el grafo
     *
     * */
    public int ejercicio9() {
        int cantIslas = 1;
        if(!conexo.esConexo(grafo)) {
            dfs = new DFS(grafo, 0);
            for (int i = 0; i < grafo.listaDeAdyacencia.size(); i++) {
                if (!dfs.controlMarcados.estaVerticeMarcado(i)) {
                    cantIslas++;
                    dfs.procesarDFS(i);
                }
            }

        }
        return cantIslas;
    }


}
