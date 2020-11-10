package java0.homework;

public class WhileDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(()->{
            Result.result=sum();
        });
        thread.start();
        while(Result.result==0);
        System.out.println("结束主线程了："+Result.result);
    }

    private static int sum(){
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2){
            return 1;
        }
        return fibo(a-1) + fibo(a-2);
    }
}
