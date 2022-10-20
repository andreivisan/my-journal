package io.programminglife.myjournal.controllers;

import io.programminglife.myjournal.services.EntriesService;
import io.programminglife.myjournal.model.tables.pojos.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entries")
public class EntriesController {

    @Autowired
    EntriesService entriesService;


    @PostMapping
    public String addEntry(@RequestBody Entry entry) {
        entriesService.insertEntry(entry);
        return "entry saved";
    }

    @GetMapping
    public List<Entry> getEntries() {
        return entriesService.getEntries();
    }

}
