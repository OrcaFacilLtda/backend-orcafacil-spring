package com.orcafacil.api.infrastructure.security.config;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class RsaKeyLoader {

    public RSAPublicKey loadPublicKey(String filepath) throws Exception {
        if (filepath.startsWith("classpath:")) {
            filepath = filepath.replace("classpath:", "");
        }

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(filepath)) {
            if (is == null) {
                throw new IllegalArgumentException("Arquivo da chave pública não encontrado: " + filepath);
            }

            String key = new String(is.readAllBytes())
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded = Base64.getDecoder().decode(key);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) kf.generatePublic(spec);
        }
    }

    public RSAPrivateKey loadPrivateKey(String filepath) throws Exception {
        if (filepath.startsWith("classpath:")) {
            filepath = filepath.replace("classpath:", "");
        }

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(filepath)) {
            if (is == null) {
                throw new IllegalArgumentException("Arquivo da chave privada não encontrado: " + filepath);
            }

            String key = new String(is.readAllBytes())
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] decoded = Base64.getDecoder().decode(key);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) kf.generatePrivate(spec);
        }
    }

}
