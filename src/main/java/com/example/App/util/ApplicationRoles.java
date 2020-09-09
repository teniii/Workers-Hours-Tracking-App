package com.example.App.util;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.App.util.ApplicationPermissions.*;

public enum ApplicationRoles {
//    COMPANY(Sets.newHashSet(ANGAJAT_READ, ANGAJAT_WRITE, COMPANY_READ, COMPANY_WRITE)),
//    ANGAJAT(Sets.newHashSet(ANGAJAT_READ, COMPANY_READ));
    COMPANY(Sets.newHashSet()),
    ANGAJAT(Sets.newHashSet());

    private final Set<ApplicationPermissions> permissionsSet;

    ApplicationRoles(Set<ApplicationPermissions> permissions) {
        this.permissionsSet = permissions;
    }

    public Set<ApplicationPermissions> getPermissionsSet() {
        return permissionsSet;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissionsSet().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
