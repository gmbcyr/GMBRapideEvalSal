package com.gmb.gmbrapideevalsal.tools;

import android.util.Log;

import java.text.NumberFormat;
import java.util.HashMap;

public class Trait implements convert {

	private double taux=500;
	private double heureParJour=8;
	private double jourParSemaine=5;
	
	static HashMap<String,TaxeRateRange> listDispo;
	static HashMap<String,TaxeDeductionProfile> listDeduc;
	


	public Trait(){

		initListDeduction();

		initListDispo();
	}
	
	
	
	public ResultSalCal getPayHourToYear(int year,int dependant,String status, boolean useTaxe, double hpday,
										 double dpweek, double hRate, double maxHpWeek, double hSupRate, double nOT2, double ot2Rate){
		
		ResultSalCal res;
		
		double gainT=0;
		double gainNet=0;
		double hpWeek=hpday*dpweek;
		double OTh=0;
		double OTpay=0;
		double regularPay=0;
		double regularH=maxHpWeek;
		double OT2H=nOT2;
		double OT2pay=0;
		
		if(hpWeek>=maxHpWeek){
			
			 OTh=hpWeek-maxHpWeek;
			 OTpay=OTh*(hRate*hSupRate);
			 regularPay=maxHpWeek*hRate;
			
			gainT=regularPay+OTpay;
		}
		else{
			regularH=hpWeek;
			gainT=hpWeek*hRate;
			regularPay=gainT;
		}
		
		if(nOT2>0 && ot2Rate>0){
			
			OT2pay=nOT2*hRate*ot2Rate;
			gainT=gainT+OT2pay;
		}
		
		
		System.out.println("Trait getPayHourToYear pos 0 et params->"+hRate);
		
		
		NumberFormat numFor = NumberFormat.getCurrencyInstance();
		numFor.setGroupingUsed(true);
		numFor.setMaximumFractionDigits(2);
		
		
		//if(useTaxe){
		if(true){
			
			boolean marie=status.equalsIgnoreCase("single")?false:true;
			
			double standardDeduction=getDeduction(status,year,marie,false,false,false,false)/52;

			boolean maries=status.equalsIgnoreCase("maries")?true:false;
			boolean mariej=status.equalsIgnoreCase("mariej")?true:false;
			boolean single=status.equalsIgnoreCase("single")?true:false;
			boolean head=status.equalsIgnoreCase("headh")?true:false;



			double personalDeduction=getPersonalExcemption(year,dependant,gainT*52,single,mariej,maries,head)/52;
			
			double totalDeduction=standardDeduction+personalDeduction;
			
			double taxable=gainT>totalDeduction? gainT-totalDeduction:gainT;
			
			double fedTax=getFederalTaxe(year,status,taxable,52);
			
			//double fedTax=getFederalTaxe(status,gainT-totalDeduction,52);
			
			double ssTax=getSocialSecurityTaxe(status,gainT);
			
			double medicareTax=getMedicareTaxe(status,gainT);
			
			
			
			
			
			//gainT=gainT-totalDeduction;
			
			gainNet=gainT-(fedTax+ssTax+medicareTax);
			
			/*gainNet=gainNet-ssTax;
			
			gainNet=gainNet-medicareTax;*/
			
			double totalTax=fedTax+ssTax+medicareTax;
			
			
			
			TaxeRateRange taxer=Trait.getTaxeCorrespondant(year,status, gainT, 52);
			String taxableRange=taxer.getMinim()+"-"+taxer.getMaxim();
			
			
			
			ResultSalCal result=new ResultSalCal("hourly", status, "weekly", taxableRange);
			result.setFederalTaxe(fedTax);
			result.setGross(gainT);
			result.setHourRate(hRate);
			result.setMedicareTaxe(medicareTax);
			result.setOtherDeduction(0);
			result.setOtherOverTimeHour(OT2H);
			result.setOtherOverTimePay(OT2pay);
			result.setOvertimeHour(OTh);
			result.setOvertimePay(OTpay);
			result.setPayNet(gainNet);
			result.setPersonalDeduction(personalDeduction);
			result.setRegularHour(regularH);
			result.setRegularPay(regularPay);
			result.setSocialSecuTaxe(ssTax);
			result.setStandardDeduction(standardDeduction);
			result.setTotalDeduction(totalDeduction);
			result.setTotalTaxe(totalTax);
			result.setAnnualGros(gainT*52);
			result.setTaxableRage(taxable+"");
			
			
			String eachWeekResult="1_-Gross income=" + numFor.format(gainT)+
					"_-Hour rate=" + numFor.format(hRate)+
					"_-Total Taxes=" + numFor.format(totalTax)+
					"_-Total Deduction=" + numFor.format(totalDeduction)+
					"_-Federal Taxes=" + numFor.format(fedTax)+
					"_-Social Security=" + numFor.format(ssTax)+
					"_-Medicare Taxe=" + numFor.format(medicareTax)+
					"_-Pay Net=" + numFor.format(gainNet)+
					"_-Regular hours=" + numFor.format(regularH)+
					"_-Regular Pay=" + numFor.format(regularPay)+
					"_-Over Time hours=" + numFor.format(OTh)+
					"_-OverTime Pay=" + numFor.format(OTpay)+
					"_-Other OverTime hours=" + numFor.format(OT2H)+
					"_-Other OverTime Pay=" + numFor.format(OT2pay);



			Log.e("Trait","getPayHourToYear eachWeekResult->"+eachWeekResult);

			
			
			/*String each2WeekResult="2_-Gross income=" + numFor.format(gainT*2)+
					"_-Hour rate=" + numFor.format(hRate)+
					"_-Total Taxes=" + numFor.format(totalTax*2)+
					"_-Total Deduction=" + numFor.format(totalDeduction*2)+
					"_-Federal Taxes=" + numFor.format(fedTax*2)+
					"_-Social Security=" + numFor.format(ssTax*2)+
					"_-Medicare Taxe=" + numFor.format(medicareTax*2)+
					"_-Pay Net=" + numFor.format(gainNet*2)+
					"_-Regular hours=" + numFor.format(regularH*2)+
					"_-Regular Pay=" + numFor.format(regularPay*2)+
					"_-Over Time hours=" + numFor.format(OTh*2)+
					"_-OverTime Pay=" + numFor.format(OTpay*2)+
					"_-Other OverTime hours=" + numFor.format(OT2H*2)+
					"_-Other OverTime Pay=" + numFor.format(OT2pay*2);
			
			
			String eachMonthResult="1_-Gross income=" + numFor.format(gainT*4)+
					"_-Hour rate=" + numFor.format(hRate)+
					"_-Total Taxes=" + numFor.format(totalTax*4)+
					"_-Total Deduction=" + numFor.format(totalDeduction*4)+
					"_-Federal Taxes=" + numFor.format(fedTax*4)+
					"_-Social Security=" + numFor.format(ssTax*4)+
					"_-Medicare Taxe=" + numFor.format(medicareTax*4)+
					"_-Pay Net=" + numFor.format(gainNet*4)+
					"_-Regular hours=" + numFor.format(regularH*4)+
					"_-Regular Pay=" + numFor.format(regularPay*4)+
					"_-Over Time hours=" + numFor.format(OTh*4)+
					"_-OverTime Pay=" + numFor.format(OTpay*4)+
					"_-Other OverTime hours=" + numFor.format(OT2H*4)+
					"_-Other OverTime Pay=" + numFor.format(OT2pay*4);
			
			
			String annualResult="1_-Gross income=" + numFor.format(gainT*52)+
					"_-Hour rate=" + numFor.format(hRate)+
					"_-Total Taxes=" + numFor.format(totalTax*52)+
					"_-Total Deduction=" + numFor.format(totalDeduction*52)+
					"_-Federal Taxes=" + numFor.format(fedTax*52)+
					"_-Social Security=" + numFor.format(ssTax*52)+
					"_-Medicare Taxe=" + numFor.format(medicareTax*52)+
					"_-Pay Net=" + numFor.format(gainNet*52)+
					"_-Regular hours=" + numFor.format(regularH*52)+
					"_-Regular Pay=" + numFor.format(regularPay*52)+
					"_-Over Time hours=" + numFor.format(OTh*52)+
					"_-OverTime Pay=" + numFor.format(OTpay*52)+
					"_-Other OverTime hours=" + numFor.format(OT2H*52)+
					"_-Other OverTime Pay=" + numFor.format(OT2pay*52);*/
			
			
			
			res=result;
			
			
		}
		else{
			
			
			TaxeRateRange taxer=Trait.getTaxeCorrespondant(year,status, gainT, 52);
			String taxableRange=taxer.getMinim()+"-"+taxer.getMaxim();
			
			
			
			ResultSalCal result=new ResultSalCal("hourly", status, "weekly", taxableRange);
			result.setFederalTaxe(0);
			result.setGross(gainT);
			result.setHourRate(hRate);
			result.setMedicareTaxe(0);
			result.setOtherDeduction(0);
			result.setOtherOverTimeHour(OT2H);
			result.setOtherOverTimePay(OT2pay);
			result.setOvertimeHour(OTh);
			result.setOvertimePay(OTpay);
			result.setPayNet(gainNet);
			result.setPersonalDeduction(0);
			result.setRegularHour(regularH);
			result.setRegularPay(regularPay);
			result.setSocialSecuTaxe(0);
			result.setStandardDeduction(0);
			result.setTotalDeduction(0);
			result.setTotalTaxe(0);
			result.setAnnualGros(gainT*52);
			result.setTaxableRage(gainT+"");
			
			
			
			
			
			
			res=result;
			
			
			/*res="1-"+gainT+"-"+0+"-0-"+0+"-"+0+"-"+
					0+"-"+gainNet+"-"+regularH+"-"+regularPay+"-"+0+"-"+0+"-"+0+"-"+0;*/
			
		}

		Log.e("Trait","getPayHourToYear resultfinal->"+res);
		return res;
	}
	
	
	
