package bo.edu.uagrm.ficct.inf310sb.grafosNoPesados;

import java.util.*;


/*
- Clase matriz de caminos X
- Clase algoritmo de warshall X
-¿Si un grafo es conexo? X
    - Hacer un recorrido X
    - Luego de realizar el recorrido evaluar si todos los vertices están marcados para saber si es conexo o no X
-Si un grafo no es conexo ¿cuantas islas tiene?
-¿Si un Digrafo es debilmente conexo? X
-¿Si un Digrafo es fuertemente conexo? X
-¿Si hay un ciclo en un grafo?
-¿Si hay un ciclo en un Digrafo?
-Clase ordenamiento topológico
- Con base en un recorrido, que podemos agregarle para saber el camino a seguir desde un vertice de partida hasta
  otro vertice que quedo marcado al realizar el recorrido.
 */
public class Grafo {
    protected List<List<Integer>> listaDeAdyacencia;
    public Grafo() {
        this.listaDeAdyacencia = new ArrayList<>();
    }

    public Grafo(int nroInicialDeVertices) {
        if(nroInicialDeVertices <= 0) {
            throw new RuntimeException("Nro de vertices no valido");
        }
        this.listaDeAdyacencia = new ArrayList<>();
        for(int i = 0; i < nroInicialDeVertices; i++) {
            this.insertarVertice();
        }
    }


    public void insertarVertice() {
        List<Integer> adyacentesDeNuevoVertice = new ArrayList<>();
        this.listaDeAdyacencia.add(adyacentesDeNuevoVertice);
    }

    public int cantidadDeVertices() {
        return listaDeAdyacencia.size();
    }

