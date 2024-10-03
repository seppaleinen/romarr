package se.romarr.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public record GameSystem(Type type, List<Game> games) {
	public enum Type {
		NES("NES", "NINTENDO"),
		THREE_DS("3DS"),
		SNES("SNES", "SUPER NINTENDO"),
		PSX("PSX", "PLAYSTATION"),
		SWITCH("Switch"),
		PS3("PS3", "PLAYSTATION 3");

		private List<String> names;
		Type(String... names) {
			this.names = List.of(names);
		}

		public Optional<Type> match(String name) {
			return Arrays.stream(Type.values())
					.filter(a -> a.names.contains(name.toUpperCase()))
					.findFirst();
		}
	}
}
