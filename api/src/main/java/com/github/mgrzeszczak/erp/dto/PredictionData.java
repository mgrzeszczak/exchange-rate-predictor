package com.github.mgrzeszczak.erp.dto;

import com.github.mgrzeszczak.erp.model.ExchangeRate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NotNull
public class PredictionData {

    private final String code;
    private final List<ExchangeRate> real;
    private final List<ExchangeRate> predicted;

}
