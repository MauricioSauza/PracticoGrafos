package bo.edu.uagrm.ficct.inf310sb.grafosPesados;

import java.util.Collections;
import java.util.List;

public class DiGrafoPesado extends GrafoPesado {

   public DiGrafoPesado() {
       super();
   }

   public DiGrafoPesado(int nroInicialDeVertices) {
       super(nroInicialDeVertices);
   }

    @Override
    public int cantidadDeAristas () {
        int cantidad = 0;
        for(int i = 0; i < this.listaDeAdyacencia.size(); i++) {
            List<AdyacentesConPeso> adyacencias = listaDeAdyacencia.get(i);
            cantidad += adyacencias.size();
        }
        return cantidad;
    }

    @Override
    public void eliminarArista (int posVerticeOrigen, int posVerticeDestino) {
        if(!this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new IllegalArgumentException("La arista ya existe");
        }
        List<AdyacentesConPeso> adyacenteDelOrigen = this.listaDeAdyacencia.get(posVerticeOrigen);
        AdyacentesConPeso adyacenciaAlOrigen = new AdyacentesConPeso(posVerticeDestino, 0);
        int posicionDelDestino = adyacenteDelOrigen.indexOf(adyacenciaAlOrigen);
        adyacenteDelOrigen.remove(posicionDelDestino);
    }

    @Override
    public void insertarArista (int posVerticeOrigen, int posVerticeDestino, double peso) {
        if(this.existeAdyacencia(posVerticeOrigen, posVerticeDestino)) {
            throw new RuntimeException("La lista ya existe");
        }
        List<AdyacentesConPeso> adyacenciaDelOrigen = this.listaDeAdyacencia.get(posVerticeOrigen);
        AdyacentesConPeso adyacenciaAlOrigen = new AdyacentesConPeso(posVerticeDestino, peso);
        adyacenciaDelOrigen.add(adyacenciaAlOrigen);
        Collections.sort(adyacenciaDelOrigen);
    }

    @Override
    public Iterable<Integer> adyancentesDeVertice (int posDeVertice) {
        return super.adyancentesDeVertice(posDeVertice);
    }

    @Override
    public int gradoDelVertice (int posDeVertice) {
        throw new UnsupportedOperationException("Método no soportado grafos dirigidos");
    }

    public int gradoDeEntrdaDeVertice (int posDeVertice) {
        super.validarVertice(posDeVertice);
        int entradaDeVertice = 0;
        for(List<AdyacentesConPeso> adyacentesDeVertice : super.listaDeAdyacencia) {
            for(AdyacentesConPeso posDeAdyacente : adyacentesDeVertice) {
                if(posDeAdyacente.getIndiceVertice() == posDeVertice) {
                    entradaDeVertice++;
                }
            }
        }
        return entradaDeVertice;
    }

    public int gradoDeSalidaDeVertice (int posDeVertice) {
        return super.gradoDelVertice(posDeVertice);
    }

    @Override
    public String toString() {
        String grafo = "";
        for ( int i = 0; i < listaDeAdyacencia.size(); i++ ) {
            List<AdyacentesConPeso> adyacentes = listaDeAdyacencia.get(i);
            grafo = grafo + i + " : " + "[";
            for ( int j = 0; j < adyacentes.size(); j++ ) {
                AdyacentesConPeso componente = adyacentes.get(j);
                if (j == adyacentes.size() - 1) {
                    grafo = grafo + "[" + componente.getIndiceVertice() + "|" + (int)componente.getPeso() + "]";
                } else {
                    grafo = grafo + "[" + componente.getIndiceVertice() + "|" + (int)componente.getPeso() + "]" + "-->";
                }

            }
            grafo = grafo + "]" + "\n";
        }
        return grafo;
    }
}
