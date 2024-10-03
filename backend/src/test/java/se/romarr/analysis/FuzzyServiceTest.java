package se.romarr.analysis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.client.WireMock;

import io.quarkiverse.wiremock.devservice.ConnectWireMock;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import se.romarr.tgdb.Data;
import se.romarr.tgdb.GameDump;
import se.romarr.tgdb.TGDBDataDumpService;
import se.romarr.tgdb.TGDBGame;
import wiremock.com.fasterxml.jackson.databind.JsonNode;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;

@QuarkusTest
@ConnectWireMock
class FuzzyServiceTest {
	@Inject
	FuzzyService fuzzyService;
	@InjectMock
	@RestClient
	TGDBDataDumpService tgdbDataDumpService;

	WireMock wiremock;

	@BeforeEach
	public void setup() {
		GameDump dump = new GameDump(200, "OK", "1", new Data(5,
				List.of(createGame(1, "game1"), createGame(2, "metroid prime"))));
		ObjectMapper mapper = new ObjectMapper();

		wiremock.register(WireMock.get("/json/database-latest.json")
				.willReturn(WireMock.aResponse()
						.withHeader("Content-Type", "application/json")
						.withJsonBody(mapper.convertValue(dump, JsonNode.class))));
	}

	@Test
	void halo() {
		mockData(List.of(
				"Halo: Combat Evolved", "Halo: Combat Evolved - Classics (PAL)", "Gato", "Havoc", "halo 2", // Halo
				"Hell: A Cyberpunk Thriller", "Cyberpunk 2077", // Cyberpunk
				"Metroid Prime", "Metroid Prime 2: Echoes", "Metroid Prime 3: Corruption")
		);

		Optional<Set<String>> response = this.fuzzyService.fuzzyMatch("Halo");

		assertTrue(response.isPresent());
		assertEquals("Halo: Combat Evolved", response.get().stream().findFirst().orElseThrow());
	}

	@Disabled("Not working yet")
	@Test
	void metroidprime() {
		mockData(List.of(
				"Halo: Combat Evolved", "Halo: Combat Evolved - Classics (PAL)", "Gato", "Havoc", "halo 2", // Halo
				"Hell: A Cyberpunk Thriller", "Cyberpunk 2077", // Cyberpunk
				"Metroid Prime", "Metroid Prime 2: Echoes", "Metroid Prime 3: Corruption")
		);

		Optional<Set<String>> response = this.fuzzyService.fuzzyMatch("metroid prime remastered");

		assertTrue(response.isPresent(), "No match for metroid prime remastered");
		assertEquals("Halo: Combat Evolved", response.get().stream().findFirst().orElseThrow());
	}

	@Disabled("fuzzy matching is not correct here")
	@Test
	void cyberpunk() {
		Optional<Set<String>> response = this.fuzzyService.fuzzyMatch("cyberpunk");

		assertTrue(response.isPresent());
		assertEquals("Cyberpunk 2077", response.get().stream().findFirst().orElseThrow());
	}

	private void mockData(List<String> data) {
		List<TGDBGame> games = IntStream.range(0, data.size())
				.boxed()
				.map(i -> createGame(i, data.get(i)))
				.collect(Collectors.toList());

		when(tgdbDataDumpService.getDump()).thenReturn(new GameDump(200, null, null, new Data(0,
				games)));
	}

	private static TGDBGame createGame(int id, String gameTitle) {
		return new TGDBGame(id, gameTitle, null, 0, 0, 0, null, null, 0, null, null, null, null, null, null, null, null);
	}

}