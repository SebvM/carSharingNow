package com.csn.carSharingNow.models;

/**
 * Rollen für die Authorit
 * 
 * @author Sebastian von Minden
 *
 */
public enum Role {
	
    USER("user"), ADMIN("admin");
    private String roleName;

    private Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

}