package ste.crypto.methods;

import java.util.List;

/**
 * description of all options one algorithm needs for its methods
 *
 * @author Mark Labenski
 */
public class MethodOptionDescription {
    /**
     * the dataType
     */
    public String type;

    /**
     * list of possible Values if there are restrictions
     */
    public List<String> possibleValues;

    /**
     * constructor for option without restrictions on possible values
     *
     * @param _type data type of the option
     */
    public MethodOptionDescription(String _type) {
        this.type = _type;
    }

    /**
     * constructor for option with restrictions on possible values
     *
     * @param _type data type of the option
     * @param _possibleValues list of possible values
     */
    public MethodOptionDescription(String _type, List<String> _possibleValues) {
        this.type = _type;
        this.possibleValues = _possibleValues;
    }
}
