package principales;

import modelo.dao.ClienteDao;
import modelo.dao.ClienteDaoImplMy8Jdbc;
import modelo.javabeans.Cliente;
import java.util.List;
import java.util.Scanner;

public class GestionClientes {

    private static Scanner leer = new Scanner(System.in);
    private static ClienteDao clienteDao = new ClienteDaoImplMy8Jdbc();

    public static void main(String[] args) {
        int opcion = 0;

        do {
            opcion = pintarMenu();
            switch (opcion) {
                case 1:
                    altaCliente();
                    break;
                case 2:
                    buscarCliente();
                    break;
                case 3:
                    mostrarTodos();
                    break;
                case 4:
                    eliminarCliente();
                    break;
                case 5:
                    exportarFichero();
                    break;
                case 6:
                    importarFichero();
                    break;
                case 7:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (opcion != 7);

        leer.close();
    }

    public static void altaCliente() {
        System.out.println("\n--- ALTA DE CLIENTE ---");
        System.out.print("CIF: ");
        String cif = leer.nextLine();
        System.out.print("Nombre: ");
        String nombre = leer.nextLine();
        System.out.print("Apellidos: "); 
        String apellidos = leer.nextLine();
        System.out.print("Domicilio: ");
        String domicilio = leer.nextLine();
        System.out.print("Facturación anual: ");
        double facturacionAnual = leer.nextDouble();
        System.out.print("Número de empleados: ");
        int numEmpleados = leer.nextInt();
        leer.nextLine(); 

        Cliente cliente = new Cliente(cif, nombre, apellidos, domicilio, facturacionAnual, numEmpleados);
        if (clienteDao.altaCliente(cliente) == 1) {
            System.out.println("Cliente dado de alta correctamente");
        } else {
            System.out.println("Error al dar de alta el cliente");
        }
    }

    public static void buscarCliente() {
        System.out.println("\n--- BUSCAR CLIENTE ---");
        System.out.print("Introduce el CIF: ");
        String cif = leer.nextLine();
        
        Cliente cliente = clienteDao.findById(cif);
        if (cliente != null) {
            System.out.println("\nDATOS DEL CLIENTE:");
            System.out.println(cliente);
        } else {
            System.out.println("No se encontró ningún cliente con ese CIF");
        }
    }

    public static void mostrarTodos() {
        System.out.println("\n--- LISTADO DE CLIENTES ---");
        List<Cliente> clientes = clienteDao.findAll();
        
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados");
        } else {
            clientes.forEach(System.out::println);
            System.out.println("\nTotal clientes: " + clientes.size());
        }
    }

    public static void eliminarCliente() {
        System.out.println("\n--- ELIMINAR CLIENTE ---");
        System.out.print("Introduce el CIF del cliente a eliminar: ");
        String cif = leer.nextLine();
        
        String resultado = clienteDao.deleteOne(cif);
        System.out.println(resultado);
    }

    public static void exportarFichero() {
        System.out.println("\n--- EXPORTAR CLIENTES A FICHERO ---");
        System.out.print("Introduce el directorio junto al nombre del archivo: ");
        String ruta = leer.nextLine();
        
        String resultado = clienteDao.exportar(ruta);
        System.out.println(resultado);
    }

    public static void importarFichero() {
        System.out.println("\n--- IMPORTAR CLIENTES DESDE FICHERO ---");
        System.out.print("Introduce el directorio junto al nombre del archivo: ");
        String ruta = leer.nextLine();
        
        List<Cliente> clientes = clienteDao.importar(ruta);
        if (clientes == null || clientes.isEmpty()) {
            System.out.println("No se importaron clientes o el fichero no es válido");
        } else {
            System.out.println("\nClientes importados (" + clientes.size() + "):");
            clientes.forEach(System.out::println);
        }
    }

    public static int pintarMenu() {
        System.out.println("\n==== MENU GESTION CLIENTES ====");
        System.out.println("1. Alta del Cliente");
        System.out.println("2. Buscar un Cliente");
        System.out.println("3. Mostrar Todos");
        System.out.println("4. Eliminar un cliente");
        System.out.println("5. Exportar a fichero");
        System.out.println("6. Importar desde fichero");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción (1-7): \n");
        
        int opcion;
        do {
            opcion = leer.nextInt();
            leer.nextLine(); 
            if (opcion < 1 || opcion > 7) {
                System.out.print("Opción no válida. Introduzca un número del 1 al 7: ");
            }
        } while (opcion < 1 || opcion > 7);
        
        return opcion;
    }
}