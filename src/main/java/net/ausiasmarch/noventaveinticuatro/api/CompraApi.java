package net.ausiasmarch.noventaveinticuatro.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.noventaveinticuatro.entity.CamisetaEntity;
import net.ausiasmarch.noventaveinticuatro.entity.CarritoEntity;
import net.ausiasmarch.noventaveinticuatro.entity.CompraEntity;
import net.ausiasmarch.noventaveinticuatro.entity.UsuarioEntity;
import net.ausiasmarch.noventaveinticuatro.service.CamisetaService;
import net.ausiasmarch.noventaveinticuatro.service.CarritoService;
import net.ausiasmarch.noventaveinticuatro.service.CompraService;
import net.ausiasmarch.noventaveinticuatro.service.UsuarioService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/compra")
public class CompraApi {

    @Autowired
    CompraService oCompraService;

    @Autowired
    UsuarioService oUsuarioService;

    @Autowired
    CarritoService oCarritoService;

    @Autowired
    CamisetaService oCamisetaService;

    @GetMapping("/{compraId}")
    public ResponseEntity<CompraEntity> getCompra(@PathVariable("compraId") Long compraId) {
        return ResponseEntity.ok(oCompraService.get(compraId));
    }

    @GetMapping("")
    public ResponseEntity<Page<CompraEntity>> getPage(Pageable oPageable, @RequestParam(value = "usuario", defaultValue = "0", required = false) Long usuarioId)  {
        return ResponseEntity.ok(oCompraService.getPage(usuarioId, oPageable));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Page<CompraEntity>> getComprasByUsuarioId(@PathVariable("usuarioId") Long usuarioId, Pageable oPageable) {
        return ResponseEntity.ok(oCompraService.getComprasUsuario(usuarioId, oPageable));
    }

    @GetMapping("/random")
    public ResponseEntity<CompraEntity> getRandomCompra() {
        CompraEntity compra = oCompraService.getOneRandom();
        return new ResponseEntity<>(compra, HttpStatus.OK);
    }

    @PostMapping("/realizar-compra-unico-carrito/{usuarioId}/{carritoId}")
    public ResponseEntity<CompraEntity> realizarCompraUnicoCarrito(@PathVariable Long usuarioId, @PathVariable Long carritoId) {
        UsuarioEntity usuario = oUsuarioService.get(usuarioId);
        CarritoEntity carrito = oCarritoService.get(carritoId);

        CompraEntity compra = oCompraService.realizarCompraUnicoCarrito(carrito, usuario);

        return new ResponseEntity<>(compra, HttpStatus.CREATED);
    }

    @PostMapping("/realizar-compra-todos-carritos/{usuarioId}")
    public ResponseEntity<CompraEntity> realizarCompraTodosCarritos(
            @PathVariable Long usuarioId) {
        UsuarioEntity usuario = oUsuarioService.get(usuarioId);
        List<CarritoEntity> carritos = oCarritoService.getCarritosUsuario(usuarioId);
        CompraEntity compra = oCompraService.realizarCompraTodosCarritos(carritos, usuario);
        return new ResponseEntity<>(compra, HttpStatus.CREATED);
    }

    @PostMapping("/realizar-compra-camiseta/{camisetaId}/{usuarioId}/{cantidad}")
    public ResponseEntity<CompraEntity> realizarCompraProducto(@PathVariable Long camisetaId, @PathVariable Long usuarioId, @PathVariable int cantidad) {
        UsuarioEntity usuario = oUsuarioService.get(usuarioId);
        CamisetaEntity camiseta = oCamisetaService.get(camisetaId);
        CompraEntity compra = oCompraService.realizarCompraProducto(camiseta, usuario, cantidad);
        return new ResponseEntity<>(compra, HttpStatus.CREATED);
    }

      @DeleteMapping("/{compraId}")
    public ResponseEntity<Long> cancelarCompra(@PathVariable("compraId") Long compraId) {
        Long cancelledCompraId = oCompraService.cancelarCompra(compraId);
        return new ResponseEntity<>(cancelledCompraId, HttpStatus.OK);
    }

   
    @GetMapping("/compras-mas-recientes")
    public ResponseEntity<Page<CompraEntity>> getComprasMasRecientes(
            @PageableDefault(size = 10, sort = {"fecha"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CompraEntity> compras = oCompraService.getComprasMasRecientes(pageable);
        return new ResponseEntity<>(compras, HttpStatus.OK);
    }

    @GetMapping("/compras-mas-antiguas")
    public ResponseEntity<Page<CompraEntity>> getComprasMasAntiguas(
            @PageableDefault(size = 10, sort = {"fecha"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<CompraEntity> compras = oCompraService.getComprasMasAntiguas(pageable);
        return new ResponseEntity<>(compras, HttpStatus.OK);
    }

    @GetMapping("/compras-usuario-mas-caras/{usuarioId}")
    public ResponseEntity<Page<CompraEntity>> getComprasMasCarasByUsuario( @PathVariable Long usuarioId,
            @PageableDefault(size = 10, sort = {"costeTotal"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CompraEntity> compras = oCompraService.getComprasMasCarasByUsuarioId(usuarioId, pageable);
        return new ResponseEntity<>(compras, HttpStatus.OK);
    }

    @GetMapping("/compras-usuario-mas-baratas/{usuarioId}")
    public ResponseEntity<Page<CompraEntity>> getComprasMasBaratasByUsuario(
            @PathVariable Long usuarioId,
            @PageableDefault(size = 10, sort = {"costeTotal"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<CompraEntity> compras = oCompraService.getComprasMasBaratasByUsuarioId(usuarioId, pageable);
        return new ResponseEntity<>(compras, HttpStatus.OK);
    }

    @PostMapping("/populate/{amount}")
    public ResponseEntity<Long> populateCompras(@PathVariable("amount") int amount) {
        Long totalCompras = oCompraService.populate(amount);
        return new ResponseEntity<>(totalCompras, HttpStatus.OK);
    }

    @DeleteMapping("/empty")
    public ResponseEntity<Long> emptyCompras() {
        Long deletedCount = oCompraService.empty();
        return new ResponseEntity<>(deletedCount, HttpStatus.OK);
    }
    
}
