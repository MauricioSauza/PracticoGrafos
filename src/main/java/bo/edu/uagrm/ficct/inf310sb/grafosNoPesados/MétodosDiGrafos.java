package bo.edu.uagrm.ficct.inf310sb.grafosNoPesados;

import java.util.LinkedList;
import java.util.List;

public class MétodosDiGrafos {
    private DiGrafo diGrafo;
    private Conexo conexo;
    private DFS dfs;
    private UtilsRecorridos controlMarcados;


    public MétodosDiGrafos (DiGrafo unDiGrafo) {
        this.diGrafo = unDiGrafo;
        this.conexo = new Conexo(diGrafo);
        controlMarcados = new UtilsRecorridos(diGrafo.cantidadDeVertices());
    }

    public boolean ciclo () {
        return ciclo(0);
    }

    public boolean ciclo (int posVertice) {
        controlMarcados.marcarVertice(posVertice);
        Iterable<Integer> adyacentesDeVertcieEnTurno = diGrafo.adyancentesDeVertice(posVertice);
        List<Integer> listaAdyacente = diGrafo.listaDeAdyacencia.get(posVertice);
        //System.out.println(adyacentesDeVertcieEnTurno);
        for ( Integer posVerticeAdyacente : adyacentesDeVertcieEnTurno ) {
            if (controlMarcados.estaVerticeMarcado(posVerticeAdyacente)) {
                //System.out.println(controlMarcados.marcados);
                //controlMarcados.marcarVertice(posVertice);
                if (verificar(listaAdyacente, controlMarcados.marcados)) {
                    return true;
                } else {
                    controlMarcados.desmarcarVertice(posVerticeAdyacente);
                    ciclo(posVerticeAdyacente);
                }

            } else {
                ciclo(posVerticeAdyacente);
            }
        }
        return false;
    }


    public boolean verificar (List<Integer> listaAdyacente, List<Boolean> listaMarcados) {
        for ( int i = 0; i < listaAdyacente.size(); i++ ) {
            int p = listaAdyacente.get(i);
            if (listaMarcados.get(p) == true) {
                return true;
            }
        }
        return false;
    }

    /**
     *  2. Para un grafo dirigido implementar método o clase para encontrar
     *  si hay ciclos sin usar matriz de caminos.
     *
     * */
    public boolean ejercicio2() {
        for(int i = 0; i < diGrafo.cantidadDeVertices(); i++) {
            if(ejercicio2(i)) {
                return true;
            }
        }
        return false;
    }

    public boolean ejercicio2(int posVertice) {
        controlMarcados.marcarVertice(posVertice);
        Iterable<Integer> adyacentesDeVerticeEnTurno = diGrafo.adyancentesDeVertice(posVertice);
        for(Integer posVerticeAdyacente : adyacentesDeVerticeEnTurno) {
            if(controlMarcados.estaVerticeMarcado(posVerticeAdyacente)) {
                return true;
            } else {
                if(ejercicio2(posVerticeAdyacente)) {
                    return true;

                }
            }
        }
        //Si no encuentra ciclo viene aqui y desmarca el vertice y sus adyacentes
        controlMarcados.desmarcarVertice(posVertice);
        for(Integer posVerticeAdyacente : adyacentesDeVerticeEnTurno) {
            controlMarcados.desmarcarVertice(posVerticeAdyacente);
        }
        return false;
    }

    /**
     *  3. Para un grafo dirigido implementar un método o clase que sea capas de
     *  retornar los componentes de las islas que existen en dicho digrafo
     *
     * */
    public void ejercicio3() {
        dfs = new DFS(diGrafo, 0);
        System.out.println("0" + dfs.recorrido);
        for (int i = 0; i < diGrafo.listaDeAdyacencia.size(); i++) {
            if (!dfs.controlMarcados.estaVerticeMarcado(i)) {
                dfs.procesarDFS(i);
                DFS dfs2 = new DFS(diGrafo,i);
                String pos = "" + i;
                System.out.println(pos + dfs2.recorrido);
            }
        }
    }



