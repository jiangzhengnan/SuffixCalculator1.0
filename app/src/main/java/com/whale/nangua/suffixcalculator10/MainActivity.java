package com.whale.nangua.suffixcalculator10;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.whale.nangua.suffixcalculator10.bean.Operator;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener{
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private ImageView changge;
    private boolean ifClear = false;//是否清屏的标识
    private  boolean ischanged = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_main);
        //设置沉浸式标题栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            initView();
        }
    }

    private void initView() {
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.C).setOnClickListener(this);
        findViewById(R.id.btnjia).setOnClickListener(this);
        findViewById(R.id.btnjian).setOnClickListener(this);
        findViewById(R.id.btncheng).setOnClickListener(this);
        findViewById(R.id.btnchu).setOnClickListener(this);
        findViewById(R.id.btnequls).setOnClickListener(this);
        findViewById(R.id.btndian).setOnClickListener(this);
        findViewById(R.id.delete).setOnClickListener(this);
        findViewById(R.id.btnleftkuo).setOnClickListener(this);
        findViewById(R.id.btnrightkuo).setOnClickListener(this);

        changge = (ImageView) findViewById(R.id.changge);
        changge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (ischanged == false) {
                        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "rotation", 0, -155, -135);
                        animator.setDuration(250);
                        animator.start();
                        ischanged = true;
                        tv3.setVisibility(View.INVISIBLE);
                    } else {
                        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "rotation", -135, -155, 0);
                        animator.setDuration(250);
                        animator.start();
                        ischanged = false;
                        tv3.setVisibility(View.VISIBLE);
                    }

                }
        });
    }

    @Override
    public void onClick(View v) {
        if (ifClear) {
            tv1.setText("");
            tv2.setText("");
            tv3.setText("");
            ifClear = false;
        }
        switch (v.getId()) {
            case R.id.btn0:
                tv1.append("0");
                break;
            case R.id.btn1:
                tv1.append("1");
                break;
            case R.id.btn2:
                tv1.append("2");
                break;
            case R.id.btn3:
                tv1.append("3");
                break;
            case R.id.btn4:
                tv1.append("4");
                break;
            case R.id.btn5:
                tv1.append("5");
                break;
            case R.id.btn6:
                tv1.append("6");
                break;
            case R.id.btn7:
                tv1.append("7");
                break;
            case R.id.btn8:
                tv1.append("8");
                break;
            case R.id.btn9:
                tv1.append("9");
                break;
            case R.id.btndian:
                tv1.append(".");
                break;
            case R.id.btnleftkuo:
                tv1.append(" ( ");
                break;
            case R.id.btnrightkuo:
                tv1.append(" ) ");
                break;
            case R.id.delete:
                String temp =  tv1.getText().toString();
                if(!temp.equals("")) {
                    temp = temp.substring(0,temp.length()-1);
                }
                tv1.setText(temp);
                break;
            case R.id.C:
                tv1.setText("");
                tv2.setText("");
                tv3.setText("");
                break;
            case R.id.btnjia:
                tv1.append(" + ");
                break;
            case R.id.btnjian:
                tv1.append(" - ");
                break;
            case R.id.btncheng:
                tv1.append(" * ");
                break;
            case R.id.btnchu:
                tv1.append(" / ");
                break;
            case R.id.btnequls:
                ifClear = true;
                tv1.append(" = ");
                String biaodashi = tv1.getText().toString();
                //清除表达式中的多个空格

                float result =Scalculation(  clear(biaodashi));
                tv2.setText(result + "");
                break;
        }
    }

    private   String clear(String biaodashi) {
        char[] temparray = biaodashi.toCharArray();
        ArrayList<Character> listarray = new ArrayList<>();
        for (int i = 0;i<temparray.length;i++) {
            listarray.add(temparray[i]);
        }
        char before = 0;
        char now = 0;
        int length = listarray.size();
        int j = 0;
        while (j<length&&length!=0) {
            now = listarray.get(j);
            System.out.println(now);
            if (now==' '&&before==' ') {
                listarray.remove(j);
                j--;
                length--;
            } else {
                before = now;
            }
            j++;
        }
        String result = "";
        for (int i = 0; i < listarray.size(); i++) {
            result += listarray.get(i);
        }
        return result;
    }

    /**
     * 中缀表达式转化为后缀表达式
     * 然后计算结果
     * @param input
     */
    private   float Scalculation(String input) {
        // 运算符栈顶元素
        Operator topOperator = new Operator();
        // 得到输入的表达式并储存在数组中
        String[] expression = input.split(" ");

        @SuppressWarnings("rawtypes")
        MyStack<?> outputStack = new MyStack(); // 输出栈

        @SuppressWarnings("rawtypes")
        MyStack<?> operatorStack = new MyStack(); // 运算符栈

        @SuppressWarnings("rawtypes")
        MyStack<?> resultStack = new MyStack<>();//结果栈
        // 储存（的位置
        float leftBracketsPosition = -1;
        // 运算符栈元素位置（从底往上依次为0,1,2...）
        float osCount = -1;
        // 储存栈顶以下的一个元素


        // 中缀转换为后缀
        for (int i = 0; i < expression.length; i++) {

            // 区分是数字还是运算符
            // 如果是数字
            try {
                float t = Float.valueOf(expression[i]);
                outputStack.push(t); // 如果是数字则推入输出栈

                // 如果是运算符
            } catch (Exception e) {

                // 储存运算符的Operator
                Operator storageOperator = new Operator();
                // 如果是运算符则区分运算符优先级然后推入运算符栈
                switch (expression[i]) {
                    case "*":
                        storageOperator.setValue("*");
                        break;
                    case "/":
                        storageOperator.setValue("/");
                        break;
                    case "+":
                        storageOperator.setValue("+");
                        break;
                    case "-":
                        storageOperator.setValue("-");
                        break;
                    case "(":
                        storageOperator.setValue("(");
                        // 记录（的位置
                        break;
                    case ")":
                        storageOperator.setValue(")");
                        break;
                    case "=":
                        storageOperator.setValue("=");
                }

                // 如果第二个元素不为空
                if (osCount != -1) {
                    if (storageOperator.getValue().endsWith("=")) {
                        while (osCount != -1) {
                            outputStack.push(operatorStack.pop());
                            osCount--;
                        }
                    } else if (storageOperator.getValue().endsWith("(")) {
                        // 推入运算符栈
                        operatorStack.push(storageOperator);
                        osCount++;
                        leftBracketsPosition = osCount;
                        topOperator = (Operator) operatorStack.top();

                    } else if (storageOperator.getValue().endsWith(")")) {
                        while ((osCount != leftBracketsPosition) && (osCount >= 1)) {
                            outputStack.push(operatorStack.pop());
                            osCount--;
                        }
                        // 再把(号踢出去
                        operatorStack.pop();
                        osCount--;
                        if (osCount != -1) {
                            topOperator = (Operator) operatorStack.top();
                        }

                    } else if ((topOperator.getPriority() >= storageOperator.getPriority())) {
                        while (((topOperator.getPriority() >= storageOperator.getPriority()))) {

                            if (topOperator.getValue().equals("(")) {
                                // 推入运算符栈
                                operatorStack.push(storageOperator);
                                osCount++;
                                topOperator = (Operator) operatorStack.top();
                                break;
                            } else {
                                if (osCount == -1) { // 如果已经没有元素
                                    // 推入运算符栈
                                    operatorStack.push(storageOperator);
                                    osCount++;
                                    topOperator = (Operator) operatorStack.top();
                                    break;
                                } else {
                                    // 将栈顶元素推入输出栈
                                    outputStack.push(topOperator);
                                    // 在operator中踢出已经推入输出栈的栈顶并设置新的栈顶
                                    operatorStack.pop();
                                    osCount--;
                                    if (osCount!=-1) {
                                        topOperator = (Operator) operatorStack.top();
                                    } else {
                                        // 推入运算符栈
                                        operatorStack.push(storageOperator);
                                        osCount++;
                                        topOperator = (Operator) operatorStack.top();
                                        break;
                                    }
                                }
                            }
                        }


                    } else if ((topOperator.getPriority() <= storageOperator.getPriority())) {
                        // 推入运算符栈
                        operatorStack.push(storageOperator);
                        osCount++;
                        topOperator = (Operator) operatorStack.top();

                    }
                } else {
                    // 第一次推入运算符栈
                    operatorStack.push(storageOperator);
                    osCount++;
                    topOperator = (Operator) operatorStack.top();

                }

            }
        }
        //到这里为止已经完成了中缀到后缀的转化，现在开始执行计算结果过程
        tv3.setText("后缀表达式："+outputStack.show());

        float result = 0;
        outputStack.show();
        //到这里为止已经完成了中缀到后缀的转化，现在开始执行计算结果过程
        //这里要先讲outputStack这个栈里面的数组反序
        int arraylength = outputStack.array.length -1;
        Object[] temparray = new Object[arraylength+1];
        for (int i = 0; i <= arraylength; i++) {
            temparray[arraylength - i] = outputStack.array[i];
        }
        outputStack.array = temparray;
        while (outputStack.count!=0) {
            Object value = new Object();
            value = outputStack.pop();
            if (value instanceof Operator) {
                float a = 0;
                float b = 0;
                float c = 0;
                b = (float) resultStack.pop();
                a = (float) resultStack.pop();
                switch (((Operator) value).getValue()) {
                    case "+":
                        c = a + b;
                        break;
                    case "-":
                        c = a - b;
                        break;
                    case "*":
                        c = a * b;
                        break;
                    case "/":
                        c = a / b;
                        break;
                }
                resultStack.push(c);
            } else {
                resultStack.push(value);
            }
        }
        result =   (float) resultStack.pop();
        return result;
    }

    /**
     * 数组实现的栈
     *
     * @author nangua
     */
    static class MyStack<T> {
        MyStack() {
        }

        Object[] array = new Object[1];
        // 数组元素个数
        int count = 0;
        // 栈顶元素
        Object topOfStack = "-1";

        private String  show() {
            String result ="";
            Object o = new Object();
            for (int i = 0; i < array.length; i++) {
                o = array[i];
                if (o instanceof Operator) {
                    Operator ceshi = (Operator) o;
                    if ((!ceshi.getValue().equals("(")) && (!ceshi.getValue().equals(")"))) {
                        result += ceshi.getValue() + " ";
                    }

                } else {
                    result += o + " ";
                }
            }
            return result;
        }

        /**
         * push进栈
         *
         * @param num
         */
        private void push(Object num) {
            // 如果数组为空
            if (count == 0) {
                array[count] = num;
            } else {
                // 如果数组不为空，扩充数组
                Object[] newarray = new Object[count + 1];
                // 复制数组
                for (int i = 0; i < array.length; i++) {
                    newarray[i] = array[i];
                }
                array = newarray;
                array[count] = num;
            }
            count++;
            topOfStack = num;
        }

        /**
         * 找出栈顶的下一个元素
         */
        private Object next() {
            // 如果总元素大于或等于2个
            if (count - 1 >= 0) {
                return array[count - 1];
            }
            return "无元素可next";
        }

        /**
         * 出栈
         *
         * @return
         */
        private Object pop() {
            // 如果数组为空
            if (count == 0) {
                try {
                    throw new Exception("栈为空时不能pop");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "无元素可pop";
            } else {
                if (count == 1) {
                    topOfStack = array[0];
                    count = 0;
                } else {
                    topOfStack = array[count - 1];
                    // 缩小数组
                    Object[] newarray = new Object[count - 1];
                    for (int j = 0; j < newarray.length; j++) {
                        newarray[j] = array[j];
                    }
                    array = newarray;
                    count--;
                }
                return topOfStack;
            }
        }

        /**
         * 显示栈顶元素
         */
        private Object top() {
            // 如果数组为空
            if (count == 0) {
                try {
                    throw new Exception("栈为空时不能top");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "无元素可top";
            } else {
                if (count == 1) {
                    topOfStack = array[0];
                } else {
                    topOfStack = array[count - 1];
                }
                return topOfStack;
            }
        }
    }
}
