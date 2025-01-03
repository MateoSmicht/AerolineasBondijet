package bondiJet;

import java.util.Arrays;
import java.util.Objects;

public class Privado extends Vuelo {
	private int dniComprador;
	private int[] acompaniantes;
	private int tripulantes;
	private double precio;
	private int cantidadJets;

	public Privado(int dniComprador, int[] acompaniantes, int tripulantes, double precio,
			String identificacion, Aeropuerto aeropuertoSalida, Aeropuerto aeropuertoDestino, String fecha) {
		super(identificacion, aeropuertoSalida, aeropuertoDestino, fecha);
		if (this.identificacion == null) {
			throw new RuntimeException("Identificacion es null");
		}
		this.dniComprador = dniComprador;
		if (this.dniComprador == 0) {
			throw new RuntimeException("Sin dni comprador");
		}
		this.acompaniantes = acompaniantes;
		if (this.acompaniantes.length < 0) {
			throw new RuntimeException("Acompaniantes es negativo");
		}
		this.tripulantes = tripulantes;
		if (precio < 0.0) {
			throw new RuntimeException("Precio negativo");
		}
		this.precio = precio;
		
		this.cantidadJets = Privado.calcularJetsNecesarios(acompaniantes);
		if (this.cantidadJets < 0) {
			throw new RuntimeException("no hay jets");
		}
	}

	@Override
	protected String generarCodigoVuelo(String tamañoDeHashMapVuelos) {
		StringBuilder codigoVuelo = new StringBuilder();
		codigoVuelo.append(super.generarCodigoVuelo(tamañoDeHashMapVuelos)).append("-PRI");
		String resultado = codigoVuelo.toString();
		return resultado;
	}

	protected static int calcularJetsNecesarios(int[] acompaniantes) {
		int capacidad = 15;
		int cantidadAcompaniantes = (acompaniantes.length) + 1; // el +1 es para contar al comprador del pasaje
		int seNecesita = (int) Math.ceil((double) cantidadAcompaniantes / capacidad);
		return seNecesita;
	}

	@Override
	protected double totalRecaudado() {
		return precioVuelo(0);
	}

	@Override
	protected double precioVuelo(int seccionAsiento) {
		double total = (this.precio * this.cantidadJets) * 1.30;
		return total; // agregamos el %30 de impuertos
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(acompaniantes);
		result = prime * result + Objects.hash(cantidadJets, dniComprador, precio, tripulantes);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Privado other = (Privado) obj;
		return Arrays.equals(acompaniantes, other.acompaniantes) && cantidadJets == other.cantidadJets
				&& dniComprador == other.dniComprador
				&& Double.doubleToLongBits(precio) == Double.doubleToLongBits(other.precio)
				&& tripulantes == other.tripulantes;
	}

	public int getDniComprador() {
		return dniComprador;
	}

	public int[] getAcompaniantes() {
		return acompaniantes;
	}

	public int getTripulantes() {
		return tripulantes;
	}

	public int getCantidadJets() {
		return cantidadJets;
	}

	public double getPrecio() {
		return precio;
	}

	// genera detalles de vuelo
	@Override
	protected String generarDetalle() {
		StringBuilder detalle = new StringBuilder();
		detalle.append(super.generarDetalle()).append("PRIVADO (").append(this.cantidadJets).append(")");
		String resultado = detalle.toString();
		return resultado;
	}

	@Override
	public String verDatos() {
		return super.verDatos() + " [Precio]: " + this.precio + " [Cantidad jets]:" + this.cantidadJets + "}\n";
	}

	@Override
	public String toString() {
		return super.toString() + " Comprador: " + this.dniComprador + " Precio: " + this.precio + " Cantidad jets:"
				+ this.cantidadJets;
	}

}
