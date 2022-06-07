package co.com.conversor.model;

import java.util.ArrayList;
import java.util.List;

public class Operacion {

	private String convertirDe; // Example: USD
	private String convertirA; // Example: COP
	private String descripcion;
	private static List<Object> opcionesEnGeneral = new ArrayList<>();

	public Operacion(OpcionConversor moneda, OpcionConversor otraMoneda) {

		this.convertirDe = moneda.getCodigo();
		this.convertirA = otraMoneda.getCodigo();
		this.descripcion = "De " + moneda.getNombre() + " a " + otraMoneda.getNombre();
		Operacion.opcionesEnGeneral.add(this.descripcion);

	}

	public String getConvertirDe() {
		return convertirDe;
	}

	public String getConvertirA() {
		return convertirA;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public static List<Object> getOpcionesEnGeneral() {
		return opcionesEnGeneral;
	}

	public static void setOpcionesEnGeneral(List<Object> opcionesEnGeneral) {
		Operacion.opcionesEnGeneral = opcionesEnGeneral;
	}

}
