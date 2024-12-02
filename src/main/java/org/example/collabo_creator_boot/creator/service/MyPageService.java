package org.example.collabo_creator_boot.creator.service;

import lombok.RequiredArgsConstructor;
import org.example.collabo_creator_boot.creator.domain.CreatorEntity;
import org.example.collabo_creator_boot.creator.dto.MyPageDTO;
import org.example.collabo_creator_boot.creator.repository.MyPageRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MyPageRepository myPageRepository;

    public MyPageDTO getMyPage(String creatorId) {
        CreatorEntity creator = myPageRepository.findById(creatorId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Creator ID입니다."));
        return new MyPageDTO(
                creator.getCreatorName(),
                creator.getCreatorEmail(),
                creator.getCreatorPhone(),
                creator.getCreatorBank(),
                creator.getCreatorAccount(),
                creator.getBackgroundImg(),
                creator.getLogoImg(),
                creator.getEmailNotifications(),
                creator.getSmsNotifications()
        );
    }

    public void updateMyPage(String creatorId, MyPageDTO myPageDTO) {
        CreatorEntity existingCreator = myPageRepository.findById(creatorId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Creator ID입니다."));

        CreatorEntity updatedCreator = CreatorEntity.builder()
                .creatorId(existingCreator.getCreatorId())
                .creatorName(myPageDTO.getCreatorName())
                .creatorEmail(myPageDTO.getCreatorEmail())
                .creatorPhone(myPageDTO.getCreatorPhone())
                .creatorBank(myPageDTO.getCreatorBank())
                .creatorAccount(myPageDTO.getCreatorAccount())
                .backgroundImg(myPageDTO.getBackgroundImg())
                .logoImg(myPageDTO.getLogoImg())
                .emailNotifications(myPageDTO.getEmailNotifications())
                .smsNotifications(myPageDTO.getSmsNotifications())
                .build();

        myPageRepository.save(updatedCreator);
    }
}
