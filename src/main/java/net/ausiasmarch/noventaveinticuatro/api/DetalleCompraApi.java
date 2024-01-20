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

import net.ausiasmarch.noventaveinticuatro.entity.DetalleCompraEntity;
import net.ausiasmarch.noventaveinticuatro.service.DetalleCompraService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/detallecompra")
public class DetalleCompraApi {

    @Autowired
    DetalleCompraService oDetalleCompraService;

    @GetMapping("/{id}")
    public ResponseEntity<DetalleCompraEntity> getDetalleCompra(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oDetalleCompraService.get(id));
    }

    @PostMapping("")
    public ResponseEntity<DetalleCompraEntity> create(@RequestBody DetalleCompraEntity oDetalleCompraEntity) {
        return ResponseEntity.ok(oDetalleCompraService.create(oDetalleCompraEntity));
    }

    @PutMapping("")
    public ResponseEntity<DetalleCompraEntity> update(@RequestBody DetalleCompraEntity oDetalleCompraEntity) {
        return ResponseEntity.ok(oDetalleCompraService.update(oDetalleCompraEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return ResponseEntity.ok(oDetalleCompraService.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<Page<DetalleCompraEntity>> getPage(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(value = "camiseta", defaultValue = "0", required = false) Long camiseta_id, @RequestParam(value = "compra", defaultValue = "0", required = false) Long compra_id) {
        return ResponseEntity.ok(oDetalleCompraService.getPage(camiseta_id, compra_id, pageable));
    }

    @PostMapping("/populate/{amount}")
    public ResponseEntity<Long> populate(@PathVariable("amount") int amount) {
        return ResponseEntity.ok(oDetalleCompraService.populate(amount));
    }

    @DeleteMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oDetalleCompraService.empty());
    }

    
    
}
