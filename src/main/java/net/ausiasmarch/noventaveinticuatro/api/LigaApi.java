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

import net.ausiasmarch.noventaveinticuatro.entity.LigaEntity;
import net.ausiasmarch.noventaveinticuatro.service.LigaService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/liga")
public class LigaApi {

    @Autowired
    LigaService oLigaService;

    @GetMapping("/{id}")
    public ResponseEntity<LigaEntity> getLiga(@PathVariable Long id) {
        return ResponseEntity.ok(oLigaService.get(id));
    }

    @GetMapping("")
    public ResponseEntity<Page<LigaEntity>> getPage(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable oPageable)  {
        return ResponseEntity.ok(oLigaService.getPage(oPageable));
    }

    @GetMapping("/random")
    public ResponseEntity<LigaEntity> getRandomLiga() {
        LigaEntity liga = oLigaService.getOneRandom();
        return ResponseEntity.ok(liga);
    }

    @PostMapping("")
    public ResponseEntity<Long> create (@RequestBody LigaEntity oLigaEntity) {
        return ResponseEntity.ok(oLigaService.create(oLigaEntity));
    }

    @PutMapping("")
    public ResponseEntity<LigaEntity> update (@RequestBody LigaEntity oLigaEntity) {
        return ResponseEntity.ok(oLigaService.update(oLigaEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete (@PathVariable("id") Long id) {
        return ResponseEntity.ok(oLigaService.delete(id));
    }

    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oLigaService.empty());
    }

}
