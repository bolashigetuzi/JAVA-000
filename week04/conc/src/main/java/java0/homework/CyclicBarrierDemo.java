package java0.homework;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        Result re = new Result();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(1,()->{
            System.out.println("结束主线程了："+re.result);
        });
        Thread thread = new Thread(()->{
            re.result=36;
            try {
                sum(re);
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
    private static int sum(Result result) throws InterruptedException {
        if(result.result == 36){
            result.result = fibo(result.result);
        }
        Thread.sleep(2000);
        System.out.println("拿到返回值："+result.result);
        return result.result;
    }

    private static int fibo(int a) {
        if ( a < 2){
            return 1;
        }
        return fibo(a-1) + fibo(a-2);
    }



}
