package ste.crypto.methods;

import java.util.List;

/**
 * Created by marklabenski on 15.08.17.
 */
public class MethodOptionDescription {
    public String type;
    public List<String> possibleValues;

    public MethodOptionDescription(String _type) {
        this.type = _type;
    }

    public MethodOptionDescription(String _type, List<String> _possibleValues) {
        this.type = _type;
        this.possibleValues = _possibleValues;
    }
}
