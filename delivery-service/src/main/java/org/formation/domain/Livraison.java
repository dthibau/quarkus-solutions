package org.formation.domain;

import java.time.Instant;


import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.formation.web.views.LivraisonViews;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonView(LivraisonViews.Base.class)
public class Livraison {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	
	public String noCommande;
	
	@OneToOne
	public Livreur livreur;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	public Status status;
	
	public Instant creationDate;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livraison other = (Livraison) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	


}
