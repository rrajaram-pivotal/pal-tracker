package io.pivotal.pal.tracker;



import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.*;


public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private long index =0;
    private Map<Long, TimeEntry> timeEntries = new HashMap<Long, TimeEntry>();

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        TimeEntry newTimeEntry;
        index++;
        if (timeEntry.getId() == 0)
            newTimeEntry = new TimeEntry(index, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
        else
            newTimeEntry = timeEntry;
        timeEntries.put(newTimeEntry.getId(), newTimeEntry);

        return newTimeEntry;
    }

    @Override
    public TimeEntry find(long timeEntryId) {

        return timeEntries.get(timeEntryId);
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<TimeEntry>(timeEntries.values());
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {

        timeEntries.replace(id,new TimeEntry(id,timeEntry.getProjectId(),timeEntry.getUserId(),timeEntry.getDate(),timeEntry.getHours()));
        return timeEntries.get(id);
    }

    @Override
    public void delete(long id) {
        timeEntries.remove(id);
    }
}
