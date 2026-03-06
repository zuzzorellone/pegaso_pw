package esposito.medicalCenter.appointment.service;

import esposito.medicalCenter.appointment.entity.MedicalExaminationTypeEntity;
import esposito.medicalCenter.appointment.repository.MedicalExaminationTypeRepository;
import esposito.medicalCenter.appointment.dto.RequestMedicalExaminationTypeDTO;
import esposito.medicalCenter.appointment.dto.ResponseMedicalExaminationTypeDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalExaminationTypeService {

    private final MedicalExaminationTypeRepository medicalExaminationTypeRepository;

    @Transactional(readOnly = true)
    public List<ResponseMedicalExaminationTypeDTO> getAllMedicalExaminationTypes() {
        return medicalExaminationTypeRepository.findAll().stream().map(
                ResponseMedicalExaminationTypeDTO::new
        ).toList();
    }

    @Transactional(readOnly = true)
    public ResponseMedicalExaminationTypeDTO getMedicalExaminationTypeByID(Long id) {
        MedicalExaminationTypeEntity entity = medicalExaminationTypeRepository.findById(id)
                .orElseThrow(
                () -> new EntityNotFoundException("Medical Examination Type Not Found With ID: " + id)
        );

        return new ResponseMedicalExaminationTypeDTO(entity);
    }

    @Transactional
    public ResponseMedicalExaminationTypeDTO createMedicalExaminationType(RequestMedicalExaminationTypeDTO requestMedicalExaminationTypeDTO) {
        MedicalExaminationTypeEntity medicalExaminationTypeEntity = new MedicalExaminationTypeEntity();
        medicalExaminationTypeEntity.setName(requestMedicalExaminationTypeDTO.name());
        medicalExaminationTypeEntity.setDescription(requestMedicalExaminationTypeDTO.description());
        medicalExaminationTypeEntity =  medicalExaminationTypeRepository.save(medicalExaminationTypeEntity);

        return new ResponseMedicalExaminationTypeDTO(medicalExaminationTypeEntity);
    }

    @Transactional
    public ResponseMedicalExaminationTypeDTO updateMedicalExaminationType(Long id, RequestMedicalExaminationTypeDTO requestMedicalExaminationTypeDTO) {
        MedicalExaminationTypeEntity entity = medicalExaminationTypeRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Medical Examination Type Not Found With ID: " + id)
                );

        entity.setDescription(requestMedicalExaminationTypeDTO.description());
        entity.setName(requestMedicalExaminationTypeDTO.name());

        entity = medicalExaminationTypeRepository.save(entity);

        return new ResponseMedicalExaminationTypeDTO(entity);
    }
}
