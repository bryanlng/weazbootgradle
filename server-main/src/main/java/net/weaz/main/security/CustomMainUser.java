package net.weaz.main.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class CustomMainUser extends User {

    private static final long serialVersionUID = -4165387689928836513L;
    
    private String favoriteCat;

    public CustomMainUser(String username, Collection<? extends GrantedAuthority> authorities, String favoriteCat) {
        super(username, EMPTY, authorities);
        this.favoriteCat = favoriteCat;
    }

    public String getFavoriteCat() {
        return favoriteCat;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }
        CustomMainUser that = (CustomMainUser) object;
        return Objects.equals(favoriteCat, that.favoriteCat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), favoriteCat);
    }
}