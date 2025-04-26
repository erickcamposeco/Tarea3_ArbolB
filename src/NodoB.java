public class NodoB {
    int[] claves;
    int grado;
    NodoB[] hijos;
    int numClaves;
    boolean esHoja;

    public NodoB(int grado, boolean esHoja) {
        this.grado = grado;
        this.esHoja = esHoja;
        this.claves = new int[2 * grado - 1];
        this.hijos = new NodoB[2 * grado];
        this.numClaves = 0;
    }

    // Recorre el subárbol e imprime las claves en orden
    public void recorrer() {
        int i;
        for (i = 0; i < numClaves; i++) {
            if (!esHoja) {
                hijos[i].recorrer();
            }
            System.out.print(claves[i] + " ");
        }
        if (!esHoja) {
            hijos[i].recorrer();
        }
    }

    // Busca una clave dentro del subárbol
    public NodoB buscar(int clave) {
        int i = 0;
        while (i < numClaves && clave > claves[i]) {
            i++;
        }
        if (i < numClaves && claves[i] == clave) {
            return this;
        }
        if (esHoja) return null;
        return hijos[i].buscar(clave);
    }

    // Inserta una clave en un nodo que no está lleno
    public void insertarNoLleno(int clave) {
        int i = numClaves - 1;
        if (esHoja) {
            while (i >= 0 && claves[i] > clave) {
                claves[i + 1] = claves[i];
                i--;
            }
            claves[i + 1] = clave;
            numClaves++;
        } else {
            while (i >= 0 && claves[i] > clave) {
                i--;
            }
            if (hijos[i + 1].numClaves == 2 * grado - 1) {
                dividirHijo(i + 1, hijos[i + 1]);
                if (claves[i + 1] < clave) {
                    i++;
                }
            }
            hijos[i + 1].insertarNoLleno(clave);
        }
    }

    // Divide un hijo cuando está lleno
    public void dividirHijo(int i, NodoB y) {
        NodoB z = new NodoB(y.grado, y.esHoja);
        z.numClaves = grado - 1;
        for (int j = 0; j < grado - 1; j++) {
            z.claves[j] = y.claves[j + grado];
        }
        if (!y.esHoja) {
            for (int j = 0; j < grado; j++) {
                z.hijos[j] = y.hijos[j + grado];
            }
        }
        y.numClaves = grado - 1;
        for (int j = numClaves; j >= i + 1; j--) {
            hijos[j + 1] = hijos[j];
        }
        hijos[i + 1] = z;
        for (int j = numClaves - 1; j >= i; j--) {
            claves[j + 1] = claves[j];
        }
        claves[i] = y.claves[grado - 1];
        numClaves++;
    }

    // Elimina una clave del subárbol
    public void eliminar(int clave) {
        int idx = encontrarClave(clave);
        if (idx < numClaves && claves[idx] == clave) {
            if (esHoja) eliminarDeHoja(idx);
            else eliminarDeNoHoja(idx);
        } else {
            if (esHoja) {
                System.out.println("La clave " + clave + " no existe en el árbol.");
                return;
            }
            boolean bandera = (idx == numClaves);
            if (hijos[idx].numClaves < grado) llenar(idx);
            if (bandera && idx > numClaves) hijos[idx - 1].eliminar(clave);
            else hijos[idx].eliminar(clave);
        }
    }

    private int encontrarClave(int clave) {
        int idx = 0;
        while (idx < numClaves && claves[idx] < clave) ++idx;
        return idx;
    }

    private void eliminarDeHoja(int idx) {
        for (int i = idx + 1; i < numClaves; ++i) {
            claves[i - 1] = claves[i];
        }
        numClaves--;
    }

    private void eliminarDeNoHoja(int idx) {
        int clave = claves[idx];
        if (hijos[idx].numClaves >= grado) {
            int pred = obtenerPredecesor(idx);
            claves[idx] = pred;
            hijos[idx].eliminar(pred);
        } else if (hijos[idx + 1].numClaves >= grado) {
            int succ = obtenerSucesor(idx);
            claves[idx] = succ;
            hijos[idx + 1].eliminar(succ);
        } else {
            unir(idx);
            hijos[idx].eliminar(clave);
        }
    }

    private int obtenerPredecesor(int idx) {
        NodoB actual = hijos[idx];
        while (!actual.esHoja) {
            actual = actual.hijos[actual.numClaves];
        }
        return actual.claves[actual.numClaves - 1];
    }

    private int obtenerSucesor(int idx) {
        NodoB actual = hijos[idx + 1];
        while (!actual.esHoja) {
            actual = actual.hijos[0];
        }
        return actual.claves[0];
    }

    private void llenar(int idx) {
        if (idx != 0 && hijos[idx - 1].numClaves >= grado) tomarPrestadoDeAnterior(idx);
        else if (idx != numClaves && hijos[idx + 1].numClaves >= grado) tomarPrestadoDeSiguiente(idx);
        else {
            if (idx != numClaves) unir(idx);
            else unir(idx - 1);
        }
    }

    private void tomarPrestadoDeAnterior(int idx) {
        NodoB hijo = hijos[idx];
        NodoB hermano = hijos[idx - 1];

        for (int i = hijo.numClaves - 1; i >= 0; --i)
            hijo.claves[i + 1] = hijo.claves[i];

        if (!hijo.esHoja) {
            for (int i = hijo.numClaves; i >= 0; --i)
                hijo.hijos[i + 1] = hijo.hijos[i];
        }

        hijo.claves[0] = claves[idx - 1];

        if (!hijo.esHoja)
            hijo.hijos[0] = hermano.hijos[hermano.numClaves];

        claves[idx - 1] = hermano.claves[hermano.numClaves - 1];

        hijo.numClaves += 1;
        hermano.numClaves -= 1;
    }

    private void tomarPrestadoDeSiguiente(int idx) {
        NodoB hijo = hijos[idx];
        NodoB hermano = hijos[idx + 1];

        hijo.claves[hijo.numClaves] = claves[idx];

        if (!hijo.esHoja)
            hijo.hijos[hijo.numClaves + 1] = hermano.hijos[0];

        claves[idx] = hermano.claves[0];

        for (int i = 1; i < hermano.numClaves; ++i)
            hermano.claves[i - 1] = hermano.claves[i];

        if (!hermano.esHoja) {
            for (int i = 1; i <= hermano.numClaves; ++i)
                hermano.hijos[i - 1] = hermano.hijos[i];
        }

        hijo.numClaves += 1;
        hermano.numClaves -= 1;
    }

    private void unir(int idx) {
        NodoB hijo = hijos[idx];
        NodoB hermano = hijos[idx + 1];

        hijo.claves[grado - 1] = claves[idx];

        for (int i = 0; i < hermano.numClaves; ++i)
            hijo.claves[i + grado] = hermano.claves[i];

        if (!hijo.esHoja) {
            for (int i = 0; i <= hermano.numClaves; ++i)
                hijo.hijos[i + grado] = hermano.hijos[i];
        }

        for (int i = idx + 1; i < numClaves; ++i)
            claves[i - 1] = claves[i];

        for (int i = idx + 2; i <= numClaves; ++i)
            hijos[i - 1] = hijos[i];

        hijo.numClaves += hermano.numClaves + 1;
        numClaves--;
    }
}
