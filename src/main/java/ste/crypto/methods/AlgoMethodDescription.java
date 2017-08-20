package ste.crypto.methods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marklabenski on 15.08.17.
 */
public class AlgoMethodDescription {
    public List<String> parameters;
    public String returns;

    public AlgoMethodDescription(List<String> _parameters, String _returns) {
        this.parameters = _parameters;
        this.returns = _returns;
    }
}

