package bo.edu.uagrm.ficct.inf310sb.grafosPesados;

public class MainGrafoPesado {
    public static void main (String[] args) {
        GrafoPesado grafo = new GrafoPesado(5);
        grafo.insertarArista(0,1, 50);
        grafo.insertarArista(0, 4, 10);
        grafo.insertarArista(1, 4, 10);
        grafo.insertarArista(1, 3, 20);
        grafo.insertarArista(3, 4, 100);
        grafo.insertarArista(3, 2, 400);

        System.out.println(grafo.toString());

        MatrizDeAdyacencia matriz = new MatrizDeAdyacencia(grafo);
        int m[][] = matriz.matrizDeAdyacencia(grafo);
        System.out.println(matriz.toString(m));

        DiGrafoPesado diGrafo = new DiGrafoPesado(6);
        diGrafo.insertarArista(0,1,50);
        diGrafo.insertarArista(0,2,10);
        diGrafo.insertarArista(0,4,60);
        diGrafo.insertarArista(0,5,100);
        diGrafo.insertarArista(1,3,50);
        diGrafo.insertarArista(1,4,15);
        diGrafo.insertarArista(2,1,5);
        diGrafo.insertarArista(3,0,80);
        diGrafo.insertarArista(3,5,20);
        diGrafo.insertarArista(4,5,20);
        diGrafo.insertarArista(5,1,40);
        diGrafo.insertarArista(5,2,70);

        //TOSTRING
        System.out.println(diGrafo.toString());
        System.out.println("12. Para un grafo dirigido usando la implementación del algoritmo de Floyd-Wharsall encontrar los " +
                "caminos de costo mínimo entre un vértice a y el resto. Mostrar los costos y cuáles son los caminos");
        FloydWarshall matrizFloyd = new FloydWarshall(diGrafo);
        matrizFloyd.floydWarshall();
        int matrix[][] = matrizFloyd.minimasDistancias();
        matrizFloyd.showMatriz(matrix);
        System.out.println("14. Para un grafo dirigido pesado implementar el algoritmo de Dijkstra que muestre cual es el camino de costo mínimo entre un vértice a y b y cual el costo");
        Dijkstra ejecutar = new Dijkstra(diGrafo);
        System.out.println(ejecutar.getPredecesores());
        System.out.println(ejecutar.getPilaDeCaminos());
        System.out.println(ejecutar.getCostoMinimo(0, 5));

    }

}
