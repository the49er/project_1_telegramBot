import ua.goit.telegrambot.feature.currency.*;
import ua.goit.telegrambot.feature.currency.dto.Currency;

import java.util.List;

public class TelegramBotApp {
    public static void main(String[] args) {

        //Test NBU
        CurrencyService currencyServiceNBU = new NBUCurrencyService();
        List<Double> nbuRate = currencyServiceNBU.getRate(Currency.USD);
        System.out.println("NBU buy USD = " + nbuRate.get(0) + "\n");


        //Test Privat
        CurrencyService currencyServicePrivat = new PrivateBankCurrencyService();
        List<Double> privateRate = currencyServicePrivat.getRate(Currency.USD);
        System.out.println("Privat buy USD = " + privateRate.get(0));
        System.out.println("Privat sale USD = " + privateRate.get(1) + "\n");

        //Test Mono
        CurrencyService currencyServiceMono = new MonoCurrencyService();
        List<Double> monoRate = currencyServiceMono.getRate(Currency.USD);
        System.out.println("Mono buy USD = " + monoRate.get(0));
        System.out.println("Mono sale USD = " + monoRate.get(1));

    }

}
