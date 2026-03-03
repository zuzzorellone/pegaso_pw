package esposito.medicalCenter.medicalExaminationType;

import esposito.medicalCenter.medicalExaminationType.dto.RequestMedicalExaminationTypeDTO;
import esposito.medicalCenter.medicalExaminationType.dto.ResponseMedicalExaminationTypeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/medicalExaminationTypes")
public class MedicalExaminationTypeController {

    private final MedicalExaminationTypeService medicalExaminationTypeService;

    @GetMapping
    public ResponseEntity<List<ResponseMedicalExaminationTypeDTO>> getAllMedicalExaminationType() {
        return ResponseEntity.ok(medicalExaminationTypeService.getAllMedicalExaminationTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMedicalExaminationTypeDTO> getMedicalExaminationTypeByID(@PathVariable Long id) {
        return ResponseEntity.ok(medicalExaminationTypeService.getMedicalExaminationTypeByID(id));
    }

    @PostMapping
    public ResponseEntity<ResponseMedicalExaminationTypeDTO> createNewMedicalExaminationType(@RequestBody RequestMedicalExaminationTypeDTO requestDTO) {
        ResponseMedicalExaminationTypeDTO responseDTO = medicalExaminationTypeService.createMedicalExaminationType(requestDTO);

        return ResponseEntity.created(buildResourceUri(responseDTO.getId()))
                .body(responseDTO);
    }

    @PutMapping
    public ResponseEntity<ResponseMedicalExaminationTypeDTO> updateMedicalExaminationType(@PathVariable Long id,
                                                                                          @RequestBody RequestMedicalExaminationTypeDTO requestDTO) {
        return ResponseEntity
                .ok(medicalExaminationTypeService.updateMedicalExaminationType(id, requestDTO));
    }

    private URI buildResourceUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
