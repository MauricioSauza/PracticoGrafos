package bo.edu.uagrm.ficct.inf310sb.grafosNoPesados;

public class Conexo {

    DFS grafoDFS;

    public Conexo (Grafo unGrafo) {
        grafoDFS = new DFS(unGrafo,0);
    }

    public Conexo (Grafo unGrafo, int posVertice) {
        grafoDFS = new DFS(unGrafo, posVertice);
    }

    public boolean esConexo (Grafo grafo) {
        return grafoDFS.hayCaminoATodos();
    }



}
