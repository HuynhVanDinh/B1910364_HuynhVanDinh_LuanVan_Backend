package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.CanBo;
import com.bezkoder.spring.security.jwt.entity.Tuan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TuanRepository extends JpaRepository<Tuan, Integer> {
    List<Tuan> findByCanboAndIsComplete(CanBo macb, int isComplete);
    List<Tuan> findByIsComplete(int isComplete);
//    @Query("SELECT COUNT(t) FROM Tuan t WHERE t.canbo.maCB = :maCB")
//    int countTuansByMaCBAndIsComplete(@Param("maCB") Integer maCB,int isComplete);
@Query("SELECT COUNT(t) FROM Tuan t WHERE t.canbo.maCB = :maCB AND t.isComplete = :isComplete")
int countTuansByMaCBAndIsComplete(@Param("maCB") Integer maCB, @Param("isComplete") int isComplete);

}
