package com.example.StarWarsProject;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StarWarsProjectApplicationTest {

    @Test
    void getPlanetAndResidentsTatooine() {
        String planetName = "Tatooine";
        String jsonFile = "{\"count\":1,\"next\":null,\"previous\":null,\"results\":[{\"name\":\"Tatooine\",\"rotation_period\":\"23\",\"orbital_period\":\"304\",\"diameter\":\"10465\",\"climate\":\"arid\",\"gravity\":\"1 standard\",\"terrain\":\"desert\",\"surface_water\":\"1\",\"population\":\"200000\",\"residents\":[\"https://swapi.dev/api/people/1/\",\"https://swapi.dev/api/people/2/\",\"https://swapi.dev/api/people/4/\"],\"films\":[\"https://swapi.dev/api/films/1/\",\"https://swapi.dev/api/films/3/\",\"https://swapi.dev/api/films/4/\",\"https://swapi.dev/api/films/5/\",\"https://swapi.dev/api/films/6/\"],\"created\":\"2014-12-09T13:50:49.641000Z\",\"edited\":\"2014-12-20T20:58:18.411000Z\",\"url\":\"https://swapi.dev/api/planets/1/\"}]}";
        JsonNode result = StarWarsProjectApplication.getPlanetAndResidents((planetName));
        assertEquals(planetName, result.get("planet").asText());
        assertEquals(10, result.get("residents").asInt());
    }
    @Test
    void getPlanetAndResidentsHoth() {
        String planetName = "Hoth";
        String jsonFile = "{\"count\":1,\"next\":null,\"previous\":null,\"results\":[{\"name\":\"Hoth\",\"rotation_period\":\"23\",\"orbital_period\":\"549\",\"diameter\":\"7200\",\"climate\":\"frozen\",\"gravity\":\"1.1 standard\",\"terrain\":\"tundra, ice caves, mountain ranges\",\"surface_water\":\"100\",\"population\":\"unknown\",\"residents\":[],\"films\":[\"https://swapi.dev/api/films/2/\"],\"created\":\"2014-12-10T11:39:13.934000Z\",\"edited\":\"2014-12-20T20:58:18.423000Z\",\"url\":\"https://swapi.dev/api/planets/4/\"}]}";
        JsonNode result = StarWarsProjectApplication.getPlanetAndResidents((planetName));
        assertEquals(planetName, result.get("planet").asText());
        assertEquals(0, result.get("residents").asInt());
    }
    @Test
    void getPlanetAndResidentsNoPlanet() {
        String planetName = "NoPlanet";
        JsonNode result = StarWarsProjectApplication.getPlanetAndResidents((planetName));
        assertEquals("This planet isn't in the database", result.get("planet").asText());
        assertEquals(-1, result.get("residents").asInt());
    }
}



