package com.example.gcj.service.impl;

import com.example.gcj.dto.expert_nation_support.ExpertNationSupportResponseDTO;
import com.example.gcj.model.ExpertNationSupport;
import com.example.gcj.repository.ExpertNationSupportRepository;
import com.example.gcj.service.ExpertNationSupportService;
import com.example.gcj.util.mapper.ExpertNationSupportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertNationSupportServiceImpl implements ExpertNationSupportService {
    private final ExpertNationSupportRepository expertNationSupportRepository;

    @Override
    public boolean create(long expertId, List<String> nations) {
        if (nations.isEmpty()) {
            return false;
        }

        for (String nation : nations) {
            if (nation == null) {
                continue;
            }
            ExpertNationSupport _expertNationSupport = expertNationSupportRepository.findByNation(nation);
            if (_expertNationSupport != null) {
                continue;
            }

            ExpertNationSupport expertNationSupport = ExpertNationSupport
                    .builder()
                    .expertId(expertId)
                    .nation(nation)
                    .build();

            expertNationSupportRepository.save(expertNationSupport);
        }

        return true;
    }

    @Override
    public List<ExpertNationSupportResponseDTO> getByExpertId(long expertId) {
        List<ExpertNationSupport> expertNationSupports = expertNationSupportRepository.findByExpertId(expertId);
        return expertNationSupports.stream().map(ExpertNationSupportMapper::toDto).toList();
    }
}
