package bo.edu.uagrm.ficct.inf310sb;

public class AVL <K extends Comparable<K>, V> extends ABB<K, V> {
    private static final byte DIFERENCIA_PERMITIDA = 1;

    @Override
    public void insertar(K clave, V valor) {
        if(valor == null) {
            throw new RuntimeException("No se permite insertar valores nulos");
        }
        this.raiz = insertar(this.raiz, clave, valor);
    }

    private NodoBinario<K, V> insertar(NodoBinario<K, V> nodoActual, K clave, V valor) {
        if(NodoBinario.esNodoVacio(nodoActual)) {
            NodoBinario<K, V> nuevoNodo = new NodoBinario<>(clave, valor);
            return nuevoNodo;
        }
        K claveActual = nodoActual.getClave();
        if(clave.compareTo(claveActual) < 0) {
            NodoBinario<K, V> nuevoSupuestoHijo = insertar(nodoActual.getHijoIzquierdo(), clave, valor);
            nodoActual.setHijoIzquierdo(nuevoSupuestoHijo);
            return balancear(nodoActual);
        }
        if(clave.compareTo(claveActual) > 0) {
            NodoBinario<K, V> nuevoSupuestoHijo = insertar(nodoActual.getHijoDerecho(), clave, valor);
            nodoActual.setHijoDerecho(nuevoSupuestoHijo);
            return balancear(nodoActual);
        }

        /*
        Si llego por acá, quiere decir que encontre en el árbol la clave que quería
        insertar, entonces, actualizo el valor
         */
        nodoActual.setValor(valor);
        return balancear(nodoActual);
    }

    private NodoBinario<K, V> balancear(NodoBinario<K, V> nodoActual) {
        int alturaPorIzquierda = altura(nodoActual.getHijoIzquierdo());
        int alturaPorDerecha = altura(nodoActual.getHijoDerecho());
        int diferenciaDeAltura = alturaPorIzquierda - alturaPorDerecha;
        if(diferenciaDeAltura > DIFERENCIA_PERMITIDA) {
            //Rotación a derecha
            NodoBinario<K, V> hijoIzquierdoDelActual = nodoActual.getHijoIzquierdo();
            alturaPorIzquierda = altura(hijoIzquierdoDelActual.getHijoIzquierdo());
            alturaPorDerecha = altura(hijoIzquierdoDelActual.getHijoDerecho());
            if(alturaPorDerecha > alturaPorIzquierda) {
                return rotacionDobleDerecha(nodoActual);
            }
            return rotacionSimpleDerecha(nodoActual);
        } else if(diferenciaDeAltura < - DIFERENCIA_PERMITIDA) {
            //Rotación a izquierda
            NodoBinario<K, V> hijoDerechoDelActual = nodoActual.getHijoDerecho();
            alturaPorIzquierda = altura(hijoDerechoDelActual);
            alturaPorDerecha = altura(hijoDerechoDelActual);
            if(alturaPorIzquierda > alturaPorDerecha) {
                return rotacionDobleIzquierda(nodoActual);
            }
            return rotacionSimpleIzquierda(nodoActual);
        }
        //Si estoy por acá, quiere decir que no hay que hacer rotaciones
        return nodoActual;
    }

    private NodoBinario<K, V> rotacionSimpleDerecha(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoQueRota = nodoActual.getHijoIzquierdo();
        nodoActual.setHijoIzquierdo(nodoQueRota.getHijoDerecho());
        nodoQueRota.setHijoDerecho(nodoActual);
        return nodoQueRota;
    }

    private NodoBinario<K, V> rotacionDobleDerecha(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoPrimeraRotacion = rotacionSimpleIzquierda(nodoActual.getHijoIzquierdo());
        nodoActual.setHijoIzquierdo(nodoPrimeraRotacion);
        return rotacionSimpleDerecha(nodoActual);
    }

    private NodoBinario<K, V> rotacionSimpleIzquierda(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoQueRota = nodoActual.getHijoDerecho();
        nodoActual.setHijoDerecho(nodoQueRota.getHijoIzquierdo());
        nodoQueRota.setHijoIzquierdo(nodoActual);
        return nodoQueRota;
    }

    private NodoBinario<K, V> rotacionDobleIzquierda(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoPrimeraRotacion = rotacionSimpleDerecha(nodoActual.getHijoDerecho());
        nodoActual.setHijoDerecho(nodoPrimeraRotacion);
        return rotacionSimpleIzquierda(nodoActual);
    }

    @Override
    public V eliminar(K clave) {
        V valorAEliminar = this.buscar(clave);
        if(valorAEliminar == null) {
            throw new RuntimeException("El valor no existe");
        }
        this.raiz = eliminar(this.raiz, clave);
        return valorAEliminar;
    }

    public NodoBinario<K, V> eliminar(NodoBinario<K, V> nodoActual, K claveABuscar) {
        K claveActual = nodoActual.getClave();
        if(claveABuscar.compareTo(claveActual) < 0) {
            NodoBinario<K, V> supuestoHijoIzquierdo = eliminar(nodoActual.getHijoIzquierdo(), claveABuscar);
            nodoActual.setHijoIzquierdo(supuestoHijoIzquierdo);
            return balancear(nodoActual);
        }
        if(claveABuscar.compareTo(claveActual) > 0) {
            NodoBinario<K, V> supuestoHijoDerecho = eliminar(nodoActual.getHijoDerecho(), claveABuscar);
            nodoActual.setHijoDerecho(supuestoHijoDerecho);
            return balancear(nodoActual);
        }
        if(nodoActual.esHoja()) {
            return NodoBinario.nodoVacio();
        }
        if(!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoIzquierdo();
        }
        if(nodoActual.esVacioHijoIzquierdo() && !nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoDerecho();
        }
        NodoBinario<K, V> nodoSucesor = buscarSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K, V> supuestoHijo = eliminar(nodoActual.getHijoDerecho(), nodoSucesor.getClave());
        nodoActual.setHijoDerecho(supuestoHijo);
        nodoActual.setClave(nodoSucesor.getClave());
        nodoActual.setValor(nodoSucesor.getValor());
        return balancear(nodoActual);
    }

    public NodoBinario<K, V> buscarSucesor(NodoBinario<K, V> nodoActual) {
        NodoBinario<K, V> nodoAnt = NodoBinario.nodoVacio();
        while(!NodoBinario.esNodoVacio(nodoActual)) {
            if(!nodoActual.esVacioHijoIzquierdo()) {
                nodoAnt = nodoActual;
                nodoActual = nodoActual.getHijoIzquierdo();
            }
        }
        return nodoAnt;
    }






}
