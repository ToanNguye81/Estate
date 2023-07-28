package com.project.estate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.estate.entity.Voucher;

public interface IVoucherRepository extends JpaRepository<Voucher, Long> {

}
