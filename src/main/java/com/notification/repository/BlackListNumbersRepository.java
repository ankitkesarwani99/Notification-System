package com.notification.repository;

import com.notification.data.model.BlackListNumbers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListNumbersRepository extends JpaRepository<BlackListNumbers,String> {
}
