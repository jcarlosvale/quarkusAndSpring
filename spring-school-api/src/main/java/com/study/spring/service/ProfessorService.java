package com.study.spring.service;

import com.study.spring.dto.ProfessorRequest;
import com.study.spring.dto.ProfessorResponse;
import com.study.spring.entity.Professor;
import com.study.spring.mapper.ProfessorMapper;
import com.study.spring.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfessorService {

    private final ProfessorRepository repository;
    private final ProfessorMapper mapper;


    public List<ProfessorResponse> retrieveAll() {
        log.info("Listing professors");
        return mapper.toResponse(repository.findAll());
    }

    public ProfessorResponse getById(int id) {
        log.info("Getting professor id-{}", id);

        var optionalProfessor = repository.findById(id);

        if (optionalProfessor.isPresent()) {
            return mapper.toResponse(optionalProfessor.get());
        }

        return new ProfessorResponse();
    }

    public void save(ProfessorRequest request) {
        log.info("Saving professor - {}", request);

        if (Objects.nonNull(request)) repository.saveAndFlush(mapper.toEntity(request));
    }

    public ProfessorResponse update(int id, ProfessorRequest request) {
        log.info("Updating professor id - {}, data - {}", id, request);

        var optionalProfessor = repository.findById(id);

        if (optionalProfessor.isPresent()) {
            Professor entity = optionalProfessor.get();
            entity.setName(request.getName());
            repository.save(entity);
            return mapper.toResponse(entity);
        }

        return null;
    }

    public void delete(int id) {

        log.info("Deleting professor id - {}", id);
        repository.deleteById(id);
    }

}
