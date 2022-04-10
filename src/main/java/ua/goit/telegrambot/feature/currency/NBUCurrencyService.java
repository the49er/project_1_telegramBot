package ua.goit.telegrambot.feature.currency;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import ua.goit.telegrambot.feature.currency.dto.Currency;
import ua.goit.telegrambot.feature.currency.dto.CurrencyItemNBU;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NBUCurrencyService implements CurrencyService {

    @Override
    public List<Double> getRate(Currency currency) {
        String url = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

        //Get JSON
        String json;
        try {
            json = Jsoup
                    .connect(url)
                    .ignoreContentType(true)
                    .get()
                    .body()
                    .text();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Can't connect to NBU API");
        }

        //Convert json => Java Object
        Type typeToken = TypeToken
                .getParameterized(List.class, CurrencyItemNBU.class)
                .getType();
        List<CurrencyItemNBU> currencyItemsNBU = new Gson().fromJson(json, typeToken);

        //Find currency
        Float nbuBuy = currencyItemsNBU.stream()
                .filter(it -> it.getCc() == currency)
                .map(it -> it.getRate())
                .findFirst()
                .orElseThrow();

        List<Double> buySeal = new ArrayList<>();
        buySeal.add((double) nbuBuy);

        return buySeal;

        /*return currencyItemsNBU.stream()
                .filter(it -> it.getCc() == currency)
                .map(it -> it.getRate())
                .findFirst()
                .orElseThrow();*/
    }
}
