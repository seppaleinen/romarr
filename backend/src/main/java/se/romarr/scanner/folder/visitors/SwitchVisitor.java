package se.romarr.scanner.folder.visitors;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import se.romarr.domain.GameSystem;
import se.romarr.persistence.PersistenceService;

@ApplicationScoped
public class SwitchVisitor extends CustomVisitor {
	private static final String ROOT_FOLDER = "switch";
	private static final List<String> ACCEPTED_FILES = List.of(
			".kp",
			".nca",
			".nro",
			".nso",
			".nsp",
			".xci"
	);

	private final PersistenceService persistenceService;

	public SwitchVisitor(PersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
		if (ACCEPTED_FILES.stream().anyMatch(file.getFileName().toString().toLowerCase()::endsWith)) {
			persistenceService.addGame(GameSystem.Type.SWITCH, file);
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public boolean isRelated(Path dir) {
		return dir.getFileName().toString().equalsIgnoreCase(ROOT_FOLDER);
	}
}
