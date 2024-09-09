package se.romarr;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
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
class FolderResourceTest {
	private static final Path tempDir = Paths.get(Optional.ofNullable(Thread.currentThread()
							.getContextClassLoader()
							.getResource("."))
					.map(URL::getPath)
					.orElse(".")
			, "roms");

	WireMock wiremock;

	@BeforeEach
	public void setup() throws IOException {
		GameDump dump = new GameDump(200, "OK", "1", new Data(5,
				List.of(createGame(1, "game1"))));
		ObjectMapper mapper = new ObjectMapper();

		wiremock.register(WireMock.get("/json/database-latest.json")
				.willReturn(WireMock.aResponse()
						.withHeader("Content-Type", "application/json")
						.withJsonBody(mapper.convertValue(dump, JsonNode.class))));

		Files.createDirectory(tempDir);
		Files.createDirectory(tempDir.resolve("3ds"));
		Files.createFile(tempDir.resolve("3ds").resolve("game.3ds"));
		Files.createFile(tempDir.resolve("3ds").resolve("game.cia"));
		Files.createFile(tempDir.resolve("3ds").resolve("game2.cia"));
		Files.createDirectory(tempDir.resolve("psx"));
		Files.createFile(tempDir.resolve("psx").resolve("game3.iso"));
	}

	@AfterEach
	public void tearDown() throws IOException {
		FileUtils.deleteDirectory(tempDir.toFile());
	}

	@Test
	void testHelloEndpoint() {
		given()
				.when().get("/folder?folder=" + tempDir.toAbsolutePath())
				.then()
				.statusCode(200)
				.body(containsString("THREE_DS"))
				.body(containsString("game"))
				.body(containsString("game2"))
				.body(containsString("PSX"))
				.body(containsString("game3"));

	}

	private static TGDBGame createGame(int id, String gameTitle) {
		return new TGDBGame(id, gameTitle, null, 0, 0, 0, null, null, 0, null, null, null, null, null, null, null, null);
	}
}