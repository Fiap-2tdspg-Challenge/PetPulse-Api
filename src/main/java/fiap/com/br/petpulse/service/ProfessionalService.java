package fiap.com.br.petpulse.service;

import fiap.com.br.petpulse.dto.request.ProfessionalRequest;
import fiap.com.br.petpulse.dto.response.ProfessionalResponse;
import fiap.com.br.petpulse.model.Clinic;
import fiap.com.br.petpulse.model.Professional;
import fiap.com.br.petpulse.repositories.ClinicRepository;
import fiap.com.br.petpulse.repositories.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "professionals")
public class ProfessionalService {

    private final ProfessionalRepository professionalRepository;
    private final ClinicRepository clinicRepository;
    private final PasswordEncoder passwordEncoder;

    @CacheEvict(allEntries = true)
    public ProfessionalResponse addProfessional(ProfessionalRequest request) {
        Clinic clinic = findClinicById(request.clinicId());

        Professional professional = Professional.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .crmv(request.crmv())
                .clinic(clinic)
                .build();

        return ProfessionalResponse.toResponse(professionalRepository.save(professional));
    }

    @Cacheable
    public Page<ProfessionalResponse> getAllProfessionals(Pageable pageable) {
        return professionalRepository.findAll(pageable)
                .map(ProfessionalResponse::toResponse);
    }

    @Cacheable
    public ProfessionalResponse getProfessionalById(Long id) {
        return ProfessionalResponse.toResponse(findProfessionalById(id));
    }

    @CacheEvict(allEntries = true)
    public ProfessionalResponse updateProfessional(Long id, ProfessionalRequest request) {
        Professional professional = findProfessionalById(id);
        Clinic clinic = findClinicById(request.clinicId());

        professional.setName(request.name());
        professional.setEmail(request.email());
        professional.setPassword(passwordEncoder.encode(request.password()));
        professional.setCrmv(request.crmv());
        professional.setClinic(clinic);

        return ProfessionalResponse.toResponse(professionalRepository.save(professional));
    }

    @CacheEvict(allEntries = true)
    public void deleteProfessional(Long id) {
        findProfessionalById(id);
        professionalRepository.deleteById(id);
    }

    @Cacheable
    public List<ProfessionalResponse> searchProfessionalsByName(String name) {
        return professionalRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(ProfessionalResponse::toResponse)
                .toList();
    }

    private Professional findProfessionalById(Long id) {
        return professionalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Profissional com id " + id + " não encontrado"
                ));
    }

    private Clinic findClinicById(Long id) {
        return clinicRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Clínica com id " + id + " não encontrada"
                ));
    }
}
