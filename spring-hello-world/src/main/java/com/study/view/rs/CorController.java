package com.study.view.rs;

import com.study.dto.CorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/cores")
@Slf4j
public class CorController {

    private final Map<Integer, CorDto> cores = new HashMap<>();

    //@GetMapping
    public ResponseEntity<List<CorDto>> listCores() {
        log.info("Listing cores");
        if (cores.isEmpty()) {
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity
                    .ok(new ArrayList<>(cores.values()));
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CorDto> getCor(@PathVariable("id") int id) {
        log.info("Getting cor {}", id);
        var cor = cores.get(id);
        if (Objects.isNull(cor)) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        else {
            return ResponseEntity
                    .ok(cor);
        }
    }

    @GetMapping
    public ResponseEntity<List<CorDto>> getCor(@RequestParam(value = "prefixo", required = false) String prefixo) {
        log.info("Getting cor with prefix {}", prefixo);
        if(Objects.isNull(prefixo)) return listCores();
        var selectedCores = cores.values().stream()
                .filter(cor -> cor.getDescricao()
                        .startsWith(prefixo))
                .collect(Collectors.toList());
        return ResponseEntity
                .ok(selectedCores);
    }

    @PostMapping
    public ResponseEntity<CorDto> saveCor(@RequestBody final CorDto cor) {
        if (Objects.isNull(cor)) {
            log.error("Invalid body - null");
            return ResponseEntity
                    .badRequest()
                    .build();
        }
        else {
            cores.put(cor.getId(), cor);
            log.info("Inserted a new color {}", cor);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(cor);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CorDto> getCor(@PathVariable("id") int id, @RequestBody CorDto corDto) {
        log.info("Updating cor {}", id);
        var cor = cores.get(id);
        if (Objects.isNull(cor)) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        else {
            cores.put(id, corDto);
            return ResponseEntity
                    .ok(corDto);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> removeCor(@PathVariable("id") int id) {
        log.info("Deleting cor {}", id);
        var cor = cores.get(id);
        if (Objects.isNull(cor)) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        else {
            cores.remove(id);
            return ResponseEntity
                    .noContent()
                    .build();
        }
    }
}
