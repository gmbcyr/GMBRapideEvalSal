package com.gmb.gmbrapideevalsal.tools;

/**
 * Created by GMB on 11/27/2016.
 */

public class TaxeDeductionProfile {

    final double singleAddDeduction=1400;
    final double mariedAddDeduction=1100;
    final double dependantMinAdd=950;
    final double dependantMaxAdd=5700;
    final double dependantCurrentAdd=300;

    String nom;
    String title;
    double amount;
    double deducAmount;
    int year;
    int nbreDependant;
    boolean married;

    boolean atLeast65;
    boolean blind;
    boolean spouse65;
    boolean spouseblind;

    boolean single;
    boolean marrieds;
    boolean marriedj;
    boolean headh;
    boolean marriedd;


    /*static constraints = {
        nom blank:false, inList: ["single", "marrieds", "marriedj","headh","marriedd"]
        title blank:false
        amount min:0 as double


    }*/



    public TaxeDeductionProfile(String nom, String title, double amount,
                                      int year, int nbreDependant, boolean married, boolean atLeast65,
                                      boolean blind, boolean spouse65, boolean spouseblind) {
       // super();

        this.deducAmount=0;
        this.nom = nom;
        this.title = title;
        this.amount = amount;
        this.year = year;
        this.nbreDependant = nbreDependant;
        this.married = married;
        this.atLeast65 = atLeast65;
        this.blind = blind;
        this.spouse65 = spouse65;
        this.spouseblind = spouseblind;
    }


    public TaxeDeductionProfile(String nom, String title, double amount, int year) {
        super();
        this.deducAmount=0;
        this.nom = nom;
        this.title = title;
        this.amount = amount;
        this.year = year;
    }


    public double getSingleAddDeduction() {
        return singleAddDeduction;
    }

    public double getMariedAddDeduction() {
        return mariedAddDeduction;
    }

    public double getDependantMinAdd() {
        return dependantMinAdd;
    }

    public double getDependantMaxAdd() {
        return dependantMaxAdd;
    }

    public double getDependantCurrentAdd() {
        return dependantCurrentAdd;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getDeducAmount() {
        return deducAmount;
    }

    public void setDeducAmount(double deducAmount) {
        this.deducAmount = deducAmount;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNbreDependant() {
        return nbreDependant;
    }

    public void setNbreDependant(int nbreDependant) {
        this.nbreDependant = nbreDependant;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public boolean isAtLeast65() {
        return atLeast65;
    }

    public void setAtLeast65(boolean atLeast65) {
        this.atLeast65 = atLeast65;
    }

    public boolean isBlind() {
        return blind;
    }

    public void setBlind(boolean blind) {
        this.blind = blind;
    }

    public boolean isSpouse65() {
        return spouse65;
    }

    public void setSpouse65(boolean spouse65) {
        this.spouse65 = spouse65;
    }

    public boolean isSpouseblind() {
        return spouseblind;
    }

    public void setSpouseblind(boolean spouseblind) {
        this.spouseblind = spouseblind;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public boolean isMarrieds() {
        return marrieds;
    }

    public void setMarrieds(boolean marrieds) {
        this.marrieds = marrieds;
    }

    public boolean isMarriedj() {
        return marriedj;
    }

    public void setMarriedj(boolean marriedj) {
        this.marriedj = marriedj;
    }

    public boolean isHeadh() {
        return headh;
    }

    public void setHeadh(boolean headh) {
        this.headh = headh;
    }

    public boolean isMarriedd() {
        return marriedd;
    }

    public void setMarriedd(boolean marriedd) {
        this.marriedd = marriedd;
    }
}
