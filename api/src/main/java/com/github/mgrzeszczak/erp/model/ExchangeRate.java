package com.github.mgrzeszczak.erp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(indexes = {
        @Index(name = "ExchangeRateDateIndex", columnList = "date")
})
public class ExchangeRate {

    @Id
    @GeneratedValue
    private Long id;
    private BigDecimal value;
    private String currency;
    private LocalDate date;
    private String code;

}
