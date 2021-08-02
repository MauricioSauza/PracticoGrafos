package bo.edu.uagrm.ficct.inf310sb.grafosNoPesados;

import java.util.ArrayList;
import java.util.List;

public class UtilsRecorridos {
    protected List<Boolean> marcados;

    public UtilsRecorridos(int numVertices) {
        marcados = new ArrayList<>();
        for(int i = 0; i < numVertices; i++) {
            marcados.add(Boolean.FALSE);
        }
    }

    public void marcarVertice(int posVertice) {
        marcados.set(posVertice, Boolean.TRUE);
    }

    public void desmarcarVertice(int posVertice) {
        marcados.set(posVertice, Boolean.FALSE);
    }

    public boolean estaVerticeMarcado(int posVertice) {
        return marcados.get(posVertice);
    }

    public void desmarcarTodos() {
        for(int i = 0; i < marcados.size(); i++) {
            marcados.add(Boolean.FALSE);
        }
    }

    public boolean estanTodosMarcados() {
        for(Boolean marcado : marcados) {
            if(!marcado) {
                return false;
            }
        }
        return true;
    }

    public int encontrarNoMarcado() {
        for (int i = 0; i < marcados.size(); i++) {
            if(marcados.get(i) == false) {
                return i;
            }
        }
        return -1;
    }
}
