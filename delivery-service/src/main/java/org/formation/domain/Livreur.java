package org.formation.domain;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
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
	
     public static List<Livreur> findActifLivreurs() {
		 return list("status", StatusLivreur.ACTIF);
	 }

	 public static void addReview(Long id, Review review) {
		 Livreur livreur = (Livreur)Livreur.findByIdOptional(id).orElseThrow(() -> new EntityNotFoundException("Livreur not found " +id));

		 livreur.getReviews().add(review);
	 }

	public static void resetReviews(Long id) {
		Livreur livreur = (Livreur)Livreur.findByIdOptional(id).orElseThrow(() -> new EntityNotFoundException("Livreur not found " +id));

		livreur.getReviews().clear();
	}
}
