package biz.picosoft.factory;

import java.awt.Rectangle;

import biz.picosoft.entity.Courrier;
import biz.picosoft.entity.CourrierArriv�;
import biz.picosoft.entity.CourrierInterne;
import biz.picosoft.entity.CourrierSortie;

public class CourrierFactory {
	 public Courrier getCourrier(String courrierType){
	      if(courrierType == null){
	         return null;
	      }		
	      if(courrierType.equalsIgnoreCase("Courrier Arriv�")){
	         return new CourrierArriv�();
	         
	      } else if(courrierType.equalsIgnoreCase("Courrier Sortie")){
	         return new CourrierSortie();
	         
	      } else if(courrierType.equalsIgnoreCase("Courrier Interne")){
	         return new CourrierInterne();
	      }
	      
	      return null;
	   }
}
