package com.spring.example.auth;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

@Slf4j
public class RsaKeyLoader {

    public static PrivateKey loadPrivateKey(String privateKeyPath) {
        try (InputStream is = RsaKeyLoader.class
                .getClassLoader()
                .getResourceAsStream(privateKeyPath)) {

            byte[] keyBytes = Objects.requireNonNull(is).readAllBytes();
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);

        } catch (Exception e) {
            log.error("Error loading private key from: {}", privateKeyPath, e);
            throw new RuntimeException(e);
        }
    }

    public static PublicKey loadPublicKey(String publicKeyPath) {
        try (InputStream is = RsaKeyLoader.class
                .getClassLoader()
                .getResourceAsStream(publicKeyPath)) {

            byte[] keyBytes = Objects.requireNonNull(is).readAllBytes();
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);

        } catch (Exception e) {
            log.error("Error loading public key from: {}", publicKeyPath, e);
            throw new RuntimeException(e);
        }
    }
}
