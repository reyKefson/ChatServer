package prog.kiev.ua.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import prog.kiev.ua.entity.Message;
import prog.kiev.ua.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Json {
    private Json() {}

    public static synchronized String listToJSON(List<String> list) {
        if (list.size() > 0) {
            Gson gson = new GsonBuilder().create();
            return gson.toJson(list.toArray());
        } else {
            return null;
        }
    }

    public static synchronized String messagesToJSON(List<Message> list, int from) {
        List<Message> res = new ArrayList<>();
        for (int i = from; i < list.size(); i++)
            res.add(list.get(i));

        if (res.size() > 0) {
            Gson gson = new GsonBuilder().create();
            return gson.toJson(res.toArray());
        } else {
            return null;
        }
    }

    public static synchronized User userFromJSON(String s) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(s, User.class);
    }

    public static String[] fromJSON(String s) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(s, String[].class);
    }
}
