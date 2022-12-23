package com.study.spring.view.rs;

import com.study.spring.dto.ProfessorRequest;
import com.study.spring.dto.ProfessorResponse;
import com.study.spring.service.ProfessorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/professores")
@Slf4j
public class ProfessorController {

    private final ProfessorService service;

    @Autowired
    public ProfessorController(ProfessorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProfessorResponse>> listProfessors() {

        final List<ProfessorResponse> professorDtoList = service.retrieveAll();

        return ResponseEntity.ok(professorDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponse> getProfessor(@PathVariable("id") int id) {

        final ProfessorResponse professorDto = service.getById(id);

        return ResponseEntity.ok(professorDto);
    }

    @PostMapping
    public ResponseEntity<Void> saveProfessor(@Valid @RequestBody final ProfessorRequest professor) {

        service.save(professor);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponse> updateProfessor(@PathVariable("id") int id, @Valid @RequestBody ProfessorRequest professor) {

        var response = service.update(id, professor);

        return ResponseEntity
                .ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProfessor(@PathVariable("id") int id) {

        service.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
