package bo.edu.uagrm.ficct.inf310sb.grafosNoPesados;

public class MainDiGrafos {
    public static void main (String[] args) {
        DiGrafo diGrafo = new DiGrafo(5);
        diGrafo.insertarArista(0,1);
        diGrafo.insertarArista(0,4);
        diGrafo.insertarArista(1,3);
        diGrafo.insertarArista(2,3);
        diGrafo.insertarArista(3,4);
        diGrafo.insertarArista(4,1);

        DiGrafo islaDiGrafo = new DiGrafo(7);
        islaDiGrafo.insertarArista(0,1);
        islaDiGrafo.insertarArista(1,1);
        islaDiGrafo.insertarArista(1,3);
        islaDiGrafo.insertarArista(3,0);
        islaDiGrafo.insertarArista(2,4);
        islaDiGrafo.insertarArista(5,5);

        DiGrafo huevades = new DiGrafo(6);
        huevades.insertarArista(0,0);
        huevades.insertarArista(1,2);
        huevades.insertarArista(3,4);
        huevades.insertarArista(4,5);
        huevades.insertarArista(5,4);


        //TOSTRING
        System.out.println("DiGrafo no isla");
        System.out.println(diGrafo.toString());
        MétodosDiGrafos ejercicios = new MétodosDiGrafos(diGrafo);
        System.out.println("2. Para un grafo dirigido implementar método o clase para encontrar si hay ciclos sin usar matriz de caminos.");
        System.out.println(ejercicios.ejercicio2());
        System.out.println("3. Para un grafo dirigido implementar un método o clase que sea capas de retornar los componentes de las islas que existen en dicho digrafo");
        ejercicios.ejercicio3();
        System.out.println("4. Para un grafo dirigido implementar un método o clase para determinar desde que vértices se puede llegar a un vértice, pero sin ejecutar más de una vez un recorrido.");
        System.out.println(ejercicios.ejercicio4(4));
        System.out.println("5. Para un grafo dirigido solo usando como base la lógica de un recorrido (dfs o bfs) encuentre desde que vértices se puede llegar a un vértice a, sin importar las veces que ejecute el recorrido elegido");
        System.out.println(ejercicios.ejercicio5(4));
        System.out.println("6. Para un grafo dirigido implementar un algoritmo para encontrar si el grafo dirigido tiene ciclos");
        System.out.println(ejercicios.ejercicio6());
        System.out.println("10. Para un grafo dirigido implementar un algoritmo para encontrar el número de islas que hay en el grafo");
        System.out.println(ejercicios.ejercicio10());
        System.out.println("11. Para un grafo dirigido implementar el algoritmo de Wharsall, que luego muestre entre que vértices hay camino");
        Warshall matriz = new Warshall(diGrafo);
        int m[][] = matriz.algoritmoWharsall();
        matriz.showMatriz(m);
        System.out.println("-------------------------------------------------------------------");

        //TOSTRING DIGRAFO CON ISLAS
        System.out.println("DiGrafo isla");
        System.out.println(islaDiGrafo.toString());
        //System.out.println(huevades.toString());
        MétodosDiGrafos ejerciciosIslas = new MétodosDiGrafos(islaDiGrafo);
        ejerciciosIslas.ejercicio3();
        System.out.println("");
        System.out.println(ejerciciosIslas.ejercicio4(0));
    }


}
