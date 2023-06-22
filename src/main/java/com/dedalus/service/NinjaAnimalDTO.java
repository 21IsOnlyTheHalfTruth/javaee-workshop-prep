package com.dedalus.service;

import java.util.HashMap;
import java.util.List;

public class NinjaAnimalDTO {
    public Integer id; // nullable
    public String name;
    public HashMap<String, String> taxonomy;
    public HashMap<String, String> characteristics;

    public List<String> locations;

}
