package io.programminglife.myjournal.services;

import io.programminglife.myjournal.model.Tables;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.programminglife.myjournal.model.tables.pojos.Entry;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EntriesService {

    @Autowired
    private DSLContext dslContext;


    public void insertEntry(Entry entries) {
        dslContext.insertInto(Tables.ENTRY,
                Tables.ENTRY.CREATED_ON,
                Tables.ENTRY.BODY,
                Tables.ENTRY.STATUS)
                .values(
                        LocalDateTime.now(),
                        entries.getBody(),
                        entries.getStatus()
                )
                .execute();
    }

    public Optional<List<Entry>> getEntries() {
        return Optional.of(
                dslContext.selectFrom(Tables.ENTRY)
                .fetchInto(Entry.class));
    }

    public Optional<Entry> getEntryById(Integer id) {
        return Optional.ofNullable(
                dslContext.selectFrom(Tables.ENTRY)
                .where(Tables.ENTRY.ID.eq(id))
                .fetchOneInto(Entry.class));
    }

    public Optional<List<Entry>> getEntriesByStatus(String status) {
        return Optional.of(
                dslContext.selectFrom(Tables.ENTRY)
                        .where(Tables.ENTRY.STATUS.like(status))
                        .fetchInto(Entry.class));
    }

    public void deleteEntryById(Integer id) {
        dslContext.deleteFrom(Tables.ENTRY)
                .where(Tables.ENTRY.ID.eq(id))
                .execute();
    }

    public void updateEntryById(Integer id, String body) {
        dslContext.update(Tables.ENTRY)
                .set(Tables.ENTRY.BODY, body)
                .where(Tables.ENTRY.ID.eq(id))
                .execute();
    }

}
