package net.ausiasmarch.noventaveinticuatro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.noventaveinticuatro.entity.CaptchaEntity;

public interface CaptchaRepository extends JpaRepository<CaptchaEntity, Long> {

    
} 