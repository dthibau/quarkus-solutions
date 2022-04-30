package org.formation.domain;

import org.formation.web.Views;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

	@JsonView(Views.Complet.class)
	public long id;
	
	@JsonView(Views.Complet.class)
	public int note;
	
	@JsonView(Views.Complet.class)
	public String commentaire;
}
