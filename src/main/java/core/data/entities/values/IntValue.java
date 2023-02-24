package core.data.entities.values;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(typeName = "int")
public class IntValue implements ValueI {

    public final int Max;
    public final int Min;
    public int value;

    public IntValue(int max, int min, int v) {
        Max = max;
        Min = min;
        value = v;
    }

    public IntValue(int v) {
        Max = Integer.MAX_VALUE;
        Min = Integer.MIN_VALUE;
        value = v;
    }
}
