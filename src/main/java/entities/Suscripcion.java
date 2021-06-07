package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the suscripcion database table.
 * 
 */
@Entity
@Table(name = "suscripcion")
@NamedQuery(name = "Suscripcion.findAll", query = "SELECT s FROM Suscripcion s")
public class Suscripcion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codSuscripcion;

	private double precioMensual;

	// bi-directional one-to-one association to Usuario
	@OneToOne(mappedBy = "suscripcion", fetch = FetchType.LAZY)
	private Usuario usuario;

	// bi-directional many-to-one association to Dispensadora
	@OneToMany(mappedBy = "suscripcion")
	private List<Dispensadora> dispensadoras;

	public Suscripcion() {
	}

	public int getCodSuscripcion() {
		return this.codSuscripcion;
	}

	public void setCodSuscripcion(int codSuscripcion) {
		this.codSuscripcion = codSuscripcion;
	}

	public double getPrecioMensual() {
		return this.precioMensual;
	}

	public void setPrecioMensual(double precioMensual) {
		this.precioMensual = precioMensual;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Dispensadora> getDispensadoras() {
		return this.dispensadoras;
	}

	public void setDispensadoras(List<Dispensadora> dispensadoras) {
		this.dispensadoras = dispensadoras;
	}

	public Dispensadora addDispensadora(Dispensadora dispensadora) {
		getDispensadoras().add(dispensadora);
		dispensadora.setSuscripcion(this);

		return dispensadora;
	}

	public Dispensadora removeDispensadora(Dispensadora dispensadora) {
		getDispensadoras().remove(dispensadora);
		dispensadora.setSuscripcion(null);

		return dispensadora;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("Suscripcion [codSuscripcion=");
		builder.append(codSuscripcion);
		
		builder.append(", dispensadoras=");
		try {
			builder.append(dispensadoras());
		} catch (NullPointerException e) {
			builder.append(", ");
		}
		
		builder.append("precioMensual=");
		builder.append(precioMensual);
		
		builder.append(", usuario=");
		try {
			builder.append(usuario.getCodUsuario());
		} catch (NullPointerException e) {
			builder.append("");
		}
		
		builder.append("]");
		return builder.toString();
	}

	private String dispensadoras() {

		String texto = "";

		if (!dispensadoras.isEmpty()) {

			for (Dispensadora dispensadora : dispensadoras) {

				texto += dispensadora.getCodDispensadora() + ", ";
			}

			return texto;
		}

		return null;
	}

}