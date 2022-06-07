package co.com.conversor.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

import co.com.conversor.model.Conversion;
import co.com.conversor.model.Convertidor;
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

			double cantAConvertir = .0;

			try {

				cantAConvertir = Double.parseDouble(messageInput());

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

	private List<Convertidor> establecerOpcionesMoneda() {

		List<Convertidor> monedas = new ArrayList<Convertidor>();

		monedas.add(new Convertidor("COP", "Peso Colombiano"));
		monedas.add(new Convertidor("USD", "Dólar"));
		monedas.add(new Convertidor("EUR", "Euros"));
		monedas.add(new Convertidor("GBP", "Libras Esterlinas"));
		monedas.add(new Convertidor("JPY", "Yen Japonés"));
		monedas.add(new Convertidor("KRW", "Won sur-coreano"));

		return monedas;

	}

	private List<Convertidor> establecerOpcionesTemperatura() {

		List<Convertidor> temperaturas = new ArrayList<Convertidor>();

		temperaturas.add(new Convertidor("C", "Celsius"));
		temperaturas.add(new Convertidor("F", "Fahrenheit"));
		temperaturas.add(new Convertidor("K", "Kelvin"));

		return temperaturas;

	}

	private List<Convertidor> cargarConversores(List<Convertidor> opciones) {

		for (Convertidor opcion : opciones) {

			List<Conversion> opcionesPorMoneda = new ArrayList<>();

			for (Convertidor opcion2 : opciones) {

				boolean esIgual = (opcion == opcion2);
				if (!esIgual) {

					opcionesPorMoneda.add(new Conversion(opcion, opcion2));

				}
			}

			opcion.setListaConversiones(opcionesPorMoneda);

		}

		return opciones;

	}

	private void hacerConversion(List<Convertidor> establecerOpciones, double cantAConvertir, int idConversor) {

		List<Convertidor> opcionesConversor = cargarConversores(establecerOpciones);
		Optional<Conversion> conversion = null;
		Object[] opciones = Conversion.getOpcionesEnGeneral().toArray();
		Object selectedMonedaIntercambio = messageOptions(opciones, "Elije una opción", "Conversores");

		for (Convertidor opcionConversor : opcionesConversor) {

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
