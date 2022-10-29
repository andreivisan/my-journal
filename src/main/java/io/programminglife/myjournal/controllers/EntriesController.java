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
import java.util.Optional;

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
    public ResponseEntity<List<Entry>> getEntries() {
        Optional<List<Entry>> entries = entriesService.getEntries();
        return entries.map(entryList -> ResponseEntity.ok().body(
                entryList
        )).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/")
    public ResponseEntity<Entry> getEntryById(@PathVariable int id) {
        Optional<Entry> entry = entriesService.getEntryById(id);
        return entry.map(value -> ResponseEntity.ok().body(
                value
        )).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}/")
    public ResponseEntity<List<Entry>> getEntriesByStatus(@PathVariable String status) {
        Optional<List<Entry>> entries = entriesService.getEntriesByStatus(status);
        return entries.map(entryList -> ResponseEntity.ok().body(
                entryList
        )).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<String> deleteEntry(@PathVariable Integer id) {
        entriesService.deleteEntryById(id);
        return ResponseEntity.ok().build();
    }

}
