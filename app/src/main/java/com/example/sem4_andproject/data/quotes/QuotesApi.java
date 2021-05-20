package com.example.sem4_andproject.data.quotes;

import com.example.sem4_andproject.data.quotes.Quote;

import retrofit2.Call;
import retrofit2.http.POST;

public interface QuotesApi
{
    @POST("?method=getQuote&key=457553&format=json&lang=en")
    Call<Quote> getQuote();
}
