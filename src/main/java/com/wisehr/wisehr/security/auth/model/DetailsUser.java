package com.wisehr.wisehr.security.auth.model;

import com.wisehr.wisehr.security.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.wisehr.wisehr.security.auth.model.dto.MemberRoleDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class DetailsUser implements UserDetails {

    private User user;
    private List<MemberRoleDTO> roles;

    public void setRoles(List<MemberRoleDTO> roles) {
        this.roles = roles;
    }
    public List<MemberRoleDTO> getRoles() {
        return roles;
    }

    public DetailsUser() {
    }

    public DetailsUser(Optional<User> user) {

        this.user = user.get();
    }

    public DetailsUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoleList().forEach(role -> authorities.add(() -> role));
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getMemPassword();
    }

    @Override
    public String getUsername() {
        return user.getMemName();
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