    /**
     *  4. Para un grafo dirigido implementar un método o clase para determinar desde
     *  que vértices se puede llegar a un vértice, pero sin ejecutar más de una vez un recorrido.
     *
     *
     * @param: x es el vértice al que queremos llegar
     * @return: L1 es la lista de vértices de los cuáles se puede llegar a x
     *
     * */

    public LinkedList<Integer> ejercicio4(int x){
        LinkedList<Integer> L1 = new LinkedList();
        Warshall ejecutar = new Warshall(diGrafo);
        int[][] matrizDeCaminos = ejecutar.algoritmoWharsall();
        for(int i=0; i<diGrafo.cantidadDeVertices() ;i++){
            if(sePuedeLlegarEj4(x,i,matrizDeCaminos) && x!=i){
                L1.add(i);
            }
        }
        return L1;
    }
    public boolean sePuedeLlegarEj4(int x, int pos, int[][] m) {
        if(m[pos][x]==1){
            return true;
        }
        return false;
    }

    /**
     *  5. Para un grafo dirigido solo usando como base la lógica de un recorrido (dfs o bfs) encuentre desde que
     *  vértices se puede llegar a un vértice a, sin importar las veces que ejecute el recorrido elegido
     *
     *
     * @param: x es el vértice al que se quiere llegar
     * @return: L1 es la lista de vértices de los cuáles se puede llegar a x
     *
     * */

    public LinkedList<Integer> ejercicio5(int x){
        LinkedList<Integer> L1 = new LinkedList();
        for(int i = 0; i < diGrafo.cantidadDeVertices(); i++){
            if(sePuedeLlegarEj5(x, i) && x != i){
                L1.add(i);
            }
        }
        return L1;
    }
    private boolean sePuedeLlegarEj5(int x, int pos) {
        DFS dfs = new DFS(diGrafo,pos);
        if(dfs.controlMarcados.estaVerticeMarcado(x)){
            return true;
        }
        return false;
    }

    /**
     *  6. Para un grafo dirigido implementar un algoritmo para
     *  encontrar si el grafo dirigido tiene ciclos
     *
     * */
    //6. Para un grafo dirigido implementar un algoritmo para
    //encontrar si el grafo dirigido tiene ciclos
    public boolean ejercicio6() {
        for(int i = 0; i < diGrafo.cantidadDeVertices(); i++) {
            if(ejercicio6(i)) {
                return true;
            }
        }
        return false;
    }

    public boolean ejercicio6(int posVertice) {
        controlMarcados.marcarVertice(posVertice);
        Iterable<Integer> adyacentesDeVerticeEnTurno = diGrafo.adyancentesDeVertice(posVertice);
        for(Integer posVerticeAdyacente : adyacentesDeVerticeEnTurno) {
            if(controlMarcados.estaVerticeMarcado(posVerticeAdyacente)) {
                return true;
            } else {
                if(ejercicio6(posVerticeAdyacente)) {
                    return true;

                }
            }
        }
        //Si no encuentra ciclo viene aqui y desmarca el vertice y sus adyacentes
        controlMarcados.desmarcarVertice(posVertice);
        for(Integer posVerticeAdyacente : adyacentesDeVerticeEnTurno) {
            controlMarcados.desmarcarVertice(posVerticeAdyacente);
        }
        return false;
    }

    /**
     *  10. Para un grafo dirigido implementar un algoritmo para
     *  encontrar el número de islas que hay en el grafo
     *
     *
     * @param:
     * @return: cantIslas retorna la cantidad de islas del gráfo
     *
     * */
    public int ejercicio10() {
        int cantIslas = 1;
        if(!conexo.esConexo(diGrafo)) {
            dfs = new DFS(diGrafo, 0);
            for(int i = 0; i < diGrafo.cantidadDeVertices(); i++) {
                List<Integer> adyacentes = diGrafo.listaDeAdyacencia.get(i);
                for(int j = 0; j < adyacentes.size(); j++) {
                    if(dfs.controlMarcados.estaVerticeMarcado(j)) {
                        dfs.controlMarcados.marcarVertice(i);
                    }
                }
                if(!dfs.controlMarcados.estaVerticeMarcado(i)) {
                    cantIslas++;
                    dfs.procesarDFS(i);
                }
            }
        }
        return cantIslas;
    }
}
