package intercorpretail.digital.reto.irdigitalbackend.controller;

import javax.validation.Valid;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import intercorpretail.digital.reto.irdigitalbackend.dto.CreateClientWebDto;
import intercorpretail.digital.reto.irdigitalbackend.dto.GetIndicatorsWebDto;
import intercorpretail.digital.reto.irdigitalbackend.dto.ListClientWebDto;
import intercorpretail.digital.reto.irdigitalbackend.service.IClientService;

@RestController
public class ClientController {

	private IClientService clientService;
	
	public ClientController(IClientService clientService) {
		this.clientService = clientService;
	}
	
	@PostMapping(value = "/createClient")
	@RequestMapping(value = "/createClient", method = RequestMethod.POST, name = "Crear Cliente")
	public HttpEntity<CreateClientWebDto> createClient(@RequestBody @Valid CreateClientWebDto request) {
		var response = clientService.createClient(request);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/listclients")
	@RequestMapping(value = "/listclients", method = RequestMethod.GET, name = "Listar Cliente")
	public HttpEntity<ListClientWebDto> listClients(@RequestParam(required = false) String dni,
			@RequestParam(required = false) String email) {
		var response = clientService.listClients(dni, email);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/indicators")
	@RequestMapping(value = "/indicators", method = RequestMethod.GET, name = "Obtiene los indicadores")
	public HttpEntity<GetIndicatorsWebDto> getIndicators() {
		var response = clientService.getIndicators();
		return ResponseEntity.ok(response);
	}
}
