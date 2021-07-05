package bo.edu.uagrm.ficct.inf310sb;

import java.util.List;

public interface IArbolBusqueda <K extends Comparable<K>, V> {

    void insertar(K clave, V valor);
    V eliminar(K clave);
    V buscar(K clave);
    boolean contiene(K clave);
    int size();
    int altura();
    int nivel();
    void vaciar();
    boolean esArbolVacio();
    List<K> recorridoPorNiveles();
    List<K> recorridoEnPreOrden();
    List<K> recorridoEnInOrden();
    List<K> recorridoEnPostOrden();
}
