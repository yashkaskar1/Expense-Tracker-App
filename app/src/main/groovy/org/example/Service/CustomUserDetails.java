package org.example.Service;

import org.example.Entities.UserInfo;
import org.example.Entities.UserRoles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails extends UserInfo  implements UserDetails
{

    private String username;
    private String password;

    Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(UserInfo byUsername){
        this.username =byUsername.getUsername();
        this.password=byUsername.getPassword();
        List<GrantedAuthority> auths =new ArrayList<>();
        for (UserRoles role:byUsername.getRoles()){
            auths.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
        }
        this.authorities= auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {return authorities;}

        @Override
        public String getPassword(){
            return password;
        }

        @Override
        public String getUsername(){
            return username;
        }

        @Override
    public boolean isAccountNonLocked(){
        return  true;
    }

    public boolean isCredentialsNonExpired(){
        return true;
};


    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }


}
