package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the pedido database table.
 * 
 */
@Entity
@Table(name="pedido")
@NamedQuery(name="Pedido.findAll", query="SELECT p FROM Pedido p")
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int codPedido;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="codUsuario", insertable=false, updatable=false)
	private Usuario usuario;

	//bi-directional many-to-one association to Dispensadora
	@OneToOne(mappedBy="pedido")
	private Dispensadora dispensadora;

	public Pedido() {
	}

	public int getCodPedido() {
		return this.codPedido;
	}

	public void setCodPedido(int codPedido) {
		this.codPedido = codPedido;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Dispensadora getDispensadoras() {
		return this.dispensadora;
	}
	
	public void setDispensadora(Dispensadora dispensadora) {
		this.dispensadora = dispensadora;
	}

	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		builder.append("Pedido [codPedido=");
		builder.append(codPedido);
		
		builder.append("dispensadora=");
		try {
			builder.append(dispensadora.getCodDispensadora() + ", ");
		} catch (NullPointerException e) {
			builder.append(", ");
		}
		
		builder.append("usuario=");
		builder.append(usuario.getCodUsuario());
		
		builder.append("]");
		return builder.toString();
	}
}