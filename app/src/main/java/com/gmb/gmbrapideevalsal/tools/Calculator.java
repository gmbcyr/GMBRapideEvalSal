package com.gmb.gmbrapideevalsal.tools;

import java.util.ArrayList;

public class Calculator{
	
 /*final String opFois="*";
 final String opPlus="+";
 final String opMoins="-";
 final String opDiv="/";
 final String opParenO="(";
 final String opParenF=")";
 final String opMod="Mod.";
 final String opExpo="^";*/
	
	 final String opFois="F";
	 final String opPlus="P";
	 final String opMoins="N";
	 final String opDiv="D";
	 final String opParenO="O";
	 final String opParenF="C";
	 final String opMod="Mod.";
	 final String opExpo="E";	
 

	long nbre=0;
    long exposant=1;
    long dvse=0;
    int current=0;
    ArrayList<Long> ar;


public String identifieOperateur(String chaine){
	
	String result="";
	int ouvre=0;
	int ferme=0;
	
	//recherche des operateurs
	for(int i=0;i<chaine.length();i++){
		
		char[] t={chaine.charAt(i)};
		String test=new String(t);
		if(test.equalsIgnoreCase(opFois) || test.equalsIgnoreCase(opDiv) ||
				test.equalsIgnoreCase(opMoins) || test.equalsIgnoreCase(opPlus)){
			
		
			result=result+i+"_"+chaine.charAt(i)+"#";
		}
	}
	
	//recherche des parentheses
	for(int i=0;i<chaine.length();i++){
		char[] t={chaine.charAt(i)};
		String test=new String(t);
		if(test.equalsIgnoreCase(opParenO) || 
				test.equalsIgnoreCase(opParenF)){
			
		
			result=result+"$"+i+"_"+chaine.charAt(i);
			
		}
	}
	return result+"%"+compteParenthese(chaine);
}


public String compteParenthese(String chaine){
	
	String result="";
	int ouvre=0;
	int ferme=0;
	
		
	//recherche des parentheses
	for(int i=0;i<chaine.length();i++){
		char[] t={chaine.charAt(i)};
		String test=new String(t);
		if(test.equalsIgnoreCase(opParenO)
				|| test.equalsIgnoreCase(opParenF)){
			
			if(test.equalsIgnoreCase(opParenO)) ouvre++;
			else ferme++;
		}
	}
	
	System.out.println("COmpte Parenthese voici mon retour->"+ouvre+"_"+ferme);
	return ouvre+"_"+ferme;
}


public String extraitChaine(String chaine){

String result="";
int ouvre=0;
int ferme=0;
boolean trouve=false;

int last=chaine.length();
	
//recherche des parentheses
for(int i=0;i<last;i++){
	
	char[] t={chaine.charAt(i)};
	String test=new String(t);
	
	if(i!=0 && i!=(last-1)){
		
		result=result+chaine.charAt(i);
	}
	
	if(test.equalsIgnoreCase(opParenO) || 
			test.equalsIgnoreCase(opParenF)){
		
		if(test.equalsIgnoreCase(opParenO)){
			trouve=true;
			ouvre++;
		}
		else ouvre--;
	}
	
	if(trouve && ouvre==0) break;
}
System.out.println("ExtraitChaine, voici mon retour->"+result);
return result;
}


public int trouvePosFermante(String chaine,int maPos){

int result=maPos;
int ouvre=0;
int ferme=0;
boolean trouve=false;

int last=chaine.length();
	
//recherche des parentheses
for(int i=maPos;i<last;i++){
	
	char[] t={chaine.charAt(i)};
	String test=new String(t);
	
	
	if(test.equalsIgnoreCase(opParenO) || 
			test.equalsIgnoreCase(opParenF)){
		
		if(test.equalsIgnoreCase(opParenO)){
			trouve=true;
			ouvre++;
		}
		else ouvre--;
	}
	
	if(trouve && ouvre==0){
		result=i;
		break;
	}
}
System.out.println("TrouveMaFermante dans chaine->"+chaine+", je suis la pos->"+maPos+" c'est a la pos->"+result);
return result;
}

public boolean fermePossible(String chaine){

boolean result=false;
int ouvre=0;
int ferme=0;
boolean trouve=false;

int last=chaine.length();
	
//recherche des parentheses
for(int i=0;i<last;i++){
	
	char[] t={chaine.charAt(i)};
	String test=new String(t);
	
	
	if(test.equalsIgnoreCase(opParenO) || 
			test.equalsIgnoreCase(opParenF)){
		
		if(test.equalsIgnoreCase(opParenO)){
			trouve=true;
			ouvre++;
		}
		else ferme++;
	}
	
	
}
    
    if(ouvre>ferme)  result=true;
System.out.println("fermePossible dans chaine->"+chaine+", c'est a la pos->"+result);
return result;
}

