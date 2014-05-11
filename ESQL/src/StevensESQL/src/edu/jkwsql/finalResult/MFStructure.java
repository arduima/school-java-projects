package edu.jkwsql.finalResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dimitrikoshkin on 4/19/14.
 * The main datastructure that will hold each tuple of aggregate data
 */
public class MFStructure {
    //    Simple select attributes corresponding to existing attributes
    private SelectAttributes selectAttributes;
    //    Aggregate attributes that will come from grouping variables or grouping attributes
    private SelectAttributes aggregateAttributes;

    public MFStructure(SelectAttributes selectAttributes, SelectAttributes aggregateAttributes) {
        this.selectAttributes = selectAttributes;
        this.aggregateAttributes = aggregateAttributes;
    }

    public SelectAttributes getSelectAttributes() {
        return selectAttributes;
    }

    public SelectAttributes getAggregateAttributes() {
        return aggregateAttributes;
    }
}

/* Map to hold the attributes
   For simple attributes the key will be the column name
   For aggregate attributes the key will be a numeric value 0-n, with 0 being the implicit attribute
*/

class SelectAttributes {
    private Map<String, IAttributesDO> attribute;

    SelectAttributes() {
        this.attribute = new HashMap<String, IAttributesDO>();
    }

    void add(String key, IAttributesDO attributeObj) {
        this.attribute.put(key, attributeObj);
    }

    Map<String, IAttributesDO> getAttribute() {
        return attribute;
    }
}



