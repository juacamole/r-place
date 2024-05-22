package com.example.backend;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "userdata")
public class UserData implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "crazy_ahh_seq")
    @SequenceGenerator(name = "crazy_ahh_seq", sequenceName = "crazy_ahh_sequence", allocationSize = 1, initialValue = 2)
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String biography;
    private int placedPixels;
    private int cpx;
    private int cpy;
    private boolean cpd = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