	public ResultSalCal getPayYearToHour(int year,int dependant,String status, boolean useTaxe, double hpday, double dpweek, double anRate,
										 double maxHpWeek, double hSupRate, double nOT2, double ot2Rate){
		
		ResultSalCal res;
		
		double gainT=anRate/52;
		double gainNet=0;
		double hpWeek=hpday*dpweek;
		double OTh=0;
		double OTpay=0;
		double regularPay=0;
		double regularH=maxHpWeek;
		double OT2H=nOT2;
		double OT2pay=0;
		double hRate=0;
		
		//Determination du cout par heure
		
		if(hpWeek>=maxHpWeek){
			
			OTh=hpWeek-maxHpWeek;
		}
		
		hRate=gainT/(maxHpWeek+hSupRate*OTh+nOT2*ot2Rate);
		
		if(hpWeek>=maxHpWeek){
			
			 OTh=hpWeek-maxHpWeek;
			 OTpay=OTh*(hRate*hSupRate);
			 regularPay=maxHpWeek*hRate;
			
			gainT=regularPay+OTpay;
		}
		else{
			gainT=hpWeek*hRate;
			regularPay=gainT;
		}
		
		if(nOT2>0 && ot2Rate>0){
			
			OT2pay=nOT2*hRate*ot2Rate;
			gainT=gainT+OT2pay;
		}
		
		

		
		
		NumberFormat numFor = NumberFormat.getCurrencyInstance();
		numFor.setGroupingUsed(true);
		numFor.setMaximumFractionDigits(2);
		
		
		//if(useTaxe){
		if(true){
			
			boolean marie=status.equalsIgnoreCase("single")?false:true;
			
			double standardDeduction=getDeduction(status,year,marie,false,false,false,false)/52;

			boolean maries=status.equalsIgnoreCase("maries")?true:false;
			boolean mariej=status.equalsIgnoreCase("mariej")?true:false;
			boolean single=status.equalsIgnoreCase("single")?true:false;
			boolean head=status.equalsIgnoreCase("headh")?true:false;


			
			double personalDeduction=getPersonalExcemption(year,dependant,gainT*52,single,mariej,maries,head)/52;
			
			double totalDeduction=standardDeduction+personalDeduction;
			
			double taxable=gainT>totalDeduction? gainT-totalDeduction:gainT;
			
			double fedTax=getFederalTaxe(year,status,taxable,52);
			
			double ssTax=getSocialSecurityTaxe(status,gainT);
			
			double medicareTax=getMedicareTaxe(status,gainT);
			
			
			
			
			
			//gainT=gainT-totalDeduction;
			
			gainNet=gainT-(fedTax+ssTax+medicareTax);
			
			/*gainNet=gainNet-ssTax;
			
			gainNet=gainNet-medicareTax;*/
			
			double totalTax=fedTax+ssTax+medicareTax;
			
			
			
			TaxeRateRange taxer=Trait.getTaxeCorrespondant(year,status, gainT, 52);
			String taxableRange=taxer.getMinim()+"-"+taxer.getMaxim();
			
			
			
			ResultSalCal result=new ResultSalCal("hourly", status, "weekly", taxableRange);
			result.setFederalTaxe(fedTax);
			result.setGross(gainT);
			result.setHourRate(hRate);
			result.setMedicareTaxe(medicareTax);
			result.setOtherDeduction(0);
			result.setOtherOverTimeHour(OT2H);
			result.setOtherOverTimePay(OT2pay);
			result.setOvertimeHour(OTh);
			result.setOvertimePay(OTpay);
			result.setPayNet(gainNet);
			result.setPersonalDeduction(personalDeduction);
			result.setRegularHour(regularH);
			result.setRegularPay(regularPay);
			result.setSocialSecuTaxe(ssTax);
			result.setStandardDeduction(standardDeduction);
			result.setTotalDeduction(totalDeduction);
			result.setTotalTaxe(totalTax);
			result.setAnnualGros(anRate);
			result.setTaxableRage(taxable+"");
			
			
			/*String eachWeekResult="1_-Gross income=" + numFor.format(gainT)+
					"_-Hour rate=" + numFor.format(hRate)+
					"_-Total Taxes=" + numFor.format(totalTax)+
					"_-Total Deduction=" + numFor.format(totalDeduction)+
					"_-Federal Taxes=" + numFor.format(fedTax)+
					"_-Social Security=" + numFor.format(ssTax)+
					"_-Medicare Taxe=" + numFor.format(medicareTax)+
					"_-Pay Net=" + numFor.format(gainNet)+
					"_-Regular hours=" + numFor.format(regularH)+
					"_-Regular Pay=" + numFor.format(regularPay)+
					"_-Over Time hours=" + numFor.format(OTh)+
					"_-OverTime Pay=" + numFor.format(OTpay)+
					"_-Other OverTime hours=" + numFor.format(OT2H)+
					"_-Other OverTime Pay=" + numFor.format(OT2pay);
			
			
			
			
			
			
			String each2WeekResult="2_-Gross income=" + numFor.format(gainT*2)+
					"_-Hour rate=" + numFor.format(hRate)+
					"_-Total Taxes=" + numFor.format(totalTax*2)+
					"_-Total Deduction=" + numFor.format(totalDeduction*2)+
					"_-Federal Taxes=" + numFor.format(fedTax*2)+
					"_-Social Security=" + numFor.format(ssTax*2)+
					"_-Medicare Taxe=" + numFor.format(medicareTax*2)+
					"_-Pay Net=" + numFor.format(gainNet*2)+
					"_-Regular hours=" + numFor.format(regularH*2)+
					"_-Regular Pay=" + numFor.format(regularPay*2)+
					"_-Over Time hours=" + numFor.format(OTh*2)+
					"_-OverTime Pay=" + numFor.format(OTpay*2)+
					"_-Other OverTime hours=" + numFor.format(OT2H*2)+
					"_-Other OverTime Pay=" + numFor.format(OT2pay*2);
			
			
			String eachMonthResult="1_-Gross income=" + numFor.format(gainT*4)+
					"_-Hour rate=" + numFor.format(hRate)+
					"_-Total Taxes=" + numFor.format(totalTax*4)+
					"_-Total Deduction=" + numFor.format(totalDeduction*4)+
					"_-Federal Taxes=" + numFor.format(fedTax*4)+
					"_-Social Security=" + numFor.format(ssTax*4)+
					"_-Medicare Taxe=" + numFor.format(medicareTax*4)+
					"_-Pay Net=" + numFor.format(gainNet*4)+
					"_-Regular hours=" + numFor.format(regularH*4)+
					"_-Regular Pay=" + numFor.format(regularPay*4)+
					"_-Over Time hours=" + numFor.format(OTh*4)+
					"_-OverTime Pay=" + numFor.format(OTpay*4)+
					"_-Other OverTime hours=" + numFor.format(OT2H*4)+
					"_-Other OverTime Pay=" + numFor.format(OT2pay*4);
			
			
			String annualResult="1_-Gross income=" + numFor.format(gainT*52)+
					"_-Hour rate=" + numFor.format(hRate)+
					"_-Total Taxes=" + numFor.format(totalTax*52)+
					"_-Total Deduction=" + numFor.format(totalDeduction*52)+
					"_-Federal Taxes=" + numFor.format(fedTax*52)+
					"_-Social Security=" + numFor.format(ssTax*52)+
					"_-Medicare Taxe=" + numFor.format(medicareTax*52)+
					"_-Pay Net=" + numFor.format(gainNet*52)+
					"_-Regular hours=" + numFor.format(regularH*52)+
					"_-Regular Pay=" + numFor.format(regularPay*52)+
					"_-Over Time hours=" + numFor.format(OTh*52)+
					"_-OverTime Pay=" + numFor.format(OTpay*52)+
					"_-Other OverTime hours=" + numFor.format(OT2H*52)+
					"_-Other OverTime Pay=" + numFor.format(OT2pay*52);*/
			
			
			
			res=result;
			
			
		}
		else{
			
			
			TaxeRateRange taxer=Trait.getTaxeCorrespondant(year,status, gainT, 52);
			String taxableRange=taxer.getMinim()+"-"+taxer.getMaxim();
			
			
			
			ResultSalCal result=new ResultSalCal("hourly", status, "weekly", taxableRange);
			result.setFederalTaxe(0);
			result.setGross(gainT);
			result.setHourRate(hRate);
			result.setMedicareTaxe(0);
			result.setOtherDeduction(0);
			result.setOtherOverTimeHour(OT2H);
			result.setOtherOverTimePay(OT2pay);
			result.setOvertimeHour(OTh);
			result.setOvertimePay(OTpay);
			result.setPayNet(gainNet);
			result.setPersonalDeduction(0);
			result.setRegularHour(regularH);
			result.setRegularPay(regularPay);
			result.setSocialSecuTaxe(0);
			result.setStandardDeduction(0);
			result.setTotalDeduction(0);
			result.setTotalTaxe(0);
			result.setAnnualGros(anRate);
			result.setTaxableRage(gainT+"");
			
			
			
			
			
			
			res=result;
			
			
			/*res="1-"+gainT+"-"+0+"-0-"+0+"-"+0+"-"+
					0+"-"+gainNet+"-"+regularH+"-"+regularPay+"-"+0+"-"+0+"-"+0+"-"+0;*/
			
		}
		
		System.out.println("Trait getPayHourToYear voici rest->"+res);
		return res;
	}
	
	
	
