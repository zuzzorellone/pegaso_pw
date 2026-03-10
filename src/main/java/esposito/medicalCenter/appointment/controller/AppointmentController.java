package esposito.medicalCenter.appointment.controller;

import esposito.medicalCenter.appointment.service.AppointmentService;
import esposito.medicalCenter.appointment.dto.RequestAppointmentDTO;
import esposito.medicalCenter.appointment.dto.ResponseAppointmentDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<ResponseAppointmentDTO>> getAllAppointment() {
        return ResponseEntity
                .ok(appointmentService.getAllAppointment());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseAppointmentDTO> getAppointmentById(@PathVariable Long id) {
        return ResponseEntity
                .ok(appointmentService.getAppointmentById(id));
    }

    @PostMapping
    public ResponseEntity<ResponseAppointmentDTO> createAppointment(@Valid @RequestBody RequestAppointmentDTO requestAppointmentDTO) {
        ResponseAppointmentDTO response = appointmentService.createAppointmentAndHandlePatient(requestAppointmentDTO);
        return ResponseEntity
                .created(buildResourceUri(response.getId()))
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseAppointmentDTO> updateAppointment(@PathVariable Long id, @RequestBody RequestAppointmentDTO requestAppointmentDTO) {
        return ResponseEntity
                .ok(appointmentService.updateAppointment(id, requestAppointmentDTO));
    }

    @GetMapping("/bookedDates")
    public ResponseEntity<List<String>> getBookedDatesForMedicalExaminationType(@RequestParam String medicalExaminationType) {
        return ResponseEntity
                .ok(appointmentService.getBookedDates(medicalExaminationType));
    }

    @GetMapping("/availableTimes")
    public ResponseEntity<List<String>> getAvailableTimes(@RequestParam String selectedDate,
                                                          @RequestParam String medicalExaminationType) {
        return ResponseEntity
                .ok(appointmentService.getOccupiedTimesForDate(selectedDate, medicalExaminationType));
    }

    // UTILITY
    private URI buildResourceUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
