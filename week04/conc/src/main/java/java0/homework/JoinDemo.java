package java0.homework;

public class JoinDemo {
    public static void main(String[] args) throws InterruptedException {
        Result re = new Result();
        Thread thread = new Thread(()->{
            re.result=36;
            try {
                sum(re);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        thread.join();
        System.out.println("结束主线程了："+re.result);
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
