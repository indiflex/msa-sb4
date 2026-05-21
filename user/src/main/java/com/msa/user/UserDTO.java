package com.msa.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.msa.user.client.AccountDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@JsonIgnoreProperties({"authorities", "accountNonExpired", "claims", "accountNonLocked", "credentialsNonExpired", "enabled", "password"})
public class UserDTO extends User {
    private Long id;
    private String email;
    private String passwd;
    private String name;
    private List<String> roleNames;

    private AccountDTO account;

    public UserDTO(Long id, String email, String passwd, String name, List<String> roleNames) {
        super(name, passwd, roleNames.stream().map(SimpleGrantedAuthority::new).toList());
        this.id = id;
        this.email = email;
        this.passwd = passwd;
        this.name = name;
        this.roleNames = roleNames;
    }

    // to make JWT token (client에게 내려 줄 값)
    public Map<String, Object> getClaims() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("email", email);
        map.put("name", name);
        map.put("passwd", passwd);
        map.put("roleNames", roleNames);

        return map;
    }

}
