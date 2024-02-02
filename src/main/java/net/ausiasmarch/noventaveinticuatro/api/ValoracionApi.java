package net.ausiasmarch.noventaveinticuatro.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
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

import net.ausiasmarch.noventaveinticuatro.entity.ValoracionEntity;
import net.ausiasmarch.noventaveinticuatro.service.ValoracionService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/valoracion")
public class ValoracionApi {
    
    @Autowired
    ValoracionService oValoracionService;

    @GetMapping("/{id}")
    public ResponseEntity<ValoracionEntity> getValoracion(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oValoracionService.get(id));
    }

    @GetMapping("/random") 
    public ResponseEntity<ValoracionEntity> getRandomValoracion() {
        ValoracionEntity valoracion = oValoracionService.getOneRandom();
        return ResponseEntity.ok(valoracion);
    }

    @GetMapping("")
    public ResponseEntity<Page<ValoracionEntity>> getPage(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(value = "usuario", defaultValue = "0", required = false) Long usuario_id, @RequestParam(value = "camiseta", defaultValue = "0", required = false) Long camiseta_id) {
        return ResponseEntity.ok(oValoracionService.getPage(pageable, usuario_id, camiseta_id));
    }

    @GetMapping("/usuario/{usuario_id}")
    public ResponseEntity<Page<ValoracionEntity>> getPageByUsuario(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable, @PathVariable("usuario_id") Long usuario_id) {
        return ResponseEntity.ok(oValoracionService.getPageByUsuarioId(usuario_id, pageable));
    }

    @GetMapping("/camiseta/{camiseta_id}")
    public ResponseEntity<Page<ValoracionEntity>> getPageByCamiseta(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable, @PathVariable("camiseta_id") Long camiseta_id) {
        return ResponseEntity.ok(oValoracionService.getPageByCamisetaId(camiseta_id, pageable));
    }

    @GetMapping("/usuario/{usuario_id}/camiseta/{camiseta_id}")
    public ResponseEntity<Optional<ValoracionEntity>> getValoracionByUsuarioAndCamiseta(@PathVariable("usuario_id") Long usuario_id, @PathVariable("camiseta_id") Long camiseta_id) {
        return ResponseEntity.ok(oValoracionService.getValoracionByUsuarioAndCamiseta(usuario_id, camiseta_id));
    }

    @GetMapping("/camiseta/{camiseta_id}/recientes")
    public ResponseEntity<Page<ValoracionEntity>> getValoracionesMasReciente(@PathVariable("camiseta_id") Long camiseta_id, @PageableDefault(size = 10, sort = {"fecha"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(oValoracionService.getPageMasRecientes(camiseta_id, pageable));
    }

    @GetMapping("/camiseta/{camiseta_id}/antiguas")
    public ResponseEntity<Page<ValoracionEntity>> getValoracionesMasAntiguas(@PathVariable("camiseta_id") Long camiseta_id, @PageableDefault(size = 10, sort = {"fecha"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(oValoracionService.getPageMasAntiguas(camiseta_id, pageable));
    }

    @GetMapping("/usuario/{usuario_id}/recientes")
    public ResponseEntity<Page<ValoracionEntity>> getValoracionesMasRecienteByUsuario(@PathVariable("usuario_id") Long usuario_id, @PageableDefault(size = 10, sort = {"fecha"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(oValoracionService.getPageMasRecientesByUsuarioId(usuario_id, pageable));
    }

    @GetMapping("/usuario/{usuario_id}/antiguas")
    public ResponseEntity<Page<ValoracionEntity>> getValoracionesMasAntiguasByUsuario(@PathVariable("usuario_id") Long usuario_id, @PageableDefault(size = 10, sort = {"fecha"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(oValoracionService.getPageMasAntiguasByUsuarioId(usuario_id, pageable));
    }

    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody ValoracionEntity oValoracionEntity) {
        return ResponseEntity.ok(oValoracionService.create(oValoracionEntity));
    }

    @PostMapping("/populate/{amount}")
    public ResponseEntity<Long> populate(@PathVariable("amount") int amount) {
        return ResponseEntity.ok(oValoracionService.populate(amount));
    }

    @PutMapping("")
    public ResponseEntity<ValoracionEntity> update(@RequestBody ValoracionEntity oValoracionEntity) {
        return ResponseEntity.ok(oValoracionService.update(oValoracionEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oValoracionService.delete(id));
    }

    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oValoracionService.empty());
    }

    



    
}
