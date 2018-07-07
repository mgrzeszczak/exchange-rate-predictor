package com.github.mgrzeszczak.erp.repository;

import com.github.mgrzeszczak.erp.model.ExchangeRate;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    @Query("from ExchangeRate where date >= ?1 and date <= ?2 and code in ?3")
    List<ExchangeRate> findAllBetween(LocalDate from, LocalDate to, Collection<String> codes);

    @Query("select DISTINCT(r.code) from ExchangeRate r")
    List<String> findDistinctCodes();

    @NotNull
    default Flowable<String> rxFindDistinctCodes() {
        return Flowable.fromCallable(this::findDistinctCodes)
                .flatMap(Flowable::fromIterable)
                .subscribeOn(Schedulers.io());
    }

    @NotNull
    default Flowable<ExchangeRate> rxFindAllBetween(@NotNull LocalDate from,
                                                    @NotNull LocalDate to,
                                                    @NotNull Collection<String> codes) {
        return Flowable.fromCallable(() -> findAllBetween(from, to, codes))
                .flatMap(Flowable::fromIterable)
                .subscribeOn(Schedulers.io());
    }

}
