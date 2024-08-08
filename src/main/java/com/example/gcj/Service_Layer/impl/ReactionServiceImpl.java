package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.Reaction;
import com.example.gcj.Repository_Layer.repository.ReactionRepository;
import com.example.gcj.Service_Layer.dto.react.CreateReactionRequestDTO;
import com.example.gcj.Service_Layer.dto.react.ReactionResponseDTO;
import com.example.gcj.Service_Layer.service.ReactionService;
import com.example.gcj.Shared.exception.CustomException;
import com.example.gcj.Service_Layer.mapper.ReactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {
    private final ReactionRepository reactionRepository;

    @Override
    public List<ReactionResponseDTO> getAll() {
        List<Reaction> reactionList = reactionRepository.findAll();
        return reactionList.stream().map(ReactionMapper::toDto).toList();
    }

    @Override
    public void create(CreateReactionRequestDTO request) {
        if(request == null) {
            throw new CustomException("Null request");
        }
        Reaction reaction = Reaction.builder()
                .icon(request.getIcon())
                .build();
        reactionRepository.save(reaction);
    }
}
