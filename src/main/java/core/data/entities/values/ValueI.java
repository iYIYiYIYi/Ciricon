package core.data.entities.values;

import com.alibaba.fastjson.annotation.JSONType;

import java.io.Serializable;

@JSONType(seeAlso = {ColorValue.class, IntValue.class})
public interface ValueI extends Serializable {
}
