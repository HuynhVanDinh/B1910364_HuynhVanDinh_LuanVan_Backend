package com.bezkoder.spring.security.jwt.repository;

import com.bezkoder.spring.security.jwt.entity.Khoa;
import com.bezkoder.spring.security.jwt.entity.ThoiGianDangKy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ThoiGianDangKyRepository extends JpaRepository<ThoiGianDangKy, Integer> {

    ThoiGianDangKy findByKhoa(Khoa khoa);

    boolean existsByKhoa(Khoa khoa);
    boolean existsByTgbdBeforeAndTgktAfter( LocalDate tgbd, LocalDate tgkt);
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM ThoiGianDangKy t WHERE t.tgkt > :tgbd AND t.tgbd < :tgkt AND t.id_tgdk <> :id")
    boolean existsByTgbdBeforeAndTgktAfterAndIdNot(
            @Param("tgkt") LocalDate tgkt,
            @Param("tgbd") LocalDate tgbd,
            @Param("id") Integer id
    );

}
