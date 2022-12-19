package com.study.quarkus.service;

import com.study.quarkus.dto.ProfessorRequest;
import com.study.quarkus.dto.ProfessorResponse;
import com.study.quarkus.mapper.ProfessorMapper;
import com.study.quarkus.model.Professor;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Slf4j
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorMapper mapper;

    public List<ProfessorResponse> retrieveAll() {
        log.info("Listing professors");
        final List<Professor> listOfProfessors = Professor.listAll();
        return  mapper.toResponse(listOfProfessors);
    }

    public ProfessorResponse getById(int id) {
        log.info("Getting professor id-{}", id);

        Professor professor = Professor.findById(id);
        return mapper.toResponse(professor);
    }

    @Transactional
    public ProfessorResponse save(ProfessorRequest professorRequest) {

        log.info("Saving professor - {}", professorRequest);

        Professor entity =
                Professor.builder()
                .name(professorRequest.getName())
                .build();

        entity.persistAndFlush();

        return mapper.toResponse(entity);
    }

    @Transactional
    public ProfessorResponse update(int id, ProfessorRequest professorRequest) {

        log.info("Updating professor id - {}, data - {}", id, professorRequest);

        Optional<Professor> professor = Professor.findByIdOptional(id);

        if (professor.isPresent()) {
            var entity = professor.get();
            entity.setName(professorRequest.getName());
            entity.persistAndFlush();
            return mapper.toResponse(entity);
        }

        return new ProfessorResponse();
    }

    @Transactional
    public void delete(int id) {
        log.info("Deleting professor id - {}", id);
        Professor.findByIdOptional(id).ifPresent(PanacheEntityBase::delete);
    }
}
