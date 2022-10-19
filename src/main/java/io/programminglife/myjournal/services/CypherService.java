package io.programminglife.myjournal.services;

import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

@Service
public class CypherService {

    private static final String CIPHER = "AES";


    public Key getKeyFromKeyGenerator(int keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(CIPHER);
        keyGenerator.init(keySize);
        return keyGenerator.generateKey();
    }

}