    public int trouvePosOuvrante(String chaine,int maPos){

int result=maPos;
int ouvre=0;
int ferme=0;
boolean trouve=false;

int last=chaine.length();
	
//recherche des parentheses
for(int i=maPos;i>=0;i--){
	
	char[] t={chaine.charAt(i)};
	String test=new String(t);
	
	
	if(test.equalsIgnoreCase(opParenO) || 
			test.equalsIgnoreCase(opParenF)){
		
		if(test.equalsIgnoreCase(opParenF)){
			trouve=true;
			ferme++;
		}
		else ferme--;
	}
	
	if(trouve && ferme==0){
		result=i;
		break;
	}
}
System.out.println("TrouveMaOuvrante dans chaine->"+chaine+", je suis la pos->"+maPos+" c'est a la pos->"+result);
return result;
}

    



	
	
	 public String calcul(String chaine,int appelNum){
     	
     	float result=0;
     	int ouvre=0;
     	
     	
     	ArrayList<Object> operand=new ArrayList<Object>();
     	

     	System.out.println("\n\nCalcul avec appelNum->"+appelNum+" et  chaine->"+chaine);
     	try{

     		int last=chaine.length();
     		String buf="";
     		//recherche des parentheses
     		for(int i=0;i<last;i++){
     			
     			char[] t={chaine.charAt(i)};
     			String test=new String(t);
     			System.out.println("Calcul avec appelNum->"+appelNum+" et   chaine pos 2 avec i->"+i+" char->"+test);
     			if(!test.equalsIgnoreCase(opParenO) && 
     					!test.equalsIgnoreCase(opParenF) && 
     					!test.equalsIgnoreCase(opPlus) && 
     					!test.equalsIgnoreCase(opMoins) && 
     					!test.equalsIgnoreCase(opFois) &&
     					!test.equalsIgnoreCase(opDiv) &&
     					!test.equalsIgnoreCase(opExpo) && 
     					!test.equalsIgnoreCase("M")){
     				
     				
     				buf=buf+chaine.charAt(i);
     				
     				System.out.println("Calcul avec appelNum->"+appelNum+" et   chaine pos 20 avec i->"+i+" char->"+test+" et buf->"+buf);
     				
     				if(i+1==last){
     					System.out.println("Calcul avec appelNum->"+appelNum+" et   chaine pos 201 avec i->"+i+" char->"+test+" et buf->"+buf);
     					if (buf!="") {
     						operand.add(buf);
     						buf = "";
     					}
     				}				
     				
     				
     			}
     			else if(test.equalsIgnoreCase(opPlus) || 
     					test.equalsIgnoreCase(opMoins) || 
     					test.equalsIgnoreCase(opFois) || 
     					test.equalsIgnoreCase(opDiv) ||
     					test.equalsIgnoreCase(opExpo) || 
     					test.equalsIgnoreCase("M")){
     				
     				System.out.println("Calcul avec appelNum->"+appelNum+" et   chaine pos 21 avec i->"+i+" char->"+test);
     				if (buf!="") {
     					operand.add(buf);
     					buf = "";
     				}
     				char[] tab={chaine.charAt(i)};
     				operand.add(new String(tab));
     				System.out.println("Calcul avec appelNum->"+appelNum+" et   chaine pos 21 apres insert operateur avec i->"+i+" char->"+test);
     			}
     			else if(test.equalsIgnoreCase(opParenO) ){
     				
     				System.out.println("Calcul avec appelNum->"+appelNum+" et   chaine pos 22 avec i->"+i+" char->"+test);
     				int dep=i;
     				i=trouvePosFermante(chaine,i);
     				int newPos=i;
     				//operand.add(calcul(extraitChaine(chaine.substring(dep,newPos))));
     				operand.add(calcul(chaine.substring(dep+1,newPos),appelNum+1));
     				if (buf!="") {
     					operand.add(buf);
     					buf = "";
     				}
     				
     				
     					
     					
     				
     			}
     			else if(test.equalsIgnoreCase(opParenF) ){
     				
     				System.out.println("Calcul avec appelNum->"+appelNum+" et   chaine pos 23 avec i->"+i+" char->"+test);
     				//operand.add(calcul(extraitChaine(chaine.substring(i))));
     				if (buf!="") {
     					operand.add(buf);
     					buf = "";
     				}
     			}

     			
     			
     			
     			
     		}
     		
     		System.out.println("\nCalcul avec appelNum->"+appelNum+" et    chaine pos 3 avec taille de operand->"+operand.size());
     		
     		//nous allons effectuer les operations en fonction de la priorite des operateur
     		System.out.println("\nCalcul avec appelNum->"+appelNum+" et   chaine pos EXPO avant for");
     		//Gestion des Expo
     		for(int i=0;i<operand.size();i++){
     			System.out.println("\nCalcul avec appelNum->"+appelNum+" et   chaine pos EXPO avec i->"+i);
     			String op=(String) operand.get(i);
     			System.out.println("\nCalcul avec appelNum->"+appelNum+" et   chaine pos EXPO avec i->"+i+" char->"+op);
     			if(op.equalsIgnoreCase(opExpo)){
     				
     				String op1=(String) operand.get(i-1);
     				String op2=(String) operand.get(i+1);
     				System.out.println("\nCalcul avec appelNum->"+appelNum+" et   chaine pos EXPO avec i->"+i+" char->"+op+" op1->"+op1+" op2->"+op2);
     				//float res=new Float(op1).floatValue()*new Float(op2).floatValue();
     				
     				double res = Math.pow(Double.valueOf(op1),Double.valueOf(op2));
     				
     				operand.set(i-1, new Double(res).toString());
     				
     				operand.remove(i+1);
     				operand.remove(i);
     				i=0;
     				
     				
     			}
     		}
     		
     		
     		
     		//Gestion des Multiplications
     		for(int i=0;i<operand.size();i++){
     			
     			String op=(String) operand.get(i);
     			
     			if(op.equalsIgnoreCase(opFois)){
     				
     				String op1=(String) operand.get(i-1);
     				String op2=(String) operand.get(i+1);
     				System.out.println("Calcul avec appelNum->"+appelNum+" et   chaine pos multi avec i->"+i+" char->"+op+" op1->"+op1+" op2->"+op2);
     				//float res=new Float(op1).floatValue()*new Float(op2).floatValue();
     				
     				double res = Double.valueOf(op1)*Double.valueOf(op2);
     				
     				operand.set(i-1, new Double(res).toString());
     				
     				operand.remove(i+1);
     				operand.remove(i);
     				i=0;
     				
     				
     			}
     		}
     		
     		//Gestion des Divisions
     		for(int i=0;i<operand.size();i++){
     			
     			String op=(String) operand.get(i);
     			
     			if(op.equalsIgnoreCase(opDiv)){
     				String op1=(String) operand.get(i-1);
     				String op2=(String) operand.get(i+1);
     				System.out.println("Calcul avec appelNum->"+appelNum+" et   chaine pos DIVISION avec i->"+i+" char->"+op+" op1->"+op1+" op2->"+op2);
     				//float res=new Float(op1).floatValue()*new Float(op2).floatValue();
     				
     				double res=0;
     				if (op2!="0") {
     					res = Double.valueOf(op1) / Double.valueOf(op2);
     				}
     				
     				operand.set(i-1, new Double(res).toString());
     				
     				operand.remove(i+1);
     				operand.remove(i);
     				i=0;
     				
     				
     			}
     		}
     		
     		
     		//Gestion des Additions
     		for(int i=0;i<operand.size();i++){
     			
     			String op=(String) operand.get(i);
     			
     			if(op.equalsIgnoreCase(opPlus)){
     				String op1=(String) operand.get(i-1);
     				String op2=(String) operand.get(i+1);
     				System.out.println("Calcul avec appelNum->"+appelNum+" et   chaine pos ADDITIONS 0 avec i->"+i+" char->"+op+" op1->"+op1+" op2->"+op2);
     				//float res=new Float(op1).floatValue()*new Float(op2).floatValue();
     				
     				double res= Double.valueOf(op1)+ Double.valueOf(op2);
     				
     				System.out.println("Calcul avec appelNum->"+appelNum+" et   chaine pos ADDITIONS 1 avec i->"+i+" char->"+op+" op1->"+op1+" op2->"+op2);
     				
     				operand.set(i-1, new Double(res).toString());
     				System.out.println("Calcul avec appelNum->"+appelNum+" et   chaine pos ADDITIONS 2 avec i->"+i+" char->"+op+" op1->"+op1+" op2->"+op2);
     				
     				operand.remove(i+1);
     				operand.remove(i);
     				i=0;
     				System.out.println("Calcul avec appelNum->"+appelNum+" et   chaine pos ADDITIONS 3 avec i->"+i+" char->"+op+" op1->"+op1+" op2->"+op2);
     				
     				
     				
     			}
     		}
     		
     		
     		
     		//Gestion des Soustractions
     		for(int i=0;i<operand.size();i++){
     			
     			String op=(String) operand.get(i);
     			
     			if(op.equalsIgnoreCase(opMoins)){
     				String op1=(String) operand.get(i-1);
     				String op2=(String) operand.get(i+1);
     				System.out.println("Calcul avec appelNum->"+appelNum+" et   chaine pos SOUSTRACTIONS avec i->"+i+" char->"+op+" op1->"+op1+" op2->"+op2);
     				//float res=new Float(op1).floatValue()*new Float(op2).floatValue();
     				
     				double res= Double.valueOf(op1)- Double.valueOf(op2);
     				
     				
     				operand.set(i-1, new Double(res).toString());
     				
     				operand.remove(i+1);
     				operand.remove(i);
     				i=0;
     				
     				
     				
     			}
     		}		
     		
     		
     		
     	}
     	catch(Exception exp){
     		
     		System.err.println("Calcul appelNum->"+appelNum+" et   Error->"+exp);
     	}
     	
     	System.out.println("Calcul voici appelNum->"+appelNum+" et   mon retour->"+operand.get(0)+" avec taille de operand->"+operand.size());
     	//return Double.valueOf((String)operand.get(0));
     	String res= operand.get(0).toString();
             
             if(res.endsWith(".0")){
                 
               return res.substring(0, res.length()-2); 
             }
             else{
                 return res;
             }
     }

}
