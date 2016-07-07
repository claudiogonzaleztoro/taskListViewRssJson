package com.nisum.delete;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by NIS1175m on 7/5/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class AtpResponse {

    public String status;
    public List<AtpItemResponse> items;
    public AtpResponse(){

    }
}
