import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;


public class VOmapping {

	
	public static void voSelection(int numberOfVO, double capValUpperBound, HashMap<String, Integer> userPref){
		
		Map<Integer, Capability> caps =generator(numberOfVO, capValUpperBound);
System.out.println(caps+"------------------");
		HashMap<Integer, Capability> scoredVOprice = calculationNeg(caps, "price", userPref);
		HashMap<Integer, Capability> scoredVOreputation = calculationPos(caps, "reputation", userPref);
		HashMap<Integer, Capability> scoredVOexperience = calculationPos(caps, "experience", userPref);
		HashMap<Integer, Capability> scoredVOsuccessrate = calculationPos(caps, "successRate", userPref);
		HashMap<Integer, Capability> scoredVOavailability = calculationPos(caps, "availability", userPref);
		
		HashMap<Integer, Double> SummedVO = new HashMap<Integer, Double>();
		
		Set<Integer> keySet = scoredVOavailability.keySet();
		for (Integer key : keySet) {
			Capability cP = scoredVOprice.get(key);
			Capability cR = scoredVOreputation.get(key);
			Capability cE = scoredVOexperience.get(key);
			Capability cS = scoredVOsuccessrate.get(key);
			Capability cA = scoredVOavailability.get(key);
			
			double sum=cP.getCapValue()+cR.getCapValue()+cE.getCapValue()+cS.getCapValue()+cA.getCapValue();
			SummedVO.put(key, sum);
//			System.out.println("\n ppppppppppppppppppppppppppppppp qqqqqqqqqqqqqqqqqqqqqqqqqq---"+key+" "+"------"+sum+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			}
		
		int selectedVOkey=0;
		double selectedVOval=(Collections.max(SummedVO.values()));
//		System.out.println(selectedVOval+"outooooooooooooooooooooo");
		for (Entry<Integer, Double> entry : SummedVO.entrySet()) {  
            if (entry.getValue()==selectedVOval) {
            	selectedVOkey=entry.getKey();
//                System.out.println(selectedVOkey+"From Inside the looop===================");     
            }
        }
		System.out.println("Selected VO========="+selectedVOkey+"Value:"+selectedVOval);
		}
	
	public static int maxIndex(Map<Integer,Capability> caps, String capName){
		int maxIndex  = -1, minIndex = -1 ;
		Capability maxCap = new Capability(capName, -1);
		Capability minCap = new Capability(capName, 101);
	    for(Entry<Integer,Capability> entry : caps.entrySet()){
	    	if(entry.getValue().getCapValue() > maxCap.getCapValue() && entry.getValue().getCapName().equals(capName) ){
	    		maxIndex = entry.getKey();
	    		maxCap = entry.getValue();
	    	}	    	
	    	if(entry.getValue().getCapValue() < minCap.getCapValue() && entry.getValue().getCapName().equals(capName)){
	    		minIndex = entry.getKey();
	    		minCap = entry.getValue();	    			    	
	    	}
	    }
	    return	maxIndex;
	}
	public static int minIndex(Map<Integer,Capability> caps, String capName){
			int maxIndex  = -1, minIndex = -1 ;
			Capability maxCap = new Capability(capName, -1);
			Capability minCap = new Capability(capName, 101);
		    for(Entry<Integer,Capability> entry : caps.entrySet()){
		    	
		    	if(entry.getValue().getCapValue() < minCap.getCapValue() && entry.getValue().getCapName().equals(capName)){
		    		minIndex = entry.getKey();
		    		minCap = entry.getValue();	    	
		    	}
		    }
		    return	minIndex;
		}
	
	public static HashMap<Integer, Capability> calculationPos(Map<Integer, Capability> caps, String capName, HashMap<String, Integer> userPref){
		HashMap<Integer, Capability> scoredVO = new HashMap<Integer, Capability>();
		double Vij =0.0;
		double w=0.0;
		double maxValue=caps.get(maxIndex(caps,capName)).getCapValue();
		double minValue=caps.get(minIndex(caps,capName)).getCapValue();
		for (Entry<Integer, Capability> entry : caps.entrySet()) {  
				if (maxValue-minValue != 0) {
					Vij=(entry.getValue().capValue-minValue)/(maxValue-minValue);
					
					w=Vij*(userPref.get(capName)); //multiplied by userPref
//					System.out.println("\n Vij>>>>>>>>>>>>>>>>>>>>>>:     "+Vij+"wwwwww="+w+"\n ---userprefe: "+userPref.get("price"));

					Capability score = new Capability(capName, w);	
					score.setCapName(capName);
					score.setCapValue(w);
					scoredVO.put(entry.getKey(), score);
					}
				else{
					Vij=1;
//					System.out.println("\n VO score: "+Vij+"  "+w);
					w=Vij*(userPref.get("price"));
					Capability score = new Capability(capName, w);	
					score.setCapName(capName);
					score.setCapValue(w);
					scoredVO.put(entry.getKey(), score);
					}
				}
		return scoredVO;
	}
	
	public static HashMap<Integer, Capability> calculationNeg(Map<Integer, Capability> caps, String capName, HashMap<String, Integer> userPref){
		HashMap<Integer, Capability> scoredVO = new HashMap<Integer, Capability>();
		double Vij =0.0;
		double w=0.0;
		double maxValue=caps.get(maxIndex(caps,capName)).getCapValue();
		double minValue=caps.get(minIndex(caps,capName)).getCapValue();
		for (Entry<Integer, Capability> entry : caps.entrySet()) {  
				if (maxValue-minValue != 0) {
					Vij=(maxValue - entry.getValue().getCapValue())/(maxValue-minValue);
					
					w=Vij*(userPref.get(capName)); //multiplied by userPref (weight)
//					System.out.println("\n Vij>>>>>>>>>>>>>>>>>>>>>>:     "+Vij+"wwwwww="+w+"\n ---userprefe: "+userPref.get("price"));

					Capability score = new Capability(capName, w);	
					score.setCapName(capName);
					score.setCapValue(w);
					scoredVO.put(entry.getKey(), score);
					}
				else{
					Vij=1;
//					System.out.println("\n VO score: "+Vij+"  "+w);
					w=Vij*(userPref.get("price"));
					Capability score = new Capability(capName, w);	
					score.setCapName(capName);
					score.setCapValue(w);
					scoredVO.put(entry.getKey(), score);
					}
				}
		return scoredVO;
	}
	public static int randomVal(int upperbound){
		Random randomGenerator = new Random();
		int randomVal = randomGenerator.nextInt(upperbound);
		
		return randomVal;	
	}
	
	public static Map<Integer, Capability> generator(int numberOfVO, double upperBound){
		Map<Integer,Capability> caps = new HashMap<Integer, Capability>();
		
		final String[] CapNames = {"price", "reputation", "availability", "successRate", "experience"};
//		int Flag;
		Random randomGenerator = new Random();
		int flag=0;
	    for (int i = 1; i <= numberOfVO; i++){
	    	int randomVal = randomGenerator.nextInt(100);
	    	double voCapVal= randomVal;
	    	if (flag<5){
//	    		Flag=flag;
	    		
	    			Capability cap = new Capability(CapNames[flag], voCapVal);
	    			
	    			caps.put(i, cap);
	    			flag++;
	    	} else {flag=0; }
	    }
	    
//	    for(Entry entry : caps.entrySet()){
//	    	 System.out.println("----------"+entry.toString());
//	    }
		return caps;
	}
	
	public static void main(String args[]) {
		
//		 Map<Integer, Capability> caps = generator(20, 100.0);
//		 System.out.println(caps.get(1));
//		 
		
//		 System.exit(0);

		HashMap<String, Integer> userPref = new HashMap<String, Integer>();
		
	    	userPref.put("price", randomVal(5));
	    	userPref.put("reputation", randomVal(5));
	    	userPref.put("availability", randomVal(5));
	    	userPref.put("successRate", randomVal(5));
	    	userPref.put("experience", randomVal(5));
	    	
//	    HashMap<String, Integer> userPrefNeg = new HashMap<String, Integer>();
			
//	    	userPrefNeg.put("price", randomVal(5));
//	    	userPrefNeg.put("reputation", randomVal(5));
//	    	userPrefNeg.put("availability", randomVal(5));
//	    	userPrefNeg.put("successRate", randomVal(5));
//	    	userPrefNeg.put("experience", randomVal(5));
	    	
//	    System.out.println("FROM main ------------"+userPrefPos);
		long startTime = System.nanoTime();
//		calculationPos(caps, capName)
		long startTime2 = System.currentTimeMillis();
		double prevUsedMemory =  (double)((double)(Runtime.getRuntime().totalMemory()/1024)/1024)- ((double)((double)(Runtime.getRuntime().freeMemory()/1024)/1024));
//		voSelection(Integer.valueOf(args[0]), 100.0, userPref);
		voSelection(10, 100.0, userPref);
		double afterusedMemory =((double)((double)(Runtime.getRuntime().totalMemory()/1024)/1024)- ((double)((double)(Runtime.getRuntime().freeMemory()/1024)/1024)));
		double memoryUsed= afterusedMemory - prevUsedMemory;
//		System.err.println("\n Memory used :"+usedMemory);
//		voSelection(1000, 100.0, userPrefPos);
		Long endTime = System.nanoTime();
		long endTime2 = System.currentTimeMillis();
		long duration2=endTime2-startTime2;
//		long endTime = System.nanoTime();
		long duration = ((endTime - startTime));
		
	
		String.format("%02d min, %02d sec", 
			    TimeUnit.MILLISECONDS.toMinutes(duration),
			    TimeUnit.MILLISECONDS.toSeconds(duration) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
			);
		
		System.out.println("Nanoseconds: Starting time: "+startTime+" End Time: "+endTime+" Duration: "+TimeUnit.NANOSECONDS.toSeconds(duration) + "sec"+"----------"+(duration));
		System.out.println("Memmory in MB: " +memoryUsed);
		
		System.out.println("Duration in MS: "+duration2);
		}

}



