package org.formation.domain;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

	public String nom;
	
	public String telephone;

	@NotNull
	@Enumerated(EnumType.STRING)
	public StatusLivreur status;

	@OneToMany(cascade = CascadeType.ALL)
	public List<Review> reviews;
	
	public static List<Livreur> findActifLivreurs(){
        return list("status", StatusLivreur.ACTIF);
    }

	public static Livreur addReview(Long id, Review review) {
		Livreur livreur = (Livreur)Livreur.findByIdOptional(id).orElseThrow(() -> new IllegalArgumentException("Livreur not found"));
		livreur.reviews.add(review);
		return livreur;
	}

	public static Livreur resetReviews(Long id) {
		Livreur me = (Livreur)Livreur.findByIdOptional(id).orElseThrow(() -> new IllegalArgumentException("Livreur not found"));
		me.reviews.clear();
		return me;
	}        
}
