package dev.johnkyaw.medmx.service;



import dev.johnkyaw.medmx.model.Physician;
import dev.johnkyaw.medmx.model.Schedule;
import dev.johnkyaw.medmx.repository.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleServices scheduleService;

    @Test
    void testGetAllSchedulesByPhysicianAndDate() {
        // Mock data
        Long physicianId = 1L;
        String searchDate = "2023-03-22";
        Physician physician = new Physician();
        physician.setId(physicianId);
        // Mock data
        Long physicianId2 = 1L;
        String searchDate2 = "2023-03-23";
        Physician physician2 = new Physician();
        physician2.setId(physicianId2);
        // Create mock schedules
        Schedule schedule1 = new Schedule();
        schedule1.setId(1L);
        schedule1.setDate(searchDate);
        schedule1.setStartTime(LocalTime.parse("08:20"));
        schedule1.setPhysician(physician);
        Schedule schedule2 = new Schedule();
        schedule2.setId(2L);
        schedule2.setDate(searchDate2);
        schedule2.setStartTime(LocalTime.parse("08:40"));
        schedule2.setPhysician(physician);
        List<Schedule> schedules1 = Arrays.asList(schedule1, schedule2);
        List<Schedule> schedules2 = List.of(schedule1);

        // Mock repository method to return the mock schedules
        when(scheduleRepository.getAllSchedulesByPhysicianAndDate(physicianId, searchDate)).thenReturn(schedules2);

        // Call the service method
        List<Schedule> result = scheduleService.getAllSchedulesByPhysicianAndDate(physicianId, searchDate);
        System.out.println(result);

        // Assertions
        assertEquals(schedules2, result); // Verify the size of the returned list
        // Add more assertions as needed
    }
}