	public double getFederalTaxe(int year,String status,double gross,double anNbre){
		

		System.out.println("Trait getFederaTaxe param ->"+status+"-"+gross+"-"+anNbre);
		
		double res=0;
		TaxeRateRange tax=Trait.getTaxeCorrespondant(year,status, gross, anNbre);
		
		if(tax!=null){
			
			double min=tax.getMinim();
			if(min>0){
				
				double precedantMax=min-1;
				double buf=gross-(min/anNbre);
				res=res+buf*tax.getTaux()/100;

				Log.e("Trait","getFederalTaxe taxMin->"+tax.getMinim()+"_taxMax->"+tax.getMaxim()+"_taux->"+tax.getTaux());

				while(min>0){
					
					tax=Trait.getTaxeCorrespondantMax(year,status, precedantMax, 1);
					
					if(tax!=null){
						
						min=tax.getMinim();
						buf=(tax.getMaxim()-tax.getMinim())/anNbre;
						res=res+buf*tax.getTaux()/100;
						//Log.e("Trait","getFederalTaxe dans while->"+res+"_taxMin->"+tax.getMinim()+"_taxMax->"+tax.getMaxim()+"_taux->"+tax.getTaux());
						//System.out.println("Trait getFederaTaxe res dans while->"+res);
						precedantMax=min-1;
					}
					else break;
				}
			}
			else{
				
				res=gross*tax.getTaux()/100;
				
			}
		}
		
		System.out.println("\n\n*************Trait getFederaTaxe Voici montant a prelever ->"+res);
			return res;
		
	}
	
