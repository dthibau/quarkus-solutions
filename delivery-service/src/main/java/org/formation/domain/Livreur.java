package org.formation.domain;

import java.util.List;

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

	public String nom;
	
	public String telephone;

	@NotNull
	@Enumerated(EnumType.STRING)
	public StatusLivreur status;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
}
