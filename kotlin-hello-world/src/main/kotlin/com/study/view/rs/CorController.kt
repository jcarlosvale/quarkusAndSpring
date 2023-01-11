package com.study.view.rs

import com.study.dto.CorDto
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/cores"])
class CorController {

    val cores: MutableMap<Int, CorDto> = mutableMapOf()

    //@GetMapping
    fun listCores(): ResponseEntity<List<CorDto>> {
        log.info("Listing cores")
        return if (cores.isEmpty()) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.ok(ArrayList(cores.values))
        }
    }

    @GetMapping(path = ["/{id}"])
    fun getCor(@PathVariable("id") id: Int): ResponseEntity<CorDto> {
        log.info("Getting cor {}", id)
        val cor = cores[id]
        return if (cor == null) {
            ResponseEntity
                .notFound()
                .build()
        } else {
            ResponseEntity
                .ok(cor)
        }
    }

    @GetMapping
    fun getCor(@RequestParam(value = "prefixo", required = false) prefixo: String?): ResponseEntity<List<CorDto>> {
        log.info("Getting cor with prefix {}", prefixo)
        if (prefixo == null) return listCores()
        val selectedCores = cores.values
            .filter { cor: CorDto ->
                cor.descricao
                    .startsWith(prefixo)
            }
        return ResponseEntity.ok(selectedCores)
    }

    @PostMapping
    fun saveCor(@RequestBody cor: CorDto?): ResponseEntity<CorDto> {
        return if (cor == null) {
            log.error("Invalid body - null")
            ResponseEntity
                .badRequest()
                .build()
        } else {
            cores[cor.id] = cor
            log.info("Inserted a new color {}", cor)
            ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cor)
        }
    }

    @PutMapping(path = ["/{id}"])
    fun getCor(@PathVariable("id") id: Int, @RequestBody corDto: CorDto): ResponseEntity<CorDto> {
        log.info("Updating cor {}", id)
        val cor = cores[id]
        return if (cor == null) {
            ResponseEntity
                .notFound()
                .build()
        } else {
            cores[id] = corDto
            ResponseEntity
                .ok(corDto)
        }
    }

    @DeleteMapping(path = ["/{id}"])
    fun removeCor(@PathVariable("id") id: Int): ResponseEntity<Unit> {
        log.info("Deleting cor {}", id)
        val cor = cores[id]
        return if (cor == null) {
            ResponseEntity
                .notFound()
                .build()
        } else {
            cores.remove(id)
            ResponseEntity
                .noContent()
                .build()
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}