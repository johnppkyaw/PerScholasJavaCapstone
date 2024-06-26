package dev.johnkyaw.medmx.service;

import dev.johnkyaw.medmx.model.Schedule;
import dev.johnkyaw.medmx.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service //annotated as spring service bean
public class ScheduleServices {
    ScheduleRepository scheduleRepository;
    @Autowired
    public ScheduleServices(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public void addSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    public Optional<Schedule> getSchedule(Long id) {
        return scheduleRepository.findById(id);
    }

    public List<Schedule> getPhysicianSchedule(Long id, String date) {
        return scheduleRepository.getAllSchedulesByPhysicianAndDate(id, date);
    }

    //Can edit only date, time, status, and note (can't change patient or doctor)
    public void updateSchedule(Long id, Schedule schedule) {
        Optional<Schedule> scheduleData = scheduleRepository.findById(id);
        if(scheduleData.isPresent()) {
            Schedule _schedule = scheduleData.get();
            _schedule.setDate(schedule.getDate());
            _schedule.setStartTime(schedule.getStartTime());
            _schedule.setStatus(schedule.getStatus());
            _schedule.setNotes(schedule.getNotes());
            _schedule.setPatient(schedule.getPatient());
            _schedule.setPhysician(schedule.getPhysician());
            scheduleRepository.save(_schedule);
        }
    }

    public void deleteSchedule(Long id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if(schedule.isPresent()) {
            scheduleRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Schedule not found with id: " + id);
        }
    }

    public List<Schedule> getAllSchedulesByPhysicianAndDate(Long physicianId, String searchDate) {
        return scheduleRepository.getAllSchedulesByPhysicianAndDate(physicianId, searchDate);
    }
}
