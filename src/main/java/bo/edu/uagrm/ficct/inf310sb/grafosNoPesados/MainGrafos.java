package bo.edu.uagrm.ficct.inf310sb.grafosNoPesados;

public class MainGrafos {
    public static void main (String[] args) {
        Grafo grafo = new Grafo(5);
        grafo.insertarArista(0,1);
        grafo.insertarArista(0,4);
        grafo.insertarArista(0,3);
        grafo.insertarArista(1,3);
        grafo.insertarArista(1,4);
        grafo.insertarArista(2,3);
        grafo.insertarArista(3,4);

        Grafo grafoMenor = new Grafo(4);
        grafoMenor.insertarArista(0,1);
        grafoMenor.insertarArista(0,2);
        grafoMenor.insertarArista(1,2);
        grafoMenor.insertarArista(1,3);
        //grafoMenor.insertarArista(2,3);



        //GRAFO SIN ISLAS
        System.out.println(grafoMenor.toString());
        MétodosGrafos ejercicios = new MétodosGrafos(grafoMenor);
        System.out.println("8. Para un grafo no dirigido implementar un algoritmo para encontrar que en que vértices del grafo hay ciclos");
        System.out.println(ejercicios.ejercicio8());
        System.out.println("9. Para un grafo no dirigido implementar un algoritmo para encontrar el número de islas que hay en el grafo");
        System.out.println(ejercicios.ejercicio9());
        System.out.println("______________________________________________________________________________________");







    }
}
