package com.example.sem4_andproject.ui.quotes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sem4_andproject.data.quotes.Quote;
import com.example.sem4_andproject.data.quotes.QuotesApi;
import com.example.sem4_andproject.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuoteFragment extends Fragment {

    TextView quoteView;
    TextView authorView;

    LinearLayout root;
    LinearLayout child;

    public QuoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quote, container, false);
        setElements(view);
        return view;
    }
    public void setElements(View view)
    {
        quoteView = view.findViewById(R.id.quoteView);
        authorView = view.findViewById(R.id.authorView);

        root = view.findViewById(R.id.rootQuoteLayout);
        child = view.findViewById(R.id.childQuoteLayout);
        getQuote();

    }
    public void getQuote()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.forismatic.com/api/1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        QuotesApi quotesApi = retrofit.create(QuotesApi.class);

        Call<Quote> call = quotesApi.getQuote();

        call.enqueue(new Callback<Quote>() {
            @Override
            public void onResponse(Call<Quote> call, Response<Quote> response) {
                if (!response.isSuccessful())
                {
                    quoteView.setText("Code: " + response.code());
                    return;
                }

                quoteView.setText(response.body().getQuoteText());
                if (response.body().getQuoteAuthor().equals(""))
                    authorView.setText("Unknown Author");
                else
                authorView.setText("\"" + response.body().getQuoteAuthor() + "\"");
            }

            @Override
            public void onFailure(Call<Quote> call, Throwable t) {
                quoteView.setText(t.getMessage());
            }
        });
    }
}