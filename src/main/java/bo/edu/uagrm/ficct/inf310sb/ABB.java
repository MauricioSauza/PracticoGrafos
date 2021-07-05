package bo.edu.uagrm.ficct.inf310sb;

import java.util.*;

public class ABB<K extends Comparable<K>, V> implements IArbolBusqueda<K, V> {

    protected NodoBinario<K, V> raiz;

    public ABB() {
    }


    @Override
    public void insertar (K clave, V valor) {
        if(valor == null) {
            throw new RuntimeException("No se permite insertar valores nulos");
        }

        if(this.esArbolVacio()) {
            this.raiz = new NodoBinario<>(clave, valor);
            return;
        }

        NodoBinario<K, V> nodoActual = this.raiz;
        NodoBinario<K, V> nodoAnterior = (NodoBinario<K, V>) NodoBinario.nodoVacio();

        while(!NodoBinario.esNodoVacio(nodoActual)) {
            K claveActual = nodoActual.getClave();
            nodoAnterior = nodoActual;
            if(clave.compareTo(claveActual) < 0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if(clave.compareTo(claveActual) > 0) {
                nodoActual = nodoActual.getHijoDerecho();
            } else {
                nodoActual.setValor(valor);
                return;
            }
        }

        /*
        Si llego a este punto quiere decir, que encontre donde insertar
        la clave y el valor
         */
        NodoBinario<K, V> nuevoNodo = new NodoBinario<>(clave, valor);
        K claveAnterior = nodoAnterior.getClave();
        if(clave.compareTo(claveAnterior) < 0) {
            nodoAnterior.setHijoIzquierdo(nuevoNodo);
        } else {
            nodoAnterior.setHijoDerecho(nuevoNodo);
        }
    }

    //----------------------------------------------RECURSIVO----------------------------------------------------//
    public void insertarRec(K clave, V valor) {
        if(clave == null) {
            throw new RuntimeException("La clave no puede ser nula");
        }
        if(this.esArbolVacio()) {
            this.raiz = new NodoBinario<>(clave, valor);
        }

        NodoBinario<K, V> nodoAnterior = NodoBinario.nodoVacio();
        insertarRec(this.raiz, nodoAnterior, clave, valor);
    }

    public void insertarRec(NodoBinario<K, V> nodoPiv, NodoBinario<K, V> nodoAnt, K claveIns, V valorIns) {
        if(NodoBinario.esNodoVacio(nodoPiv)) {
            K claveAnt = nodoAnt.getClave();
            NodoBinario<K, V> nuevoNodo = new NodoBinario<>(claveIns, valorIns);
            if(claveIns.compareTo(claveAnt) < 0) {
                nodoAnt.setHijoIzquierdo(nuevoNodo);
                return;
            } else if(claveIns.compareTo(claveAnt) > 0) {
                nodoAnt.setHijoDerecho(nuevoNodo);
                return;
            }
        }
        K claveActual = nodoPiv.getClave();
        NodoBinario<K, V> nodoAnterior = nodoPiv;
        if(claveIns.compareTo(claveActual) < 0) {
            nodoAnt = nodoPiv;
            insertarRec(nodoPiv.getHijoIzquierdo(), nodoAnt, claveIns, valorIns);
        } else if(claveIns.compareTo(claveActual) > 0) {
            nodoAnt = nodoPiv;
            insertarRec(nodoPiv.getHijoDerecho(), nodoAnt, claveIns, valorIns);
        } else {
            nodoPiv.setValor(valorIns);
            return;
        }
    }

    //----------------------------------------------------------------------------------------------------------//


    @Override
    public V eliminar (K clave) {
        V valorAEliminar = this.buscar(clave);
        if(valorAEliminar == null) {
            throw new IllegalArgumentException("La clave no existe");
        }
        this.raiz = eliminar(this.raiz,clave);
        return valorAEliminar;
    }

    private NodoBinario<K, V> eliminar(NodoBinario<K, V> nodoActual, K clave) {
        K claveActual = nodoActual.getClave();
        if(clave.compareTo(claveActual) < 0) {
            NodoBinario<K, V> supuestoNuevoHijoIzq = eliminar(nodoActual.getHijoIzquierdo(), clave);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzq);
            return nodoActual;
        }
        if(clave.compareTo(claveActual) > 0) {
            NodoBinario<K, V> supuestoNuevoHijoDer = eliminar(nodoActual.getHijoDerecho(), clave);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDer);
            return nodoActual;
        }
        /*
        Si llego aqui ya encontre el nodo con la clave a eliminar
        revisamos que caso es
         */
        //Caso 1
        if(nodoActual.esHoja()) {
            return NodoBinario.nodoVacio();
        }