    public int gradoDelVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<Integer> adyacenteDelVertice = this.listaDeAdyacencia.get(posDeVertice);
        return adyacenteDelVertice.size();

    }

    public void validarVertice(int posicionDeVertice) {
        if (posicionDeVertice < 0 || posicionDeVertice >= this.cantidadDeVertices()) {
            throw new IllegalArgumentException("No existe vértice en la " + "posición " + posicionDeVertice + "en este grafo");
        }
    }

    public void insertarArista(int posVerticeOrigen, int posVerticeDestino) {
        if(this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new RuntimeException("La lista ya existe");
        }
        List<Integer> adyacenciaDelOrigen = this.listaDeAdyacencia.get(posVerticeOrigen);
        adyacenciaDelOrigen.add(posVerticeDestino);
        Collections.sort(adyacenciaDelOrigen);
        if(posVerticeOrigen != posVerticeDestino) {
            List<Integer> adyacenciaDelDestino = this.listaDeAdyacencia.get(posVerticeDestino);
            adyacenciaDelDestino.add(posVerticeOrigen);
            Collections.sort(adyacenciaDelDestino);
        }
    }

    public boolean existeAdyacencia(int posVerticeOrigen, int posVerticeDestino) {
        validarVertice(posVerticeOrigen);
        validarVertice(posVerticeDestino);
        List<Integer> adyacenciaDelOrigen = this.listaDeAdyacencia.get(posVerticeOrigen);
        return adyacenciaDelOrigen.contains(posVerticeDestino);
    }

    public Iterable<Integer> adyancentesDeVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        List<Integer> adyaventeDelVertice = this.listaDeAdyacencia.get(posDeVertice);
        Iterable<Integer> iterableDeAdyacentes = adyaventeDelVertice;
        return iterableDeAdyacentes;
    }

    public int cantidadDeAristas() {
        int cantAristas = 0;
        int cantLazos = 0;
        for (int i = 0; i < this.listaDeAdyacencia.size(); i++) {
            List<Integer> adyacentesDeUnVertice = this.listaDeAdyacencia.get(i);
            for(Integer posDeAdyacente : adyacentesDeUnVertice) {
                if(i == posDeAdyacente) {
                    cantLazos++;
                } else {
                    cantAristas++;
                }
            }
        }
        return cantLazos + (cantAristas / 2);
    }

    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino) {
        if(!this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new IllegalArgumentException("La arista ya existe");
        }
        List<Integer> adyacenteDelOrigen = this.listaDeAdyacencia.get(posVerticeOrigen);
        int posicionDelDestino = adyacenteDelOrigen.indexOf(posVerticeDestino);
        adyacenteDelOrigen.remove(posicionDelDestino);
        if(posVerticeOrigen != posVerticeDestino) {
            List<Integer> adyacentesDelDestino = this.listaDeAdyacencia.get(posicionDelDestino);
            int posicionDelOrigen = adyacenteDelOrigen.indexOf(posVerticeOrigen);
            adyacentesDelDestino.remove(posicionDelOrigen);
        }
    }

    /**
     *  1. Para un grafo no dirigido implementar el método de eliminar vértice.
     *
     *
     * @param: posición del vertice a eliminar
     *
     *
     * */

    public void eliminarVertice(int posDeVertice) {
        validarVertice(posDeVertice);
        listaDeAdyacencia.remove(posDeVertice);
        for (int i = 0; i < listaDeAdyacencia.size(); i++) {
            List<Integer> adyacencias = listaDeAdyacencia.get(i);
            for(int j = 0; j < adyacencias.size(); j++) {
                int pos = adyacencias.indexOf(posDeVertice);
                if(pos >= 0) {
                    adyacencias.remove(pos);
                } else if (pos > posDeVertice) {
                    int p = adyacencias.get(j);
                    adyacencias.set(j, p - 1);
                }
            }
        }
    }

    public void eliminarVertice2(int posVerticeAEliminar){
        validarVertice(posVerticeAEliminar);
        this.listaDeAdyacencia.remove(posVerticeAEliminar);
        for (List<Integer> adyacentesDeUnVertice: this.listaDeAdyacencia) {
            int posicionDeVerticeEnAdy = adyacentesDeUnVertice.indexOf(posVerticeAEliminar);
            if (posicionDeVerticeEnAdy >= 0) {
                adyacentesDeUnVertice.remove(posicionDeVerticeEnAdy);
            }
            for (int i = 0; i < adyacentesDeUnVertice.size(); i++){
                int posicionAdyacente = adyacentesDeUnVertice.get(i);
                if (posicionAdyacente > posVerticeAEliminar){
                    adyacentesDeUnVertice.set(i,posicionAdyacente - 1);
                }
            }
        }
    }

    private Grafo transformar(DiGrafo digrafo) {
        int cantidad= digrafo.cantidadDeVertices();
        Grafo grafo = new Grafo();
        for(int i=0; i<digrafo.cantidadDeVertices();i++){
            grafo.insertarVertice();
        }
        for(int i=0; i<digrafo.cantidadDeVertices();i++){
            List<Integer> L1 = digrafo.listaDeAdyacencia.get(i);
            for(int j=0;j<L1.size();j++ ){
                grafo.listaDeAdyacencia.get(i).add(L1.get(j));
                grafo.listaDeAdyacencia.get(L1.get(j)).add(i);
            }
        }

        return grafo;
    }

    private void llenarCeros(int m[][]) {
        for(int i = 0; i < m.length; i++) {
            for(int j = 0; j < m.length; j++) {
                m[i][j] = 0;
            }
        }
    }

    public int[][] matrizDeAdyacencia(List<List<Integer>> listaDeAdyacencia) {
        int m[][] = new int[this.listaDeAdyacencia.size()][this.listaDeAdyacencia.size()];
        llenarCeros(m);
        for(int i = 0; i < this.listaDeAdyacencia.size(); i++) {
            List<Integer> listaAdyacente = this.listaDeAdyacencia.get(i);
            for(int k = 0; k < listaAdyacente.size(); k++) {
                int pos = listaAdyacente.get(k);
                for(int j = 0; j < this.listaDeAdyacencia.size(); j++) {
                    if(pos == j) {
                        m[i][j] = 1;
                    }
                }
            }
        }
        return m;
    }

    public String soutMatriz(int m[][]) {
        String matriz = "";
        for(int i = 0; i < m.length; i++) {
            for(int j = 0; j < m.length; j++) {
                matriz = matriz + m[i][j] + " " + ",";
            }
            matriz = matriz + "\n";
        }
        return matriz;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < listaDeAdyacencia.size(); i++) {
            List<Integer> listaDeAdyacentes = listaDeAdyacencia.get(i);
            s = s + i + "  [ " + listaDeAdyacencia.get(i) + " ]-->[ ";
            for (int j = 0; j < listaDeAdyacentes.size(); j++) {
                if (j == listaDeAdyacentes.size() - 1) {
                    s = s + "" + listaDeAdyacentes.get(j);
                } else {
                    s = s + "" + listaDeAdyacentes.get(j) + " , ";
                }
            }
            s = s + " ]" + '\n';
        }
        return s;
    }




}

