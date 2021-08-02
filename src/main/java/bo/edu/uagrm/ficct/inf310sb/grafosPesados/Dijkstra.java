package bo.edu.uagrm.ficct.inf310sb.grafosPesados;

import java.util.*;


/**
 * 14. Para un grafo dirigido pesado implementar el algoritmo de Dijkstra que
 * muestre cual es el camino de costo mínimo entre un vértice a y b y cual el costo
 *
 * */
public class Dijkstra {
    double INFINITO = Double.MAX_VALUE;
    DiGrafoPesado diGrafo;
    List<Double> listaDeCostos;
    UtilsRecorridos marcados;
    List<Integer> predecesores;
    Stack<Integer> pilaDeCaminos;
    double costoMinimo;

   public Dijkstra(DiGrafoPesado unDigrafo) {
       this.diGrafo = unDigrafo;
       listaDeCostos = new LinkedList<>();
       marcados = new UtilsRecorridos(unDigrafo.cantidadDeVertices());
       predecesores = new LinkedList<>();
       pilaDeCaminos = new Stack<>();
       costoMinimo = INFINITO;
       for(int i = 0; i < diGrafo.cantidadDeVertices(); i++) {
           listaDeCostos.add(INFINITO);
           predecesores.add(-1);
       }
   }

   public double menorNoMarcado(List<Double> listaDeCostos, UtilsRecorridos marcados) {
       double menor = Double.MAX_VALUE;
       int i = 0;
       while(i < listaDeCostos.size()) {
           if(listaDeCostos.get(i) < menor && !marcados.estaVerticeMarcado(i)) {
               menor = listaDeCostos.get(i);
           }
           i++;
       }
       return menor;
   }


   public void caminoMinimo(int verticeI, int verticeF) {
       listaDeCostos.set(verticeI, 0.0);
       int verticeActual = verticeI;
       while (verticeActual != verticeF && !marcados.estanTodosMarcados()) {
           double posMenor = menorNoMarcado(listaDeCostos, marcados);
           verticeActual = listaDeCostos.indexOf(posMenor);
           pilaDeCaminos.add(verticeActual);
           List<Integer> listaDeAdyacentes = (List<Integer>) diGrafo.adyancentesDeVertice(verticeActual);
           for(int i = 0; i < listaDeAdyacentes.size(); i++) {
               int posicionActual = listaDeAdyacentes.get(i);
               if(!marcados.estaVerticeMarcado(posicionActual)) {
                   predecesores.set(posicionActual, verticeActual);
               }
               if(listaDeCostos.get(posicionActual) > (listaDeCostos.get(verticeActual) + diGrafo.peso(verticeActual,posicionActual))) {
                   double nuevoCosto = posMenor + diGrafo.peso(verticeActual, posicionActual);
                   listaDeCostos.set(posicionActual, nuevoCosto);
               }
           }
           marcados.marcarVertice(verticeActual);
       }
       costoMinimo = listaDeCostos.get(verticeF);
   }

   public Stack<Integer> getPilaDeCaminos() {
       return pilaDeCaminos;
   }

   public List<Integer> getPredecesores() {
       return predecesores;
   }

   public double getCostoMinimo(int verticeI, int verticeF) {
       caminoMinimo(verticeI, verticeF);
       //System.out.println(pilaDeCaminos);
       return costoMinimo;

   }
}
