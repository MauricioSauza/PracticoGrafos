package bo.edu.uagrm.ficct.inf310sb;


public class Main {

    public static void main (String[] args) {
        //ÁRBOLES BINARIOS DE BUSQUEDA
        ABB<Integer,String> tree = new ABB<>();

        tree.insertar(20,"");
        tree.insertar(16,"");
        tree.insertar(30,"");
        tree.insertar(18,"");
        tree.insertar(25,"");
        tree.insertar(35, "");
        tree.insertar(22, "");
        tree.insertar(32, "");
        /*
        tree.insertarRec(14, "");
        tree.insertarRec(27, "");
        tree.insertarRec(38, "");
        */
        System.out.println(tree.arbolGrafico());


        //Prueba para ejercicios 14 y 15
        NodoBinario<Integer, String> nodo = new NodoBinario<>();
        nodo.setValor("");
        nodo.setClave(10);

        //Métodos propios del árbol binario
        System.out.println("Recorrido por niveles" + tree.recorridoPorNiveles());
        System.out.println("Recorrido en pre orden" + tree.recorridoEnPreOrden());
        System.out.println("Recorrido en in orden" + tree.recorridoEnInOrden());
        System.out.println("Recorrido en post orden" + tree.recorridoEnPostOrden());
        System.out.println("Size por niveles " + tree.size());
        System.out.println("La altura del árbol por niveles es: " + tree.altura());

        //PRACTICO DE ARBOLES BINARIOS
        System.out.println("PRACTICO DE ARBOLES BINARIOS");

        System.out.println("Ejercicio 3");
        System.out.println("Ejercicio 3 " + "cantidad de nodos completos: " + tree.ejercicio3());

        System.out.println("Ejercicio 4");
        System.out.println("Ejercicio 4 " + "cantidad de nodos completos recursivo: " + tree.ejercicio4());

        System.out.println("Ejercicio 5");
        System.out.println("Ejercicio 5 " + "cantida de nodos completos en nivel n " + tree.ejercicio5(0));

        System.out.println("Ejercicio 6");
        System.out.println("Ejercicio 6 " + "cantida de nodos completos en nivel n " + tree.ejercicio6(0));

        System.out.println("Ejercicio 7");
        System.out.println("Ejercicio 7 " + "Cantidad de nodos con un solo hijo antes del nivel N " + tree.ejercicio7(3));

        System.out.println("Ejercicio 12");
        System.out.println("Ejercicios 12 " + "Cantidad de nodos con InOrden " + tree.ejercicio12());

        System.out.println("Ejercicio 14");
        System.out.println("Ejercicio 14 " + "El sucesor InOrden del nodo enviado es: " + tree.ejercicio14(nodo));

        System.out.println("Ejercicio 15");
        //System.out.println("Ejercicio 15 " + "El predecesor InOrden del nodo enviado es: " + tree.ejercicio15(nodo));

        System.out.println("Ejercicio 16");
        System.out.println("Ejercicio 16 " + "Encontrar la clave menor del árbol " + tree.ejercicio16());

        System.out.println("Ejercicio 21");
        System.out.println("Ejercicio 21 " + "verificar si un árbol esta lleno " + tree.ejercicio21());
    }


}
