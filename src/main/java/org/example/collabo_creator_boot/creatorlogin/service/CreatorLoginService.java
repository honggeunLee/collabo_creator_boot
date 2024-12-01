package org.example.collabo_creator_boot.creatorlogin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.collabo_creator_boot.creator.domain.CreatorEntity;
import org.example.collabo_creator_boot.creatorlogin.dto.CreatorLoginDTO;
import org.example.collabo_creator_boot.creatorlogin.exception.CreatorLoginException;
import org.example.collabo_creator_boot.creatorlogin.repository.CreatorLoginRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CreatorLoginService {

    private final CreatorLoginRepository creatorLoginRepository;

    private final PasswordEncoder passwordEncoder;

    public CreatorLoginDTO authenticate(String creatorId, String creatorPassword){

        Optional<CreatorEntity> result = creatorLoginRepository.findById(creatorId);

        CreatorEntity creator = result.orElseThrow(() -> CreatorLoginException.BAD_AUTH.getException());

        String enterPw = creator.getCreatorPassword();
        boolean match = passwordEncoder.matches(creatorPassword, enterPw);

        if(!match){
            log.info("Creator ID and Admin PW do not match");
            throw CreatorLoginException.BAD_AUTH.getException();
        }

        CreatorLoginDTO creatorLoginDTO = new CreatorLoginDTO();
        creatorLoginDTO.setCreatorID(creatorId);
        creatorLoginDTO.setCreatorPassword(creatorPassword);
        creatorLoginDTO.setCreatorName(creator.getCreatorName());

        return creatorLoginDTO;
    }
}
