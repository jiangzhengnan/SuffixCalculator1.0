package com.whale.nangua.suffixcalculator10.bean;

/**
 * 运算符类，储存优先级和运算符
 *
 * @author nangua
 */
public class Operator {
    String value = "";

    // 优先级默认为-1
    float priority = -1;

    public Operator() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        if (value.equals("+") || (value.equals("-"))) {
            this.priority = 1;
        } else if (value.equals("*") || (value.equals("/"))) {
            this.priority = 2;
        } else if (value.equals("(") || (value.equals(")"))) {
            this.priority = 3;
        } else if (value.equals("=")) {
            this.priority = 0;
        }
    }

    public float getPriority() {
        return priority;
    }

    public void setPriority(float priority) {
        this.priority = priority;
    }
}