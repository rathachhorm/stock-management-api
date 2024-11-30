package com.innovisor.quickpos.customer;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customers_sequence")
    @SequenceGenerator(name = "customers_sequence", sequenceName = "customers_sequence", allocationSize = 1)
    private Integer id;
    @Column(name = "fullname", length = 100)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 10)
    private CustomerType type;

    @Column(name = "phone", length = 25)
    private String phone;

    @Column(name = "address_code", length = 8)
    private String addressCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10)
    private CustomerStatus status;

    private String remark;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
