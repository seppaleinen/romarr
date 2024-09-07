package se.romarr.folder;

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
import se.romarr.folder.visitors.PSXVisitor;
import se.romarr.folder.visitors.RootVisitor;
import se.romarr.folder.visitors.ThreeDSVisitor;
import se.romarr.persistence.PersistenceService;

@ApplicationScoped
public class FolderService {
	private PersistenceService persistenceService;
	private RootVisitor rootVisitor;

	public FolderService(PersistenceService persistenceService) {
		this.persistenceService = persistenceService;
		this.rootVisitor = new RootVisitor(
				List.of(
						new PSXVisitor(persistenceService),
						new ThreeDSVisitor(persistenceService)));
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
				.map(a -> new GameSystem(a.getKey(), a.getValue().stream().map(b -> new Game(b, new ArrayList<>())).toList()))
				.collect(Collectors.toSet());
	}
}