	public double getSocialSecurityTaxe(String status,double gross){
		
		return 6.2*gross/100;
	}
	
	public double getMedicareTaxe(String status,double gross){
		
		return 1.45*gross/100;
	}
	
	
	public double getPersonalExcemption(int annee,int nbreDependant,double gros,boolean single,boolean mariej,boolean maries,boolean head){

		if(annee>=2018){

			return 0;
		}
		else{

			if((single && gros>=261500) || (mariej && gros>=313800) || (maries && gros>=156900) || (head && gros>=287650)) return 0;

			return nbreDependant*4050;
		}


	}
	
public double getDeduction(String status,int annee,boolean married,boolean atLeast65,
		boolean blind,boolean spouse65,boolean spouseblind){
		
		double res=0;
	TaxeDeductionProfile buf =Trait.getDeductionCorrespondant(status, annee);
	
	if(buf!=null){
		
		double stand=buf.getAmount();
		
		double surplus=0;
		
		surplus=+ (atLeast65?(married?buf.getMariedAddDeduction():buf.getSingleAddDeduction()):0);
		
		surplus=+ (blind?(married?buf.getMariedAddDeduction():buf.getSingleAddDeduction()):0);
		
		surplus=+ (spouse65?(married?buf.getMariedAddDeduction():buf.getSingleAddDeduction()):0);
		
		surplus=+ (spouseblind?(married?buf.getMariedAddDeduction():buf.getSingleAddDeduction()):0);
		
		
		res=stand+surplus;
	}
	
	
	System.out.println("Trait getDeduction voici la deduction->"+res);

	
	return res;
	}
	

public static TaxeDeductionProfile getDeductionCorrespondant(String status, int annee){
	
	TaxeDeductionProfile res=null;
	
	if(listDeduc==null || listDeduc.size()<4){
		
		System.out.println("Trait getDeductionCorrespondant List need to be initialize");
		initListDeduction();
	
	}


	if(listDeduc.containsKey((String) (annee+"#"+status))){

		res=listDeduc.get(annee+"#"+status);
	}

	
	/*for (TaxeDeductionProfile buf : listDeduc.values()) {
		
		//System.out.println("Trait getTaxeCorrespondant test avec status->"+status+"-buf->"+buf.getNom());
		
		if(buf.getNom().equalsIgnoreCase(status) && (buf.getYear()==annee)){
			
			System.out.println("!!!!!!!!!!Trait getDeductionCorrespondant avec date  status found taux->"+buf.getAmount()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			res=buf;
			break;
		}
	}*/
	

	
	return res;
}



