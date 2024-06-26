package net.ausiasmarch.noventaveinticuatro.api;

import java.util.List;

import org.hibernate.query.SortDirection;
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

import net.ausiasmarch.noventaveinticuatro.entity.CarritoEntity;
import net.ausiasmarch.noventaveinticuatro.service.CarritoService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/carrito")
public class CarritoApi {

    @Autowired
    CarritoService oCarritoService;

    @GetMapping("/{carritoId}")
    public ResponseEntity<CarritoEntity> getCarrito(@PathVariable("carritoId") Long carritoId) {
        return ResponseEntity.ok(oCarritoService.get(carritoId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Page<CarritoEntity>> getCarritoByUsuario(@PathVariable("usuarioId") Long usuarioId, @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable oPageable) {
        return ResponseEntity.ok(oCarritoService.getCarritoByUsuario(usuarioId, oPageable));
    }

    @GetMapping("/usuario/{usuarioId}/camiseta/{camisetaId}")
    public ResponseEntity<CarritoEntity> getCarritoByUsuarioAndCamiseta(@PathVariable("usuarioId") Long usuarioId, @PathVariable("camisetaId") Long camisetaId) {
        return ResponseEntity.ok(oCarritoService.getCarritoByUsuarioAndCamiseta(usuarioId, camisetaId));
    }

    @GetMapping("")
    public ResponseEntity<Page<CarritoEntity>> getPage(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable oPageable)  {
        return ResponseEntity.ok(oCarritoService.getPage(oPageable));
    }

    @GetMapping("/coste/{carritoId}")
    public ResponseEntity<Double> getCosteCarrito(@PathVariable("carritoId") Long carritoId) {
        return ResponseEntity.ok(oCarritoService.calcularCosteCarrito(carritoId));
    }

    @GetMapping("/costetotal/{usuarioId}")
    public ResponseEntity<Double> getCosteTotalCarrito(@PathVariable("usuarioId") Long usuarioId) {
        return ResponseEntity.ok(oCarritoService.calcularCosteTotalCarrito(usuarioId));
    }

    @PostMapping("")
    public ResponseEntity<Long> createCarrito(@RequestBody CarritoEntity oCarritoEntity) {
        return ResponseEntity.ok(oCarritoService.create(oCarritoEntity));
    }

    @PostMapping("populate/{amount}")
    public ResponseEntity<Long> populateCarrito(@PathVariable("amount") int amount) {
        return ResponseEntity.ok(oCarritoService.populate(amount));
    }

    @PutMapping("")
    public ResponseEntity<CarritoEntity> updateCarrito(@RequestBody CarritoEntity oCarritoEntity) {
        return ResponseEntity.ok(oCarritoService.update(oCarritoEntity));
    }

    @DeleteMapping("/{carritoId}")
    public ResponseEntity<Long> deleteCarrito(@PathVariable("carritoId") Long carritoId) {
        return ResponseEntity.ok(oCarritoService.delete(carritoId));
    }

    @DeleteMapping("/eliminarCamisetaCarrito/{carritoId}")
    public ResponseEntity<Long> eliminarCamisetaCarrito(@PathVariable("carritoId") Long carritoId) {
        return ResponseEntity.ok(oCarritoService.eliminarCamisetaCarrito(carritoId));
    }

    @DeleteMapping("/usuario/{usuarioId}")
    public ResponseEntity<Long> deleteCarritoByUsuario(@PathVariable("usuarioId") Long usuarioId) {
        oCarritoService.deleteByUsuario(usuarioId);
        return ResponseEntity.ok(usuarioId);
    }

    @DeleteMapping("/eliminarCarritoUsuario/{usuarioId}")
    public ResponseEntity<Long> eliminarCamisetasCarritoUsuario(@PathVariable("usuarioId") Long usuarioId) {
        oCarritoService.eliminarTodasCamisetasCarrito(usuarioId);
        return ResponseEntity.ok(usuarioId);
    }

    @DeleteMapping("/empty")
    public ResponseEntity<Long> emptyCarrito() {
        Long totalCarritos = oCarritoService.empty();
        return ResponseEntity.ok(totalCarritos);
    }


    
}
