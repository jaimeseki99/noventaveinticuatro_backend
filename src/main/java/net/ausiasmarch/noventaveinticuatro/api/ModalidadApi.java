package net.ausiasmarch.noventaveinticuatro.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.noventaveinticuatro.entity.ModalidadEntity;
import net.ausiasmarch.noventaveinticuatro.service.ModalidadService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/modalidad")
public class ModalidadApi {
    
    @Autowired
    ModalidadService oModalidadService;

    @GetMapping("/{id}")
    public ResponseEntity<ModalidadEntity> getModalidad(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oModalidadService.get(id));
    }

    @GetMapping("/random")
    public ResponseEntity<ModalidadEntity> getRandomModalidad() {
        ModalidadEntity modalidad = oModalidadService.getOneRandom();
        return ResponseEntity.ok(modalidad);
    }

    @GetMapping("")
    public ResponseEntity<Page<ModalidadEntity>> getPage(@PageableDefault(size = 10, sort = "{id}", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(value = "filtro", required = false) String filtro) {
        return ResponseEntity.ok(oModalidadService.getPage(pageable, filtro));
    }

    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody ModalidadEntity oModalidadEntity) {
        return ResponseEntity.ok(oModalidadService.create(oModalidadEntity));
    }

    @PutMapping("") 
    public ResponseEntity<ModalidadEntity> update(@RequestBody ModalidadEntity oModalidadEntity) {
        return ResponseEntity.ok(oModalidadService.update(oModalidadEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oModalidadService.delete(id));
    }

    @PostMapping("/populate/{amount}")
    public ResponseEntity<Long> populate(@PathVariable("amount") int amount) {
        return ResponseEntity.ok(oModalidadService.populate(amount));
    }

    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oModalidadService.empty());
    }

    
}
