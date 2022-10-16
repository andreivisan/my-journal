package io.programminglife.myjournal.services;

import io.programminglife.myjournal.model.Tables;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.programminglife.myjournal.model.tables.pojos.Entry;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EntriesService {

    @Autowired
    private DSLContext dslContext;


    public void insertEntry(Entry entries) {
        dslContext.insertInto(Tables.ENTRY,
                Tables.ENTRY.ID,
                Tables.ENTRY.CREATED_ON,
                Tables.ENTRY.BODY,
                Tables.ENTRY.STATUS)
                .values(
                        entries.getId(),
                        LocalDateTime.now(),
                        entries.getBody(),
                        entries.getStatus()
                )
                .execute();
    }

    public List<Entry> getEntries() {
        return dslContext.selectFrom(Tables.ENTRY)
                .fetchInto(Entry.class);
    }

}
