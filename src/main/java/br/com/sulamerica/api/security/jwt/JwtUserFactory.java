package br.com.sulamerica.api.security.jwt;


import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.com.sulamerica.api.entity.Role;
import br.com.sulamerica.api.entity.User;


public class JwtUserFactory {

	private JwtUserFactory() {
	}

	public static JwtUser create(User user) {
		return new JwtUser(user.getId(), user.getCpf(), user.getPassword(),
				mapToGrantedAuthorities(user.getRole()));
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(Role profile) {
		List<GrantedAuthority>authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(profile.toString()));
		return authorities;
	}
}