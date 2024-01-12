package org.formation.domain;

import java.util.List;

import org.formation.web.LivraisonViews;

import com.fasterxml.jackson.annotation.JsonView;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Livreur extends PanacheEntity {

	@JsonView(LivraisonViews.Complet.class)
	public String nom;
	
	@JsonView(LivraisonViews.Complet.class)
	public String telephone;

	@NotNull
	@Enumerated(EnumType.STRING)
	@JsonView(LivraisonViews.Complet.class)
	public StatusLivreur status;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonView(LivraisonViews.Complet.class)
	public List<Review> reviews;
	
	public static List<Livreur> findActifLivreurs(){
        return list("status", StatusLivreur.ACTIF);
    }

	public void addReview(Review review) {
		Livreur me = Livreur.findById(id);
		me.reviews.add(review);
	}

	public Livreur resetReviews() {
		Livreur me = findById(id);
		me.reviews.clear();
		return me;

	}  
	@JsonView(LivraisonViews.Base.class)
	public Long getId() {
		return id;
	}
}
