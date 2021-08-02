package bo.edu.uagrm.ficct.inf310sb.grafosNoPesados;

public class FuerteMenteConexo {

  private boolean esFuerteMenteConexo;

  public FuerteMenteConexo (DiGrafo unDigrafo) {
    esFuerteMenteConexo = true;
    for (int i = 0; i < unDigrafo.cantidadDeVertices(); i++) {
      DFS dfs = new DFS(unDigrafo, i);
      esFuerteMenteConexo = dfs.hayCaminoATodos();
      if(!esFuerteMenteConexo) {
        break;
      }
    }
  }

  public boolean esFuerteMenteConexo() {
    return this.esFuerteMenteConexo;
  }
}
