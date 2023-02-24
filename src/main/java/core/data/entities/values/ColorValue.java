package core.data.entities.values;

import com.alibaba.fastjson.annotation.JSONType;

@JSONType(typeName = "color")
public class ColorValue implements ValueI{

    public String value;

    public ColorValue(String value) {
        this.value = value;
    }

}
