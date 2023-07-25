package com.example.StarWarsProject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.ILoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;


@RestController
@SpringBootApplication
public class StarWarsProjectApplication {
	@GetMapping(value = "/api/v1/planet", produces = MediaType.APPLICATION_JSON_VALUE)
	public static JsonNode getPlanetAndResidents(@RequestParam("search") String planetName) {
		try {
			String baseUrl = "https://swapi.dev/api/planets/";
			String searchUrl = baseUrl + "?search=" + planetName + "&format=json";
			HttpURLConnection connection = null;
			try {
				URL url = new URL(searchUrl);
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
			} catch (ConnectException exception) {
				System.out.println("Failed to connect, check URL address or if the URL is up");
				exception.printStackTrace();
			}

			int responseCode = connection.getResponseCode();
			System.out.println("Response code: " + responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) {
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode jsonResponse = objectMapper.readTree(connection.getInputStream());

				// Get the first planet result
				JsonNode planetResult = jsonResponse.get("results").get(0);

				// Extract the planet name and the number of residents
				String nameOfPlanet;
				int nrOfResidents;
				try {
					nameOfPlanet = planetResult.get("name").asText();
					nrOfResidents = planetResult.get("residents").size();
				} catch (Exception noPlanet){
					System.out.println("This planet isn't in the database");
					nameOfPlanet = "This planet isn't in the database";
					nrOfResidents = -1;
					noPlanet.printStackTrace();
				}
				// Create the JSON response
				ObjectNode jsonNode = objectMapper.createObjectNode();
				jsonNode.put("planet", nameOfPlanet);
				jsonNode.put("residents", nrOfResidents);

				return jsonNode;
			} else {
				System.out.println("Failed to get data from the API. Response code: " + responseCode);
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(StarWarsProjectApplication.class, args);
	}
}