import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        System.out.print("Ingrese el grado del árbol B: ");
        int grado = entrada.nextInt();
        ArbolB arbol = new ArbolB(grado);

        int opcion;
        do {
            System.out.println("\n1. Insertar clave");
            System.out.println("2. Eliminar clave");
            System.out.println("3. Buscar clave");
            System.out.println("4. Salir");
            System.out.print("Opción: ");
            opcion = entrada.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese la clave a insertar: ");
                    int claveInsertar = entrada.nextInt();
                    arbol.insertar(claveInsertar);
                    System.out.println("Clave " + claveInsertar + " insertada correctamente.");
                    break;
                case 2:
                    System.out.print("Ingrese la clave a eliminar: ");
                    int claveEliminar = entrada.nextInt();
                    arbol.eliminar(claveEliminar);
                    System.out.println("Clave " + claveEliminar + " eliminada correctamente.");
                    break;
                case 3:
                    System.out.print("Ingrese la clave a buscar: ");
                    int claveBuscar = entrada.nextInt();
                    NodoB resultado = arbol.buscar(claveBuscar);
                    System.out.println((resultado != null) ? "Clave encontrada" : "Clave no encontrada");
                case 4:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 4);
    }
}
