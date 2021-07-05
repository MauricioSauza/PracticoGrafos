package bo.edu.uagrm.ficct.inf310sb;

public class MainMvias {
    public static void main (String[] args) {
        ArbolMViasBusqueda<Integer, String> tree = new ArbolMViasBusqueda<>();
        tree.insertar(100,"xxx");
        tree.insertar(200,"xxx");
        tree.insertar(300,"xxx");
        tree.insertar(30,"xxx");
        tree.insertar(50,"xxx");
        tree.insertar(130,"xxx");
        tree.insertar(150,"xxx");
        tree.insertar(430,"xxx");
        tree.insertar(500,"xxx");
        tree.insertar(145,"xxx");
        tree.insertar(155,"xxx");
        tree.insertar(135,"xxx");
        tree.insertar(156,"xxx");
        tree.insertar(157,"xxx");
        System.out.println(tree.arbolGrafico());
        System.out.println(tree.recorridoPorNiveles());

        //PRACTICO M-VIAS

        ArbolMViasBusqueda<Integer, String> tree2 = new ArbolMViasBusqueda<>();
        tree2.insertar(100,"xxx");
        tree2.insertar(200,"xxx");
        tree2.insertar(300,"xxx");
        tree2.insertar(30,"xxx");
        tree2.insertar(50,"xxx");
        tree2.insertar(130,"xxx");
        tree2.insertar(150,"xxx");
        tree2.insertar(430,"xxx");
        tree2.insertar(500,"xxx");
        tree2.insertar(145,"xxx");
        tree2.insertar(155,"xxx");
        tree2.insertar(135,"xxx");
        tree2.insertar(156,"xxx");
        tree2.insertar(157,"xxx");

        System.out.println("Ejercicio 17 " + "La clave mayor del árbol es: " + tree.ejercicio17());
        System.out.println("Ejercicio 18 " + "El árbol tiene hojas en el último nivel: " + tree.ejercicio18());
        System.out.println("Ejercicio 19 " + "El árbol tiene hojas o nodos completos: " + tree.ejercicio19());
        System.out.println("Ejercicio 20 " + "El árbol es similar " + tree.esSimilar(tree2));


    }

}
