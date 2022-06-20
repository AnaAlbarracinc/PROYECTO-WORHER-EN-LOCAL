package com.worher.loginSpring.controllers;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.worher.loginSpring.models.Usuario;
import com.worher.loginSpring.payload.request.LoginRequest;
import com.worher.loginSpring.payload.request.SignupRequest;
import com.worher.loginSpring.payload.response.*;
import com.worher.loginSpring.repository.UsuarioRepository;
import com.worher.loginSpring.security.jwt.JwtUtils;
import com.worher.loginSpring.security.services.UserDetailsImpl;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtUtils jwtUtils;
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		if (!usuarioRepository.existsByEmail(loginRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Ese email no existe"));
		}
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		return ResponseEntity.ok(new JwtResponse(
				jwt, 
				userDetails.getId(), 
				userDetails.getEmail(),
				userDetails.getNombre(),
				userDetails.getApellido()));
	}
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (usuarioRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Ese email ya tiene una cuenta"));
		}
		// Create new user's account
		Usuario user = new Usuario(signUpRequest.getNombre(),
							 signUpRequest.getApellido(),
							 signUpRequest.getEmail(), 
							 encoder.encode(signUpRequest.getPassword()));
		
		usuarioRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("Usuario registrado correctamente!"));
	}
}
