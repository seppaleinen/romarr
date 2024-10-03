package se.romarr.folder.visitors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static se.romarr.Util.createDump;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.client.WireMock;

import io.quarkiverse.wiremock.devservice.ConnectWireMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import se.romarr.domain.GameSystem;
import se.romarr.persistence.PersistenceService;
import se.romarr.scanner.folder.visitors.SwitchVisitor;
import wiremock.com.fasterxml.jackson.databind.JsonNode;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;

@QuarkusTest
@ConnectWireMock
class SwitchVisitorTest {
	private static final ObjectMapper mapper = new ObjectMapper();

	private static final Path tempDir = Paths.get(Optional.ofNullable(Thread.currentThread()
							.getContextClassLoader()
							.getResource("."))
					.map(URL::getPath)
					.orElse(".")
			, "roms");

	WireMock wiremock;

	@Inject
	SwitchVisitor switchVisitor;
	@Inject
	PersistenceService persistenceService;

	@BeforeEach
	void setup() throws IOException {
		Files.createDirectory(tempDir);
		Files.createDirectory(tempDir.resolve("switch"));
		Files.createFile(tempDir.resolve("switch").resolve("game.xci"));
		Files.createFile(tempDir.resolve("switch").resolve("game2.nsp"));
		Files.createFile(tempDir.resolve("switch").resolve("game3.kp"));
		Files.createFile(tempDir.resolve("switch").resolve("game4.nca"));
		Files.createFile(tempDir.resolve("switch").resolve("game5.nro"));
		Files.createFile(tempDir.resolve("switch").resolve("game5.nso"));

		wiremock.register(WireMock.get("/json/database-latest.json")
				.willReturn(WireMock.aResponse()
						.withHeader("Content-Type", "application/json")
						.withJsonBody(mapper.convertValue(createDump(), JsonNode.class))));
	}

	@AfterEach
	public void tearDown() throws IOException {
		FileUtils.deleteDirectory(tempDir.toFile());
	}

	@Test
	void asd() throws IOException {
		Files.walkFileTree(tempDir, switchVisitor);

		Map<GameSystem.Type, Set<String>> entries = persistenceService.getGames();

		assertFalse(entries.isEmpty());
		assertEquals(1, entries.size());
		assertEquals(entries.get(GameSystem.Type.SWITCH).size(), 6);
	}
}