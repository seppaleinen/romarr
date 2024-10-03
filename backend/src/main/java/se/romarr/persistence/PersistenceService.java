package se.romarr.persistence;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import io.vertx.core.impl.ConcurrentHashSet;
import jakarta.enterprise.context.ApplicationScoped;
import se.romarr.domain.GameSystem;

@ApplicationScoped
public class PersistenceService {
	private static final Map<GameSystem.Type, Set<String>> games = new ConcurrentHashMap<>();

	public void addGame(GameSystem.Type system, Path file) {
		String fileName = file.getFileName().toString();
		System.out.println("Adding game: " + fileName + " to system: " + system);
		games.computeIfAbsent(system, a -> new ConcurrentHashSet<>()).add(fileName);

	}

	public Map<GameSystem.Type, Set<String>> getGames() {
		return games;
	}
}
