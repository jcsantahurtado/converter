package co.com.conversor.model;

import java.util.List;

public class OpcionConversor {

	private String codigo;
	private String nombre;

	private List<Operacion> listaConversiones;

	public OpcionConversor(String codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public List<Operacion> getListaConversiones() {
		return listaConversiones;
	}

	public void setListaConversiones(List<Operacion> listaConversiones) {
		this.listaConversiones = listaConversiones;
	}

}
