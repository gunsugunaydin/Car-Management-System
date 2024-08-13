package org.gunsugunaydin.SpringCarUser.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

import org.springframework.stereotype.Component;


@Component
final class KeyGenerator{

    private KeyGenerator() {}

    //key-pair'i generate edip döndüren bir method, bunun için javanın security.KeyPairGenerator'unu kullanıyor.
    static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }
}
