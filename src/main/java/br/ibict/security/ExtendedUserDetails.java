package br.ibict.security;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * This class implements UserDetails, extending Spring's User class with extra
 * fields that are used in authorization and question routing.
 */
public class ExtendedUserDetails extends User {

    private static final long serialVersionUID = 1L;

    public ExtendedUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public ExtendedUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

    }

    private String UF = "";
    private List<Long> legalEntitiesIDs = new LinkedList<>();
    private Long userID;

    public String getUF() {
        return UF;
    }

    public void setUF(String uF) {
        this.UF = uF;
    }

    public List<Long> getLegalEntitiesIDs() {
        return legalEntitiesIDs;
    }

    public void setLegalEntitiesIDs(List<Long> legalEntitiesIDs) {
        this.legalEntitiesIDs = legalEntitiesIDs;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

}