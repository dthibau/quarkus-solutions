package org.formation.domain;

import java.util.List;

import org.formation.web.Views;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Livreur {

	@JsonView(Views.Base.class)
	public long id;
	
	@JsonView(Views.Complet.class)
	public String nom;
	
	@JsonView(Views.Complet.class)
	public String telephone;

	@JsonView(Views.Complet.class)
	public List<Review> reviews;
	
}
