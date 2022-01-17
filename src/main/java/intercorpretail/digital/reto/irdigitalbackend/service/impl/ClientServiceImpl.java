package intercorpretail.digital.reto.irdigitalbackend.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import intercorpretail.digital.reto.irdigitalbackend.domain.Client;
import intercorpretail.digital.reto.irdigitalbackend.domain.Months;
import intercorpretail.digital.reto.irdigitalbackend.dto.ClientWebDto;
import intercorpretail.digital.reto.irdigitalbackend.dto.CreateClientWebDto;
import intercorpretail.digital.reto.irdigitalbackend.dto.GetIndicatorsWebDto;
import intercorpretail.digital.reto.irdigitalbackend.dto.ListClientWebDto;
import intercorpretail.digital.reto.irdigitalbackend.dto.GetIndicatorsWebDto.BirthRate;
import intercorpretail.digital.reto.irdigitalbackend.repository.ClientRepository;
import intercorpretail.digital.reto.irdigitalbackend.service.IClientService;
import intercorpretail.digital.reto.irdigitalbackend.utils.Constants;

@Service
public class ClientServiceImpl implements IClientService {
	
	
	private ClientRepository clientRepository;
	
	public ClientServiceImpl(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public CreateClientWebDto createClient(CreateClientWebDto webRequest) {
		var clientWebRequest = webRequest.getClient();
		var requestDb = new Client();
		requestDb.setBirthDate(clientWebRequest.getBirthDate());
		requestDb.setCreationDate(new Date());
		requestDb.setDni(clientWebRequest.getDni());
		requestDb.setEmail(clientWebRequest.getEmail());
		requestDb.setSurnames(clientWebRequest.getSurnames());
		requestDb.setName(clientWebRequest.getName());
		
		clientRepository.insert(requestDb);
		
		var response = new CreateClientWebDto();
		response.setResultProcess(Constants.RESULT_OK);
		return response;
	}

	@Override
	public ListClientWebDto listClients(String dni, String email) {
		
		List<Client> dbResponse;
		
		if(!StringUtils.isEmpty(dni)) {
			dbResponse = clientRepository.findByDni(dni);

		} else if(!StringUtils.isEmpty(email)) {
			dbResponse = clientRepository.findByEmail(email.trim());
		} else {
			dbResponse = clientRepository.findAll();
		}

		var clientsWeb = dbResponse.parallelStream()
						 .map(this::mapDbClientToWebClient)
						 .collect(Collectors.toList());
		
		var response = new ListClientWebDto();
		response.setClientes(clientsWeb);
		
		return response;
	}

	@Override
	public GetIndicatorsWebDto getIndicators() {
		var allClients = clientRepository.findAll();
		var response = new GetIndicatorsWebDto();
		if (allClients.size() < 1) {
			return response;
		}
		var birthMonthYearMap = new HashMap<String, Integer>();
		var birthMonthMap = new HashMap<String, Integer>();
		
		for(var client : allClients) {
			var key = (new DateTime(client.getBirthDate()).getMonthOfYear()) + "/" +(new DateTime(client.getBirthDate()).getYear());
			var birthKey = new DateTime(client.getBirthDate()).toString("MM");
			if(birthMonthYearMap.containsKey(key)) {
				var value = birthMonthYearMap.get(key);
				birthMonthYearMap.put(key, value + 1);
			} else {
				birthMonthYearMap.put(key, 1);
			}
			
			if(birthMonthMap.containsKey(birthKey)) {
				var value = birthMonthMap.get(birthKey);
				birthMonthMap.put(birthKey, value + 1);
			} else {
				birthMonthMap.put(birthKey, 1);
			}
		}
		
		var maxKey = birthMonthYearMap.entrySet().stream().max((e1, e2)-> e1.getValue() > e2.getValue() ? 1 : -1).get().getKey();
		var minKey = birthMonthYearMap.entrySet().stream().min((e1, e2)-> e1.getValue() > e2.getValue() ? 1 : -1).get().getKey();
		
		var birthRateMap = getBirthMontRate(birthMonthMap, allClients.size());
		
		response.setBirthByMonth(birthMonthYearMap);
		response.setMaxBirthDate(maxKey);
		response.setMinBirthDate(minKey);
		response.setBirthRate(birthRateMap);
		return response;
	}
	
	private ClientWebDto mapDbClientToWebClient(Client dbClient) {
		var clientWebDto = new ClientWebDto();
		clientWebDto.setBirthDate(dbClient.getBirthDate());
		clientWebDto.setCreationDate(dbClient.getCreationDate());
		clientWebDto.setDni(dbClient.getDni());
		clientWebDto.setEmail(dbClient.getEmail());
		clientWebDto.setName(dbClient.getName());
		clientWebDto.setSurnames(dbClient.getSurnames());

		return clientWebDto;
	}
	
	private List<BirthRate> getBirthMontRate(Map<String, Integer> birthMonthMap, int totalClients) {
		var birthRates = new ArrayList<BirthRate>();
		for(var month : Months.values()) {
			var birthRate = new GetIndicatorsWebDto.BirthRate();
			if(birthMonthMap.containsKey(month.getNumber())) {				
				var quantity = birthMonthMap.get(month.getNumber());
				var rate = ((double) quantity / totalClients) * 1000;
				birthRate.setMonth(month.getName());
				birthRate.setRate(rate);
			} else {
				birthRate.setMonth(month.getName());
				birthRate.setRate(0.0);
			}
			birthRates.add(birthRate);
		}
		
		return birthRates;
	}
	
}
