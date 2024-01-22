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

import net.ausiasmarch.noventaveinticuatro.entity.UsuarioEntity;
import net.ausiasmarch.noventaveinticuatro.service.UsuarioService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/usuario")
public class UsuarioApi {

    @Autowired
    UsuarioService oUsuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntity> getUsuario(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oUsuarioService.get(id));
    }

    @GetMapping("/random")
    public ResponseEntity<UsuarioEntity> getRandom() {
        return ResponseEntity.ok(oUsuarioService.getOneRandom());
    }

    @GetMapping("")
    public ResponseEntity<Page<UsuarioEntity>> getPage(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(oUsuarioService.getPage(pageable));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UsuarioEntity> getByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(oUsuarioService.getByUsername(username));
    }

    @GetMapping("/mas-compras")
    public ResponseEntity<Page<UsuarioEntity>> getUsuariosMasCompras(Pageable pageable) {
        Page<UsuarioEntity> usuariosMasCompras = oUsuarioService.getUsuariosMasCompras(pageable);
        return ResponseEntity.ok(usuariosMasCompras);
    }

    @GetMapping("/mas-valoraciones")
    public ResponseEntity<Page<UsuarioEntity>> getUsuariosMasValoraciones(Pageable pageable) {
        Page<UsuarioEntity> usuariosMasValoraciones = oUsuarioService.getUsuariosMasValoraciones(pageable);
        return ResponseEntity.ok(usuariosMasValoraciones);
    }

    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody UsuarioEntity oUsuarioEntity) {
        return ResponseEntity.ok(oUsuarioService.create(oUsuarioEntity));
    }

    @PostMapping("/populate/{amount}")
    public ResponseEntity<Long> populate(@PathVariable("amount") int amount) {
        return ResponseEntity.ok(oUsuarioService.populate(amount));
    }

    @PutMapping("")
    public ResponseEntity<UsuarioEntity> update(@RequestBody UsuarioEntity oUsuarioEntity) {
        return ResponseEntity.ok(oUsuarioService.update(oUsuarioEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oUsuarioService.delete(id));
    }

    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oUsuarioService.empty());
    }
    
}
