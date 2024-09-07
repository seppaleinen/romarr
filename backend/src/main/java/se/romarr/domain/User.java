package se.romarr.domain;

import java.util.List;

public record User(String username, List<GameSystem> gameSystems) {
}
