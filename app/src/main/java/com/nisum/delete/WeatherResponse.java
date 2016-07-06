package com.nisum.delete;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by NIS1175m on 7/5/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    public String message;
    public List<DayWeatherResponse> list;
    public WeatherResponse(){

    }
}
