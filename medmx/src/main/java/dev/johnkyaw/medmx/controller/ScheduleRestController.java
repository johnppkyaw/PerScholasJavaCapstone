package dev.johnkyaw.medmx.controller;

import dev.johnkyaw.medmx.model.Patient;
import dev.johnkyaw.medmx.model.Schedule;
import dev.johnkyaw.medmx.service.ScheduleServices;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ScheduleRestController {
    ScheduleServices scheduleServices;
    private final Logger logger = LoggerFactory.getLogger(ScheduleRestController.class);

    @Autowired
    public ScheduleRestController(ScheduleServices scheduleServices) {
        this.scheduleServices = scheduleServices;
    }

    @Transactional(readOnly = true)
    @GetMapping("/physicians/{id}/schedules") //LocalDate: YYYY-MM-DD
    //fetch URL: "/physicians/{id}/schedules?date={date}"
    public ResponseEntity<List<Schedule>> getAllSchedulesByPhysicianAndDate(@PathVariable("id") long id, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Schedule> schedules = scheduleServices.getPhysicianSchedule(id, date);
        return ResponseEntity.ok(schedules);
    }

    @Transactional
    @PostMapping("/schedules")
    public ResponseEntity<Void> createSchedule(@RequestBody Schedule schedule) {
        scheduleServices.addSchedule(schedule);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    @PutMapping("/schedules/{id}")
    public ResponseEntity<Void> updatePatient(@PathVariable("id") long id, @RequestBody Schedule schedule) {
        Optional<Schedule> scheduleData = scheduleServices.getSchedule(id);
        if(scheduleData.isPresent()) {
            scheduleServices.updateSchedule(id, schedule);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            logger.warn("Attempted editing but the schedule was not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") long id) {
        Optional<Schedule> scheduleData = scheduleServices.getSchedule(id);
        if(scheduleData.isPresent()) {
            scheduleServices.deleteSchedule(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            logger.warn("Attempted editing but the patient not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
