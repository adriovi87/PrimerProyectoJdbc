package modelo.dao;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.javabeans.Cliente;

public class ClienteDaoImplMy8Jdbc extends AbsMy8Jdbc implements  ClienteDao{
	
	public ClienteDaoImplMy8Jdbc() {
		super();
	}

	@Override
	public int altaCliente(Cliente cliente) {
	    sql = "insert into clientes (cif, nombre, apellidos, domicilio, facturacion_anual, numero_empleados) VALUES (?, ?, ?, ?, ?, ?)";
	    filas=0;
	    try {
	        ps = conn.prepareStatement(sql);
	        ps.setString(1, cliente.getCif());
	        ps.setString(2, cliente.getNombre());
	        ps.setString(3, cliente.getApellidos());
	        ps.setString(4, cliente.getDomicilio());
	        ps.setDouble(5, cliente.getFacturacionAnual());
	        ps.setInt(6, cliente.getNumeroEmpleados());
	        filas = ps.executeUpdate();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return filas;
	    }
		return filas;
	}

	@Override
	public Cliente findById(String cif) {
	    sql = "select * from clientes where cif = ?";
	    Cliente cliente = null;
	    try {
	        ps = conn.prepareStatement(sql);
	        ps.setString(1, cif);
	        rs = ps.executeQuery();

	        if (rs.next()) {
	            cliente = new Cliente();
	            cliente.setCif(rs.getString("cif"));
	            cliente.setNombre(rs.getString("nombre"));
	            cliente.setApellidos(rs.getString("apellidos"));
	            cliente.setDomicilio(rs.getString("domicilio"));
	            cliente.setFacturacionAnual(rs.getDouble("facturacion_anual"));
	            cliente.setNumeroEmpleados(rs.getInt("numero_empleados"));
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return cliente;
	}

	@Override
	public List<Cliente> findAll() {
	    List<Cliente> lista = new ArrayList<>();
	    sql = "select * from clientes";
	    try {
	        ps = conn.prepareStatement(sql);
	        rs = ps.executeQuery();
	        
	        while (rs.next()) {
	            Cliente cliente = new Cliente();
	            cliente.setCif(rs.getString("cif"));
	            cliente.setNombre(rs.getString("nombre"));
	            cliente.setApellidos(rs.getString("apellidos"));
	            cliente.setDomicilio(rs.getString("domicilio"));
	            cliente.setFacturacionAnual(rs.getDouble("facturacion_anual"));
	            cliente.setNumeroEmpleados(rs.getInt("numero_empleados"));
	            
	            lista.add(cliente);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return lista;
	}

	@Override
	public String deleteOne(String cif) {
	    sql = "delete from clientes where cif=?";
	    filas = 0;
	    try {
	        ps = conn.prepareStatement(sql);
	        ps.setString(1, cif);
	        filas = ps.executeUpdate();
	        
	        if (filas == 1) {
	            return "Cliente eliminado correctamente"; 
	        } else {
	            return "No se encontró el cliente"; 
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return "Error al eliminar el cliente"; 
	    }
	}
	@Override
	public String exportar(String nombreFichero) {
	    File fichero = new File(nombreFichero);
	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichero))) {
	        
	        List<Cliente> clientes = findAll(); 
	        
	        for (Cliente cliente : clientes) {
	            oos.writeObject(cliente); 
	        }
	        
	        return "Clientes bien exportados";
	        
	    } catch (FileNotFoundException e) {
	        return "Fichero no existe";
	    } catch (IOException e) {
	        e.printStackTrace();
	        return "Error al exportar clientes: " + e.getMessage();
	    }
	}

	@Override
	public List<Cliente> importar(String nombreFichero) {
	    List<Cliente> clientesImportados = new ArrayList<>();
	    File fichero = new File(nombreFichero);
	    
	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero))) {
	        
	        while (true) {
	            try {
	                Cliente cliente = (Cliente) ois.readObject();
	                clientesImportados.add(cliente);
	            } catch (EOFException e) {
	                break; 
	            }
	        }
	        
	    } catch (FileNotFoundException e) {
	        System.err.println("Fichero no encontrado: " + nombreFichero);
	    } catch (IOException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    
	    return clientesImportados;
	}

}
