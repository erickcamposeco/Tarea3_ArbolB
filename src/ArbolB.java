public class ArbolB {
    private NodoB raiz;
    private int grado;

    public ArbolB(int grado) {
        this.raiz = null;
        this.grado = grado;
    }

    // Muestra el recorrido en orden del árbol
    public void recorrer() {
        if (raiz != null) raiz.recorrer();
    }

    // Busca una clave en el árbol
    public NodoB buscar(int clave) {
        return (raiz == null) ? null : raiz.buscar(clave);
    }

    // Inserta una clave en el árbol
    public void insertar(int clave) {
        if (raiz == null) {
            raiz = new NodoB(grado, true);
            raiz.claves[0] = clave;
            raiz.numClaves = 1;
        } else {
            if (raiz.numClaves == 2 * grado - 1) {
                NodoB nuevaRaiz = new NodoB(grado, false);
                nuevaRaiz.hijos[0] = raiz;
                nuevaRaiz.dividirHijo(0, raiz);
                int i = (nuevaRaiz.claves[0] < clave) ? 1 : 0;
                nuevaRaiz.hijos[i].insertarNoLleno(clave);
                raiz = nuevaRaiz;
            } else {
                raiz.insertarNoLleno(clave);
            }
        }
    }

    // Elimina una clave del árbol
    public void eliminar(int clave) {
        if (raiz == null) {
            System.out.println("El árbol está vacío");
            return;
        }
        raiz.eliminar(clave);
        if (raiz.numClaves == 0) {
            if (raiz.esHoja) {
                raiz = null;
            } else {
                raiz = raiz.hijos[0];
            }
        }
    }
}
