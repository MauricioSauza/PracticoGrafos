package bo.edu.uagrm.ficct.inf310sb.grafosNoPesados;

import java.util.Collections;
import java.util.List;

public class DiGrafo extends Grafo{

    public DiGrafo() {
        super();
    }

    public DiGrafo(int nroInicialDeVertices) {
        super(nroInicialDeVertices);
    }

    @Override
    public int cantidadDeAristas() {
        int cantidad = 0;
        for(int i = 0; i < this.listaDeAdyacencia.size(); i++) {
            List<Integer> adyacencias = listaDeAdyacencia.get(i);
            cantidad += adyacencias.size();
        }
        return cantidad;
    }

    public int cantidadDeVertices() {
        return listaDeAdyacencia.size();
    }

    @Override
    public void eliminarArista(int posVerticeOrigen, int posVerticeDestino) {
        if(!this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new IllegalArgumentException("La arista ya existe");
        }
        List<Integer> adyacenteDelOrigen = this.listaDeAdyacencia.get(posVerticeOrigen);
        int posicionDelDestino = adyacenteDelOrigen.indexOf(posVerticeDestino);
        adyacenteDelOrigen.remove(posicionDelDestino);

    }

    @Override
    public void eliminarVertice (int posDeVertice) {
        super.eliminarVertice(posDeVertice);
    }

    @Override
    public void insertarArista(int posVerticeOrigen, int posVerticeDestino) {
        if(this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new RuntimeException("La lista ya existe");
        }
        List<Integer> adyacenciaDelOrigen = this.listaDeAdyacencia.get(posVerticeOrigen);
        adyacenciaDelOrigen.add(posVerticeDestino);
        Collections.sort(adyacenciaDelOrigen);
    }
    //@Override
    public int gradoDeVertice(int posDeVertice) {
        throw new UnsupportedOperationException("Método no soportado en grafos dirigidos");
    }

    public int gradoDeEntradaDeVertice(int posDeVertice) {
        super.validarVertice(posDeVertice);
        int entradaDeVertice = 0;
        for(int i = 0; i < super.listaDeAdyacencia.size(); i++) {
            Iterable<Integer> adyacentesDeUnVertice = super.adyancentesDeVertice(i);
            for(Integer posDeAdyacente : adyacentesDeUnVertice) {
                if(posDeAdyacente == posDeVertice) {
                    entradaDeVertice++;
                }
            }
        }
        return entradaDeVertice;
    }


    public int gradoDeSalidaDeVertice(int posDeVertice) {
        return super.gradoDelVertice(posDeVertice);
    }




}
