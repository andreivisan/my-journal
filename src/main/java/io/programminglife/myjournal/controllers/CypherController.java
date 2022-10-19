package io.programminglife.myjournal.controllers;

import io.programminglife.myjournal.services.CypherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@RestController
@RequestMapping("/cypher/")
public class CypherController {

    @Autowired
    CypherService cypherService;


    @GetMapping("/unique-key/")
    public String getUniqueKey() {
        try {
            Key secretKey = cypherService.getKeyFromKeyGenerator(128);
            byte[] rawData = secretKey.getEncoded();
            String encodedKey = Base64.getEncoder().encodeToString(rawData);
            return encodedKey;
        } catch (NoSuchAlgorithmException e) {
            return "Failure";
        }
    }

}
