package foodtruckfinder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;


public class FoodTruckFinder {

	public static void main(String[] args) {
		UserInformation userInfo = new UserInformation();

		List<FoodTruck> foodTrucks = new ArrayList<>(getFoodTruckDataFromAPI(userInfo.dayUserAccessedProgram));

		List<FoodTruck> openFoodTrucks = filterForOpenFoodTrucks(foodTrucks, userInfo);

		if(openFoodTrucks.size() <= 0) {
			System.out.println("Currently, no food trucks are open.");
		} else {
			returnIncrementedResultsToUser(openFoodTrucks, userInfo);
		}

	}

	public static void returnIncrementedResultsToUser(List<FoodTruck> openFoodTrucks, UserInformation userInfo) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("\nType 'show-open-food-trucks' to see available food trucks in your area right now!");
		userInfo.userLastInput = scanner.nextLine();

		while(!userInfo.userLastInput.equals("show-open-food-trucks")) {
			System.out.println("Oops, that was a wrong command. Type 'show-open-food-trucks' to see available food trucks in your area right now! ");
			userInfo.userLastInput = scanner.nextLine();
		}

		int count = 0;

		// Iterator through list to display results
		for(int i = 0; i < openFoodTrucks.size(); i++) {
			openFoodTrucks.get(i).foodTruckName();
			count++;

			// After displaying 10 results ask for input
			if(count == 10) {
				System.out.println("\nEnter '1' to get more results.");
				userInfo.userLastInput = scanner.nextLine();

				while(!userInfo.userLastInput.equals("1")) {
					System.out.println("You entered the wrong command. Please enter '1' to get more results.");
					userInfo.userLastInput = scanner.nextLine();
				}

				count = 0;
			}
		}
	}

	public static List<FoodTruck>  filterForOpenFoodTrucks(List<FoodTruck> foodTrucks, UserInformation  userInformation) {
		// Prevent errors and notify user if API didn't return results
		if(foodTrucks.size() <= 0) {
			return Collections.emptyList();
		}

		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;

		/*
		* This stream will filter for open food trucks at the time the program
		* was accessed. The SF Data Socrata API ends the day with '24:00', but
		* LocalTime only goes to '23:59' and considers '24:00' to be a new day.
		* */
		List<FoodTruck> openFoodTrucks = foodTrucks.stream().filter(ft -> {
			LocalTime timeProgramWasAccessed = LocalTime.parse(userInformation.timeUserAccessedProgram, formatter);
			LocalTime foodTruckStartTime = LocalTime.parse(ft.start24, formatter);
			LocalTime foodTruckEndTime;

			/*
			* This conditional statement prevents getting a error
			* due to LocalTime not being able to parse '24:00'.
			* */
			if(ft.end24.equals("24:00")) {
				foodTruckEndTime = LocalTime.parse( "23:59", formatter);
			} else {
				foodTruckEndTime = LocalTime.parse(ft.end24, formatter);
			}


			Boolean isFoodTruckStillOpen = (
					timeProgramWasAccessed.isAfter(foodTruckStartTime)
							&& timeProgramWasAccessed.isBefore(foodTruckEndTime)
			);

			return isFoodTruckStillOpen;

		}).collect(Collectors.toList());

		// sort openFoodTruckList ascending
		Collections.sort(openFoodTrucks, (ft1, ft2) -> ft1.applicant.compareTo(ft2.applicant));

		return openFoodTrucks;
	}

	public static List<FoodTruck> getFoodTruckDataFromAPI(int dayorder) {

		/*
		* Kept most of original starter code, but used gson to convert the
		* array of json objects into java objects.
		* */
		try {
			StringBuilder result = new StringBuilder();

			// Get data based on day number value. i.e Monday = 1
			URL url = new URL("https://data.sfgov.org/resource/jjew-r69b.json?dayorder=" + dayorder);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line;
			 while ((line = rd.readLine()) != null) {
			 	result.append(line);
			 }

			 FoodTruck[] fromJsonToFoodTruck = new Gson().fromJson(result.toString(), FoodTruck[].class);

			rd.close();

			List<FoodTruck> foodTruckList = Arrays.asList(fromJsonToFoodTruck);

			return foodTruckList;

		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return Collections.emptyList();
		}
	}

}


