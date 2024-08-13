package org.gunsugunaydin.SpringCarUser.security;

import com.nimbusds.jose.jwk.RSAKey;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;


public class Jwks {
    private Jwks() {}

    //key-pair çiftini ayrıştıran method: public ve private key olmak üzere.
    //Sonra private ve public keyleri kullanarak bir RSA key oluşturuyor.
    public static RSAKey generateRsa() {
        KeyPair keyPair = KeyGenerator.generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }
}
