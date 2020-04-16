package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;


    public TimeEntryController(TimeEntryRepository timeEntryRepository,  MeterRegistry meterRegistry) {
        this.timeEntryRepository = timeEntryRepository;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");

    }

    @PostMapping("")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry)
    {
        timeEntry = timeEntryRepository.create(timeEntry);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        return ResponseEntity.status(HttpStatus.CREATED).body(timeEntry);
    }

    @PutMapping("{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable long id, @RequestBody TimeEntry timeEntry)
    {
        TimeEntry foundEntry = timeEntryRepository.update(id, timeEntry);
        if (foundEntry != null) {
            actionCounter.increment();
            return ResponseEntity.status(HttpStatus.OK).body(foundEntry);
        }
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id)
    {
        TimeEntry timeEntry = timeEntryRepository.find(id);
        if (timeEntry != null) {
            actionCounter.increment();
            return ResponseEntity.status(HttpStatus.OK).body(timeEntry);
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list()
    {
        List<TimeEntry> timeEntries = timeEntryRepository.list();
        actionCounter.increment();
        return ResponseEntity.status(HttpStatus.OK).body(timeEntries);
    }

    @DeleteMapping ("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long id)
    {
        timeEntryRepository.delete(id);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
