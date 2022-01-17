package intercorpretail.digital.reto.irdigitalbackend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import intercorpretail.digital.reto.irdigitalbackend.domain.Client;
import intercorpretail.digital.reto.irdigitalbackend.dto.ClientWebDto;
import intercorpretail.digital.reto.irdigitalbackend.dto.CreateClientWebDto;
import intercorpretail.digital.reto.irdigitalbackend.dto.GetIndicatorsWebDto;
import intercorpretail.digital.reto.irdigitalbackend.dto.ListClientWebDto;
import intercorpretail.digital.reto.irdigitalbackend.repository.ClientRepository;
import intercorpretail.digital.reto.irdigitalbackend.service.impl.ClientServiceImpl;

@SpringBootTest
public class ClientControllerTest {

	public static ClientRepository repository;
	
	private static MockMvc mockMvc;
	
	ObjectMapper mapper = new ObjectMapper();
	
	@BeforeAll
	public static void init() {
		repository = Mockito.mock(ClientRepository.class);
		var service = new ClientServiceImpl(repository);
		var clientController = new ClientController(service);
		
		mockMvc = MockMvcBuilders.standaloneSetup(clientController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.build();
	}
	
	@Test
	public void createClientTest() throws Exception {
		var createClientWebDto  = new CreateClientWebDto();
		var clientWebDto = new ClientWebDto();
		
		String yourDateString = "21-01-1988";
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date birthDate = formatter.parse(yourDateString);
		clientWebDto.setBirthDate(birthDate);
		clientWebDto.setDni("48556620");
		clientWebDto.setEmail("ir.retail.test@gmail.com");
		clientWebDto.setName("Dev1");
		clientWebDto.setSurnames("backend");
		createClientWebDto.setClient(clientWebDto);
		
		when(repository.insert(any(Client.class))).thenReturn(new Client());
		when(repository.existsByDniOrEmail(clientWebDto.getDni(), clientWebDto.getEmail())).thenReturn(Boolean.FALSE);
		var requestBody = mapper.writeValueAsString(createClientWebDto);
		var mockrequest = post("/createClient")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(requestBody);
		
		var result = mockMvc.perform(mockrequest)
					.andExpect(status().isOk())
					.andReturn();
		
		var resultStr = result.getResponse().getContentAsString();
		var response = mapper.readValue(resultStr, CreateClientWebDto.class);
		Assertions.assertNotNull(response);
		Assertions.assertEquals("1", response.getResultProcess());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void listClients() throws Exception {
		var clientListStr = readJSON("responses/clientList.json");
		var clientList = (List<Client>) mapper.readValue(clientListStr, mapper.getTypeFactory().constructCollectionType(List.class, Client.class));
		when(repository.findAll()).thenReturn(clientList);
		
		var mockRequest = get("/listclients");
		var result = mockMvc.perform(mockRequest)
					.andExpect(status().isOk())
					.andReturn();
		
		var resultStr = result.getResponse().getContentAsString();
		var response = mapper.readValue(resultStr, ListClientWebDto.class);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(4, response.getClientes().size());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void getIndicators() throws Exception {
		var clientListStr = readJSON("responses/clientList.json");
		var clientList = (List<Client>) mapper.readValue(clientListStr, mapper.getTypeFactory().constructCollectionType(List.class, Client.class));
		when(repository.findAll()).thenReturn(clientList);
		
		var mockRequest = get("/indicators");
		var result = mockMvc.perform(mockRequest)
					.andExpect(status().isOk())
					.andReturn();
		
		var resultStr = result.getResponse().getContentAsString();
		var response = mapper.readValue(resultStr, GetIndicatorsWebDto.class);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals("10/1990", response.getMaxBirthDate());
		Assertions.assertEquals("10/1986", response.getMinBirthDate());
		Assertions.assertEquals(3, response.getBirthByMonth().size());
		Assertions.assertEquals(12, response.getBirthRate().size());
	}
	
	public String readJSON(String path) throws URISyntaxException, IOException {
        URL url = ClientControllerTest.class.getClassLoader().getResource(path);
        return new String(Files.readAllBytes(Paths.get(url.toURI())));
    }
}
