package se.romarr.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "FUZZY_TITLE")
public class FuzzyTitle extends PanacheEntityBase {
	@Id
	@Column(name = "title") public String title;

	public FuzzyTitle() {
	}

	public FuzzyTitle(String title) {
		this.title = title;
	}
}
