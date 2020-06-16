package com.example.shirodemo;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

public class MyRealm1 implements Realm {
    @Override
    public String getName() {
        return "myrealm1";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = String.valueOf((char[]) token.getCredentials());

        if ("zhang"!=username && !"zhang".equals(username)){
            throw new UnknownAccountException();
        }
        if ("123"!=password && !"123".equals(password)){
            throw new IncorrectCredentialsException();
        }

        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
