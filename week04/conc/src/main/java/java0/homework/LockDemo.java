package java0.homework;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
    final static ReentrantLock lock = new ReentrantLock();
    final static Condition getResult = lock.newCondition();
    public static void main(String[] args) throws InterruptedException {
       Result re = new Result();
       Thread thread = new Thread(()-> {
           try {
               re.result=36;
               sum(re);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       });
       thread.start();
       printResult(re);
    }


    public static void printResult(Result result) throws InterruptedException {
       lock.lock();
       getResult.await();
       System.out.println("结束主线程了："+result.result);
       lock.unlock();
    }

    private static int sum(Result result) throws InterruptedException {
        lock.lock();
        if(result.result == 36){
            result.result = fibo(result.result);
        }
        Thread.sleep(2000);
        System.out.println("拿到返回值："+result.result);
        getResult.signal();
        lock.unlock();
        return result.result;
    }

    private static int fibo(int a) {
        if ( a < 2){
            return 1;
        }
        return fibo(a-1) + fibo(a-2);
    }
}
