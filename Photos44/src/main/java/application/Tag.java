package application;

import java.io.Serial;
import java.io.Serializable;

public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String value;

    public Tag(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return name + ":" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Tag)) {
            return false;
        }
        Tag other = (Tag) o;
        return this.name.equals(other.getName()) && this.value.equals(other.getValue());
    }
}
