package com.study.spring.view.rs;

import com.study.spring.dto.ProfessorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professores")
@Slf4j
public class ProfessorController {

    @GetMapping
    public ResponseEntity<Void> listProfessors() {
        log.info("Listing professors");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> getProfessor(@PathVariable("id") int id) {
        log.info("Getting professor id-{}", id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> saveProfessor(@RequestBody final ProfessorDto professor) {
        log.info("Saving professor - {}", professor);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDto> updateProfessor(@PathVariable("id") int id, @RequestBody ProfessorDto professor) {
        log.info("Updating professor id - {}", id);
        return ResponseEntity
                .ok(professor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProfessor(@PathVariable("id") int id) {
        log.info("Deleting professor id - {}", id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
