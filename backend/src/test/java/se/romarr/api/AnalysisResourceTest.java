package se.romarr.api;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.client.WireMock;

import io.quarkiverse.wiremock.devservice.ConnectWireMock;
import io.quarkus.test.junit.QuarkusTest;
import se.romarr.tgdb.Data;
import se.romarr.tgdb.GameDump;
import se.romarr.tgdb.TGDBGame;
import wiremock.com.fasterxml.jackson.databind.JsonNode;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;

@QuarkusTest
@ConnectWireMock
class AnalysisResourceTest {
	WireMock wiremock;

	@BeforeEach
	void setup() {
		GameDump dump = new GameDump(200, "OK", "1", new Data(5,
				List.of(createGame(1, "game1"))));
		ObjectMapper mapper = new ObjectMapper();

		wiremock.register(WireMock.get("/json/database-latest.json")
				.willReturn(WireMock.aResponse()
						.withHeader("Content-Type", "application/json")
						.withJsonBody(mapper.convertValue(dump, JsonNode.class))));
	}

	@Test
	void testAnalysis() {
		given()
				.when().get("/analysis/test?game_name=halo")
				.then()
				.statusCode(200);

	}

	private static TGDBGame createGame(int id, String gameTitle) {
		return new TGDBGame(id, gameTitle, null, 0, 0, 0, null, null, 0, null, null, null, null, null, null, null, null);
	}
}