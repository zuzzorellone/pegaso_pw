package esposito.medicalCenter.appointment;

import esposito.medicalCenter.appointment.dto.RequestAppointmentDTO;
import esposito.medicalCenter.appointment.dto.ResponseAppointmentDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/all")
    public ResponseEntity<List<ResponseAppointmentDTO>> getAllAppointment() {
        return ResponseEntity.ok(appointmentService.getAllAppointment());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseAppointmentDTO> getAppointmentById(@RequestParam Long id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<ResponseAppointmentDTO> createAppointment(@Valid @RequestBody RequestAppointmentDTO requestAppointmentDTO) {
        return ResponseEntity.ok(appointmentService.createAppointment(requestAppointmentDTO));
    }

    @PutMapping
    public ResponseEntity<ResponseAppointmentDTO> updateAppointment(@RequestBody RequestAppointmentDTO requestAppointmentDTO) {
        return null;
    }

}