	public static TaxeRateRange getTaxeCorrespondant(int year,String status,double gross,double anNbre){
		
		TaxeRateRange res=null;
		
		if(listDispo==null || listDispo.size()<28){
			
			//System.out.println("Trait getTaxeCorrespondant List need to be initialize");
			initListDispo();
		
		}
		
		
		for (TaxeRateRange buf : listDispo.values()) {
			
			//System.out.println("Trait getTaxeCorrespondant test avec status->"+status+"-buf->"+buf.getNom());
			
			if(buf.getYear() == year && buf.getNom().equalsIgnoreCase(status) && (buf.getMinim()/anNbre<gross && gross<=buf.getMaxim()/anNbre)){
				
				System.out.println("!!!!!!!!!!Trait getTaxeCorrespondant Taxe status found taux->"+buf.getTaux()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				res=buf;
				break;
			}
		}
		
		return res;
	}
	
	
	public static TaxeRateRange getTaxeCorrespondantMax(int year,String status,double max,double anNbre){
		
		TaxeRateRange res=null;
		
		if(listDispo==null || listDispo.size()<28){
			
			//System.out.println("Trait getTaxeCorrespondant List need to be initialize");
			initListDispo();
		
		}

		String key=year+"#"+status+"#"+max;

		//Log.e("Trait","getTaxeCorrespondantMax key->"+key);

		if(listDispo.containsKey(key)) res=listDispo.get(key);


		//Log.e("Trait","getTaxeCorrespondantMax res->"+res);
		
		return res;
	}
	
	public static void initListDispo(){
		
		listDispo=new HashMap<>();

		int year;
		String name,title;
		double tau,min,max;
		boolean seul,maries,mariej,head;

	/**********************************************FOR YEAR 2017***********************************************/

		year=2017;




		/*
		 ************************************************************************ Liste des taxes rate single*/

		name="single";
		title="Single Taxable Income";
		tau=10;
		min=0;
		max=9325;
		seul=true;
		maries=false;
		mariej=false;
		head=false;


		TaxeRateRange buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);
		
		listDispo.put(year+"#"+name+"#"+max,buf);




		tau=15;
		min=9326;
		max=37950;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		tau=25;
		min=37951;
		max=91900;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);





