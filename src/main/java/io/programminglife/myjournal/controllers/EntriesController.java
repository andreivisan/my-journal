package io.programminglife.myjournal.controllers;

import io.programminglife.myjournal.services.CypherService;
import io.programminglife.myjournal.services.EntriesService;
import io.programminglife.myjournal.model.tables.pojos.Entry;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/entries")
public class EntriesController {

    @Autowired
    EntriesService entriesService;

    @Autowired
    CypherService cypherService;

    private static IvParameterSpec ivParameterSpec;

    @PostConstruct
    public void init() {
        ivParameterSpec = cypherService.generateIv();
    }

    @PostMapping("/{key}/")
    public ResponseEntity<String> addEntry(@PathVariable String key, @RequestBody Entry entry) {
        Key decodedKey = cypherService.getDecodedKey(key);

        try {
            entry.setBody(cypherService.encrypt(entry.getBody(), decodedKey, ivParameterSpec));
            entriesService.insertEntry(entry);
        } catch (NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | NoSuchAlgorithmException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Entry> getEntries() {
        return entriesService.getEntries();
    }

}
