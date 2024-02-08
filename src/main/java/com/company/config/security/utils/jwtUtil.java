package com.company.config.security.utils;


import com.company.auth.dtos.JwtResponse;
import com.company.expections.exp.UnAuthorizedException;
import com.company.user.enums.Role;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class jwtUtil {
    @Value("${jwt.expiry}")
    private static long expiry = 1000L * 3600 * 24 * 365 * 30;

    @Value("${jwt.secret}")
    private static String secret = "!mazgi_pazgi234^sad***-*-*+*-0293nd42839+nyf423gikeyodun298143ydrijom!mh9l8e]fh";

    public static String encode(UUID id, List<Role> roles) {

        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secret);

        jwtBuilder.claim("id", id);
        jwtBuilder.claim("roles", roles);

        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (expiry)));
        jwtBuilder.setIssuer("think store");
        return jwtBuilder.compact();
    }

    public static JwtResponse decode(String token) {
        try {
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(secret);

            Jws<Claims> jws = jwtParser.parseClaimsJws(token);
            Claims claims = jws.getBody();

            String id = (String) claims.get("id");
            List<Role> roles = (List<Role>) claims.get("roles");

            return new JwtResponse(UUID.fromString(id), roles,
                    encode(UUID.fromString(id), roles));

        } catch (JwtException e) {
            throw new UnAuthorizedException("Your session expired");
        }
    }
}