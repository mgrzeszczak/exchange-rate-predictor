package com.github.mgrzeszczak.erp.converter;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateConverter implements Converter<String, LocalDate> {

    @Override
    @NotNull
    public LocalDate convert(@NotNull String source) {
        return LocalDate.parse(source, DateTimeFormatter.ISO_LOCAL_DATE);
    }

}
