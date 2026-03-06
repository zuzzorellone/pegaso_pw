package esposito.medicalCenter.appointment.api;

public interface AppointmentFacade {

    public void existsById(Long id);

    public Long getPatientIdByAppointment(Long id);
}
