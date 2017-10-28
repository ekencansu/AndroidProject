package com.example.cansu.gezgor;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    Map<String, String> places;

    public Constants(){
        places = new HashMap<>();
        addPlaces();
    }

    private void addPlaces(){
        places.put("atm", "ic_local_atm_white_24dp");
        places.put("bar", "ic_local_bar_white_24dp");
        places.put("book_store","ic_local_library_white_24dp");
        places.put("cafe", "ic_local_cafe_white_24dp");
        places.put("dentist", "ic_local_hospital_white_24dp");
        places.put("doctor", "ic_local_hospital_white_24dp");
        places.put("clothing_store", "ic_local_mall_white_24dp");
        places.put("florist", "ic_local_florist_white_24dp");
        places.put("food", "ic_local_dining_white_24dp");
        places.put("grocery_or_supermarket", "ic_local_grocery_store_white_24dp");
        places.put("train_station", "ic_directions_railway_white_24dp");
        places.put("school", "ic_map_white_24dp");
        places.put("university", "ic_map_white_24dp");
        places.put("hospital", "ic_local_hospital_white_24dp");
        places.put("library", "ic_local_library_white_24dp");
        places.put("pharmacy", "ic_local_pharmacy_white_24dp");
        places.put("post_office", "ic_map_white_24dp");
        places.put("restaurant", "ic_local_dining_white_24dp");
        places.put("shopping_mall", "ic_local_mall_white_24dp");
        places.put("subway_station", "ic_directions_subway_white_24dp");
        places.put("bank", "ic_trending_up_white_24dp");
        places.put("beauty_salon", "ic_mood_white_24dp");
        places.put("night_club", "ic_adjust_white_24dp");
        places.put("casino", "ic_adjust_white_24dp");
        places.put("fire_station", "ic_map_white_24dp");
        places.put("gym", "ic_map_white_24dp");
        places.put("mosque", "ic_map_white_24dp");
        places.put("museum", "ic_map_white_24dp");
        places.put("park", "ic_people_white_24dp");
        places.put("bus_station", "ic_map_white_24dp");
        places.put("pet_store", "ic_map_white_24dp");
        places.put("police", "ic_map_white_24dp");
    }
    public String[] places_list = {
            "atm",
            "bank",
            "bar",
            "beauty_salon",
            "book_store",
            "cafe",
            "casino",
            "clothing_store",
            "dentist",
            "doctor",
            "fire_station",
            "florist",
            "food",
            "gym",
            "hospital",
            "library",
            "mosque",
            "museum",
            "night_club",
            "park",
            "pet_store",
            "pharmacy",
            "police",
            "restaurant",
            "subway_station",
            "school",
            "university",
            "bus_station",
    };
}
