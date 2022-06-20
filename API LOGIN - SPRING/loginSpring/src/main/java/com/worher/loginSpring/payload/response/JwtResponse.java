package com.worher.loginSpring.payload.response;

import java.util.List;

public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private Integer id;
  private String email;
  private String nombre;
  private String apellido;

  public JwtResponse(String accessToken, Integer id, String email, String nombre, String apellido) {
    this.token = accessToken;
    this.id = id;
    this.email = email;
    this.nombre = nombre;
    this.apellido = apellido;
  }

  public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
	return apellido;
  }

  public void setApellido(String apellido) {
	 this.apellido = apellido;
  }
}