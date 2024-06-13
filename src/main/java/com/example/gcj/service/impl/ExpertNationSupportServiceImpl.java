package com.example.gcj.service.impl;

import com.example.gcj.dto.expert_nation_support.ExpertNationSupportResponseDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.ExpertNationSupport;
import com.example.gcj.repository.ExpertNationSupportRepository;
import com.example.gcj.repository.ExpertRepository;
import com.example.gcj.service.ExpertNationSupportService;
import com.example.gcj.service.ExpertService;
import com.example.gcj.service.UserService;
import com.example.gcj.util.Status;
import com.example.gcj.util.mapper.ExpertNationSupportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertNationSupportServiceImpl implements ExpertNationSupportService {
    private final ExpertNationSupportRepository expertNationSupportRepository;
    private final ExpertRepository expertRepository;

    @Override
    public boolean create(long expertId, List<String> nations) {
        if (nations == null || nations.isEmpty()) {
            return false;
        }

        boolean isExistExpert = expertRepository.existsById(expertId);
        if (!isExistExpert) {
            throw new CustomException("expert not found with id " + expertId);
        }

        for (String nation : nations) {
            if (nation == null) {
                continue;
            }
            ExpertNationSupport _expertNationSupport = expertNationSupportRepository.findByNationAndExpertId(nation, expertId);
            if (_expertNationSupport == null) {
                ExpertNationSupport expertNationSupport = ExpertNationSupport
                        .builder()
                        .expertId(expertId)
                        .nation(nation)
                        .status(Status.ACTIVE)
                        .build();

                expertNationSupportRepository.save(expertNationSupport);
                continue;
            }

            if (_expertNationSupport.getStatus() == 0) {
                _expertNationSupport.setStatus(1);
                expertNationSupportRepository.save(_expertNationSupport);
            }
        }

        return true;
    }

    @Override
    public List<ExpertNationSupportResponseDTO> getByExpertId(long expertId) {
        List<ExpertNationSupport> expertNationSupports = expertNationSupportRepository.findByExpertId(expertId);
        return expertNationSupports.stream().map(ExpertNationSupportMapper::toDto).toList();
    }

    @Override
    public boolean deleteAllByExpertId(long expertId) {
        int rowAffected = expertNationSupportRepository.updateStatusByExpertId(expertId);
        return rowAffected > 0;
    }
}
