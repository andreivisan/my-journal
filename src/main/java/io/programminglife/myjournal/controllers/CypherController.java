package io.programminglife.myjournal.controllers;

import io.programminglife.myjournal.services.CypherService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/cypher")
public class CypherController {

    @Autowired
    CypherService cypherService;

    private static IvParameterSpec ivParameterSpec;

    @PostConstruct
    public void init() {
        ivParameterSpec = cypherService.generateIv();
    }

    @GetMapping("/unique-key/")
    public String getUniqueKey() {
        try {
            Key decodedKey = cypherService.getKeyFromKeyGenerator(128);
            return cypherService.getEncodedKey(decodedKey);
        } catch (NoSuchAlgorithmException e) {
            return "Failure";
        }
    }

    @PostMapping("/decrypt/{key}/")
    public String decryptedEntryBody(@PathVariable String key, @RequestBody String encryptedEntryBody) {
        Key decodedKey = cypherService.getDecodedKey(key);

        try {
            return cypherService.decrypt(encryptedEntryBody, decodedKey, ivParameterSpec);
        } catch (NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | NoSuchAlgorithmException e) {
            return "Error";
        }
    }

}
