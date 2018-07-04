package com.github.mgrzeszczak.erp.repository;

import com.github.mgrzeszczak.erp.model.ExchangeRate;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    @Query("from ExchangeRate where date between ?1 and ?2")
    List<ExchangeRate> findAllBetween(LocalDate from, LocalDate to);

    default Flowable<ExchangeRate> rxFindAllBetween(@NotNull LocalDate from, @NotNull LocalDate to) {
        return Flowable.fromCallable(() -> findAllBetween(from, to))
                .flatMap(Flowable::fromIterable)
                .subscribeOn(Schedulers.io());
    }

}
