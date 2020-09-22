package br.com.sulamerica.api.security.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.sulamerica.api.entity.User;
import br.com.sulamerica.api.security.jwt.JwtAuthenticationRequest;
import br.com.sulamerica.api.security.jwt.JwtTokenUtil;
import br.com.sulamerica.api.security.model.CurrentUser;
import br.com.sulamerica.api.service.UserService;


@RestController
@CrossOrigin(origins = "*")
public class AuthenticationRestController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	@PostMapping(value = "/api/auth")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)
			throws Exception {
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getCpf(),
						authenticationRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getCpf());
		final String token = jwtTokenUtil.generateToken(userDetails);
		final User user = userService.findByCpf(authenticationRequest.getCpf());
		user.setPassword(null);

		return ResponseEntity.ok(new CurrentUser(token, user));
	}

	@PostMapping(value = "/api/refresh")
	public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request) throws Exception {
		String token = request.getHeader("Authorization");
		String username = jwtTokenUtil.getUsernameByToken(token);

		final User user = userService.findByCpf(username);

		if (jwtTokenUtil.canTokenBeRefreshed(token)) {
			String refreshToken = jwtTokenUtil.refreshToken(token);

			return ResponseEntity.ok(new CurrentUser(refreshToken, user));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

}
