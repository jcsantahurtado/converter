package co.com.conversor.model;

import java.util.List;

public class Convertidor {

	private String codigo;
	private String nombre;

	private List<Conversion> listaConversiones;

	public Convertidor(String codigo, String nombre) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public List<Conversion> getListaConversiones() {
		return listaConversiones;
	}

	public void setListaConversiones(List<Conversion> listaConversiones) {
		this.listaConversiones = listaConversiones;
	}

}
