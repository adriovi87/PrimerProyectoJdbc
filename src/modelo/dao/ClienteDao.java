package modelo.dao;

import java.io.File;
import java.util.List;

import modelo.javabeans.Cliente;

public interface ClienteDao {
	int altaCliente(Cliente cliente);
	Cliente findById(String cif);
	List<Cliente> findAll();
	String deleteOne(String cif);
	String exportar(String nombreFichero);
	List<Cliente> importar(String nombreFichero);
	
}
