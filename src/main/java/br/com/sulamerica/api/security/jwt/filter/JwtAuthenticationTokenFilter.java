package br.com.sulamerica.api.security.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.sulamerica.api.security.jwt.JwtTokenUtil;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailService;

	@Autowired
	private JwtTokenUtil jwtTokenUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authToken = request.getHeader("Authorization");
		String username = jwtTokenUtils.getUsernameByToken(authToken);

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailService.loadUserByUsername(username);
			if (jwtTokenUtils.validateToken(authToken, userDetails)) {
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());
				token.setDetails(new WebAuthenticationDetails(request));
				SecurityContextHolder.getContext().setAuthentication(token);
			}
		}
		filterChain.doFilter(request, response);
	}

}
