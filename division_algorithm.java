package project_self.division_algorithm;
import java.util.Scanner;

public class division_algorithm {
    public static void main(String[] args) {
        System.out.println("输入第一个多项式f（X）");
        double[] f = way.input();
        System.out.println("输入第一个多项式g（X）");
        double[] g = way.input();
        way.division(f, g);
    }
}

class way {

    public static double[] input() {
        //System.out.println("请输入");
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入最高次");
        int max = scanner.nextInt();
        System.out.println("输入时按次数从小到大输入");
        double[] f = new double[max + 1];//用数组来接受多项式，数数组下标为次数，组元素为系数
        for (int i = 0; i < f.length; i++) {
            System.out.println("输入" + i + "次项系数");
            f[i] = scanner.nextDouble();
        }
        System.out.println("输入完成");
        System.out.println("本次输入的多项式为");
        printPolynomial(f);
        return f;
    }

    public static double[] get_1(double[] f, double[] g, String ret) {
        boolean n = true;
        double[] q = new double[f.length - g.length + 1];
        do {
            if (f.length >= g.length) {//判断高次多项式
                q[f.length - g.length] = f[f.length - 1] / g[g.length - 1];//计算q（x）
                int poor = f.length - g.length;
                g = addArray(g, poor);//数组扩容
                double[] g_new = copyArray(g);//数组复制
                for (int i = 0; i < g_new.length; i++) {
                    if (i == 0 && poor == 0) {
                        g[0] = q[poor] * g_new[0];
                    } else if (i == 0) {
                        g[0] = 0;
                    } else if (i - poor >= 0) {
                        g[i] = g_new[i - poor] * q[poor];//更新g，也就是g*q
                    }
                }
                n = false;
                //return q;
            } else {//如果g为高次，交换f和g
                double[] temp = f;
                f = g;
                g = temp;
            }
        } while (n);
        double[] r = new double[g.length - (f.length - g.length)];
        for (int i = 0; i < r.length; i++) {
            r[i] = f[i] - g[i];
        }
//        System.out.println("本次带余除法计算结果q（x）为");
//        printPolynomial(q);
//        printPolynomial(g);
//        printPolynomial(f);
//        System.out.println("本次带余除法的余式r（x）为");
//        printPolynomial(r);
        if (ret.equals("q")) {
            return q;
        } else {
            return r;
        }
    }

    public static double[] get_2(double[] f, double[] g, String ret) {
        double[][] q_x = new double[f.length - g.length + 1][];
        double[] r;
        //q_x[f.length- g.length-1] = way.getQ(f,g);
        for (int i = q_x.length - 1; i >= 0; i--) {
            q_x[i] = way.get_1(f, g, "q");
            f = way.get_1(f, g, "r");
            f = checkArray(f);
            g = checkArray(g);
        }
        r = f;
        double[] q = new double[q_x.length];
        for (int i = 0; i < q_x.length; i++) {
            q[i] = q_x[i][i];
        }
        System.out.println("本次带余除法计算结果q（x）为");
        printPolynomial(q);
        System.out.println("本次带余除法的余式r（x）为");
        printPolynomial(r);
        if (ret.equals("q")) {
            return q;
        } else {
            return r;
        }
    }

    public static double[] addArray(double[] f, int poor) {
        double[] f_new = new double[f.length + poor];
        for (int i = 0; i < f.length; i++) {
            f_new[i] = f[i];
        }
        return f_new;
    }

    public static double[] copyArray(double[] f) {
        double[] f_new = new double[f.length];
        for (int i = 0; i < f.length; i++) {
            f_new[i] = f[i];
        }
        return f_new;
    }

    public static double[] checkArray(double[] f) {
        int num = 0;
        for (int i = f.length-1; i >= 0; i--) {
            if(f[i]==0){
                num++;
            }
        }
        if (num != 0) {
            double[] f_new = new double[f.length - num];
            for (int i = 0; i < f_new.length; i++) {
                f_new[i] = f[i];
            }
            return f_new;
        } else {
            return f;
        }
    }

    public static double[] checkInt(double[] f) {
        for (int i = 0; i < f.length; i++) {
            if (Math.abs(Math.ceil(f[i]) - f[i]) < 0.00000000001) {
                f[i] = Math.ceil(f[i]);
            } else if (Math.abs(Math.floor(f[i]) - f[i]) < 0.00000000001) {
                f[i] = Math.floor(f[i]);
            }
        }
        return f;
    }

    public static double[] change1(double[] f) {
        if (f[f.length - 1] == 1) {
            return f;
        }
        double AA = f[f.length - 1] / 1;
        for (int i = 0; i < f.length; i++) {
            f[i] = f[i] / AA;
        }
        return f;
    }

    public static void printPolynomial(double[] f) {//打印输入和计算出来的多项式
        for (int i = f.length - 1; i >= 0; i--) {
            if (i != 0) {
                if (f[i] > 0) {
                    System.out.print(f[i] + "x^" + i + "+");
                } else if (f[i] < 0) {
                    System.out.print("(" + f[i] + "x^" + i + ")+");
                }
            } else {
                System.out.println(f[i]);
            }
        }
    }

    public static void division(double[] f, double[] g) {
        double[] r = get_2(f, g, "r");
        while (r.length != 1) {
            double[] q = get_2(f, g, "q");
            r = get_2(f, g, "r");
            f = checkArray(g);
            g = checkArray(r);
        }
        System.out.println("最大公因式为");
        printPolynomial(change1(checkInt(f)));
    }
/*
1.最大公因式 系数为一              --完成
2.最大公因式最后取整               --完成
3.优化代码，减少代码数量            --基本完成
 */
}