package net.ausiasmarch.noventaveinticuatro.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

import jakarta.transaction.Transactional;
import net.ausiasmarch.noventaveinticuatro.entity.CamisetaEntity;
import net.ausiasmarch.noventaveinticuatro.service.CamisetaService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/camiseta")
public class CamisetaApi {

    @Autowired
    CamisetaService oCamisetaService;

    @GetMapping("/{id}")
    public ResponseEntity<CamisetaEntity> getCamisetaById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oCamisetaService.get(id));
    }

    @PostMapping("")
    public ResponseEntity<Long> createCamiseta(@RequestBody CamisetaEntity camisetaEntity) {
        return ResponseEntity.ok(oCamisetaService.create(camisetaEntity));
    }

    @PutMapping("")
    public ResponseEntity<CamisetaEntity> updateCamiseta(@RequestBody CamisetaEntity camisetaEntity) {
        return ResponseEntity.ok(oCamisetaService.update(camisetaEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteCamiseta(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oCamisetaService.delete(id));
    }

    @GetMapping("")
    public ResponseEntity<Page<CamisetaEntity>> getPage(Pageable oPageable, @RequestParam(value = "equipo", defaultValue = "0", required = false) Long equipo_id, @RequestParam(value = "modalidad", defaultValue = "0", required = false) Long modalidad_id, @RequestParam(value = "liga", defaultValue = "0", required = false) Long liga_id) { 
        return ResponseEntity.ok(oCamisetaService.getPage(oPageable, equipo_id, modalidad_id, liga_id));
    }

    @GetMapping("/random")
    public ResponseEntity<CamisetaEntity> getRandomCamiseta() {
        CamisetaEntity randomCamiseta = oCamisetaService.getOneRandom();
        return new ResponseEntity<>(randomCamiseta, HttpStatus.OK);
    }

    @PostMapping("/populate/{amount}")
    public ResponseEntity<Long> populateCamisetas(@PathVariable("amount") int amount) {
        Long totalCamisetas = oCamisetaService.populate(amount);
        return new ResponseEntity<>(totalCamisetas, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/empty")
    public ResponseEntity<Long> emptyCamisetas() {
        Long totalCamisetas = oCamisetaService.empty();
        return new ResponseEntity<>(totalCamisetas, HttpStatus.OK);
    }

    @GetMapping("/equipo/{equipoId}")
    public ResponseEntity<Page<CamisetaEntity>> getCamisetasByEquipoId(
            @PathVariable("equipoId") Long equipoId,
            @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<CamisetaEntity> camisetas = oCamisetaService.getPageByEquipoId(equipoId, pageable);
        return new ResponseEntity<>(camisetas, HttpStatus.OK);
    }

    @GetMapping("/modalidad/{modalidadId}")
    public ResponseEntity<Page<CamisetaEntity>> getCamisetasByModalidadId(
            @PathVariable("modalidadId") Long modalidadId,
            @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<CamisetaEntity> camisetas = oCamisetaService.getPageByModalidadId(modalidadId, pageable);
        return new ResponseEntity<>(camisetas, HttpStatus.OK);
    }

    @GetMapping("/liga/{ligaId}")
    public ResponseEntity<Page<CamisetaEntity>> getCamisetasByLigaId(
            @PathVariable("ligaId") Long ligaId,
            @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<CamisetaEntity> camisetas = oCamisetaService.getPageByLigaId(ligaId, pageable);
        return new ResponseEntity<>(camisetas, HttpStatus.OK);
    }

    @GetMapping("/mas-vendidas")
    public ResponseEntity<Page<CamisetaEntity>> getCamisetasMasVendidas(
            @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<CamisetaEntity> camisetas = oCamisetaService.getPageCamisetasMasVendidas(pageable);
        return new ResponseEntity<>(camisetas, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CamisetaEntity>> searchCamisetas(
            @RequestParam String searchText,
            @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<CamisetaEntity> camisetas = oCamisetaService.getPageBySearchIgnoreCase(searchText, pageable);
        return new ResponseEntity<>(camisetas, HttpStatus.OK);
    }

    @GetMapping("/descuento")
    public ResponseEntity<Page<CamisetaEntity>> getCamisetasConDescuento(
            @PageableDefault(size = 10, sort = {"porcentaje_descuento"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CamisetaEntity> camisetas = oCamisetaService.getPageCamisetasConDescuento(pageable);
        return new ResponseEntity<>(camisetas, HttpStatus.OK);
    }

    @GetMapping("/precio/{id}")
    public ResponseEntity<Double> getPrecioConDescuento(@PathVariable("id") Long id) {
        Double precioConDescuento = oCamisetaService.getPrecioTotal(id);
        return new ResponseEntity<>(precioConDescuento, HttpStatus.OK);
    }

    




    
}
