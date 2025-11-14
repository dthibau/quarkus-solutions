package org.formation.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.formation.web.LivraisonViews;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonView(LivraisonViews.Full.class)
public class Livreur extends PanacheEntity {


	public String nom;
	
	public String telephone;

	@NotNull
	@Enumerated(EnumType.STRING)
	public StatusLivreur status;

	@OneToMany(cascade = CascadeType.ALL)
	public List<Review> reviews;

	@JsonView(LivraisonViews.Base.class)
	public Long getId() {
		return id;
	}

    public static List<Livreur> findActifs() {
		StatusLivreur status = StatusLivreur.ACTIF;
		return list("status", status);
	}

	public void addReview(Review review) {
		if (reviews == null) {
			reviews = new java.util.ArrayList<>();
		}
		reviews.add(review);
		persist();
	}

	public void resetReviews() {
		reviews = new java.util.ArrayList<>();
		persist();
	}
}
