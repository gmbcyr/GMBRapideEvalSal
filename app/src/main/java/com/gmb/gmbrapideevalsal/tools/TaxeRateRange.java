package com.gmb.gmbrapideevalsal.tools;

class TaxeRateRange {

	String nom;
	String title;
	double taux;
	double minim;
	double maxim;
	boolean single;
	boolean marrieds;
	boolean marriedj;
	boolean headh;
	int year=2017;
	
	
    /*static constraints = {
		nom blank:false, inList: ["single", "marrieds", "marriedj","headh"];
		title blank:false;
		taux min:0 as double;
		minim min:0 as double
		maxim min:0 as double

		String title;
		double taux,minim,maxim;
		
		
    }*/
	
	public TaxeRateRange(int year,String name,boolean seul,boolean maries,boolean mariej,boolean head){
        this.year=year;
		nom=name;
		title="Unknow";
		taux=0;
		minim=0;
		maxim=1;
		single=seul;
		marrieds=maries;
		marriedj=mariej;
		headh=head;
	}
	
	public TaxeRateRange(int year,String name,String titre,double tau,double mini,double maxi,boolean seul,boolean maries,boolean mariej,boolean head){
		this.year=year;
		nom=name;
		title=titre;
		taux=tau;
		minim=mini;
		maxim=maxi;
		single=seul;
		marrieds=maries;
		marriedj=mariej;
		headh=head;
	}

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public double getTaux() {
        return taux;
    }

    public void setTaux(double taux) {
        this.taux = taux;
    }

    public double getMinim() {
        return minim;
    }

    public void setMinim(double minim) {
        this.minim = minim;
    }

    public double getMaxim() {
        return maxim;
    }

    public void setMaxim(double maxim) {
        this.maxim = maxim;
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
}
