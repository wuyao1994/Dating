package com.dating.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author elvis
 */
public class JwtUser implements UserDetails {
	private Integer id;

	private String username;

	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public JwtUser(User user) {
		this.id = user.getId();
		this.username = user.getEmail();
		this.password = user.getPassword();
		this.authorities = user.getRoles();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
