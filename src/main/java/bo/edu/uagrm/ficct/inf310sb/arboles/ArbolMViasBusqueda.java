package bo.edu.uagrm.ficct.inf310sb;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ArbolMViasBusqueda <K extends Comparable<K>, V> implements IArbolBusqueda<K, V>{

    protected NodoMVias<K, V> raiz;
    protected int orden;
    protected int POSICION_INVALIDA = -1;
    public ArbolMViasBusqueda() {
        this.orden = 3;
    }

    public ArbolMViasBusqueda(int orden) {
        if(orden < 3) {
            throw new RuntimeException("Orden invalido");
        }
        this.orden = orden;
    }

    @Override
    public void insertar (K claveAInsertar, V valorAInsertar) {
        if(valorAInsertar == null){
            throw new RuntimeException("No se permite insertar valores nulos");
        }
        if(claveAInsertar == null){
            throw new RuntimeException("No se permite insertar claves nulas");
        }
        if(this.esArbolVacio()){
            this.raiz = new NodoMVias<>(this.orden, claveAInsertar, valorAInsertar);
            return;
        }
        NodoMVias<K,V> nodoActual = this.raiz;
        while(!NodoMVias.esNodoVacio(nodoActual)) {
            int posicionDeClave = this.obtenerPosicionDeClave(nodoActual, claveAInsertar);
            if(posicionDeClave != POSICION_INVALIDA){
                nodoActual.setValor(posicionDeClave, valorAInsertar);
                nodoActual = NodoMVias.nodoVacio();
            }else{
                if(nodoActual.esHoja()){
                    if(nodoActual.estanClavesLlenas()){
                        int posicionPorDondeBajar = this.obtenerPosicionPorDondeBajar(nodoActual, claveAInsertar);
                        NodoMVias<K,V> nuevoHijo = new NodoMVias<>(this.orden,claveAInsertar,valorAInsertar);
                        nodoActual.setHijo(posicionPorDondeBajar, nuevoHijo);
                    }else{
                        this.insertarClaveYValorOrdenadaEnNodo(nodoActual,claveAInsertar,valorAInsertar);
                    }
                    nodoActual= NodoMVias.nodoVacio();
                }else{
                    int posicionPorDondeBajar = this.obtenerPosicionPorDondeBajar(nodoActual, claveAInsertar);
                    if(nodoActual.esHijoVacio(posicionPorDondeBajar)){
                        NodoMVias<K,V> nuevoHijo = new NodoMVias<>(this.orden,claveAInsertar,valorAInsertar);
                        nodoActual.setHijo(posicionPorDondeBajar, nuevoHijo);
                    }else{
                        nodoActual = nodoActual.getHijo(posicionPorDondeBajar);
                    }

                }
            }
        }
    }

    public String arbolGrafico() {
        return arbolGrafico(raiz, 0);
    }

    protected String arbolGrafico(NodoMVias<K, V> nodoActual, int cont) {
        String ret = "";
        if (!NodoMVias.esNodoVacio(nodoActual)) {
            int cant = nodoActual.cantidadDeClavesNoVacias();
            for (int i = cant; i >= 0; i--) {
                ret += arbolGrafico(nodoActual.getHijo(i), cont + 1);
                if (i > 0) {
                    for (int j = 0; j < cont; j++) {
                        ret += "\t\t";
                    }
                    ret += "[" + String.valueOf(i) + "]|" + nodoActual.getClave(i - 1) + "\n";
                }
            }
        }
        return ret;
    }

    private void insertarClaveYValorOrdenadaEnNodo(NodoMVias<K,V> nodoActual, K claveAInsertar, V valorAInsertar) {
        if (!NodoMVias.esNodoVacio(nodoActual)) {
            int cant = nodoActual.cantidadDeClavesNoVacias();
            for (int i = 0; i < cant; i++) {
                K claveActual = nodoActual.getClave(i);
                if (claveAInsertar.compareTo(claveActual) < 0) {
                    for (int k = cant; k > i; k--) {
                        K clave = nodoActual.getClave(k - 1);
                        V valor = nodoActual.getValor(k - 1);
                        nodoActual.setClave(k, clave);
                        nodoActual.setValor(k, valor);
                    }
                    nodoActual.setClave(i, claveAInsertar);
                    nodoActual.setValor(i, valorAInsertar);
                    return;
                }
            }
            nodoActual.setClave(cant, claveAInsertar);
            nodoActual.setValor(cant, valorAInsertar);
        }
    }
    private int obtenerPosicionDeClave(NodoMVias<K,V> nodoActual, K claveAInsertar) {
        int posicionDeLaClave = POSICION_INVALIDA;
        for(int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            K claveActual = nodoActual.getClave(i);
            if(claveActual == claveAInsertar) {
                posicionDeLaClave = i;
            }
        }
        return posicionDeLaClave;
    }
    private int obtenerPosicionPorDondeBajar(NodoMVias<K,V> nodoActual, K claveAInsertar){
        for(int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            K claveActual = nodoActual.getClave(i);
            if(claveAInsertar.compareTo(claveActual) < 0) {
                return i;
            }
        }
        return nodoActual.cantidadDeClavesNoVacias();
    }

    @Override
    public V eliminar (K claveAEliminar) {
        V valorAEliminar = this.buscar(claveAEliminar);
        if(valorAEliminar == null ){
            throw new RuntimeException("El valor no existe");
        }
        this.raiz = eliminar(this.raiz, claveAEliminar);
        return valorAEliminar;
    }

    private NodoMVias<K,V> eliminar(NodoMVias<K,V> nodoActual, K claveAEliminar){
        //orden 10 - cantidad=9 - i solo llega a 8
        for(int i=0; i< nodoActual.cantidadDeClavesNoVacias(); i++){
            K claveActual = nodoActual.getClave(i);
            if(claveAEliminar.compareTo(claveActual)==0){
                if(nodoActual.esHoja()){
                    this.eliminarClaveYValorDelNodo(nodoActual,i);
                    if(nodoActual.cantidadDeClavesNoVacias()==0){
                        return NodoMVias.nodoVacio();
                    }
                    return nodoActual;
                }
                //No es hoja el nodo Actual
                K claveDeReemplazo;
                if(hayHijosMasAdelante(nodoActual, i)){
                    claveDeReemplazo = buscarClaveSucesoraInOrden(nodoActual, claveAEliminar);
                } else{
                    claveDeReemplazo = buscarClavePredecesoraInOrden(nodoActual, claveAEliminar);
                }
                V valorDeReemplazo = buscar(claveDeReemplazo);
                nodoActual = eliminar(nodoActual, claveDeReemplazo);
                nodoActual.setClave(i, claveDeReemplazo);
                nodoActual.setValor(i, valorDeReemplazo);
                return nodoActual;
            }
            //no esta en la posicion i del nodoActual
            if(claveAEliminar.compareTo(claveActual)<0){
                NodoMVias<K,V> supuestoNuevoHijo = this.eliminar(nodoActual.getHijo(i), claveAEliminar);
                nodoActual.setHijo(i, supuestoNuevoHijo);
                return nodoActual;
            }
        }
        //si llego aqui sin retornar quiere decir que nunca baje por ningun lado ni lo encontre
        NodoMVias<K,V> supuestoNuevoHijo = this.eliminar(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()), claveAEliminar);
        nodoActual.setHijo(nodoActual.cantidadDeClavesNoVacias(), supuestoNuevoHijo);
        return nodoActual;
    }

    private K buscarClavePredecesoraInOrden (NodoMVias<K,V> nodoActual, K claveAEliminar) {
        if (this.contiene(claveAEliminar)) {
            List<K> datos = new LinkedList<>();
            this.recorrridoEnInOrden(nodoActual, datos);
            int ind = indiceDe(datos, claveAEliminar);
            return datos.get(ind - 1);
        }
        return (K) NodoMVias.datoVacio();
    }

    private K buscarClaveSucesoraInOrden (NodoMVias<K,V> nodoActual, K claveAEliminar) {
        if (this.contiene(claveAEliminar)) {
            List<K> datos = new LinkedList<>();
            recorrridoEnInOrden(nodoActual, datos);
            int ind = indiceDe(datos, claveAEliminar);
            return datos.get(ind + 1);
        }
        return (K) NodoMVias.datoVacio();
    }

    private int indiceDe(List<K> datos, K clave) {
        for (int i = 0; i < datos.size(); i++) {
            K claveActual = datos.get(i);
            if (clave.compareTo(claveActual) == 0) {
                return i;
            }
        }
        return -1;
    }

    private boolean hayHijosMasAdelante (NodoMVias<K,V> nodoActual, int i) {
        for (int j = i + 1; j < orden; j++) {
            if (!NodoMVias.esNodoVacio(nodoActual.getHijo(j))) {
                return true;
            }
        }
        return false;
    }

    private void eliminarClaveYValorDelNodo(NodoMVias<K,V> nodoActual, int i){
        int cant = nodoActual.cantidadDeClavesNoVacias();
        for (int k = i; k < cant - 1; k++) {
            K claveNuevo = nodoActual.getClave(k + 1);
            V valorNuevo = nodoActual.getValor(k + 1);
            nodoActual.setClave(k, claveNuevo);
            nodoActual.setValor(k, valorNuevo);
        }
        nodoActual.setClave(cant - 1, (K) NodoMVias.datoVacio());
        nodoActual.setValor(cant - 1, (V) NodoMVias.datoVacio());
    }

    @Override
    public V buscar (K clave) {
        NodoMVias<K, V> nodoActual = this.raiz;
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            NodoMVias<K, V> nodoAnterior = nodoActual;
            for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias()
                    && nodoAnterior == nodoActual; i++) {
                K claveActual = nodoActual.getClave(i);
                V valorActual = nodoActual.getValor(i);
                if (clave.compareTo(claveActual) == 0) {
                    return valorActual;
                }
                if (clave.compareTo(claveActual) < 0) {
                    if (nodoActual.esHijoVacio(i)) {
                        return (V) NodoMVias.datoVacio();
                    }
                    //el hijo no es vacio
                    nodoActual = nodoActual.getHijo(i);
                }
            }//fin for
            if (nodoAnterior == nodoActual) {
                nodoActual = nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias());
            }
        }
        return (V) NodoMVias.datoVacio();
    }

    public V buscarRec (K clave) {
        return this.buscarRec(this.raiz, clave);
    }

    public V buscarRec (NodoMVias<K, V> nodoActual, K clave) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return (V)NodoMVias.datoVacio();
        }
        int cant = nodoActual.cantidadDeClavesNoVacias();
        for (int i = 0; i < cant; i++) {
            K claveActual = nodoActual.getClave(i);
            V valorActual = nodoActual.getValor(i);
            if(clave.compareTo(claveActual) == 0){
                return valorActual;
            }else if(clave.compareTo(claveActual) < 0) {
                return buscarRec(nodoActual.getHijo(i), clave);
            }
        }
        return buscarRec(nodoActual.getHijo(cant), clave);
    }

    @Override
    public boolean contiene (K clave) {
        return this.buscar(clave) != NodoMVias.nodoVacio();
    }

    @Override
    public int size () {
        return 0;
    }

    @Override
    public int altura () {
        return altura(this.raiz);
    }

    public int altura(NodoMVias<K,V> nodoActual){
        if(NodoMVias.esNodoVacio(nodoActual)){
            return 0;
        }
        int alturaMayor = 0;
        for(int i=0;i<this.orden;i++){
            int alturaActual = altura(nodoActual.getHijo(i));
            if(alturaActual>alturaMayor){
                alturaMayor= alturaActual;
            }
        }
        return alturaMayor+1;
    }

    @Override
    public int nivel () {
        return this.altura() - 1;
    }

    @Override
    public void vaciar () {
        this.raiz = NodoMVias.nodoVacio();
    }

    @Override
    public boolean esArbolVacio () {
        return NodoMVias.esNodoVacio(this.raiz);
    }

    @Override
    public List<K> recorridoPorNiveles () {
        List<K> recorrido = new LinkedList<>();
        if(this.esArbolVacio()){
            return recorrido;
        }

        Queue<NodoMVias<K,V>> colaDeNodos =new LinkedList<>();
        colaDeNodos.offer(this.raiz);

        while (!colaDeNodos.isEmpty()) {
            NodoMVias<K, V> nodoActual = colaDeNodos.poll();
            for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
                recorrido.add(nodoActual.getClave(i));
                if (!nodoActual.esHijoVacio(i)) {
                    colaDeNodos.offer(nodoActual.getHijo(i));
                }
            }
            if(!nodoActual.esHijoVacio(nodoActual.cantidadDeClavesNoVacias())){
                colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
            }
        }
        return recorrido;
    }

    @Override
    public List<K> recorridoEnPreOrden () {
        List<K> recorrido = new LinkedList<>();
        recorridoEnPreOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPreOrden(NodoMVias<K,V> nodoActual, List<K> recorrido){
        if(NodoMVias.esNodoVacio(nodoActual)){
            return;
        }
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            recorrido.add(nodoActual.getClave(i));
            recorridoEnPreOrden(nodoActual.getHijo(i), recorrido);
        }
        recorridoEnPreOrden(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()), recorrido);
    }

    @Override
    public List<K> recorridoEnInOrden () {
        List<K> recorrido = new LinkedList<>();
        recorrridoEnInOrden(this.raiz, recorrido);
        return recorrido;
    }


    private void recorrridoEnInOrden(NodoMVias<K, V> nodoActual, List<K> recorrido) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return;
        }
        int cant = nodoActual.cantidadDeClavesNoVacias();
        for (int i = 0; i < cant; i++) {
            K claveActual = nodoActual.getClave(i);
            this.recorrridoEnInOrden(nodoActual.getHijo(i), recorrido);
            recorrido.add(claveActual);
        }
        this.recorrridoEnInOrden(nodoActual.getHijo(cant), recorrido);
    }

    @Override
    public List<K> recorridoEnPostOrden () {
        List<K> recorrido = new LinkedList<>();
        recorridoEnPostOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPostOrden(NodoMVias<K,V> nodoActual, List<K> recorrido){
        if(NodoMVias.esNodoVacio(nodoActual)){
            return;
        }
        recorridoEnPostOrden(nodoActual.getHijo(0), recorrido);
        for (int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
            recorridoEnPostOrden(nodoActual.getHijo(i+1), recorrido);
            recorrido.add(nodoActual.getClave(i));
        }
    }

    //---------------------------------------PRACTICO DE ÁRBOLES M-VIAS-----------------------------------------------//
    //17. Implemente un método que retorne la mayor llave en un árbol m vias de búsqueda.
    public K ejercicio17() {
        if(this.esArbolVacio()) {
            return null;
        }
        Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        K claveABuscar = this.raiz.getClave(0);
        while(!colaDeNodos.isEmpty()) {
            NodoMVias<K, V> nodoActual = colaDeNodos.poll();
            for(int i = 0; i < nodoActual.cantidadDeClavesNoVacias(); i++) {
                if(claveABuscar.compareTo(nodoActual.getClave(i)) < 0) {
                    claveABuscar = nodoActual.getClave(i);
                }
                if(!nodoActual.esHijoVacio(i)) {
                    colaDeNodos.offer(nodoActual.getHijo(i));
                }
            }
            if(!nodoActual.esHijoVacio(nodoActual.cantidadDeClavesNoVacias())) {
                colaDeNodos.offer(nodoActual.getHijo(nodoActual.cantidadDeClavesNoVacias()));
            }
        }
        return claveABuscar;
    }

    //18. Implemente un método que retorne verdadero si solo hay hojas en el último
    // nivel de un árbol m-vias de búsqueda. Falso en caso contrario.
    public boolean ejercicio18() {
        if(raiz == null)
            return false;
        int nivelRaiz = nivel();
        return ejercicio18(raiz, nivelRaiz, 0);
    }

    private boolean ejercicio18(NodoMVias<K, V> nodoActual, int ultimo, int n) {
        if (NodoMVias.esNodoVacio(nodoActual))
            return true;

        if (nodoActual.esHoja())
            return true;

        if (ultimo == n) {
            if (nodoActual.esHoja() == false)
                return false;
            return true;
        }

        for (int i = 0; i < orden; i++) {
            if(ejercicio18(nodoActual.getHijo(i), ultimo, n+1) == false)
                return false;
        }
        return true;
    }

    //19. Implemente un método que retorne verdadero si un árbol m vias tiene solo hojas
    // o nodos con todos sus hijos distinto de vacío. Falso en caso contrario
    public boolean ejercicio19() {
        if(NodoMVias.esNodoVacio(this.raiz))
            return false;
        return ejercicio19(this.raiz);
    }

    private boolean ejercicio19(NodoMVias<K, V> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual))
            return true;

        if (nodoActual.esHoja())
            return true;

        if(nodoActual.cantidadDeHijosNoVacios() < orden)
            return false;

        boolean esVerdadero = true;
        for(int i = 0; i < orden; i++) {
            if(ejercicio19(nodoActual.getHijo(i)) == false)
                return false;
        }
        return true;
    }

    //20. Para un árbol m vías implementar un método que reciba otro árbol de parámetro y que
    // retorne verdadero si los arboles son similares. Falso en caso contrario.
    public boolean esSimilar(ArbolMViasBusqueda<K, V> arbol) {
        return esSimilar1(raiz, arbol.raiz);
    }

    public boolean esSimilar1(NodoMVias<K, V> p, NodoMVias<K, V> p2) {
        if(NodoMVias.esNodoVacio(p) && NodoMVias.esNodoVacio(p2))
            return true;
        if(NodoMVias.esNodoVacio(p) || NodoMVias.esNodoVacio(p2))
            return false;

        if(p.esHoja() && p2.esHoja()) {
            if(p.cantidadDeClavesNoVacias() == p2.cantidadDeClavesNoVacias())
                return true;
            return false;
        }
        if(p.esHoja() || p2.esHoja())
            return false;

        if(p.cantidadDeHijosNoVacios() != p2.cantidadDeHijosNoVacios()) {
            return false;
        }

        for(int i = 0; i < orden-1; i++) {
            if (p.getClave(i).compareTo(p2.getClave(i)) != 0)
                return false;
        }

        for(int i = 0; i < orden; i++) {
            if (esSimilar1(p.getHijo(i), p2.getHijo(i)) == false)
                return false;
        }
        return true;
    }
}
