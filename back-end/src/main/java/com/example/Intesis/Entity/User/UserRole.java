package com.example.Intesis.Entity.User;

public enum UserRole {
    VENDEDOR("vendedor"),
    ADMINISTRADOR("administrador");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
