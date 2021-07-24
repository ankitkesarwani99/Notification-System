package com.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.notification.data.model.SmsRecord;

@Repository
public interface NotificationRepository extends JpaRepository<SmsRecord,Integer> {



}
