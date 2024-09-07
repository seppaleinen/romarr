package se.romarr.folder.visitors;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import se.romarr.domain.Game;
import se.romarr.domain.GameSystem;
import se.romarr.persistence.PersistenceService;

public class PSXVisitor extends CustomVisitor {
	private static final String ROOT_FOLDER = "psx";
	private static final List<String> ACCEPTED_FILES = List.of(".cue", ".bin", ".m3u", ".iso");

	private PersistenceService persistenceService;

	public PSXVisitor(PersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
		if(ACCEPTED_FILES.stream().anyMatch(file.getFileName().toString().toLowerCase()::endsWith)) {
			persistenceService.addGame(GameSystem.Type.PSX, file.getFileName().toString());
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public boolean isRelated(Path dir) {
		return dir.getFileName().toString().equalsIgnoreCase(ROOT_FOLDER);
	}
}
