package com.adnan.apparelstore.apparelstore.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.adnan.apparelstore.apparelstore.user.User;

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private GrantedAuthority authority;

    public CustomUserDetails(User user) {
        super();
        this.username = user.getEmail();
        this.password = user.getPassword();
        String role = user.getRole();

        this.authority = new SimpleGrantedAuthority(role);
    }

    @Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(authority);
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
