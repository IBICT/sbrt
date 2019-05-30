package br.ibict.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.ibict.security.ExtendedUserDetails;
import io.github.jhipster.config.JHipsterProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenProvider {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private static final String USER_ID_KEY = "USER_ID_KEY";

    private static final String USER_UF_KEY = "USER_UF_KEY";

    private static final String USER_ENTITIES_KEY = "USER_ENTITIES_KEY";

    private Key key;

    private long tokenValidityInMilliseconds;

    private long tokenValidityInMillisecondsForRememberMe;

    private final JHipsterProperties jHipsterProperties;

    public TokenProvider(JHipsterProperties jHipsterProperties) {
        this.jHipsterProperties = jHipsterProperties;
    }

    @PostConstruct
    public void init() {
        byte[] keyBytes;
        String secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();
        if (!StringUtils.isEmpty(secret)) {
            log.warn("Warning: the JWT key used is not Base64-encoded. " +
                "We recommend using the `jhipster.security.authentication.jwt.base64-secret` key for optimum security.");
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        } else {
            log.debug("Using a Base64-encoded JWT secret key");
            keyBytes = Decoders.BASE64.decode(jHipsterProperties.getSecurity().getAuthentication().getJwt().getBase64Secret());
        }
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.tokenValidityInMilliseconds =
            1000 * jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
        this.tokenValidityInMillisecondsForRememberMe =
            1000 * jHipsterProperties.getSecurity().getAuthentication().getJwt()
                .getTokenValidityInSecondsForRememberMe();
    }

    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }

        ExtendedUserDetails principal = (ExtendedUserDetails) authentication.getPrincipal();

        JwtBuilder jwtBuilder = Jwts.builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .claim(USER_ID_KEY, principal.getUserID());

        if (principal.getUF() != ""){
            jwtBuilder = jwtBuilder.claim(USER_UF_KEY, principal.getUF());
        }

        String entities = principal.getLegalEntitiesIDs().stream()
            .map(l -> l.toString())
            .collect(Collectors.joining(","));

        if (entities != null && !entities.isEmpty()){
            jwtBuilder = jwtBuilder.claim(USER_ENTITIES_KEY, entities);
        }
        
        return jwtBuilder
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .compact();
    }

    /**
     * Get an Authentication object from the JWT token. 
     * Gets ID, UF and legalEntities of the user from the token claims
     * @param token
     * @return Spring's authentication object with {@ExtendedUserDetails} as a principal 
     */
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(key)
            .parseClaimsJws(token)
            .getBody();

        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        ExtendedUserDetails principal = new ExtendedUserDetails(claims.getSubject(), "", authorities);

        Optional<Long> userIDopt = Optional.ofNullable((null == claims.get(USER_ID_KEY)) ? null : new Long((Integer) claims.get(USER_ID_KEY)));
        Optional<String> ufOpt = Optional.ofNullable((String) claims.get(USER_UF_KEY));

        Optional<String> entitiesOpt = Optional.ofNullable((String) claims.get(USER_ENTITIES_KEY));
        if(entitiesOpt.isPresent() && !entitiesOpt.get().isEmpty()) {
            List<Long> entities =
                Arrays.stream(entitiesOpt.get().split(","))
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
            principal.setLegalEntitiesIDs(entities);
        } else {
            principal.setLegalEntitiesIDs(new LinkedList<Long>());
        }

        userIDopt.ifPresent(id -> principal.setUserID(id));
        ufOpt.ifPresent(uf -> principal.setUF(uf));

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }
}