		tau=28;
		min=91901;
		max=191650;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		tau=33;
		min=191651;
		max=416700;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);





		tau=35;
		min=416701;
		max=418400;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);


		tau=39.6;
		min=418401;
		max=999999999;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);
		


		/*
		 ************************************************************************ Liste des taxes rate Married Jointly*/

		name="marriedj";
		title="Married Filing Jointly or Qualified Widow(er) Taxable Income";
		tau=10;
		min=0;
		max=18650;
		seul=false;
		maries=true;
		mariej=false;
		head=false;


		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		tau=15;
		min=18651;
		max=75900;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);



		tau=25;
		min=75901;
		max=153100;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);





		tau=28;
		min=153101;
		max=233350;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		tau=33;
		min=233351;
		max=416700;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		tau=35;
		min=416701;
		max=470700;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		tau=39.6;
		min=470701;
		max=999999999;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);
		

		
		
		/*
		 * *****************************************************************Liste des taxes rate Married sepa*/


		name="marrieds";
		title="Married Filing Separately Taxable Income";
		tau=10;
		min=0;
		max=9325;
		seul=false;
		maries=false;
		mariej=true;
		head=false;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);



		tau=15;
		min=9326;
		max=37950;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		tau=25;
		min=37951;
		max=76550;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);





		tau=28;
		min=76551;
		max=116675;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		tau=33;
		min=116676;
		max=208350;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);





		tau=35;
		min=208351;
		max=235350;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);





		tau=39.6;
		min=235351;
		max=999999999;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);
		
		

		
		/*
		 * *********************************************************Liste des taxes rate head household*/



		name="headh";
		title="Head of Household Taxable Income";
		tau=10;
		min=0;
		max=13350;
		seul=false;
		maries=false;
		mariej=false;
		head=true;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);



		tau=15;
		min=13351;
		max=50800;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);



		tau=25;
		min=50801;
		max=131200;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		tau=28;
		min=131201;
		max=212500;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		tau=33;
		min=212501;
		max=416700;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);
		


		tau=35;
		min=416701;
		max=444500;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);
		


		tau=39.6;
		min=444501;
		max=999999999;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);






		/**********************************************FOR YEAR 2018********************************************************************************************/

		year=2018;




		/*
		 ************************************************************************ Liste des taxes rate single*/

		name="single";
		title="Single Taxable Income";
		tau=10;
		min=0;
		max=9525;
		seul=true;
		maries=false;
		mariej=false;
		head=false;


		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		tau=12;
		min=9526;
		max=38700;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		tau=22;
		min=38701;
		max=82500;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);





		tau=24;
		min=82501;
		max=157500;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		tau=32;
		min=157501;
		max=200000;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);





		tau=35;
		min=200001;
		max=500000;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);


		tau=37;
		min=500001;
		max=999999999;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);



		/*
		 ************************************************************************ Liste des taxes rate Married Jointly*/

		name="marriedj";
		title="Married Filing Jointly or Qualified Widow(er) Taxable Income";
		tau=10;
		min=0;
		max=19050;
		seul=false;
		maries=true;
		mariej=false;
		head=false;


		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		tau=12;
		min=19051;
		max=77400;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);



		tau=22;
		min=77401;
		max=165000;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);





		tau=24;
		min=165001;
		max=315000;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		tau=32;
		min=315001;
		max=400000;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		tau=35;
		min=400001;
		max=600000;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		tau=37;
		min=600001;
		max=999999999;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		/*
		 * *****************************************************************Liste des taxes rate Married sepa*/


		name="marrieds";
		title="Married Filing Separately Taxable Income";
		tau=10;
		min=0;
		max=9525;
		seul=false;
		maries=false;
		mariej=true;
		head=false;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		tau=12;
		min=9526;
		max=38700;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		tau=22;
		min=38701;
		max=82500;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);





		tau=24;
		min=82501;
		max=157500;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		tau=32;
		min=157501;
		max=200000;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);





		tau=35;
		min=200001;
		max=300000;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);


		tau=37;
		min=300001;
		max=999999999;

		/*
		 * Liste des taxes rate single*/
		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		/*
		 * *********************************************************Liste des taxes rate head household*/



		name="headh";
		title="Head of Household Taxable Income";
		tau=10;
		min=0;
		max=13600;
		seul=false;
		maries=false;
		mariej=false;
		head=true;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);



		tau=12;
		min=13601;
		max=51800;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);



		tau=22;
		min=51801;
		max=82500;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		tau=24;
		min=82501;
		max=157500;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);




		tau=32;
		min=157501;
		max=200000;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);



		tau=35;
		min=200001;
		max=500000;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);



		tau=37;
		min=500001;
		max=999999999;

		buf=new TaxeRateRange(year,name,title,tau,min,max,seul,maries,mariej,head);

		listDispo.put(year+"#"+name+"#"+max,buf);
		

		
	
	}	
	
	
	
