package se.romarr.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import se.romarr.domain.Game;
import se.romarr.domain.GameSystem;

@ApplicationScoped
public class PersistenceService {
	private static final Map<GameSystem.Type, Map<Game, List<String>>> games = new ConcurrentHashMap<>();

	public void addGame(GameSystem.Type system, String file) {
		System.out.println("Adding game: " + file + " to system: " + system);
		games.computeIfAbsent(system, a -> new ConcurrentHashMap<>())
				.computeIfAbsent(new Game(file, new ArrayList<>()), a -> new ArrayList<>())
				.add(file);

	}

	public Map<GameSystem.Type, List<String>> getGames() {
		return games.entrySet()
				.stream()
				.map(a -> Map.entry(a.getKey(), a.getValue().keySet().stream().map(Game::name).collect(Collectors.toList())))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
}
