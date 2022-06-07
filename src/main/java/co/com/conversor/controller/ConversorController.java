package co.com.conversor.controller;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import co.com.conversor.model.Operacion;

public class ConversorController {

	int id;
	Operacion conversion;
	double cantAConvertir;
	double resultado;

	public ConversorController(int id, Operacion conversion, double cantAConvertir) {
		this.id = id;
		this.conversion = conversion;
		this.cantAConvertir = cantAConvertir;
	}

	public void lanzarConversorMoneda() {

		try {

			// Setting URL
			String url_str = "https://api.exchangerate.host/convert?from=" + this.conversion.getConvertirDe() + "&to="
					+ this.conversion.getConvertirA() + "&amount=" + this.cantAConvertir + "&places=2";

			// Making Request
			URL url = new URL(url_str);
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			request.connect();

			// Convert to JSON
			JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent()));
			JsonObject jsonobj = root.getAsJsonObject();

			this.resultado = jsonobj.get("result").getAsDouble();

			request.disconnect();

		} catch (Exception e) {

		}

	}

	public void lanzarConversorTemperatura() {

		switch (this.conversion.getDescripcion()) {

		case "De Kelvin a Celsius":
			this.resultado = this.cantAConvertir - 273.15;
			break;

		case "De Kelvin a Fahrenheit":
			this.resultado = 9 * (this.cantAConvertir - 273.15) / 5 + 32;
			break;

		case "De Fahrenheit a Celsius":
			this.resultado = 5 * (this.cantAConvertir - 32) / 9;
			break;

		case "De Fahrenheit a Kelvin":
			this.resultado = 5 * (this.cantAConvertir - 32) / 9 + 273.15;
			break;

		case "De Celsius a Kelvin":
			this.resultado = this.cantAConvertir + 273.15;
			break;

		case "De Celsius a Fahrenheit":
			this.resultado = 9 * this.cantAConvertir / 5 + 32;
			break;

		}

	}

	public String getResultado() {

		if (this.id == 1) {

			lanzarConversorMoneda();

		} else {

			lanzarConversorTemperatura();

		}

		return String.valueOf(this.resultado);
	}

}
