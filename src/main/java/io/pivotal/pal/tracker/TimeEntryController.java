package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping("")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry)
    {
        timeEntry = timeEntryRepository.create(timeEntry);
        return ResponseEntity.status(HttpStatus.CREATED).body(timeEntry);
    }

    @PutMapping("{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable long id, @RequestBody TimeEntry timeEntry)
    {
        TimeEntry foundEntry = timeEntryRepository.update(id, timeEntry);
        if (foundEntry != null)
            return ResponseEntity.status(HttpStatus.OK).body(foundEntry);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id)
    {
        TimeEntry timeEntry = timeEntryRepository.find(id);
        if (timeEntry != null)
            return ResponseEntity.status(HttpStatus.OK).body(timeEntry);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list()
    {
        List<TimeEntry> timeEntries = timeEntryRepository.list();
        return ResponseEntity.status(HttpStatus.OK).body(timeEntries);
    }

    @DeleteMapping ("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long id)
    {
        timeEntryRepository.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
