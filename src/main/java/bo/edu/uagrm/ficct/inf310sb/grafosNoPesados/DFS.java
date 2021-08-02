package bo.edu.uagrm.ficct.inf310sb.grafosNoPesados;

import java.util.ArrayList;
import java.util.List;

public class DFS {
    protected UtilsRecorridos controlMarcados;
    private Grafo grafo;
    protected List<Integer> recorrido;

    public DFS(Grafo unGrafo, int posVerticePartida) {
        this.grafo = unGrafo;
        grafo.validarVertice(posVerticePartida);
        recorrido = new ArrayList<>();
        controlMarcados = new UtilsRecorridos(this.grafo.cantidadDeVertices()); //Ya está todo desmarcado
        procesarDFS(posVerticePartida);
    }

    public void procesarDFS(int posVertice) {
        controlMarcados.marcarVertice(posVertice);
        recorrido.add(posVertice);
        Iterable<Integer> adyacentesDeVerticeEnTurno = grafo.adyancentesDeVertice(posVertice);
        for(Integer posVerticeAdyacente : adyacentesDeVerticeEnTurno) {
            if(!controlMarcados.estaVerticeMarcado(posVerticeAdyacente)) {
                procesarDFS(posVerticeAdyacente);
            }
        }
    }

    public boolean hayCaminoAVertice(int posVertice) {
        grafo.validarVertice(posVertice);
        return controlMarcados.estaVerticeMarcado(posVertice);
    }

    public Iterable<Integer> alRecorrido() {
        return this.recorrido;
    }

    public boolean hayCaminoATodos() {
        return controlMarcados.estanTodosMarcados();
    }



}
