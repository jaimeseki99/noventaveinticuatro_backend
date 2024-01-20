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
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.noventaveinticuatro.entity.EquipoEntity;
import net.ausiasmarch.noventaveinticuatro.service.EquipoService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/equipo")
public class EquipoApi {

    @Autowired
    EquipoService oEquipoService;
    
    @GetMapping("/{id}")
    public ResponseEntity<EquipoEntity> getEquipo(@PathVariable Long id) {
        return ResponseEntity.ok(oEquipoService.get(id));
    }

    @GetMapping("")
    public ResponseEntity<Page<EquipoEntity>> getPage(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable oPageable)  {
        return ResponseEntity.ok(oEquipoService.getPage(oPageable));
    }

    @GetMapping("/random")
    public ResponseEntity<EquipoEntity> getRandomEquipo() {
        EquipoEntity equipo = oEquipoService.getOneRandom();
        return ResponseEntity.ok(equipo);
    }

    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody EquipoEntity oEquipoEntity) {
        return ResponseEntity.ok(oEquipoService.create(oEquipoEntity));
    }

    @PutMapping("")
    public ResponseEntity<EquipoEntity> update(@RequestBody EquipoEntity oEquipoEntity) {
        return ResponseEntity.ok(oEquipoService.update(oEquipoEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return ResponseEntity.ok(oEquipoService.delete(id));
    }

    @PostMapping("/populate/{amount}")
    public ResponseEntity<Long> populate(@PathVariable("amount") int amount) {
        return ResponseEntity.ok(oEquipoService.populate(amount));
    }

    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oEquipoService.empty());
    }
}
