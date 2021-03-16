package persistence;

import org.json.JSONObject;

// Represents a writer that writes JSON representation of schedule to file
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
