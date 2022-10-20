package io.programminglife.myjournal.controllers;

import io.programminglife.myjournal.services.CypherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/cypher/")
public class CypherController {

    @Autowired
    CypherService cypherService;


    @GetMapping("/unique-key/")
    public String getUniqueKey() {
        try {
            Key decodedKey = cypherService.getKeyFromKeyGenerator(128);
            return cypherService.getEncodedKey(decodedKey);
        } catch (NoSuchAlgorithmException e) {
            return "Failure";
        }
    }

}
