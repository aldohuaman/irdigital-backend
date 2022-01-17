package intercorpretail.digital.reto.irdigitalbackend.domain;

import lombok.Getter;

@Getter
public enum Months {
	ENERO("01", "enero"), FEBRERO("02", "febrero"), MARZO("03", "marzo"), ABRIL("04", "abril"), MAYO("05", "mayo"), JUNIO("06", "junio"), JULIO("07", "julio"),
	AGOSTO("08", "agosto"), SEPTIEMBRE("09", "septiembre"), OCTUBRE("10", "octubre"), NOVIEMBRE("11", "noviembre"), DICIEMBRE("12", "diciembre");

	private String number;
	private String name;

	Months(String number, String name) {
		this.number = number;
		this.name = name;
	}

}
