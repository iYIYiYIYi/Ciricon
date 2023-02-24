package core.data.entities.values;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(typeName = "float")
public class FloatValue {

    public float value;
    public final float Max;
    public final float Min;

    public FloatValue(float value, float max, float min) {
        this.value = value;
        Max = max;
        Min = min;
    }

    public FloatValue(float value) {
        this.value = value;
        this.Max = Float.MAX_VALUE;
        this.Min = Float.MIN_VALUE;
    }
}
