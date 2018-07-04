package com.github.mgrzeszczak.erp.loader;

import com.github.mgrzeszczak.erp.nbp.NbpApi;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
@Slf4j
public class ExchangeDataLoader {

    private final NbpApi nbpApi;

    public ExchangeDataLoader(NbpApi nbpApi) {
        this.nbpApi = nbpApi;
    }

    @PostConstruct
    public void preloadData() {
        nbpApi.findExchangeRatesBetween("A", LocalDate.now().minusDays(10), LocalDate.now())
                .subscribeOn(Schedulers.io())
                .subscribe(v -> log.info("{}", v));
    }

}