public static void initListDeduction(){
		
		listDeduc=new HashMap<>();
		
		System.out.println("***********Trait initListDeduction!!!!!!!!!!!!Initialisation de la liste***************************");
		
		/*
		 * Liste des taxes Standard Deductions                                                                                                                            2016*/
		int annee=2017;
		String status="single";
		double amount=6350;
		String title="Single";

		TaxeDeductionProfile buf=new TaxeDeductionProfile(status, title, amount, annee);
		listDeduc.put(annee+"#"+status,buf);



	   status="marriedj";
	   amount=12700;
	   title="Married Filing Jointly";

	   buf=new TaxeDeductionProfile(status, title, amount, annee);
	   listDeduc.put(annee+"#"+status,buf);



		status="marrieds";
		amount=6350;
		title="Married Filing Separately";

		buf=new TaxeDeductionProfile(status, title, amount, annee);
		listDeduc.put(annee+"#"+status,buf);



		status="headh";
		amount=9350;
		title="Head of household";

		buf=new TaxeDeductionProfile(status, title, amount, annee);
		listDeduc.put(annee+"#"+status,buf);




		status="marriedd";
		amount=12700;
		title="Qualifying Surviving Spouse";

		buf=new TaxeDeductionProfile(status, title, amount, annee);
		listDeduc.put(annee+"#"+status,buf);






		/*
		 * Liste des taxes Standard Deductions 2018*/
	 annee=2018;
	 status="single";
	 amount=12000;
	 title="Single";

	 buf=new TaxeDeductionProfile(status, title, amount, annee);
	listDeduc.put(annee+"#"+status,buf);



	status="marriedj";
	amount=24000;
	title="Married Filing Jointly";

	buf=new TaxeDeductionProfile(status, title, amount, annee);
	listDeduc.put(annee+"#"+status,buf);



	status="marrieds";
	amount=12000;
	title="Married Filing Separately";

	buf=new TaxeDeductionProfile(status, title, amount, annee);
	listDeduc.put(annee+"#"+status,buf);



	status="headh";
	amount=18000;
	title="Head of household";

	buf=new TaxeDeductionProfile(status, title, amount, annee);
	listDeduc.put(annee+"#"+status,buf);




	status="marriedd";
	amount=24000;
	title="Qualifying Surviving Spouse";

	buf=new TaxeDeductionProfile(status, title, amount, annee);
	listDeduc.put(annee+"#"+status,buf);

	
	}	
	
	
	
	
	@Override
    public double dollarToCfa(double amount, double rate) {
		if (rate != 0) taux=rate;
		
        return amount*taux;
    }
	
	@Override
	public double cfaToDollar(double amount, double rate) {
		if (rate != 0) taux=rate;
        
		if(amount!=0) return amount/taux;
		else return 0;
    }
	
	@Override
	  public String sayHello(String name) {
	       return String.format("Hello %s !", name);
	  }
	
	@Override
	public String calc(String entry){
		
		Calculator cal=new Calculator();
		
		return cal.calcul(entry, 0);
		
	}
	
	
	@Override
	public String testConnexion(String entry){
		
		if(entry==null) return "0";
		
		
		int num=entry.length();
		
		int ind=entry.indexOf("g");
		
		double res=(num-(ind+1))-ind;
		
		res=res/2;
		
		
		System.out.println("ServerGMB Trait testConnexion voici le result send->"+res);
		
		
		return ""+res;
		
	}

	
	public String calcPayHourToYear(double amount, double hpday, double dpweek) {


		String res="";
		double prHour=amount;
		double prDay=prHour*hpday;
		double prWeek=prDay*dpweek;
		double prMonth=prWeek*4;
		double prYear=prWeek*52;
		
		try {
			NumberFormat numFor = NumberFormat.getCurrencyInstance();
			numFor.setGroupingUsed(true);
			numFor.setMaximumFractionDigits(2);
			res = "-Per Hour=" + numFor.format(prHour) + "_-Per Day="
					+ numFor.format(prDay) + "_-Per Week="
					+ numFor.format(prWeek) + "_-Per Month="
					+ numFor.format(prMonth) + "_-Per Year="
					+ numFor.format(prYear);
			
		} catch (Exception e) {

			res="Error while evaluating";
		}
		
		return res;
	}

	
	public String calcPayYearToHour(double amount, double hpday, double dpweek,
			double pYear) {

		String res="";
		
		double prDay=0;
		double prWeek=0;
		double prMonth=0;
		double prYear=0;
		double prHour=0;
		
		
		if(pYear==1){
			
			prYear=amount;
			
			prMonth=prYear/12;
			
			prWeek=prYear/52;
			
			if(dpweek!=0){
				
				prDay=prWeek/dpweek;
			}
			
			if(hpday!=0){
				
				prHour=prDay/hpday;
			}
		}
		else if(pYear==2){
			
			prMonth=amount;
			
			prYear=prMonth*12;
			
			prWeek=prMonth/4;
			
			if(dpweek!=0){
				
				prDay=prWeek/dpweek;
			}
			
			if(hpday!=0){
				
				prHour=prDay/hpday;
			}
		}
		else {
			
			
			prWeek=amount;
			
			prMonth=prWeek*4;
			
			prYear=prWeek*52;
			
			
			
			if(dpweek!=0){
				
				prDay=prWeek/dpweek;
			}
			
			if(hpday!=0){
				
				prHour=prDay/hpday;
			}			
			
			
		}
		
		
		try {
			NumberFormat numFor = NumberFormat.getCurrencyInstance();
			numFor.setGroupingUsed(true);
			numFor.setMaximumFractionDigits(2);
			res = "-Per Hour=" + numFor.format(prHour) + "_-Per Day="
					+ numFor.format(prDay) + "_-Per Week="
					+ numFor.format(prWeek) + "_-Per Month="
					+ numFor.format(prMonth) + "_-Per Year="
					+ numFor.format(prYear);
			
		} catch (Exception e) {

			res="Error while evaluating";
		}
		
		return res;
	}

	@Override
	public String payHourToYear(double... params) {


		switch(params.length) {
	    case 3: return calcPayHourToYear(params[0],params[1],params[2]);
	            
	    case 2: return calcPayHourToYear(params[0],params[1],jourParSemaine);
	            
	    case 1: return calcPayHourToYear(params[0],heureParJour,jourParSemaine);
	            
	    default: return calcPayHourToYear(0,heureParJour,jourParSemaine);
	            
		
	}
	
	
	
}

    @Override
    public String payFromHour(String... params) {

        /*getPayHourToYear(String status,boolean useTaxe,
	 * double hpday, double dpweek,double hRate, double maxHpWeek,double hSupRate,double nOT2,double ot2Rate)
	 * "/phty2/$status?/$useTaxe?/$hpday?/$dpweek?/$hRate?/$maxHpWeek?/$hSupRate?/$nOT2?/$ot2Rate?"{*/

		String yearStr = params[0];
		String dependantStr = params[1];
		String status = params[2];
		String useTaxeStr = params[3];
        boolean useTaxe = (useTaxeStr == "true" || useTaxeStr == "1" || useTaxeStr == "1.0") ? true : false;


		int year=2017;
		int dependant=1;
        double hpday = 8;
        double dpweek = 5;
        double hRate = 0;
        double maxHpWeek = 40;
        double hSupRate = 1.5;
        double nOT2 = 0;
        double ot2Rate = 0;


        try {

			year=Integer.valueOf(yearStr);
			dependant=Integer.valueOf(dependantStr);
			hpday = Double.valueOf(params[4]);
			dpweek = Double.valueOf(params[5]);
			hRate = Double.valueOf(params[6]);
			maxHpWeek = Double.valueOf(params[7]);
			hSupRate = Double.valueOf(params[8]);
			nOT2 = Double.valueOf(params[9]);
			ot2Rate = Double.valueOf(params[10]);

        } catch (Exception e) {


        }

        Log.e("Trait","payFromHour list of params->"+year+"_"+dependant+"_"+status+"_"+useTaxe+"_"+hpday+"_"+dpweek+"_"+ hRate+"_"+ maxHpWeek+"_"+ hSupRate+"_"+ nOT2+"_"+ ot2Rate);


        String retour= payHourToYear2(year,dependant,status, useTaxe, hpday, dpweek, hRate, maxHpWeek, hSupRate, nOT2, ot2Rate).toString();

		Log.e("Trait","payFromHour return->"+retour);

		return retour;
    }


    @Override
    public String payFromYear(String... params) {

        /*getPayHourToYear(String status,boolean useTaxe,
	 * double hpday, double dpweek,double hRate, double maxHpWeek,double hSupRate,double nOT2,double ot2Rate)
	 * "/phty2/$status?/$useTaxe?/$hpday?/$dpweek?/$hRate?/$maxHpWeek?/$hSupRate?/$nOT2?/$ot2Rate?"{*/

        String yearStr = params[0];
        String dependantStr = params[1];
		String status = params[2];
		String useTaxeStr = params[3];
        boolean useTaxe = (useTaxeStr == "true" || useTaxeStr == "1" || useTaxeStr == "1.0") ? true : false;


        int year=2017;
        int dependant=1;
        double hpday = 8;
        double dpweek = 5;
        double hRate = 0;
        double maxHpWeek = 40;
        double hSupRate = 1.5;
        double nOT2 = 0;
        double ot2Rate = 0;


        try {
			year=Integer.valueOf(yearStr);
			dependant=Integer.valueOf(dependantStr);
            hpday = Double.valueOf(params[4]);
            dpweek = Double.valueOf(params[5]);
            hRate = Double.valueOf(params[6]);
            maxHpWeek = Double.valueOf(params[7]);
            hSupRate = Double.valueOf(params[8]);
            nOT2 = Double.valueOf(params[9]);
            ot2Rate = Double.valueOf(params[10]);

        } catch (Exception e) {


        }


        return payYearToHour2(year,dependant,status, useTaxe, hpday, dpweek, hRate, maxHpWeek, hSupRate, nOT2, ot2Rate).toString();

    }



	public ResultSalCal payHourToYear2(int year,int dependent,String status, boolean useTaxe, double... params) {


		/*getPayHourToYear(String status,boolean useTaxe, 
		 * double hpday, double dpweek,double hRate, double maxHpWeek,double hSupRate,double nOT2,double ot2Rate)*/
		
		switch(params.length) {
		
	    case 7: return getPayHourToYear(year,dependent,status, useTaxe,params[0],params[1],params[2],params[3],params[4],params[5],params[6]);
	            
	    case 6: return getPayHourToYear(year,dependent,status, useTaxe,params[0],params[1],params[2],params[3],params[4],params[5],0);
	            
	    case 5: return getPayHourToYear(year,dependent,status, useTaxe,params[0],params[1],params[2],params[3],params[4],0,0);
	    
	    case 4: return getPayHourToYear(year,dependent,status, useTaxe,params[0],params[1],params[2],params[3],1.5,0,0);
	    
	    case 3: return getPayHourToYear(year,dependent,status, useTaxe,params[0],params[1],params[2],40,1.5,0,0);
	            
	    default: return getPayHourToYear(year,dependent,status, useTaxe,0,0,0,40,1.5,0,0);
	            
		
	}
	
	
	
}
	
	
	public ResultSalCal payYearToHour2(int year,int dependent,String status, boolean useTaxe, double... params) {


		/*getPayHourToYear(String status,boolean useTaxe, 
		 * double hpday, double dpweek,double hRate, double maxHpWeek,double hSupRate,double nOT2,double ot2Rate)*/
		
		switch(params.length) {
		
	    case 7: return getPayYearToHour(year,dependent,status, useTaxe,params[0],params[1],params[2],params[3],params[4],params[5],params[6]);
	            
	    case 6: return getPayYearToHour(year,dependent,status, useTaxe,params[0],params[1],params[2],params[3],params[4],params[5],0);
	            
	    case 5: return getPayYearToHour(year,dependent,status, useTaxe,params[0],params[1],params[2],params[3],params[4],0,0);
	    
	    case 4: return getPayYearToHour(year,dependent,status, useTaxe,params[0],params[1],params[2],params[3],1.5,0,0);
	    
	    case 3: return getPayYearToHour(year,dependent,status, useTaxe,params[0],params[1],params[2],40,1.5,0,0);
	            
	    default: return getPayYearToHour(year,dependent,status, useTaxe,0,0,0,40,1.5,0,0);
	            
		
	}
	
	
	
}
	
	
	@Override
	public String payYearToHour(double... params) {

		switch(params.length) {
		
		case 4: return calcPayYearToHour(params[0],params[1],params[2],params[3]);
		
	    case 3: return calcPayYearToHour(params[0],params[1],params[2],1);
	            
	    case 2: return calcPayYearToHour(params[0],params[1],jourParSemaine,1);
	            
	    case 1: return calcPayYearToHour(params[0],heureParJour,jourParSemaine,1);
	            
	    default: return calcPayYearToHour(0,heureParJour,jourParSemaine,1);
	            
		
	}
	}


	@Override
	public String payFromWeek(String... params) {


		double hRate = 0;
		double maxHpWeek = 40;


		try {

			hRate = Double.valueOf(params[6]);
			maxHpWeek = Double.valueOf(params[7]);

		} catch (Exception e) {


		}

		hRate=hRate/maxHpWeek;
		params[6]=hRate+"";


		return payFromHour(params);
	}

	@Override
	public String payFrom2Weeks(String... params) {

		double hRate = 0;
		double maxHpWeek = 40;


		try {

			hRate = Double.valueOf(params[6]);
			maxHpWeek = Double.valueOf(params[7]);

		} catch (Exception e) {


		}

		maxHpWeek=maxHpWeek*2;
		hRate=hRate/maxHpWeek;
		params[6]=hRate+"";


		return payFromHour(params);
	}

	@Override
	public String payFromMonth(String... params) {

		double hRate = 0;
		double maxHpWeek = 40;


		try {

			hRate = Double.valueOf(params[6]);
			maxHpWeek = Double.valueOf(params[7]);

		} catch (Exception e) {


		}

		maxHpWeek=maxHpWeek*4;
		hRate=hRate/maxHpWeek;
		params[6]=hRate+"";


		return payFromHour(params);
	}
	
	
	
	
	
}
