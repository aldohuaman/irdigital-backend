package intercorpretail.digital.reto.irdigitalbackend.service;

import intercorpretail.digital.reto.irdigitalbackend.dto.CreateClientWebDto;
import intercorpretail.digital.reto.irdigitalbackend.dto.GetIndicatorsWebDto;
import intercorpretail.digital.reto.irdigitalbackend.dto.ListClientWebDto;

public interface IClientService {

	CreateClientWebDto createClient(CreateClientWebDto client);
	
	ListClientWebDto listClients(String dni, String email);
	
	GetIndicatorsWebDto getIndicators();
}
