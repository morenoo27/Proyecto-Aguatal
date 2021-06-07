package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name = "usuario")
@NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codUsuario;

	private String apellidosUsuario;

	private String contraseña;

	private String dirUsuario;

	private String email;

	private String nombreUsuario;

	private String usuario;

	// bi-directional many-to-one association to Pedido
	@OneToMany(mappedBy = "usuario")
	private List<Pedido> pedidos;

	// bi-directional one-to-one association to Suscripcion
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "codUsuario", referencedColumnName = "codUsuario", insertable = false, updatable = false)
	private Suscripcion suscripcion;

	public Usuario() {
	}

	public int getCodUsuario() {
		return this.codUsuario;
	}

	public void setCodUsuario(int codUsuario) {
		this.codUsuario = codUsuario;
	}

	public String getApellidosUsuario() {
		return this.apellidosUsuario;
	}

	public void setApellidosUsuario(String apellidosUsuario) {
		this.apellidosUsuario = apellidosUsuario;
	}

	public String getContraseña() {
		return this.contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getDirUsuario() {
		return this.dirUsuario;
	}

	public void setDirUsuario(String dirUsuario) {
		this.dirUsuario = dirUsuario;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombreUsuario() {
		return this.nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public List<Pedido> getPedidos() {
		return this.pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Pedido addPedido(Pedido pedido) {
		getPedidos().add(pedido);
		pedido.setUsuario(this);

		return pedido;
	}

	public Pedido removePedido(Pedido pedido) {
		getPedidos().remove(pedido);
		pedido.setUsuario(null);

		return pedido;
	}

	public Suscripcion getSuscripcion() {
		return this.suscripcion;
	}

	public void setSuscripcion(Suscripcion suscripcion) {
		this.suscripcion = suscripcion;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();

		builder.append("Usuario [codUsuario=");
		builder.append(codUsuario);

		builder.append(", usuario=");
		builder.append(usuario);

		builder.append(", contraseña=");
		builder.append(contraseña);

		builder.append(", nombreUsuario=");
		builder.append(nombreUsuario);

		builder.append(", apellidosUsuario=");
		builder.append(apellidosUsuario);

		builder.append(", pedidos=");
		try {
			builder.append(pedidos());
		} catch (NullPointerException e) {
			builder.append(", ");
		}

		builder.append("dirUsuario=");
		builder.append(dirUsuario);

		builder.append(", email=");
		builder.append(email);

		builder.append(", suscripcion=");
		try {
			builder.append(suscripcion.getCodSuscripcion());
		} catch (NullPointerException e) {
			builder.append("");
		}

		builder.append("]");

		return builder.toString();
	}

	/**
	 * Metodo que devuelve todos los codigos de los pedidos que tiene un cliente
	 * 
	 * @return
	 */
	private String pedidos() {

		String text = "";

		if (!this.pedidos.isEmpty()) {

			for (Pedido pedido : pedidos) {

				text += pedido.getCodPedido() + ", ";

			}

			return text;
		}

		return " , ";
	}

}