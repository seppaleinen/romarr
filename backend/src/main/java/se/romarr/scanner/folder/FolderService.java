package se.romarr.scanner.folder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import se.romarr.domain.Game;
import se.romarr.domain.GameSystem;
import se.romarr.scanner.folder.visitors.PSXVisitor;
import se.romarr.scanner.folder.visitors.RootVisitor;
import se.romarr.scanner.folder.visitors.SwitchVisitor;
import se.romarr.scanner.folder.visitors.ThreeDSVisitor;
import se.romarr.persistence.PersistenceService;

@ApplicationScoped
public class FolderService {
	private final PersistenceService persistenceService;
	private final RootVisitor rootVisitor;

	public FolderService(PersistenceService persistenceService, PSXVisitor psxVisitor, ThreeDSVisitor threeDSVisitor, SwitchVisitor switchVisitor) {
		this.persistenceService = persistenceService;
		this.rootVisitor = new RootVisitor(
				List.of(
						psxVisitor,
						threeDSVisitor,
						switchVisitor));
	}

	public Set<GameSystem> doStuff(String folder) {
		try {
			Files.walkFileTree(Paths.get(folder), rootVisitor);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return persistenceService.getGames()
				.entrySet()
				.stream()
				.map(a -> new GameSystem(a.getKey(), a.getValue().stream().map(Game::new).toList()))
				.collect(Collectors.toSet());
	}
}
