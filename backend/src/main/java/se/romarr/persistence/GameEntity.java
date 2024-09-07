package se.romarr.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "GAME")
public class GameEntity extends PanacheEntityBase {
	@Id
	public int id;
	@Column(name = "game_title") public String gameTitle;
	@Column(name = "release_date") public String releaseDate;
	public int platform;
	@Column(name = "region_id") public int regionId;
	@Column(name = "country_id") public int countryId;
	public String overview;
	public String youtube;
	public int players;
	public String coop;
	public String rating;
	//public List<Integer> developers;
	//public List<Integer> genre;
	//public List<Integer> publisher;
	//public List<String> alternates;
	//public List<UID> uids;
	//public List<String> hashes;

	public GameEntity() {
	}

	public GameEntity(int id, String gameTitle) {
		this.id = id;
		this.gameTitle = gameTitle;
	}

}
