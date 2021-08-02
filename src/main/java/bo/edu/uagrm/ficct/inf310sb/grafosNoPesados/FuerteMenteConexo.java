package bo.edu.uagrm.ficct.inf310sb.grafosNoPesados;

import java.util.List;

public class ConexoDirigido {
  private ConexoNoDirigido grafoConexo;
  private boolean esFuerteMenteConexo;

  public ConexoDirigido(DiGrafo unDigrafo) {
    esFuerteMenteConexo = true;
    for (int i = 0; i < unDigrafo.listaDeAdyacencia.size(); i++) {
      DFS dfs = new DFS(unDigrafo, i);
      esFuerteMenteConexo = dfs.hayCaminoATodos();
    }
  }

  public boolean esFuerteMenteConexo() {
    return this.esFuerteMenteConexo;
  }
}
