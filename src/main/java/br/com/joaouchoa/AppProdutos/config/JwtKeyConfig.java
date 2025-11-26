package br.com.joaouchoa.AppProdutos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class JwtKeyConfig {

    @Bean
    public RSAPublicKey rsaPublicKey() throws Exception {
        try (InputStream is = getClass().getResourceAsStream("/meuKeystore.jks")) {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(is, "senha123".toCharArray());

            Certificate cert = keyStore.getCertificate("minhaChaveRSA");
            return (RSAPublicKey) cert.getPublicKey();
        }
    }

    @Bean
    public RSAPrivateKey rsaPrivateKey() throws Exception {
        try (InputStream is = getClass().getResourceAsStream("/meuKeystore.jks")) {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(is, "senha123".toCharArray());

            return (RSAPrivateKey) keyStore.getKey("minhaChaveRSA", "senha123".toCharArray());
        }
    }
}
