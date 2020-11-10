package java0.homework;

public class SynchronizedDemo {
   public static void main(String[] args) {
       Result re = new Result();
       Thread thread1 = new Thread(()-> {
            synchronized(re){
                re.result=36;
                try {
                    sum(re);
                    re.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
       });
       Thread thread2 = new Thread(()-> {
           synchronized(re){
               try {
                   re.wait(2000);
                   System.out.println("结束主线程了："+re.result);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       });
       thread1.start();
       thread2.start();
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
