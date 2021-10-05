package com.CS590.sample.util;

import com.CS590.sample.authentication.UserDetail;
import com.CS590.sample.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class UserUtil {
    public static UserDetail userToPrincipal(User user) {
        UserDetail userDetail = new UserDetail();
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName())).collect(Collectors.toList());

        userDetail.setUsername(user.getUserName());
        userDetail.setPassword(user.getPassword());
        userDetail.setAuthorities(authorities);
        return userDetail;
    }

    public static UserDetail create(User user) {
        return new UserDetail();
    }
}
