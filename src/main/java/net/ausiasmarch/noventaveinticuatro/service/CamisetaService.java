package net.ausiasmarch.noventaveinticuatro.service;

import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import net.ausiasmarch.noventaveinticuatro.entity.CamisetaEntity;
import net.ausiasmarch.noventaveinticuatro.entity.EquipoEntity;
import net.ausiasmarch.noventaveinticuatro.entity.LigaEntity;
import net.ausiasmarch.noventaveinticuatro.entity.ModalidadEntity;
import net.ausiasmarch.noventaveinticuatro.exception.ResourceNotFoundException;
import net.ausiasmarch.noventaveinticuatro.helper.DataGenerationHelper;
import net.ausiasmarch.noventaveinticuatro.repository.CamisetaRepository;

@Service
public class CamisetaService {

    @Autowired
    CamisetaRepository oCamisetaRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    @Autowired
    EquipoService oEquipoService;

    @Autowired
    ModalidadService oModalidadService;

    @Autowired
    LigaService oLigaService;

    @Autowired
    SessionService oSessionService;

    public CamisetaEntity get(Long id) {
        return oCamisetaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Error: Camiseta no encontrada."));
    }

    public Long create(CamisetaEntity oCamisetaEntity) {
        oSessionService.onlyAdmins();
        oCamisetaEntity.setId(null);
        return oCamisetaRepository.save(oCamisetaEntity).getId();
    }

    public CamisetaEntity update(CamisetaEntity oCamisetaEntity) {
        oSessionService.onlyAdmins();
        if (oCamisetaEntity.getId() == null) {
            throw new ResourceNotFoundException("Error: La camiseta no existe.");
        } else {
            return oCamisetaRepository.save(oCamisetaEntity);
        }
    }

    public Long delete(Long id) {
        oSessionService.onlyAdmins();
        if (oCamisetaRepository.existsById(id)) {
            oCamisetaRepository.deleteById(id);
            return id;
        } else {
            throw new ResourceNotFoundException("Error: La camiseta no existe.");
        }
    }

    // public Page<CamisetaEntity> getPage(Pageable oPageable) {
    //     return oCamisetaRepository.findAll(oPageable);
    // }

    

    public Page<CamisetaEntity> getPage(Pageable oPageable, Long equipo_id, Long modalidad_id, Long liga_id ) {
        oSessionService.onlyAdminsOUsuarios();
        if (equipo_id == 0) {
            if (modalidad_id == 0) {
                if (liga_id == 0) {
                    return oCamisetaRepository.findAll(oPageable);
                } else {
                    return oCamisetaRepository.findByLigaId(liga_id, oPageable);
                }
            } else {
                if (liga_id == 0) {
                    return oCamisetaRepository.findByModalidadId(modalidad_id, oPageable);
                } else {
                    return oCamisetaRepository.findByModalidadIdAndLigaId(modalidad_id, liga_id, oPageable);
                }
            }
        } else {
            if (modalidad_id == 0) {
                if (liga_id == 0) {
                    return oCamisetaRepository.findByEquipoId(equipo_id, oPageable);
                } else {
                    return oCamisetaRepository.findByEquipoIdAndLigaId(equipo_id, liga_id, oPageable);
                }
            } else {
                if (liga_id == 0) {
                    return oCamisetaRepository.findByEquipoIdAndModalidadId(equipo_id, modalidad_id, oPageable);
                } else {
                    return oCamisetaRepository.findByEquipoIdAndModalidadIdAndLigaId(equipo_id, modalidad_id, liga_id, oPageable);
                }
            }
        }
    }

    public CamisetaEntity getOneRandom() {
        Pageable oPageable = PageRequest.of((int) (Math.random() * oCamisetaRepository.count()), 1);
        return oCamisetaRepository.findAll(oPageable).getContent().get(0);
    }

    @Transactional
    public void actualizarStock(CamisetaEntity oCamisetaEntity, int cantidad) {
        CamisetaEntity camisetaEncontrada = oCamisetaRepository.findById(oCamisetaEntity.getId()).orElseThrow(() -> new ResourceNotFoundException("Error: Camiseta no encontrada."));

        if (camisetaEncontrada != null) {
            int stockActual = camisetaEncontrada.getStock();
            int nuevoStock = stockActual - cantidad;
            if (nuevoStock < 0) {
                nuevoStock = 0;
            }
            camisetaEncontrada.setStock(nuevoStock);
            oCamisetaRepository.save(camisetaEncontrada);
        }
    }

    public Page<CamisetaEntity> getPageByEquipoId(Long equipo_id, Pageable oPageable) {
        return oCamisetaRepository.findByEquipoId(equipo_id, oPageable);
    }

    public Page<CamisetaEntity> getPageByModalidadId(Long modalidad_id, Pageable oPageable) {
        return oCamisetaRepository.findByModalidadId(modalidad_id, oPageable);
    }

    public Page<CamisetaEntity> getPageByLigaId(Long liga_id, Pageable oPageable) {
        return oCamisetaRepository.findByLigaId(liga_id, oPageable);
    }

    public Page<CamisetaEntity> getPageCamisetasMasVendidas(Pageable oPageable) {
        return oCamisetaRepository.findCamisetasMasVendidas(oPageable);
    }

    public Page<CamisetaEntity> getPageBySearchIgnoreCase(String searchText, Pageable oPageable) {
        return oCamisetaRepository.findBySearchIgnoreCase(searchText, oPageable);
    }

    public Page<CamisetaEntity> getPageCamisetasConDescuento(Pageable oPageable) {
        return oCamisetaRepository.findCamisetasConDescuento(oPageable);
    }

    public Double getPrecioTotal(Long id) {
        return oCamisetaRepository.getPrecioTotal(id);
    }

    public Long populate(int amount) {
        oSessionService.onlyAdmins();
        for (int i = 0; i<amount; i++) {
            String titulo = DataGenerationHelper.generarTituloCamisetaRandom();
            String talla = DataGenerationHelper.getTallaCamisetaRandom();
            String manga = DataGenerationHelper.getMangaCamisetaRandom();
            String temporada = "2023/2024";
            Double precio = DataGenerationHelper.getRandomDouble(50, 100);
            Double iva = DataGenerationHelper.getRandomDouble(0, 21);
            int stock = DataGenerationHelper.getRandomInt(0, 100);
            EquipoEntity equipo = oEquipoService.getOneRandom();
            ModalidadEntity modalidad = oModalidadService.getOneRandom();
            LigaEntity liga = oLigaService.getOneRandom();
            oCamisetaRepository.save(new CamisetaEntity(titulo, talla, manga, temporada, precio, iva, stock, equipo, modalidad, liga));
        }
        return oCamisetaRepository.count();
    }

    @Transactional
    public Long empty() {
        oSessionService.onlyAdmins();
        oCamisetaRepository.deleteAll();
        oCamisetaRepository.resetAutoIncrement();
        oCamisetaRepository.flush();
        return oCamisetaRepository.count();
    }
    
    
}
