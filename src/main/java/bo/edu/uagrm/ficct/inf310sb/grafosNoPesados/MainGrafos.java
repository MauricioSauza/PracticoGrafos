package bo.edu.uagrm.ficct.inf310sb.grafosNoPesados;

public class Main {
    public static void main (String[] args) {
        Grafo grafo = new Grafo(7);
        grafo.insertarArista(0,1);
        grafo.insertarArista(1, 2);
        grafo.insertarArista(2 , 0);
        grafo.insertarArista(2, 3);
        grafo.insertarArista(4, 4);
        grafo.insertarArista(4, 5);
        grafo.insertarArista(5, 5);
        grafo.insertarArista(6,6);

        Grafo grafo2 = new Grafo(5);
        grafo2.insertarArista(0,1);
        grafo2.insertarArista(0,2);
        grafo2.insertarArista(2,3);
        grafo2.insertarArista(1,4);
        grafo2.insertarArista(3,4);

        DiGrafo diGrafo = new DiGrafo(5);
        diGrafo.insertarArista(0, 1);
        diGrafo.insertarArista(0, 4);
        diGrafo.insertarArista(1, 3);
        diGrafo.insertarArista(2, 3);
        diGrafo.insertarArista(3, 4);
        diGrafo.insertarArista(4, 1);

        diGrafo.insertarArista(4, 2);
        diGrafo.insertarArista(3, 0);

        DiGrafo diGrafo1 = new DiGrafo(7);
        diGrafo1.insertarArista(0, 1);
        diGrafo1.insertarArista(2, 0);
        diGrafo1.insertarArista(2, 1);
        diGrafo1.insertarArista(3, 1);
        diGrafo1.insertarArista(3, 2);
        diGrafo1.insertarArista(4 ,5);
        /*
        diGrafo1.insertarArista(4,3);
        diGrafo1.insertarArista(4, 6);
         */

        /*
        BFS grafoBFS = new BFS(grafo,0);
        System.out.println(grafoBFS.elRecorrido());

        DFS grafoDFS = new DFS(grafo, 0);
        System.out.println(grafoDFS.alRecorrido());

        Conexo verificar = new Conexo(grafo,0);
        System.out.println(verificar.esConexo());

         */

        /*
        System.out.println(diGrafo.toString());

        MatrizDeAdyacencia matriz = new MatrizDeAdyacencia(grafo2);
        System.out.println(matriz.toString(matriz.matrizDeAdyacencia(diGrafo)));

        Warshall algWharsall = new Warshall(diGrafo, matriz);
        System.out.println(algWharsall.toString(algWharsall.algoritmoDeWarshall(diGrafo, matriz)));

        //Warshall2 algWarshall2 = new Warshall2(diGrafo);
        //System.out.println(algWarshall2.toString());

        FuerteMenteConexo diGrafoFuerteMenteConexo = new FuerteMenteConexo(diGrafo);
        System.out.println("El DiGrafo es fuertemente conexo? : " + diGrafoFuerteMenteConexo.esFuerteMenteConexo());
        DebilMenteConexo diGrafoDebilMenteConexo = new DebilMenteConexo(diGrafo);
        System.out.println("El DiGrafo es debilmente conexo? : " + diGrafoDebilMenteConexo.esDebilMenteConexo());
         */

        System.out.println(grafo.toString());

        MétodosGrafos ejercicios = new MétodosGrafos(grafo);
        System.out.println("El grafo tiene " + ejercicios.islas() + " de islas");





        //System.out.println(diGrafo.toString());

        //System.out.println(grafoChikito.toString());
        System.out.println(diGrafo1.toString());
        MétodosDiGrafos ejerciciosDigrafo = new MétodosDiGrafos(diGrafo1);
        System.out.println(ejerciciosDigrafo.islas());







    }
}
