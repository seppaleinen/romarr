package se.romarr;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class FolderResourceTest {
	private static final Path tempDir = Paths.get(FolderResourceTest.class
			.getClassLoader()
			.getResource(".")
			.getPath(), "roms");

	@BeforeEach
	public void setup() throws IOException {
		Files.createDirectory(tempDir);
		Files.createDirectory(tempDir.resolve("3ds"));
		Files.createFile(tempDir.resolve("3ds").resolve("game.3ds"));
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
				.body(containsString("game.3ds"))
				.body(containsString("game2.cia"))
				.body(containsString("PSX"))
				.body(containsString("game3.iso"));
	}
}