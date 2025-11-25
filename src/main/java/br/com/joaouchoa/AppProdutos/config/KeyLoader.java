package br.com.joaouchoa.AppProdutos.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

@Component
public class KeyLoader {

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @PostConstruct
    public void loadKeys() throws Exception {

        try (InputStream is = getClass().getResourceAsStream("/meuKeystore.jks")) {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(is, "senha123".toCharArray());

            String alias = "minhaChaveRSA";

            privateKey = (PrivateKey) keyStore.getKey(alias, "senha123".toCharArray());

            Certificate cert = keyStore.getCertificate(alias);
            publicKey = cert.getPublicKey();
        }
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
