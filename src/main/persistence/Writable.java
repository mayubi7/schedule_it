package persistence;

import org.json.JSONObject;

public interface Writable {
    // Represents a writer that writes JSON representation of schedule to file

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
