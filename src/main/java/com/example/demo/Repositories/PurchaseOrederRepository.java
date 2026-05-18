package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.PurchaseOrder;

@Repository
public interface PurchaseOrederRepository extends JpaRepository<PurchaseOrder, Long> {}
