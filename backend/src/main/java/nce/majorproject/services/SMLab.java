package nce.majorproject.services;

import java.util.Scanner;

public class SMLab {

    public static void main(String[] args) {

        double a11, a21, a22, b1,b2, k11, k12, k21,k22,k31, k32, k41,k42;
        a11= -50; a21= -100000; a22= -2.15*10; b1=-a11;b2=-a21;
        double t=0, v1=0, v2=0;
        double h=0.05, e1=1.5;
        int i,k; int n = 10;
        for (i=1;i<=n;++i) {
            k11=h*((a11*v1)+e1);
            k12=h*((a21*v1)+ (a22*v2)+b2);
            k21=h* (a11*(v1+0.5*k11)+b1);
            k22=h*(a21*( v1+0.5*k11)+a22*(v2+0.5*k12)+b2);
            k31=h*(a11*(v1+0.5*k21)+b1);
            k32=h*(a21*(v1+0.5*k21)+a22*(v2+0.5*k22)+b2);
            k41=h*(a11*(v1+k31)+b1);
            k42=h*(a21*(v1+k31)+a22*(v2+k32)+b2);
            v1=v1+(k11+2*k21+2*k31+k41)/6;
            v2=v2+(k12+2*k22+2*k32+k42)/6;
            t=t+h;
            System.out.println("i = "+ i+"\nt = "+t+"\nv1 = "+v1+"\nv2 = "+v2+"");
        }
        System.out.println( "Final value: " + " t="+ t+" v1="+v1+" v2=" +v2);
//        System.out.println("any digit:");
//        Scanner sc = new Scanner(System.in);
//        double d = sc.nextInt();
    }

}
