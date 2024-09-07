package se.romarr.tgdb;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UID(String uid,
				  @JsonProperty("games_uids_patterns_id") String gamesUidsPatternsId) {
}
