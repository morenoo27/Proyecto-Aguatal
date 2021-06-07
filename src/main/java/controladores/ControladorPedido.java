package controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entities.Dispensadora;
import entities.Pedido;
import entities.Usuario;

public class ControladorPedido {

	// Factoria para obtener objetos EntityManager
	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("aguatalGrafica");
	private EntityManager em;
	private Query consulta;

	public void deletePed(Pedido cli) {

		this.em = entityManagerFactory.createEntityManager();

		Pedido aux = null;

		this.em.getTransaction().begin();

		if (!this.em.contains(cli)) {
			aux = this.em.merge(cli);
		}

		this.em.remove(aux);

		this.em.getTransaction().commit();

		this.em.close();
	}

	public int deleteAll() {

		EntityManager em2 = entityManagerFactory.createEntityManager();

		int numFilas = 0;

		List<Pedido> registros = findAll();

		for (Pedido aux : registros) {
			deletePed(aux);
			numFilas++;
		}

		em2.getTransaction().begin();

		em2.createNativeQuery("alter table pedido auto_increment=1;").executeUpdate();

		em2.getTransaction().commit();

		em2.close();

		return numFilas;
	}

	public void modifyPed(Pedido user) {

		this.em = entityManagerFactory.createEntityManager();

		this.em.getTransaction().begin();

		this.em.merge(user);

		this.em.getTransaction().commit();

		this.em.close();
	}

	public void insertPed(Pedido pedido) {

		this.em = entityManagerFactory.createEntityManager();

		this.em.getTransaction().begin();

		this.em.persist(pedido);

		this.em.getTransaction().commit();

		this.em.close();
	}

	public int insertDiss(ArrayList<Pedido> suscripciones) {

		int numFilas = 0;
		for (Pedido aux : suscripciones) {

			insertPed(aux);
			numFilas++;
		}

		return numFilas;
	}

	public List<Pedido> findAll() {

		this.em = entityManagerFactory.createEntityManager();

		this.consulta = em.createNamedQuery("Pedido.findAll");

		@SuppressWarnings("unchecked")
		List<Pedido> listaTrabajadores = consulta.getResultList();

		this.em.close();

		return listaTrabajadores;
	}

	public Pedido findByPK(int pk) {

		try {
			this.em = entityManagerFactory.createEntityManager();

			Pedido aux = null;

			this.consulta = em.createNativeQuery("Select * from pedido where codPedido = ?", Pedido.class);

			this.consulta.setParameter(1, pk);// intercambiar primera ? por pk

			aux = (Pedido) consulta.getSingleResult();

			this.em.close();

			return aux;
		} catch (NoResultException ex) {
			System.out.println("No se encuentra el dato que se queire buscar");
			return null;
		}
	}

	public static void main(String[] args) {

		ControladorUsuario cu = new ControladorUsuario();
		ControladorPedido cp = new ControladorPedido();
		ControladorDispensadora cd = new ControladorDispensadora();

		Usuario u = new Usuario();
		u.setNombreUsuario("Alejandro");
		u.setApellidosUsuario("Moreno");
		u.setEmail("privado@gmail.com");
		u.setDirUsuario("mi casa");
		cu.insertUser(u);// Si está creado lanzará una excepcións
		
		
		
		Pedido p = new Pedido();
		p.setUsuario(cu.findByPK(1));
		
		cd.deleteAll();
		
		Dispensadora d1 = new Dispensadora();
		d1.setTamanio("mediana");
		d1.setPedido(null);
		d1.setSuscripcion(null);
		
		cd.insertDis(d1);
		
		Dispensadora dispensadora = cd.findAll().stream().collect(Collectors.toCollection(ArrayList::new)).get(0);
		
		p.setDispensadora(dispensadora);
		cp.insertPed(p);
		
		ArrayList<Pedido> pedidos = recogerDatosPedido();
		System.out.println("------------------------------------------------");
		System.out.println("Pedidos antes de cualquier operacion");
		pedidos.forEach(System.out::println);
		
		System.out.println("------------------------------------------------");
		System.out.println("Pedidos despues de la operacion");
		pedidos = recogerDatosPedido();

//		Almacenamos los datos ya existentes		
		ArrayList<Usuario> usuarios = recogerDatosUsuario();

		System.out.println("------------------------------------------------");
		System.out.println("Usuarios antes de cualquier operacion");
		usuarios.forEach(System.out::println);

//		Volvemos a captar todos los datos
		usuarios = recogerDatosUsuario();

		System.out.println("------------------------------------------------");
		System.out.println("Usuarios despues de la operacion");
		usuarios.forEach(System.out::println);

	}

	private static ArrayList<Pedido> recogerDatosPedido() {
		ControladorPedido cp = new ControladorPedido();
		
		return cp.findAll().stream().collect(Collectors.toCollection(ArrayList::new));
	}

	private static ArrayList<Usuario> recogerDatosUsuario() {

		ControladorUsuario cu = new ControladorUsuario();

		return cu.findAll().stream().collect(Collectors.toCollection(ArrayList::new)); // conversion por stream a un
																						// arraylist
	}
}
