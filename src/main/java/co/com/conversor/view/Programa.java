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

	private List<Convertidor> cargarConversores(List<Convertidor> monedas) {

		for (Convertidor moneda : monedas) {

			List<Conversion> opcionesPorMoneda = new ArrayList<>();
			for (Convertidor moneda2 : monedas) {

				boolean esIgual = (moneda == moneda2);
				if (!esIgual) {

					opcionesPorMoneda.add(new Conversion(moneda, moneda2));

				}
			}

			moneda.setListaConversiones(opcionesPorMoneda);

		}

		return monedas;

	}

	private void presentarMenu() {

		Object[] conversores = { "Conversor de Moneda", "Conversor de Temperatura" };

		int respuesta = 0;

		do {

			Object selectedConversor = messageOptions(conversores, "Seleccione una opción de conversión", "Menú");

			double cantAConvertir = .0;
			boolean esValido = false;

			try {
				cantAConvertir = Double.parseDouble(messageInput());
				esValido = true;
			} catch (Exception e) {
				messageError();
			}

			if (selectedConversor == "Conversor de Moneda" && esValido) {

				hacerConversion(establecerOpcionesMoneda(), cantAConvertir, 1);

			} else if (esValido) {

				hacerConversion(establecerOpcionesTemperatura(), cantAConvertir, 2);

			}

			respuesta = messageDecision();

		}

		while (respuesta == 0);

		messageInfo("Programa terminado");

	}

	private void hacerConversion(List<Convertidor> establecerOpcionesMoneda, double cantAConvertir, int idConversor) {

		List<Convertidor> monedas = cargarConversores(establecerOpcionesMoneda);
		Optional<Conversion> conversion = null;
		Object[] opciones = Conversion.getOpcionesEnGeneral().toArray();
		Object selectedMonedaIntercambio = messageOptions(opciones, "Elije una opción", "Conversores");

		for (Convertidor moneda : monedas) {
			conversion = moneda.getListaConversiones().stream()
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
