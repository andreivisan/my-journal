package io.programminglife.myjournal.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.spec.IvParameterSpec;
import java.security.Key;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CypherServiceTest {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    @Autowired
    private CypherService cypherService;


    @Test
    public void test_encode_decode_key() throws Exception {
        Key generatedKey = cypherService.getKeyFromKeyGenerator(128);

        String encodedKey = cypherService.getEncodedKey(generatedKey);
        assertNotNull(encodedKey);

        Key decodedKey = cypherService.getDecodedKey(encodedKey);
        assertNotNull(decodedKey);

        assertEquals(generatedKey, decodedKey);
    }

    @Test
    public void test_encrypt_decrypt_text() throws Exception {
        Key generatedKey = cypherService.getKeyFromKeyGenerator(128);
        IvParameterSpec ivParameterSpec = cypherService.generateIv();
        String text = "test text";

        String encryptedText = cypherService.encrypt(text, generatedKey, ivParameterSpec);
        assertNotNull(encryptedText);

        String decryptedText = cypherService.decrypt(encryptedText, generatedKey, ivParameterSpec);
        assertNotNull(decryptedText);

        assertEquals(text, decryptedText);
    }

}
