package com.nisum.delete;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by NIS1175m on 7/5/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AtpItemResponse {

    public String title;
    public AtpItemResponse(){

    }
}
