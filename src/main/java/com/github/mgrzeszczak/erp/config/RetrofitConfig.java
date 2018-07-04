package com.github.mgrzeszczak.erp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mgrzeszczak.erp.nbp.NbpApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class RetrofitConfig {

    @Bean
    public Retrofit retrofit(ObjectMapper objectMapper) {
        return new Retrofit.Builder()
                .baseUrl("http://api.nbp.pl/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();
    }

    @Bean
    public NbpApi nbpApi(Retrofit retrofit) {
        return retrofit.create(NbpApi.class);
    }

}
