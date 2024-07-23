package com.testing;

public class NewLeapYear {


		public boolean isLeapYear(long year) {
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
		
		public long getDaysFromEpoch() {
			long currentTimeMillis = System.currentTimeMillis();
			System.out.println("Current time in milliseconds: " + currentTimeMillis);

			// Calculate the number of days from the milliseconds
			long millisInADay = 24 * 60 * 60 * 1000; // Number of milliseconds in a day
			long daysSinceEpoch = currentTimeMillis / millisInADay;

			System.out.println("Number of days since January 1, 1970: " + daysSinceEpoch);
			return daysSinceEpoch;
		}

		public long findYear() {
			// Get the current time in milliseconds

			long currentYear = 1970;
			long daysSinceEpoch = getDaysFromEpoch();
			while (daysSinceEpoch > 365) {
				if (isLeapYear(currentYear)) {
					if (daysSinceEpoch > 366) {
						daysSinceEpoch -= 366;
						currentYear += 1;
					}
					else {
						break;
					}
				} else {
					daysSinceEpoch -= 365;
					currentYear += 1;
				}
			}

			return currentYear;
		}
		
		
		
		
		
  public static void main(String[] args) {
			NewLeapYear year=new NewLeapYear();
			long findYear = year.findYear();
			System.out.println(findYear);
			
		}
	}
