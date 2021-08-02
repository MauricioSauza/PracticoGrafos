package bo.edu.uagrm.ficct.inf310sb.grafosNoPesados;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS {
    private UtilsRecorridos controlMarcados;
    private Grafo grafo;
    private List<Integer> recorrido;

    public BFS(Grafo unGrafo, int posVerticePartida) {
        this.grafo = unGrafo;
        grafo.validarVertice(posVerticePartida);
        recorrido = new ArrayList<>();
        controlMarcados = new UtilsRecorridos(this.grafo.cantidadDeVertices()); //Ya está todo desmarcado
        ejecutarBFS(posVerticePartida);
    }

    private void ejecutarBFS(int posVertice) {
        Queue<Integer> cola = new LinkedList<>();
        cola.offer(posVertice);
        controlMarcados.marcarVertice(posVertice);
        do {
            int posVerticeEnTurno = cola.poll();
            recorrido.add(posVerticeEnTurno);
            Iterable<Integer> adyacentesDEVerticeEnTurno = grafo.adyancentesDeVertice(posVerticeEnTurno);
            for (Integer posVerticeAdyacente : adyacentesDEVerticeEnTurno) {
                if(!controlMarcados.estaVerticeMarcado(posVerticeAdyacente)) {
                    cola.offer(posVerticeAdyacente);
                    controlMarcados.marcarVertice(posVerticeAdyacente);
                }
            }
        } while (!cola.isEmpty());
    }

    public boolean hayCaminoAVertice(int posVertice) {
        grafo.validarVertice(posVertice);
        return controlMarcados.estaVerticeMarcado(posVertice);
    }

    public Iterable<Integer> elRecorrido() {
        return this.recorrido;
    }


}
