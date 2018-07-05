package com.github.mgrzeszczak.erp.loader;

import com.github.mgrzeszczak.erp.utils.Pair;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
class LocalDateRangeGenerator {

    @NotNull
    List<Pair<LocalDate, LocalDate>> generate(@NotNull LocalDate from, @NotNull LocalDate to, int stepDays) {
        LocalDate current = from;
        LocalDate end = null;
        List<Pair<LocalDate, LocalDate>> values = new ArrayList<>();
        do {
            end = current.plusDays(stepDays);
            end = end.isAfter(to) ? to : end;
            values.add(new Pair<>(current, end));
            current = end.plusDays(1);
        }
        while (!end.isEqual(to));
        return values;
    }


}
