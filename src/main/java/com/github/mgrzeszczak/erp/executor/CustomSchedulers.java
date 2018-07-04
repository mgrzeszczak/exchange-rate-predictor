package com.github.mgrzeszczak.erp.executor;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;

@Slf4j
public class CustomSchedulers {

    private CustomSchedulers() {
        throw new AssertionError("no instances");
    }

    public final static Scheduler JPA_SCHEDULER = Schedulers.from(Executors.newFixedThreadPool(8));

}
