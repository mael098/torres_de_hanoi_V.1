public class Nodo {
    private int valor;
    private Nodo siguiente;
    private Nodo anterior;
    private int posicion;

    public Nodo(int valor, int posicion) {
        this.valor = valor;
        this.posicion = posicion;
        this.siguiente = null;
        this.anterior = null;
    }

    public  int getValor() {
        return valor;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public Nodo getAnterior() {
        return anterior;
    }
    public int getPosicion() {
        return posicion;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    public void setAnterior(Nodo anterior) {
        this.anterior = anterior;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }


}
