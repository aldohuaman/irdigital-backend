package intercorpretail.digital.reto.irdigitalbackend.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetIndicatorsWebDto implements Serializable {

	private static final long serialVersionUID = -1322378677432023592L;

	private Map<String, Integer> birthByMonth;
	private String maxBirthDate;
	private String minBirthDate;
	private List<BirthRate> birthRate;

	@Getter
	@Setter
	public static class BirthRate implements Serializable {
		
		private static final long serialVersionUID = -1466800014700454799L;
		
		public String month;
		public Double rate;
	}
}
