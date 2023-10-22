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
    List<Tuan> findByCanbo(CanBo macb);
    @Query("SELECT COUNT(t) FROM Tuan t WHERE t.canbo.maCB = :maCB")
    int countTuansByMaCB(@Param("maCB") Integer maCB);

}
