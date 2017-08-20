package ste.crypto.methods;

import java.util.ArrayList;
import java.util.List;

/**
 * describes the parameters and return values of a cryptographic method
 *
 * @author Mark Labenski
 */
public class AlgoMethodDescription {
    /**
     * The parameters needed to provide by a consumer
     */
    public List<String> parameters;

    /**
     * The name of the return value
     */
    public String returns;

    /**
     * Create a description
     *
     * used in the static body inside of concrete implementations
     *
     * @param _parameters method parameters needed
     * @param _returns return value name
     */
    public AlgoMethodDescription(List<String> _parameters, String _returns) {
        this.parameters = _parameters;
        this.returns = _returns;
    }
}

