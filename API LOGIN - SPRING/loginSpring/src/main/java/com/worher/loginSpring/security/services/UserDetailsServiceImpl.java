package com.worher.loginSpring.security.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.worher.loginSpring.models.Usuario;
import com.worher.loginSpring.repository.UsuarioRepository;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UsuarioRepository usuarioRepository;
	@Transactional
	public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("No se ha encontrado ningún usuario con el email: " + email));
		return UserDetailsImpl.build(usuario);
	}
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("No se ha encontrado ningún usuario con el email: " + email));
		return UserDetailsImpl.build(usuario);
	}
}
