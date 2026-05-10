import java.util.ArrayList;
import java.util.List;

public class Torre {

    private Nodo tope;

    public void agregarDisco(Nodo nuevo) {
        nuevo.setSiguiente(tope);

        if (tope != null) {
            tope.setAnterior(nuevo);
        }

        tope = nuevo;
    }

    public Nodo quitarDisco() {

        if (tope == null) {
            return null;
        }

        Nodo aux = new Nodo(tope.getValor(), tope.getPosicion());

        tope = tope.getSiguiente();

        if (tope != null) {
            tope.setAnterior(null);
        }

        return aux;
    }

    public Nodo getTope() {
        return tope;
    }

    public Torre clonar() {

        Torre nueva = new Torre();

        List<Nodo> nodos = new ArrayList<>();

        Nodo actual = this.tope;

        while (actual != null) {

            nodos.add(
                new Nodo(
                    actual.getValor(),
                    actual.getPosicion()
                )
            );

            actual = actual.getSiguiente();
        }

        for (int i = nodos.size() - 1; i >= 0; i--) {
            nueva.agregarDisco(nodos.get(i));
        }

        return nueva;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        Nodo actual = tope;

        while (actual != null) {
            sb.append(actual.getValor()).append("-");
            actual = actual.getSiguiente();
        }

        return sb.toString();
    }
}