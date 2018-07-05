package com.github.mgrzeszczak.erp.nbp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NbpExchangeDataResponse {

    private String table;
    @JsonProperty("no")
    private String number;

    private LocalDate effectiveDate;
    private List<NbpExchangeRate> rates;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class NbpExchangeRate {

        private String currency;
        private String code;
        @JsonProperty("mid")
        private BigDecimal value;
    }

}
