package ua.goit.telegrambot.settings;

public class User {
    private final int id;
    private String bank;
    private boolean usd;
    private boolean eur;
    private boolean rub;
    private int rounding;

    public User(int id) {
        this.id = id;
        bank = "NBU";
        usd = true;
        eur = true;
        rub = true;
        rounding = 2;
    }

    public int getId() {
        return id;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public boolean isUsd() {
        return usd;
    }

    public void setUsd(boolean usd) {
        this.usd = usd;
    }

    public boolean isEur() {
        return eur;
    }

    public void setEur(boolean eur) {
        this.eur = eur;
    }

    public boolean isRub() {
        return rub;
    }

    public void setRub(boolean rub) {
        this.rub = rub;
    }

    public int getRounding() {
        return rounding;
    }

    public void setRounding(int rounding) {
        this.rounding = rounding;
    }
}
