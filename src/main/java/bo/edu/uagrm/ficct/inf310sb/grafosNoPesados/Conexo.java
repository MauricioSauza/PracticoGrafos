package bo.edu.uagrm.ficct.inf310sb.grafosNoPesados;

public class ConexoNoDirigido {

    DFS grafoDFS;

    public ConexoNoDirigido (Grafo unGrafo) {
        grafoDFS = new DFS(unGrafo,0);
    }

    public ConexoNoDirigido (Grafo unGrafo, int posVertice) {
        grafoDFS = new DFS(unGrafo, posVertice);
    }

    public ConexoNoDirigido (DiGrafo unDiGrafo) {
        grafoDFS = new DFS(unDiGrafo,0);
    }

    public ConexoNoDirigido (DiGrafo unDiGrafo, int posVertice) {
        grafoDFS = new DFS(unDiGrafo, posVertice);
    }

    public boolean esConexo() {
        return grafoDFS.hayCaminoATodos();
    }



}
