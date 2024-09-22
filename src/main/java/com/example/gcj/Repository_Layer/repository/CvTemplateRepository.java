package com.example.gcj.Repository_Layer.repository;

import com.example.gcj.Repository_Layer.model.CvTemplate;
import com.example.gcj.Service_Layer.dto.cv_template.CvTemplateListDetailResponseDTO;
import com.example.gcj.Service_Layer.dto.dash_board.CountCvTemplateDashBoardResponseDTO;
import com.example.gcj.Service_Layer.dto.dash_board.TopUseCvTemplateResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CvTemplateRepository extends JpaRepository<CvTemplate, Long> {

    @Query("SELECT c FROM CvTemplate c WHERE c.id = :id AND c.status != 0")
    CvTemplate findById(long id);

    @Query("SELECT c FROM CvTemplate c WHERE c.categoryId = :categoryId and c.status != 0")
    List<CvTemplate>  findByCategoryId(@Param("categoryId") long categoryId);

    @Query("SELECT c FROM CvTemplate c WHERE c.status != 0")
    List<CvTemplate>  findAll();

    @Query("SELECT new com.example.gcj.Service_Layer.dto.cv_template.CvTemplateListDetailResponseDTO(ct.id, ct.categoryId, cc.name, ct.name, ct.image, ct.status, COUNT(c.id), ct.createdAt) " +
            "FROM CvTemplate ct " +
            "LEFT JOIN Cv c ON c.cvTemplateId = ct.id " +
            "INNER JOIN CvCategory cc ON ct.categoryId = cc.id " +
            "WHERE (:categoryId IS NULL OR ct.categoryId = :categoryId) " +
            "AND (:status IS NULL OR ct.status = :status) AND ct.status != 0 " +
            "GROUP BY ct.id, ct.categoryId, cc.name, ct.name, ct.image, ct.status, ct.createdAt")
    List<CvTemplateListDetailResponseDTO> getAndCountNumberUse(Long categoryId, Integer status);
    @Query("SELECT new com.example.gcj.Service_Layer.dto.dash_board.CountCvTemplateDashBoardResponseDTO(cc.id, cc.name, COUNT(ct.id)) " +
            "FROM CvCategory cc LEFT JOIN CvTemplate ct ON ct.categoryId = cc.id " +
            "GROUP BY cc.id, cc.name")
    List<CountCvTemplateDashBoardResponseDTO> countCvTemplate();


    @Query("SELECT new com.example.gcj.Service_Layer.dto.dash_board.TopUseCvTemplateResponseDTO(ct.id, cc.name, count(c.id) as countUsage, ct.name, ct.image) " +
            "FROM CvTemplate ct " +
            "LEFT JOIN Cv c ON c.cvTemplateId = ct.id " +
            "INNER JOIN CvCategory cc ON ct.categoryId = cc.id " +
            "WHERE ct.status != 0 " +
            "GROUP BY ct.id, ct.categoryId, cc.name, ct.name, ct.image, ct.status, ct.createdAt")
    List<TopUseCvTemplateResponseDTO> getAndCountNumberUse(Pageable pageable);

}
