package se.romarr.folder.visitors;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import se.romarr.domain.GameSystem;
import se.romarr.persistence.PersistenceService;

public class ThreeDSVisitor extends CustomVisitor {
	private static final String ROOT_FOLDER = "3DS";
	private static final List<String> ACCEPTED_FILES = List.of(".3ds", ".cia");

	private PersistenceService persistenceService;

	public ThreeDSVisitor(PersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
		if(ACCEPTED_FILES.stream().anyMatch(file.getFileName().toString().toLowerCase()::endsWith)) {
			persistenceService.addGame(GameSystem.Type.THREE_DS, file.getFileName().toString());
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public boolean isRelated(Path dir) {
		return dir.getFileName().toString().equalsIgnoreCase(ROOT_FOLDER);
	}
}
