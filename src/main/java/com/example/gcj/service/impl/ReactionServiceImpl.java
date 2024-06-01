package com.example.gcj.service.impl;

import com.example.gcj.dto.react.CreateReactionRequestDTO;
import com.example.gcj.dto.react.ReactionResponseDTO;
import com.example.gcj.exception.CustomException;
import com.example.gcj.model.Reaction;
import com.example.gcj.repository.ReactionRepository;
import com.example.gcj.service.ReactionService;
import com.example.gcj.util.mapper.ReactionMapper;
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
