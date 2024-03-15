package dev.johnkyaw.medmx.controller;

import dev.johnkyaw.medmx.model.Patient;
import dev.johnkyaw.medmx.model.Schedule;
import dev.johnkyaw.medmx.service.ScheduleServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

    @GetMapping("/physicians/{id}/schedules") //LocalDate: YYYY-MM-DD
    //fetch URL: "/physicians/{id}/schedules?date={date}"
    public List<Schedule> getAllSchedulesByPhysicianAndDate(@PathVariable("id") long id, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return scheduleServices.getPhysicianSchedule(id, date);
    }
    @PostMapping("/schedules")
    public void createSchedule(@RequestBody Schedule schedule) {
        scheduleServices.addSchedule(schedule);
    }

    @PutMapping("/schedules/{id}")
    public void updatePatient(@PathVariable("id") long id, @RequestBody Schedule schedule) {
        Optional<Schedule> scheduleData = scheduleServices.getSchedule(id);
        if(scheduleData.isPresent()) {
            scheduleServices.updateSchedule(id, schedule);
        } else {
            logger.warn("Attempted editing but the schedule was not found!");
        }
    }
    
    @DeleteMapping("/schedules/{id}")
    public void deletePatient(@PathVariable("id") long id) {
        Optional<Schedule> scheduleData = scheduleServices.getSchedule(id);
        if(scheduleData.isPresent()) {
            scheduleServices.deleteSchedule(id);
        } else {
            logger.warn("Attempted editing but the patient not found!");
        }
    }
}
