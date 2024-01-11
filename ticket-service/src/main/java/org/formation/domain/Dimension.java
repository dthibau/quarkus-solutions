package org.formation.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dimension {

	private float hauteur,longueur,largeur;

	@Override
	public String toString() {
	
		return largeur+" x "+longueur+" x "+hauteur;
	}
	
	
}
