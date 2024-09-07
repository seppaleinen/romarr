package se.romarr.tgdb;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TGDBGame(int id,
					   @JsonProperty("game_title") String gameTitle,
					   @JsonProperty("release_date") String releaseDate,
					   int platform,
					   @JsonProperty("region_id") int regionId,
					   @JsonProperty("country_id") int countryId,
					   String overview,
					   String youtube,
					   int players,
					   String coop,
					   String rating,
					   List<Integer> developers,
					   List<Integer> genre,
					   List<Integer> publisher,
					   List<String> alternates,
					   List<UID> uids,
					   List<String> hashes
) {
}
