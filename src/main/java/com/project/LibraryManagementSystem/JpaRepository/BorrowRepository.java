package com.project.LibraryManagementSystem.JpaRepository;

import com.project.LibraryManagementSystem.entity.BorrowHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BorrowRepository extends JpaRepository<BorrowHistory, Long> {

    List<BorrowHistory> findByUserUsername(String Username);

    List<BorrowHistory> findByStatus(String pending);
}
