package com.cibertec.api.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class RolMenuPK implements Serializable{
	private int idRol;

	private int idMenu;
	
	@Override
	public int hashCode() {
		return Objects.hash(idMenu, idRol);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RolMenuPK other = (RolMenuPK) obj;
		return idMenu == other.idMenu && idRol == other.idRol;
	}
	//
	public int getIdRol() {
		return idRol;
	}
	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}
	public int getIdMenu() {
		return idMenu;
	}
	public void setIdMenu(int idMenu) {
		this.idMenu = idMenu;
	}
	
}
