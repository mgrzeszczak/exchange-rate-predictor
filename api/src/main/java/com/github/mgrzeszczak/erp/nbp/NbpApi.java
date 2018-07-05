package com.github.mgrzeszczak.erp.nbp;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import java.time.LocalDate;
import java.util.List;


public interface NbpApi {

    @Headers({
            "Accept: application/json"
    })
    @GET("exchangerates/tables/{table}/{startDate}/{endDate}")
    Single<List<NbpExchangeDataResponse>> findExchangeRatesBetween(@Path("table") String table,
                                                                  @Path("startDate") LocalDate startDate,
                                                                  @Path("endDate") LocalDate endDate);

}
