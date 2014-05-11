package edu.jkwsql.finalResult;

/**
 * Created by Dimitri on 4/23/14.
 * Helper classes for the MF Structure
 */

// An interface to hold value datastrucutre for the attributes
interface IAttributesDO<T> {
    String getDataType();

    T getValue();
}

// Class for the simple attributes
class SimpleAttributesDO<T> implements IAttributesDO<T> {
    //    The Java data type: String, int, etc.
    private String dataType;
    //    Will hold the value directly from the db
    private T value;

    SimpleAttributesDO(String dataType, T value) {
        this.dataType = dataType;
        this.value = value;
    }

    @Override
    public String getDataType() {
        return dataType;
    }

    @Override
    public T getValue() {
        return value;
    }
}

// Class for the aggregate attributes
class AggregateAttributesDO<T> implements IAttributesDO<T> {
    //    The attribute name from the db, the key is a numeric value and not the attribute name as it is for simple attributes
    private String attributeName;
    //    The Java data type: String, int, etc.
    private String dataType;
    //    The aggregate function: sum, count, max, min, avg
    private String aggregateType;
    //    The calcualted value of the aggregate function
    private T value;
    //    A helper counter used for avg function
    private int helperCounter;

    AggregateAttributesDO(String attributeName, String dataType, String aggregateType, T value) {
        this.dataType = dataType;
        this.attributeName = attributeName;
        this.aggregateType = aggregateType;
        this.value = value;
    }

    @Override
    public String getDataType() {
        return dataType;
    }

    public String getAttributeName() {
        return attributeName;
    }

    @Override
    public T getValue() {
        return value;
    }

    public int getHelperCounter() {
        return helperCounter;
    }
}
