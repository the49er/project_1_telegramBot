package ua.goit.telegrambot.feature.currency;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import ua.goit.telegrambot.feature.currency.dto.Currency;
import ua.goit.telegrambot.feature.currency.dto.CurrencyItemPrivat;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PrivateBankCurrencyService implements CurrencyService {

    @Override
    public List<Double> getRate(Currency currency) {

        String url = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";

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
            throw new IllegalStateException("Can't connect to Privat API");
        }

        //Convert json => Java Object
        Type typeToken = TypeToken
                .getParameterized(List.class, CurrencyItemPrivat.class)
                .getType();
        List<CurrencyItemPrivat> currencyItemPrivats = new Gson().fromJson(json, typeToken);

        //Find currency
        /*return currencyItems.stream()
                .filter(it -> it.getCcy() == currency)
                .filter(it -> it.getBase_ccy() == Currency.UAH)
                .map(currencyItem -> currencyItem.getBuy())
                //.map(currencyItem -> currencyItem.getSale())
                .findFirst()
                .orElseThrow();*/

        Float privatBuy = currencyItemPrivats.stream()
                .filter(it -> it.getCcy() == currency)
                .filter(it -> it.getBase_ccy() == Currency.UAH)
                .map(currencyItemPrivat -> currencyItemPrivat.getBuy())
                .findFirst()
                .orElseThrow();

        Float privatSele = currencyItemPrivats.stream()
                .filter(it -> it.getCcy() == currency)
                .filter(it -> it.getBase_ccy() == Currency.UAH)
                .map(currencyItemPrivat -> currencyItemPrivat.getSale())
                .findFirst()
                .orElseThrow();

        List<Double> buySeal = new ArrayList<>();
        buySeal.add((double) privatBuy);
        buySeal.add((double) privatSele);

        return buySeal;
    }
}
