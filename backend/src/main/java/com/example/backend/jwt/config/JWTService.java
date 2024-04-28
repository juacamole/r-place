package com.example.backend.jwt.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JWTService {

    private static int EXPIRATION_TIME_IN_DAYS = 3;
    private static int EXPIRATION_TIME = EXPIRATION_TIME_IN_DAYS * 1000 * 60  * 60 * 24;

    private static final String SECRET_KEY = "7482742fd6e7e26a33432b672e02d3492b20743bda0076e8bfe508e3ebeff0cac87804eed581c2bfc851bc482b27199d402aa6066227dc12bf3d00cd8f9cb649caa1afd8ee36cfeba12f337d03bd07695ff96fa99f818b0b8fb99c44ca7d9bbdd602bf820d9ce41be39c0f10b89eb1cdee1cc8a22376227e9713ecc0fc959ce71c601c28736a2f520b5e6255f39d12ddc58fc5a5102c2780d7d4ff2f718078fc3f75201704b0b8b7a6bba1955f47ee586c9f2daa4cf95607dfa67159a7e5a440803d3a7a86bf91fb70499d8f47fc259d5e7e8495ee87a5f26e0c9981b48362274ec054abf2ca90b1c58793bc2ba415c6f5ddd0c34af567f57df8bbdd5f9cb8bcbe3042128fd88312cfc55565919a99894861c91834d207fbbe01972ff8868eaafef74917ec46bbb0f33a1b6509df2c288614717db2465e2309384bff61abfd04558a56cbea1641f5ba3b2c7472c67ee0b9eca0611bae4e95ad2db553d223262cc12c00c7186a927147334c14b57485d85b8873c54e17ebe0f78ac30fdac824ba5e6df6e23ff7a1437649d26c08234bfe5ddf244d8c7afb1847b111a17baab9d96482fe25bc19901e25766424a704d30c738b6d4eab9d95b0a4370d4724609632a71292c80395a11dd2e0e3c059b058f8d54e25870ecb307ca3b4f1d02e1e0fc4da09340191377e2a62a04870fd82eac8319ba85f7640024e44611b0d3d820cf98e231f4a181b3c01c42e2584f040536afc0b3edc744e3401e19ac143d6d9de981bbe7c253aaf9adf086f6dc3ce8b82eaa74e444348e0ac447ea22d5e9c51b8191e8039174f3a638a0cf309dc19f6b8e3f1c4b6d9d3b81bbd26ea8fc98a95f49843f810cd9f28b880486c98231062de03728009e4699b622502456100b83b0e6f3b541e4ef549d31986c6c2f42732c39a6c07e3049d82e56be7bc3b8c1169977096dc1217840922f9873b8af9d6f23b1bbebbbe2d1cd85ef365a13f8b59bc115bf32158a248a201573c6e6d7ebad99a1749cd0217b3ae0f4398c0b40714dfc5657f179ca4bceb55822f6845757bb811141b3760c95562c16f8544174eb40b2cea0ee665340c97957a74db57bb4dc46e95eee31d7c0434bfed0f030460da81d0d14872d634936944a3859b6a0d31a8730524a9b8b02c29ad386ac7c456a4ba89923580c075f839575434fb7c117b81baed2214b018811a050db2add0f8037dbd1ce5feddfa7de093f9d604e4bcf2ed4536056c9d7c1191d653ddf4133db120bb02e30a2628f0f295b40413809eb99674cff884f6bb423a3a0883818fb6cad33e2b13f4c097e5af7d46a277c8ceaefbbaac0f74a494558bfe7a04d9a7ba7d33c8d5de84d0bd02d8518364f9cb0cabf9934eb6c4eb9fa161d400344941cd05002e7677d8096398779630a4742d4f28ace21f6d18cdc8a80b8659ea1c63fb87ac8697cc0d259eed9fcd68995c194895d778bc37d1365b9aa5329f9e444ca4a715adf5627dca4a3d89adb1bf4cb9633a267f2c2cc80cbcf1df643806575dd335ffeb719db88b606bff01083e72784978ff862429af62b148bcb93747f441f5f640a63104d29c6f1642a5a110a0101997241c1d42e91b0c09f00cf3db95363cef0f0674d2847ab2ffd51b6db9661144f6dcc3ec144f22f777278f540ba370af34158b810946a9a6c8a4b4d10d1d52ac53f6ba45f4a8a1d548430307922e50645ba3ca0d78c7e2a545875e440257d7ecaaac5e4eadbb7888242cce2f1ca85f0bc3f7fd87b7adb972d7852ef0dc63acb021e36492e5901bb9e97deca0b277eaa2cbd1be1345d2dd4b011572288fef537766c2822a6c7994d778efebf573158b4c9f0b895c89e365e6d0a72c0594e3e4860b483b888e871cec99cb5bd5667a3af19b701e8ed77c6ca7be1d94c9c85d799419ee7dc6a27c89d2b26c4492bea0bc27ba67c5a296aa473cfd2857c1bcc02f56f57f166e291dff0f7ec09d4def0f972ae036ba8205246673f2afe0f48d99ff625fb3befa7959922c8ea31b474a88b34c149e8271402d1bbd07cc7eb978d7d0156befa2b581c21f0d44debf510d063063fa31240ebfca56ab4d46a1af22959f96e2ae682ce8fe4208d6069bd7c850b03e1b8c25257c925d1d73750500ebef8d20bb5bb334ff2e2fc92f0af322b2f3884d949d2097b43186aaf7fe004e35121cead2b4d3ea781fb553ac890914dfbd2e54efddee2dfdee211772daf4e332d2b49ca2243c4de7e0d1aa58405a470803693ca911c87295a1dd054288f8bffd33433c6ea3cb33f96f9e23c39d8ec754aa6b24094936572b0f744841092520cd9452c5894ae7ec1b92fe3ad27800168aeccfb94c74c4faa0008d822a287f2b467b81cba8d7fa384288344ebdfc8bd56001bb14fe2b911f7d359948a29677d15d526fdd54c0d9231ef24fcfb9ea47169ce0f83ef5fdeee1ea3191a1c4eaf6535fb12a2cef64ec4e037bfa4517e9685443c374ebd7571ce11778fae4b18ea81c40ec8f04db74feb6d74f4039a6884422d2554c3af9c57e3467a5ab38b4e6d9d816873352f71bdc2296111fb06b915fc8b7399b22f2728e6fdc1897db76a70440a467de053cba29a086d5e739980109f5fa201326eb28983e25a155e52a403221369736b70113971da42c8029d85afd75c34e24f80ff370ffe43252a0d77e2f438385d887e781e3b03397a894288080478d280b513332cdebd49fa26f99bf4558a6ca556f3299243762d3734faec17dcda3218e0f7eb829a0c69c032824ebb471af45244e5896cd3a88ab727427487548bc99afbaf62f84f741f673dae0891af9501ddce735b038e7963bb7fb4fcbcf672bcaf2d8b53d1150546e8da4fcbebbdada7bef3e7cf00613e0a7b59b56bb124c48e6e9244bf58c5f";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Objects> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