        //Caso 2.1
        if(!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoIzquierdo();
        }
        //Caso 2.2
        if(nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoDerecho();
        }
        //Caso 3
        NodoBinario<K, V> nodoDelSucesor = buscarSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K, V> supuestoNuevoHijo = eliminar(nodoActual.getHijoIzquierdo(), nodoDelSucesor.getClave());
        nodoActual.setHijoDerecho(supuestoNuevoHijo);
        nodoActual.setClave(nodoDelSucesor.getClave());
        nodoActual.setValor(nodoDelSucesor.getValor());
        return nodoActual;
    }

    private NodoBinario<K, V> buscarSucesor(NodoBinario<K, V> nodoActual) {
        NodoBinario<K ,V> nodoAnterior = NodoBinario.nodoVacio();
        while(!NodoBinario.esNodoVacio(nodoActual)) {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return nodoAnterior;
    }

    @Override
    public V buscar (K clave) {

        NodoBinario<K, V> nodoActual = this.raiz;

        while(!NodoBinario.esNodoVacio(nodoActual)) {
            K claveActual = nodoActual.getClave();
            if(clave.compareTo(claveActual) < 0) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else if(clave.compareTo(claveActual) > 0) {
                nodoActual = nodoActual.getHijoDerecho();
            } else {
                return nodoActual.getValor();
            }
        }
        /*
        Si llego a este punto, quiere decir que la clave a buscar no
        está en el árbol, entonces debe retornar null
         */
        return null;
    }

    public V buscarRec (K clave) {
        return this.buscarRec(this.raiz, clave);
    }

    public V buscarRec (NodoBinario<K, V> nodoActual, K clave) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return (V)NodoMVias.datoVacio();
        }
        K claveActual = nodoActual.getClave();
        V valorActual = nodoActual.getValor();
        if(clave.compareTo(claveActual) == 0){
            return valorActual;
        }else if(clave.compareTo(claveActual) < 0) {
            return buscarRec(nodoActual.getHijoIzquierdo(), clave);
        }
        return buscarRec(nodoActual.getHijoDerecho(), clave);
    }

    @Override
    public boolean contiene (K clave) {
        return this.buscar(clave) != null;
    }
    //----------------------------------------------------------------------------------------------------------------//
    //--------------------------------------SIZE CON TODOS LOS RECORRIDOS---------------------------------------------//
    //Size recorrido por niveles
    @Override
    public int size () {
        if(this.esArbolVacio()) {
            return 0;
        }
        int cantidadDeNodos = 0;
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while(!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();
            cantidadDeNodos++;
            if(!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if(!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return cantidadDeNodos;
    }

    public int sizePreOrden() {
        int i = 0;
        if(this.esArbolVacio()) {
            return 0;
        }
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        pilaDeNodos.push(this.raiz);
        while(!pilaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
            i++;
            if(!nodoActual.esVacioHijoDerecho()) {
                pilaDeNodos.push(nodoActual.getHijoDerecho());
            }
            if(!nodoActual.esVacioHijoIzquierdo()) {
                pilaDeNodos.push(nodoActual.getHijoIzquierdo());
            }
        }
        return i;
    }

    public int sizeInOrden()  {
        int i = 0;
        if(this.esArbolVacio()) {
            return 0;
        }
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        NodoBinario<K, V> nodoActual = this.raiz;
        pilaDeNodos.push(this.raiz);
        while(!pilaDeNodos.isEmpty()) {
            while(!nodoActual.esVacioHijoIzquierdo()) {
                nodoActual = nodoActual.getHijoIzquierdo();
                pilaDeNodos.push(nodoActual);
            }
            nodoActual = pilaDeNodos.pop();
            i++;
            while(nodoActual.esVacioHijoDerecho() && !pilaDeNodos.isEmpty()) {
                nodoActual = pilaDeNodos.pop();
                i++;
            }
            if(!nodoActual.esVacioHijoDerecho()) {
                nodoActual = nodoActual.getHijoDerecho();
                pilaDeNodos.push(nodoActual);
            }
        }
        return i;
    }

    public int sizePostOrden() {
        int i = 0;
        if(this.esArbolVacio()) {
            return 0;
        }
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        NodoBinario<K, V> nodoActual = this.raiz;
        pilaParaPostOrdenSize(pilaDeNodos, nodoActual);
        while(!pilaDeNodos.isEmpty()) {
            nodoActual = pilaDeNodos.pop();
            i++;
            if(!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoDelTope = pilaDeNodos.peek();
                if(!nodoDelTope.esVacioHijoDerecho() && nodoDelTope.getHijoDerecho() != nodoActual) {
                    pilaParaPostOrdenSize(pilaDeNodos, nodoDelTope.getHijoDerecho());
                }
            }
        }
        return i;
    }

    public void pilaParaPostOrdenSize(Stack<NodoBinario<K, V>> pilaDeNodos, NodoBinario<K, V> nodoActual) {
        while(!NodoBinario.esNodoVacio(nodoActual)) {
            pilaDeNodos.push(nodoActual);
            if(!nodoActual.esVacioHijoIzquierdo()) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else {
                nodoActual = nodoActual.getHijoDerecho();
            }
        }
    }

    //--------------------------------------------------RECURSIVO------------------------------------------------//

    public int sizeRec() {
        return sizeRec(this.raiz);
    }

    private int sizeRec(NodoBinario<K, V> nodoActual) {
        if(NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int cantidadPorIzquierda = sizeRec(nodoActual.getHijoIzquierdo());
        int cantidadPorDerecha = sizeRec(nodoActual.getHijoDerecho());
        return cantidadPorIzquierda + cantidadPorDerecha + 1;
    }
    //----------------------------------------------------------------------------------------------------------------//

    @Override
    public int altura () {
        return altura(this.raiz);
    }

    protected int altura(NodoBinario<K, V> nodoActual) {
        if(NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int alturaPorIzquierda = sizeRec(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = sizeRec(nodoActual.getHijoDerecho());
        return alturaPorIzquierda > alturaPorDerecha? alturaPorIzquierda + 1: alturaPorDerecha + 1;
    }

    public int alturaIt() {
        if(this.esArbolVacio()) {
            return 0;
        }
        int alturaDelArbol = 0;
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while(!colaDeNodos.isEmpty()) {
            int nroDeNodosDeNivel = colaDeNodos.size();
            int posicion = 0;
            while(posicion < nroDeNodosDeNivel) {
                NodoBinario<K ,V> nodoActual = colaDeNodos.poll();
                if(!nodoActual.esVacioHijoIzquierdo()) {
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if(!nodoActual.esVacioHijoDerecho()) {
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }
                posicion++;
            }
            alturaDelArbol++;
        }
        return alturaDelArbol++;
    }
    //----------------------------------------------------------------------------------------------------------------//
    @Override
    public int nivel () {
        return this.altura() - 1;
    }
    //----------------------------------------------------------------------------------------------------------------//
    @Override
    public void vaciar () {
        this.raiz = NodoBinario.nodoVacio();
    }
    //----------------------------------------------------------------------------------------------------------------//
    @Override
    public boolean esArbolVacio () {
        return NodoBinario.esNodoVacio(this.raiz);
    }
    //----------------------------------------------------------------------------------------------------------------//
    @Override
    public List<K> recorridoPorNiveles () {
        List<K> recorrido = new LinkedList<>();
        if(this.esArbolVacio()) {
            return recorrido;
        }
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while(!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();
            recorrido.add(nodoActual.getClave());
            if(!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if(!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return recorrido;
    }
    //----------------------------------------------------------------------------------------------------------------//
    @Override
    public List<K> recorridoEnPreOrden () {
        List<K> recorrido = new LinkedList<>();
        if(this.esArbolVacio()) {
            return recorrido;
        }
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        pilaDeNodos.push(this.raiz);
        while(!pilaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = pilaDeNodos.pop();
            recorrido.add(nodoActual.getClave());
            if(!nodoActual.esVacioHijoDerecho()) {
                pilaDeNodos.push(nodoActual.getHijoDerecho());
            }
            if(!nodoActual.esVacioHijoIzquierdo()) {
                pilaDeNodos.push(nodoActual.getHijoIzquierdo());
            }
        }
        return recorrido;
    }

    //----------------------------------------------------------------------------------------------------------------//
    @Override
    public List<K> recorridoEnInOrden () {
        List<K> recorrido = new ArrayList<>();
        if(this.esArbolVacio()) {
            return recorrido;
        }
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        NodoBinario<K, V> nodoActual = this.raiz;
        pilaDeNodos.push(this.raiz);
        //Bucle principal de control
        while (!pilaDeNodos.isEmpty()) {
            //Recorre todos los hijos izquierdos del nodo actual y los agrega a la pila
            while (!nodoActual.esVacioHijoIzquierdo()) {
                nodoActual = nodoActual.getHijoIzquierdo();
                pilaDeNodos.push(nodoActual);
            }
            //Actualiza el nodo actual con el ultimo elemento agregado a la pila
            nodoActual= pilaDeNodos.pop();
            //Añade la clave del nodo al recorrido
            recorrido.add(nodoActual.getClave());
            //
            while (nodoActual.esVacioHijoDerecho() && !pilaDeNodos.isEmpty()) {
                nodoActual = pilaDeNodos.pop();
                recorrido.add(nodoActual.getClave());
            }
            if(!nodoActual.esVacioHijoDerecho()) {
                nodoActual= nodoActual.getHijoDerecho();
                pilaDeNodos.push(nodoActual);
            }
        }
        return recorrido;
    }

    //----------------------------------------------------------------------------------------------------------------//
    @Override
    public List<K> recorridoEnPostOrden () {
        List<K> recorrido = new LinkedList<>();
        if (this.esArbolVacio()) {
            return recorrido;
        }

        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        NodoBinario<K, V> nodoActual = this.raiz;
        //El proceso inicial antes de iterar en la pila
        // Proceso en el cual se mete todos los hijos de un nodo a la pila
        pilaParaPostOrden(pilaDeNodos, nodoActual);
        //Empezamos a iterar sobre la pila
        while (!pilaDeNodos.isEmpty()) {
            //Proceso en el cual se actualiza el nodo actual y se meten las claves de los nodos al recorrido
            nodoActual = pilaDeNodos.pop();
            recorrido.add(nodoActual.getClave());
            if (!pilaDeNodos.isEmpty()) {
                NodoBinario<K, V> nodoDelTope = pilaDeNodos.peek();
                if (!nodoDelTope.esVacioHijoDerecho() && nodoDelTope.getHijoDerecho() != nodoActual) {
                    /*Volver a hacer el mismo bucle inicial*/
                    pilaParaPostOrden(pilaDeNodos, nodoDelTope.getHijoDerecho());
                }
            }
        }
        return recorrido;
    }

    private void pilaParaPostOrden(Stack<NodoBinario<K, V>> pilaDeNodos, NodoBinario<K, V> nodoActual) {
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            pilaDeNodos.push(nodoActual);
            if (!nodoActual.esVacioHijoIzquierdo()) {
                nodoActual = nodoActual.getHijoIzquierdo();
            } else {
                nodoActual = nodoActual.getHijoDerecho();
            }
        }
    }

    //---------------------------------------------RECORRIDOS RECURSIVOS----------------------------------------------//
    public List<K> recorridoEnPreOrdenRec() {
        List<K> recorrido = new LinkedList<>();
        recorridoEnPreOrdenRec(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPreOrdenRec(NodoBinario<K, V> nodoActual, List<K> recorrido) {
        if(NodoBinario.esNodoVacio(nodoActual)) {
            return;
        }
        recorrido.add(nodoActual.getClave());
        recorridoEnPreOrdenRec(nodoActual.getHijoIzquierdo(), recorrido);
        recorridoEnPreOrdenRec(nodoActual.getHijoDerecho(), recorrido);

    }
    //----------------------------------------------------------------------------------------------------------------//
    public List<K> recorridoEnInOrdenRec() {
        List<K> recorrido = new ArrayList<>();
        //Para una implementación recursiva se necesita
        //un metodo amigo que haga el grueso del trabajo
        recorridoEnInOrdenRec(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnInOrdenRec(NodoBinario<K, V> nodoActual, List<K> recorrido) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return;
        }
        recorridoEnInOrdenRec(nodoActual.getHijoIzquierdo(), recorrido);
        recorrido.add(nodoActual.getClave());
        recorridoEnInOrdenRec(nodoActual.getHijoDerecho(), recorrido);
    }
    //----------------------------------------------------------------------------------------------------------------//
    public List<K> recorridoEnPostOrdenRec() {
        List<K> recorrido = new ArrayList<>();
        //Para una implementación recursiva se necesita
        //un metodo amigo que haga el grueso del trabajo
        recorridoEnPostOrdenRec(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPostOrdenRec(NodoBinario<K, V> nodoActual, List<K> recorrido) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return;
        }
        recorridoEnPostOrdenRec(nodoActual.getHijoIzquierdo(), recorrido);
        recorridoEnPostOrdenRec(nodoActual.getHijoDerecho(), recorrido);
        recorrido.add(nodoActual.getClave());
    }
    //----------------------------------------------------------------------------------------------------------------//
    //----------------------------------------------------PRACTICO----------------------------------------------------//
    //3. Implemente un método iterativo que retorne la cantidad de
    // nodos que tienen ambos hijos distintos de vacío en un árbol binario
    public int ejercicio3() {
        int cant = 0;
        if(this.esArbolVacio()) {
            return 0;
        }
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while(!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();
            if(nodoActual.esNodoCompleto()) {
                cant++;
            }
            if(!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if(!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return cant;
    }

    //4. Implemente un método recursivo que retorne la cantidad de
    // nodos que tienen ambos hijos distintos de vacío en un árbol binario
    public int ejercicio4() {
        if(this.esArbolVacio()) {
            return 0;
        }
        return ejercicio4(this.raiz);
    }

    public int ejercicio4(NodoBinario<K, V> nodoActual) {
        if(NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        int cantIzq = ejercicio4(nodoActual.getHijoIzquierdo());
        int cantDer = ejercicio4(nodoActual.getHijoDerecho());
        int cantTotal = cantIzq + cantDer;
        if(!nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()) {
            cantTotal++;
        }
        return cantTotal;
    }

    //5. Implemente un método iterativo que retorne la cantidad de nodos que tienen ambos
    // hijos distintos de vacío en un árbol binario, pero solo en el nivel N
    public int nivelDelNodoIt(NodoBinario<K, V> nodoABuscar) {
        if (this.esArbolVacio()) {
            return 0;
        }
        int size = 0;
        NodoBinario<K, V> nodoActual = this.raiz;
        while(!NodoBinario.esNodoVacio(nodoActual) && nodoABuscar.getClave() != nodoActual.getClave()) {
            if(nodoActual.getClave().compareTo(nodoABuscar.getClave()) > 0) {
                nodoActual = nodoActual.getHijoIzquierdo();
                size++;
            } else if(nodoActual.getClave().compareTo(nodoABuscar.getClave()) < 0) {
                nodoActual = nodoActual.getHijoDerecho();
                size++;
            }
        }
        return size;
    }

    public int ejercicio5(int nivel) {
        int nC = 0;
        if(this.esArbolVacio()) {
            return 0;
        }
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while(!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();
            int nodoNivel = nivelDelNodoIt(nodoActual);
            if(nodoNivel == nivel) {
                if(nodoActual.esNodoCompleto()) {
                    nC++;
                }
            }
            if(!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if(!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return nC;
    }

    //6. Implemente un método iterativo que retorne la cantidad de nodos que tienen ambos
    // hijos distintos de vacío en un árbol binario, pero solo en el nivel N
    public int ejercicio6(int nivel) {
        int nC = 0;
        if(this.esArbolVacio()) {
            return 0;
        }
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while(!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();
            int nodoNivel = nivelDelNodoIt(nodoActual);
            if(nodoNivel == nivel) {
                if(nodoActual.esNodoCompleto()) {
                    nC++;
                }
            }
            if(!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if(!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return nC;
    }

    //7. Implemente un método iterativo que retorne la cantidad nodos que tienen un solo
    // hijo diferente de vacío en un árbol binario, pero solo antes del nivel N
    public int ejercicio7(int nivel) {
        int cant = 0;
        if(this.esArbolVacio()) {
            return 0;
        }
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while(!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();
            int cantNodo = nivelDelNodoIt(nodoActual);
            if(cantNodo < nivel) {
                if(!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho() ||
                        nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()) {
                    cant++;
                }
            }
            if(!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if(!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return cant;
    }

    //12. Implemente un método iterativo con la lógica de un recorrido en
    // inOrden que retorne el número de nodos que tiene un árbol binario.
    public int ejercicio12() {
        int i = 0;
        if(this.esArbolVacio()) {
            return 0;
        }
        Stack<NodoBinario<K, V>> pilaDeNodos = new Stack<>();
        NodoBinario<K, V> nodoActual = this.raiz;
        pilaDeNodos.push(this.raiz);
        while(!pilaDeNodos.isEmpty()) {
            while(!nodoActual.esVacioHijoIzquierdo()) {
                nodoActual = nodoActual.getHijoIzquierdo();
                pilaDeNodos.push(nodoActual);
            }
            nodoActual = pilaDeNodos.pop();
            i++;
            while(nodoActual.esVacioHijoDerecho() && !pilaDeNodos.isEmpty()) {
                nodoActual = pilaDeNodos.pop();
                i++;
            }
            if(!nodoActual.esVacioHijoDerecho()) {
                nodoActual = nodoActual.getHijoDerecho();
                pilaDeNodos.push(nodoActual);
            }
        }
        return i;
    }
    //13. Implemente un método que reciba en listas de parámetros las llaves y valores de los recorridos en
    // postorden e inorden respectivamente y que reconstruya el árbol binario original.
    // Su método no debe usar el método insertar.
    public ABB(List<K> clavesInOrden,List<V> valoresInOrden,
                                List<K> clavesNoInOrden, List<V> valoresNoInOrden, boolean esConPreOrden){
        if(clavesInOrden.size()!= clavesNoInOrden.size() ||
                valoresInOrden.size()!= valoresNoInOrden.size() ||
                clavesInOrden.size() != valoresNoInOrden.size()){
            throw new RuntimeException("No se permiten tamaños distintos");
        }
        if(esConPreOrden){
            this.raiz = reconstruccionConPreOrden( clavesInOrden,valoresInOrden, clavesNoInOrden, valoresNoInOrden);
        }
        else{
            this.raiz= reconstruccionConPostOrden(clavesInOrden,valoresInOrden, clavesNoInOrden, valoresNoInOrden);
        }
    }


    private NodoBinario<K,V>reconstruccionConPostOrden(List<K> clavesInOrden,List<V> valoresInOrden,
                                                       List<K> clavesPostOrden, List<V> valoresPostOrden){
        if(clavesInOrden.isEmpty()){
            return null;
        }
        int posicionDeClavePadreEnPostOrden =clavesPostOrden.size()-1;
        K clavePadre = clavesPostOrden.get(posicionDeClavePadreEnPostOrden);
        V valorPadre = valoresPostOrden.get(posicionDeClavePadreEnPostOrden);
        int posicionDeClavePadreEnInOrden = this.posicionDeClave(clavePadre, clavesInOrden);
        //Armar rama por Izquierda
        List<K> clavesInOrdenPorIzquierda = clavesInOrden.subList(0, posicionDeClavePadreEnInOrden);
        List<V> valoresInOrdenPorIzquierda = valoresInOrden.subList(0, posicionDeClavePadreEnInOrden);
        List<K> clavesPostOrdenPorIzquierda = clavesPostOrden.subList(0, posicionDeClavePadreEnInOrden);
        List<V> valoresPostOrdenPorIzquierda = valoresPostOrden.subList(0, posicionDeClavePadreEnInOrden);
        NodoBinario<K,V> hijoIzquierdo = reconstruccionConPostOrden(clavesInOrdenPorIzquierda,valoresInOrdenPorIzquierda,
                clavesPostOrdenPorIzquierda,valoresPostOrdenPorIzquierda);
        //Armar rama derecha
        List<K> clavesInOrdenPorDerecha = clavesInOrden.subList(posicionDeClavePadreEnInOrden+1, clavesInOrden.size());
        List<V> valoresInOrdenPorDerecha = valoresInOrden.subList(posicionDeClavePadreEnInOrden+1, clavesInOrden.size());
        List<K> clavesPostOrdenPorDerecha = clavesPostOrden.subList(posicionDeClavePadreEnInOrden,clavesPostOrden.size()-1);
        List<V> valoresPostOrdenPorDerecha = valoresPostOrden.subList(posicionDeClavePadreEnInOrden,clavesPostOrden.size()-1);
        NodoBinario<K,V> hijoDerecho = reconstruccionConPostOrden(clavesInOrdenPorDerecha,valoresInOrdenPorDerecha,
                clavesPostOrdenPorDerecha,valoresPostOrdenPorDerecha);
        // Aramar nodo Actual
        NodoBinario<K,V> nodoActual = new NodoBinario<>(clavePadre,valorPadre);
        nodoActual.setHijoDerecho(hijoDerecho);
        nodoActual.setHijoIzquierdo(hijoIzquierdo);
        return nodoActual;
    }
    private NodoBinario<K,V>reconstruccionConPreOrden(List<K> clavesInOrden,List<V> valoresInOrden,
                                                      List<K> clavesPreOrden, List<V> valoresPreOrden){
        if(clavesInOrden.isEmpty()){
            return null;
        }
        int posicionDeClavePadrePreOrden = 0;
        K clavePadre = clavesPreOrden.get(posicionDeClavePadrePreOrden);
        V valorPadre = valoresPreOrden.get(posicionDeClavePadrePreOrden);
        int posicionDeClavePadreInOrden = posicionDeClave(clavePadre, clavesInOrden);
        //Armando Rama Izquierda
        List<K> clavesInOrdenPorIzquierda= clavesInOrden.subList(0, posicionDeClavePadreInOrden);
        List<V> valoresInOrdenPorIzquierda= valoresInOrden.subList(0, posicionDeClavePadreInOrden);
        List<K> clavesPreOrdenPorIzquierda= clavesInOrden.subList(1, posicionDeClavePadreInOrden+1);
        List<V> valorespreOrdenPorIzquierda= valoresInOrden.subList(1, posicionDeClavePadreInOrden+1);
        NodoBinario<K,V> hijoIzquierdo = reconstruccionConPreOrden(clavesInOrdenPorIzquierda,valoresInOrdenPorIzquierda,clavesPreOrdenPorIzquierda,valorespreOrdenPorIzquierda);
        //Armando Rama Derecha
        List<K> clavesInOrdenPorDerecha= clavesInOrden.subList(posicionDeClavePadreInOrden+1, clavesInOrden.size());
        List<V> valoresInOrdenPorDerecha= valoresInOrden.subList(posicionDeClavePadreInOrden+1, clavesInOrden.size());
        List<K> clavesPreOrdenPorDerecha= clavesInOrden.subList(posicionDeClavePadreInOrden+1, clavesInOrden.size());
        List<V> valorespreOrdenPorDerecha= valoresInOrden.subList(posicionDeClavePadreInOrden+1, clavesInOrden.size());
        NodoBinario<K,V> hijoDerecho = reconstruccionConPreOrden(clavesInOrdenPorDerecha,valoresInOrdenPorDerecha,clavesPreOrdenPorDerecha,valorespreOrdenPorDerecha);
        //Armamos el arbol
        NodoBinario<K,V> nodoActual = new NodoBinario<>(clavePadre, valorPadre);
        nodoActual.setHijoIzquierdo(hijoIzquierdo);
        nodoActual.setHijoDerecho(hijoDerecho);
        return nodoActual;
    }
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //14. Implemente un método privado que reciba un nodo binario de un árbol de un
    //árbol binario y que retorne cual sería su sucesor inorden de la clave de dicho nodo
    public NodoBinario<K, V> ejercicio14(NodoBinario<K, V> nodoRecibido) {
        List<K> recorridoInOrden = new LinkedList<>();
        recorridoInOrden = this.recorridoEnInOrden();
        int posicionRecibido = this.posicionDeClave(nodoRecibido.getClave(), recorridoInOrden);
        if((posicionRecibido == recorridoInOrden.size() - 1)) {
            //throw new RuntimeException("Nodo no tiene sucesor");
            return null;
        }
        int posicionDelSucesor = this.posicionDeClave(nodoRecibido.getClave(), recorridoInOrden) + 1;
        K claveDelSucesor = recorridoInOrden.get(posicionDelSucesor);
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);


        while(!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();
            if(nodoActual.getClave() == claveDelSucesor) {
                return nodoActual;
            }
            if(!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if(!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return null;
    }

    //15. Implemente un método privado que reciba un nodo binario de un árbol de un
    //árbol binario y que retorne cual sería su predecesor inorden de la clave de dicho nodo
    public NodoBinario<K, V> ejercicio15(NodoBinario<K, V> nodoRecibido) {
        List<K> recorridoInOrden = new LinkedList<>();
        recorridoInOrden = this.recorridoEnInOrden();
        int posicionRecibido = this.posicionDeClave(nodoRecibido.getClave(), recorridoInOrden);
        if(posicionRecibido == 0) {
            //throw new RuntimeException("Nodo no tiene sucesor");
            return null;
        }
        int posicionDelPredecesor = this.posicionDeClave(nodoRecibido.getClave(), recorridoInOrden) - 1;
        K claveDelSucesor = recorridoInOrden.get(posicionDelPredecesor);
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while(!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();
            if(nodoActual.getClave() == claveDelSucesor) {
                return nodoActual;
            }
            if(!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if(!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return null;
    }

    //16. Implemente un método que retorne la menor llave en un árbol binario de búsqueda
    public K ejercicio16() {
        if (this.esArbolVacio()) {
            return null;
        }
        NodoBinario<K, V> nodoAnt = NodoBinario.nodoVacio();
        NodoBinario<K, V> nodoActual = this.raiz;
        while (!NodoBinario.esNodoVacio(nodoActual)) {
            nodoAnt = nodoActual;
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return nodoAnt.getClave();
    }

    //21. Implemente un método que retorne verdadero si un árbol binario esta lleno. Falso en caso contrario.
    public boolean ejercicio21() {
        if(this.esArbolVacio()) {
            return false;
        }
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while(!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();
            if(!nodoActual.esHoja()) {
                if(!nodoActual.esNodoCompleto()) {
                    return false;
                }
            }
            if(!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if(!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return true;
    }
    //-----------------------------------------------------------------------------------------------------------
    public int posicionDeClave(K claveAEncontrar, List<K> listaDeClaves) {
        for(int i=0; i< listaDeClaves.size(); i++){
            K claveActual=listaDeClaves.get(i);
            if(claveActual.compareTo(claveAEncontrar)==0){
                return i;
            }
        }
        return -1;
    }

    //ESTUDIACION
    //Implemente un método que retorne la cantidad nodos que
    // tienen solo hijo izquierdo no vacío en un árbol binario

    //ITERATIVO
    public int cantHijosIzq() {
        int i = 0;
        if(this.esArbolVacio()) {
            return 0;
        }
        Queue<NodoBinario<K, V>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(this.raiz);
        while(!colaDeNodos.isEmpty()) {
            NodoBinario<K, V> nodoActual = colaDeNodos.poll();
            if(!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()) {
                i++;
            }
            if(!nodoActual.esVacioHijoIzquierdo()) {
                colaDeNodos.offer(nodoActual.getHijoIzquierdo());
            }
            if(!nodoActual.esVacioHijoDerecho()) {
                colaDeNodos.offer(nodoActual.getHijoDerecho());
            }
        }
        return i;
    }

    public String arbolGrafico() {
        return arbolGrafico(raiz, 0, true);
    }

    protected String arbolGrafico(NodoBinario<K, V> nodoActual, int cont, boolean izquierdo) {
        String ret = "";
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            ret += arbolGrafico(nodoActual.getHijoDerecho(), cont + 1, false);
            for (int i = 0; i < cont; i++) {
                ret += "\t";
            }
            String hijo = nodoActual == raiz ? "[R]" : izquierdo ? "[I]" : "[D]";
            String impre = nodoActual.getClave().toString();
            ret += impre + hijo + "\n";
            ret += arbolGrafico(nodoActual.getHijoIzquierdo(), cont + 1, true);
        }
        return ret;
    }

    //Implemente un método iterativo que retorne la cantidad nodos que
    // tienen solo hijo izquierdo no vacío en un árbol binario

    //Implemente un método recursivo que retorne la cantidad nodos que tienen solo hijo
    // izquierdo no vacío en un árbol binario, pero solo en el nivel N

    //Implemente un método iterativo que retorne la cantidad nodos que tienen solo hijo
    // izquierdo no vacío en un árbol binario, pero solo después del nivel N

    //Implemente un método iterativo que retorne la cantidad nodos que tienen solo hijo
    // izquierdo no vacío en un árbol binario, pero solo antes del nivel N

    //Implemente un método iterativo que retorne la cantidad de
    // nodos que tienen ambos hijos distintos de vacío en un árbol binario

    //Implemente un método recursivo que retorne la cantidad de
    // nodos que tienen ambos hijos distintos de vacío en un árbol binario

    //Implemente un método iterativo que retorne la cantidad de nodos que tienen ambos
    // hijos distintos de vacío en un árbol binario, pero solo en el nivel N

    //Implemente un método recursivo que retorne la cantidad de nodos que tienen ambos
    // hijos distintos de vacío en un árbol binario, pero solo en el nivel N

    //Implemente un método iterativo con la lógica de un recorrido en
    // inOrden que retorne el número de nodos que tiene un árbol binario.

    //Implemente un método recursivo que reciba como parámetro otro árbol binario de búsqueda que
    // retorne verdadero, si el árbol binario es similar al árbol binario
    // recibido como parámetro, falso en caso contrario.

    //Para un árbol binario implemente un método que retorne la cantidad de
    // nodos que tienen ambos hijos desde el nivel N.

    //Implementar un método que retorne un nuevo árbol binario de búsqueda invertido.

    //Implementar un método que retorne verdadero si un árbol binario esta lleno.

    //Implemente un método privado que reciba un nodo binario de un árbol de un
    //árbol binario y que retorne cual sería su sucesor inorden de la clave de dicho nodo

    //Implemente un método privado que reciba un nodo binario de un árbol de un
    //árbol binario y que retorne cual sería su predecesor inorden de la clave de dicho nodo

    //Implemente un método que retorne la menor llave en un árbol binario de búsqueda

    //Implemente un método que retorne la mayor llave en un árbol binario de búsqueda


}
