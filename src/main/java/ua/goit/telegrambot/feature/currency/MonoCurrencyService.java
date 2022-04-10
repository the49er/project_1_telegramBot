package ua.goit.telegrambot.feature.currency;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import ua.goit.telegrambot.feature.currency.dto.Currency;
import ua.goit.telegrambot.feature.currency.dto.CurrencyItemMono;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MonoCurrencyService implements CurrencyService{
    @Override
    public List<Double> getRate(Currency currency) {

        String url = "https://api.monobank.ua/bank/currency";

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
            throw new IllegalStateException("Can't connect to Mono API");
        }


        json = json.replace("840", "USD")
                .replace("978", "EUR")
                .replace("980", "UAH")
                .replace("643", "RUB");


        //Convert json => Java Object
        Type typeToken = TypeToken
                .getParameterized(List.class, CurrencyItemMono.class)
                .getType();
        List<CurrencyItemMono> currencyItemMono = new Gson().fromJson(json, typeToken);

        //Find currency
        /*return currencyItems.stream()
                .filter(it -> it.getCcy() == currency)
                .filter(it -> it.getBase_ccy() == Currency.UAH)
                .map(currencyItem -> currencyItem.getBuy())
                //.map(currencyItem -> currencyItem.getSale())
                .findFirst()
                .orElseThrow();*/

        Float monoBuy = currencyItemMono.stream()
                .filter(it -> it.getCurrencyCodeA() == currency)
                .filter(it -> it.getCurrencyCodeB() == Currency.UAH)
                .map(it -> it.getRateBuy())
                .findFirst()
                .orElseThrow();

        Float monoSele = currencyItemMono.stream()
                .filter(it -> it.getCurrencyCodeA() == currency)
                .filter(it -> it.getCurrencyCodeB() == Currency.UAH)
                .map(it -> it.getRateSell())
                .findFirst()
                .orElseThrow();

        List<Double> buySeal = new ArrayList<>();
        buySeal.add((double) monoBuy);
        buySeal.add((double) monoSele);

        return buySeal;
    }
}
