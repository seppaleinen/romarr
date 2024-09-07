package se.romarr.persistence;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import se.romarr.domain.GameSystem;

@ApplicationScoped
public class PersistenceService {
	private static final Map<GameSystem.Type, Map<String, List<String>>> games = new ConcurrentHashMap<>();

	public void addGame(GameSystem.Type system, String gameName, Path file) {
		System.out.println("Adding game: " + gameName + " to system: " + system);
		games.computeIfAbsent(system, a -> new ConcurrentHashMap<>())
				.computeIfAbsent(gameName, a -> new ArrayList<>())
				.add(gameName);

	}

	public Map<GameSystem.Type, List<String>> getGames() {
		return games.entrySet()
				.stream()
				.map(a -> Map.entry(a.getKey(), new ArrayList<>(a.getValue().keySet())))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
}
