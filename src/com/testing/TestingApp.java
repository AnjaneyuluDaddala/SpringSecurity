package com.testing;

public class TestingApp {
	
	
	
	public void memeoryClear() {
		System.out.println("cleared calculated memory!");
	}
	
	
	public static long leapYear(long days) {
		long currentyear = 1970;
		
		while (days > 365)
		{
		    if (isLeapYear(currentyear))
		    {
		        if (days > 366)
		        {
		            days -= 366;
		            currentyear +=1;
		        }else {
		        	break;
		        }
		    }
		    else
		    {
		        days -= 365;
		        currentyear +=1;
		        
		        
		    }
		}return currentyear;

		
	}
	
	
	
    public static boolean isLeapYear(long year) {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    
    public static long epochTime(long milliseconds) {
    	
    	long millisInADay = 24 * 60 * 60 * 1000; 
    	
    	
        return milliseconds / millisInADay;  //
    }
		
	
	public void reset() {
		System.out.println("leap year is reseted!");
	}

		

	public static void main(String[] args) {
		
		long currentTimeMillis = System.currentTimeMillis();
		
		long days = epochTime(currentTimeMillis);
		
		
		long year = leapYear(days);
		
		System.out.println(year);
		
	}
	
	


	
}