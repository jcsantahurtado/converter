package co.com.conversor.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

import co.com.conversor.model.Operacion;
import co.com.conversor.model.OpcionConversor;
import co.com.conversor.controller.ConversorController;

public class Programa {

	public Programa() {

		presentarMenu();

	}

	private void presentarMenu() {

		Object[] conversores = { "Conversor de Moneda", "Conversor de Temperatura" };

		int respuesta = 0;

		do {

			Object selectedConversor = messageOptions(conversores, "Seleccione una opción de conversión", "Menú");

			try {

				double cantAConvertir = Double.parseDouble(messageInput());

				if (selectedConversor == "Conversor de Moneda") {

					hacerConversion(establecerOpcionesMoneda(), cantAConvertir, 1);

				} else {

					hacerConversion(establecerOpcionesTemperatura(), cantAConvertir, 2);

				}

			} catch (Exception e) {

				messageError();

			}

			respuesta = messageDecision();

		}

		while (respuesta == 0);

		messageInfo("Programa terminado");

	}

	private List<OpcionConversor> establecerOpcionesMoneda() {

		List<OpcionConversor> monedas = new ArrayList<>();

		monedas.add(new OpcionConversor("COP", "Peso Colombiano"));
		monedas.add(new OpcionConversor("USD", "Dólar"));
		monedas.add(new OpcionConversor("EUR", "Euros"));
		monedas.add(new OpcionConversor("GBP", "Libras Esterlinas"));
		monedas.add(new OpcionConversor("JPY", "Yen Japonés"));
		monedas.add(new OpcionConversor("KRW", "Won sur-coreano"));

		return monedas;

	}

	private List<OpcionConversor> establecerOpcionesTemperatura() {

		List<OpcionConversor> temperaturas = new ArrayList<>();

		temperaturas.add(new OpcionConversor("C", "Celsius"));
		temperaturas.add(new OpcionConversor("F", "Fahrenheit"));
		temperaturas.add(new OpcionConversor("K", "Kelvin"));

		return temperaturas;

	}

	private List<OpcionConversor> cargarConversores(List<OpcionConversor> opciones) {

		Operacion.setOpcionesEnGeneral(new ArrayList<>());

		for (OpcionConversor opcion : opciones) {

			List<Operacion> opcionesPorMoneda = new ArrayList<>();

			for (OpcionConversor otraOpcion : opciones) {

				boolean esIgual = (opcion == otraOpcion);
				if (!esIgual) {

					opcionesPorMoneda.add(new Operacion(opcion, otraOpcion));

				}
			}

			opcion.setListaConversiones(opcionesPorMoneda);

		}

		return opciones;

	}

	private void hacerConversion(List<OpcionConversor> establecerOpciones, double cantAConvertir, int idConversor) {

		List<OpcionConversor> opcionesConversor = cargarConversores(establecerOpciones);
		Optional<Operacion> conversion = null;
		Object[] opciones = Operacion.getOpcionesEnGeneral().toArray();
		Object selectedMonedaIntercambio = messageOptions(opciones, "Elije una opción", "Conversores");

		for (OpcionConversor opcionConversor : opcionesConversor) {

			conversion = opcionConversor.getListaConversiones().stream()
					.filter(conv -> conv.getDescripcion() == selectedMonedaIntercambio).findFirst();

			if (!conversion.isEmpty()) {

				break;

			}
		}

		ConversorController request = new ConversorController(idConversor, conversion.get(), cantAConvertir);

		String mensaje = String.format("%s \n\r\n\r %,.2f %s es igual a %,.2f %s",

				selectedMonedaIntercambio,

				cantAConvertir,

				conversion.get().getConvertirDe(),

				Double.parseDouble(request.getResultado()),

				conversion.get().getConvertirA());

		messageInfo(mensaje);
	}

	private Object messageOptions(Object[] options, Object message, String title) {

		return JOptionPane.showInputDialog(null,

				message, title,

				JOptionPane.PLAIN_MESSAGE, null,

				options, options[0]);
	}

	private String messageInput() {

		return JOptionPane

				.showInputDialog("Ingresa la cantidad que deseas convertir: ");

	}

	private void messageInfo(String mensaje) {

		JOptionPane.showMessageDialog(null, mensaje);

	}

	private void messageError() {

		JOptionPane.showMessageDialog(null, "Valor no valido", "Error", JOptionPane.ERROR_MESSAGE);

	}

	private int messageDecision() {

		return JOptionPane.showInternalConfirmDialog(null,

				"¿Desea continuar?", "information",

				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
	}
}
