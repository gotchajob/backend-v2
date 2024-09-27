package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.ExpertNationSupport;
import com.example.gcj.Repository_Layer.repository.ExpertNationSupportRepository;
import com.example.gcj.Repository_Layer.repository.ExpertRepository;
import com.example.gcj.Service_Layer.dto.expert_nation_support.CreateNationSupportRequestDTO;
import com.example.gcj.Service_Layer.dto.expert_nation_support.ExpertNationSupportResponseDTO;
import com.example.gcj.Service_Layer.dto.expert_nation_support.UpdateNationSupportListRequestDTO;
import com.example.gcj.Service_Layer.dto.expert_nation_support.UpdateNationSupportRequestDTO;
import com.example.gcj.Service_Layer.service.ExpertNationSupportService;
import com.example.gcj.Service_Layer.service.ShareService;
import com.example.gcj.Shared.exception.CustomException;
import com.example.gcj.Shared.util.Status;
import com.example.gcj.Service_Layer.mapper.ExpertNationSupportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertNationSupportServiceImpl implements ExpertNationSupportService, ShareService<ExpertNationSupport> {
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
            if (nation == null || nations.isEmpty()) {
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

    @Override
    public boolean createNation(long expertId, CreateNationSupportRequestDTO request) {
        ExpertNationSupport byNationAndExpertId = expertNationSupportRepository.findByNationAndExpertIdAndStatus(request.getNation(), expertId, 1);
        if (byNationAndExpertId!= null) {
            throw new CustomException("quốc gia hỗ trợ đã tồn tại");
        }

        ExpertNationSupport build = ExpertNationSupport
                .builder()
                .expertId(expertId)
                .nation(request.getNation())
                .status(1)
                .build();
        save(build);

        return true;
    }

    @Override
    public boolean update(long expertId, long id, UpdateNationSupportRequestDTO request) {
        ExpertNationSupport byNationAndExpertId = expertNationSupportRepository.findByNationAndExpertIdAndStatus(request.getNation(), expertId, 1);
        if (byNationAndExpertId!= null) {
            throw new CustomException("quốc gia hỗ trợ đã tồn tại");
        }

        ExpertNationSupport expertNationSupport = get(id);
        checkAuthor(expertNationSupport, expertId);

        expertNationSupport.setNation(request.getNation());
        save(expertNationSupport);

        return true;
    }

    @Override
    public boolean delete(long expertId, long id) {
        ExpertNationSupport expertNationSupport = get(id);
        checkAuthor(expertNationSupport, expertId);

        expertNationSupport.setStatus(0);
        save(expertNationSupport);

        return true;
    }

    @Override
    public boolean update(long expertId, UpdateNationSupportListRequestDTO nations) {
        if (nations == null) {
            throw new CustomException("bad request");
        }
        expertNationSupportRepository.updateStatusByExpertId(expertId);

        create(expertId, nations.getNations());

        return true;
    }

    private void checkAuthor(ExpertNationSupport expertNationSupport, long expertId) {
        if (expertNationSupport == null) {
            return;
        }

        if (expertNationSupport.getExpertId() != expertId) {
            throw new CustomException("expert nation support not yours");
        }
    }

    @Override
    public ExpertNationSupport get(long id) {
        ExpertNationSupport expertNationSupport = expertNationSupportRepository.findById(id);
        if (expertNationSupport == null) {
            throw new CustomException("not found expert nation support ");
        }

        return expertNationSupport;
    }
    @Override
    public ExpertNationSupport save(ExpertNationSupport entity) {
        if (entity == null) {
            return null;
        }

        return expertNationSupportRepository.save(entity);
    }
}
