package org.formation.domain;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import org.formation.web.Views;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Livraison {

	@JsonView(Views.Base.class)
	public long id;
	
	@JsonView(Views.Base.class)
	public String noCommande;
	
	@JsonView(Views.Base.class)
	public Livreur livreur;
	
	@JsonView(Views.Base.class)
	@NotNull
	public Status status;
	
	@JsonView(Views.Complet.class)
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
